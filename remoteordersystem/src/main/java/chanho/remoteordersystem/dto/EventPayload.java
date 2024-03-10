package chanho.remoteordersystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EventPayload(@JsonProperty("event") String event) {
}
