package co.edu.microspringboot.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Target(ElementType.METHOD) // Se puede aplicar a métodos
@Retention(RetentionPolicy.RUNTIME) // Disponibilidad en tiempo de ejecución
public @interface RequestMapping {
    String value() default "";
}

