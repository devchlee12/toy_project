package chanho.remoteordersystem.Service;

import chanho.remoteordersystem.dto.EventPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class SseEmitterService {
    private final Map<String, SseEmitter> emitterMap = new ConcurrentHashMap<>();
    private static final long TIMEOUT = 60 * 1000;
    private static final long RECONNECTION_TIMEOUT = 1000L;

    public SseEmitter subscribe(String id){
        SseEmitter emitter = createEmitter();
        emitter.onTimeout(() -> {
            log.info("server sent event timed out : id={}", id);
            emitter.complete();
        });
        emitter.onError(e -> {
            log.info("server sent event error occurred : id={}, message={}", id, e.getMessage());
            emitter.complete();
        });
        emitter.onCompletion(() -> {
            if (emitterMap.remove(id) != null) {
                log.info("server sent event removed in emitter cache: id={}", id);
            }

            log.info("disconnected by completed server sent event: id={}", id);
        });
        emitterMap.put(id,emitter);
        try{
            SseEmitter.SseEventBuilder event = SseEmitter.event()
                    .name("connect")
                    .id(String.valueOf("id-1"))
                    .data("connected")
                    .reconnectTime(RECONNECTION_TIMEOUT);
            emitter.send(event);
        } catch (IOException e){
            log.error("failure send media position data, id={}, {}", id, e.getMessage());
        }
        return emitter;
    }

    public void broadcast(EventPayload eventPayload, String uid) {
        emitterMap.forEach((id, emitter) -> {
            try {
                if (uid.equals(id)) {
                    emitter.send(SseEmitter.event()
                            .name("event")
                            .id("broadcast event")
                            .reconnectTime(RECONNECTION_TIMEOUT)
                            .data(eventPayload, MediaType.APPLICATION_JSON));
                    log.info("sended notification, id={}, payload={}", id, eventPayload);
                }
            } catch (IOException e) {
                //SSE 세션이 이미 해제된 경우
                log.error("fail to send emitter id={}, {}", id, e.getMessage());
            }
        });
    }

    private SseEmitter createEmitter() {
        return new SseEmitter(TIMEOUT);
    }
}
