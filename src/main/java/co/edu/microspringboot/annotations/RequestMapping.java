package co.edu.microspringboot.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Annotation to map HTTP requests to handler methods.
 * 
 * - `value()`: URL path to map to the method.
 */
@Target(ElementType.METHOD) 
@Retention(RetentionPolicy.RUNTIME) 
public @interface RequestMapping {
    String value() default "";
}


