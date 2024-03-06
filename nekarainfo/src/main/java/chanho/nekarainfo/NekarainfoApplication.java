package chanho.nekarainfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NekarainfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(NekarainfoApplication.class, args);
	}
	
}
