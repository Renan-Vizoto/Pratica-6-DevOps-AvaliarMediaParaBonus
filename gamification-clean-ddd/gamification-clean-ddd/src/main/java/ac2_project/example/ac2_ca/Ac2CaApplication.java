package ac2_project.example.ac2_ca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;

@SpringBootApplication
@EnableKafka
@EnableRabbit
public class Ac2CaApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ac2CaApplication.class, args);
    }
}
