package co.edu.microspringboot.controllers;

import java.util.concurrent.atomic.AtomicLong;
import co.edu.microspringboot.annotations.*;
import co.edu.microspringboot.classes.Greeting;

/**
 * Controller that handles requests for greeting messages.
 */
@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    /**
     * Handles GET requests to "/greeting" and returns a Greeting message.
     * 
     * @param name The name to include in the greeting. Defaults to "World" if not provided.
     * @return A Greeting object with the message "Hola {name}"
     */
    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        if(name == null){
            return new Greeting("Hello World");
        }else{
        return new Greeting("Hola " + name);
        }
    }
}
