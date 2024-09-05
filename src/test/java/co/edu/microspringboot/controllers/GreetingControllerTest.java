package co.edu.microspringboot.controllers;

import co.edu.microspringboot.annotations.RequestParam;
import co.edu.microspringboot.annotations.RestController;
import co.edu.microspringboot.annotations.GetMapping;
import co.edu.microspringboot.classes.Greeting;
import org.junit.Test;
import static org.junit.Assert.*;

@RestController
public class GreetingControllerTest {

    @Test
    public void testGreeting() {
        GreetingController controller = new GreetingController();
        Greeting result = controller.greeting("John");
        assertEquals("Hola John", result.getContent());
    }

    @Test
    public void testGreetingDefault() {
        GreetingController controller = new GreetingController();
        Greeting result = controller.greeting(null);
        assertEquals("Hello World", result.getContent());
    }
}

