package com.avaliarMedia.avaliar_media_para_bonus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class AvaliarMediaParaBonus {

	public static void main(String[] args) {
		SpringApplication.run(AvaliarMediaParaBonus.class, args);
	}
}
