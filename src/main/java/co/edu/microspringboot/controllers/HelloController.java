package co.edu.microspringboot.controllers;

import co.edu.microspringboot.annotations.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from MicroSpring Boot!";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    @GetMapping("/greet")
    public String greet(@RequestParam("name") String name) {
        return "Hello, " + name + "!";
    }

    @GetMapping("/index")
    public byte[] getHtmlPage() throws IOException {
        Path path = Paths.get("target/classes/webroot/index.html");
        return Files.readAllBytes(path);
    }

    @GetMapping("/image")
    public byte[] getImage() throws IOException {
        Path path = Paths.get("src/main/resources/webroot/png.png");
        return Files.readAllBytes(path);
    }
}
