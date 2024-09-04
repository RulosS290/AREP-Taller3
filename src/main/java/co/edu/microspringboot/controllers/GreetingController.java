package co.edu.microspringboot.controllers;

import java.util.concurrent.atomic.AtomicLong;

import co.edu.microspringboot.annotations.*;
import co.edu.microspringboot.classes.Greeting;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        String nameS = greeting.getContent();
		return "Hola " + nameS;
	}
}




