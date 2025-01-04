package paagbi.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}

@Component
class MongoDBConnectionHandler {

    @EventListener(ApplicationFailedEvent.class)
    public void handleApplicationFailedEvent(ApplicationFailedEvent event) {
        Throwable cause = event.getException();
        if (cause.getMessage().contains("Mongo")) {
            System.err.println("ERROR: Ezin izan da mongoDB datu-basera konektatu");
            System.err.println("Zehaztasunak: " + cause.getMessage());
        }
    }
}
