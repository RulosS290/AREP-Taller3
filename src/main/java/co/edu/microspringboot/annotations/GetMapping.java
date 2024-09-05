package co.edu.microspringboot.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Annotation to map HTTP GET requests to handler methods.
 * 
 * - `value()`: URL path to handle with a GET request.
 */
@Target(ElementType.METHOD) 
@Retention(RetentionPolicy.RUNTIME) 
public @interface GetMapping {
    String value();
}

