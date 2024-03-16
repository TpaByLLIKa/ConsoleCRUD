package com.ais.consolecrud;

import com.ais.consolecrud.console.MainMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class ConsoleCrudApplication implements CommandLineRunner {

	private final MainMenu mainMenu;
	public static void main(String[] args) {
		SpringApplication.run(ConsoleCrudApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mainMenu.run();
	}
}
