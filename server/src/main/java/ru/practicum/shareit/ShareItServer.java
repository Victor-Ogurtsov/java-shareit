package ru.practicum.shareit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShareItServer {

	public static void main(String[] args) {
		//TimeZone.setDefault(TimeZone.getTimeZone("GMT+03:00"));
		SpringApplication.run(ShareItServer.class, args);
	}

}
