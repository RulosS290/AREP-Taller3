package co.edu.microspringboot.controllers;

import co.edu.microspringboot.annotations.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Controller that handles requests for various endpoints.
 */
@RestController
public class HelloController {

    /**
     * Handles requests to the root URL ("/") and returns a greeting message.
     * 
     * @return A greeting message as a String
     */
    @RequestMapping("/")
    public String index() {
        return "Greetings from MicroSpring Boot!";
    }

    /**
     * Handles GET requests to "/hello" and returns a simple hello message.
     * 
     * @return A hello message as a String
     */
    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    /**
     * Handles GET requests to "/index" and returns the contents of the HTML file.
     * 
     * @return The contents of "index.html" as a byte array
     * @throws IOException If the file cannot be read
     */
    @GetMapping("/index")
    public byte[] getHtmlPage() throws IOException {
        Path path = Paths.get("target/classes/webroot/index.html");
        return Files.readAllBytes(path);
    }

    /**
     * Handles GET requests to "/image" and returns the contents of the PNG image file.
     * 
     * @return The contents of "png.png" as a byte array
     * @throws IOException If the file cannot be read
     */
    @GetMapping("/image")
    public byte[] getImage() throws IOException {
        Path path = Paths.get("src/main/resources/webroot/png.png");
        return Files.readAllBytes(path);
    }
}