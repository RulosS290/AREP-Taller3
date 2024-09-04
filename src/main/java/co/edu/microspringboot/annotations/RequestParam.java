package co.edu.microspringboot.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // Aplica a parámetros de métodos
@Retention(RetentionPolicy.RUNTIME) // Disponible en tiempo de ejecución
public @interface RequestParam {
    String value();
}

