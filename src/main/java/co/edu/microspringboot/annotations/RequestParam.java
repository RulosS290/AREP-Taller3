package co.edu.microspringboot.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to bind a method parameter to a request parameter.
 * 
 * - `value()`: Name of the request parameter.
 * - `defaultValue()`: Default value if the parameter is not provided.
 */
@Target(ElementType.PARAMETER) 
@Retention(RetentionPolicy.RUNTIME) 
public @interface RequestParam {
    String value();
    String defaultValue() default "";
}
