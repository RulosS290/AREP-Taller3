package co.edu.microspringboot.annotations;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Annotation to mark a class as a REST controller.
 * Classes annotated with `@RestController` can handle HTTP requests
 * and return data, typically in JSON format.
 */
@Target(ElementType.TYPE) 
@Retention(RetentionPolicy.RUNTIME) 
public @interface RestController {
}


