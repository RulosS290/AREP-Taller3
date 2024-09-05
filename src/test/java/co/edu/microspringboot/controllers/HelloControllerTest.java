package co.edu.microspringboot.controllers;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class HelloControllerTest {

    private HelloController controller = new HelloController();

    @Test
    public void testIndex() {
        String result = controller.index();
        assertEquals("Greetings from MicroSpring Boot!", result);
    }

    @Test
    public void testHello() {
        String result = controller.hello();
        assertEquals("Hello World!", result);
    }

    @Test
    public void testGetHtmlPage() throws IOException {
        byte[] expected = Files.readAllBytes(Paths.get("target/classes/webroot/index.html"));
        byte[] result = controller.getHtmlPage();
        assertArrayEquals(expected, result);
    }

    @Test
    public void testGetImage() throws IOException {
        byte[] expected = Files.readAllBytes(Paths.get("target/classes/webroot/png.png"));
        byte[] result = controller.getImage();
        assertArrayEquals(expected, result);
    }
}


