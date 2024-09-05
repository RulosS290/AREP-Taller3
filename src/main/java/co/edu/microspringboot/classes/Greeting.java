package co.edu.microspringboot.classes;

/**
 * Represents a greeting message.
 */
public class Greeting {
    private String content;

    /**
     * Constructs a new Greeting with the specified content.
     * 
     * @param content The content of the greeting
     */
    public Greeting(String content) {
        this.content = content;
    }

    /**
     * Gets the content of the greeting.
     * 
     * @return The content of the greeting
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the greeting.
     * 
     * @param content The new content of the greeting
     */
    public void setContent(String content) {
        this.content = content;
    }
}


