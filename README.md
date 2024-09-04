### Descripción del Taller

Este proyecto consiste en la implementación de un servidor web en Java similar a Apache, capaz de entregar páginas HTML e imágenes PNG. Además, el servidor incorpora un framework de Inversión de Control (IoC) que permite construir aplicaciones web a partir de POJOs (Plain Old Java Objects).

### Objetivos

1. **Servidor Web**: Implementar un servidor que maneje solicitudes HTTP y sea capaz de servir contenido estático como páginas HTML e imágenes PNG.
2. **Framework IoC**: Desarrollar un framework de IoC en Java que permita inyectar dependencias y manejar el ciclo de vida de los POJOs utilizados en la aplicación.
3. **Reflexión en Java**: Utilizar las capacidades de reflexión de Java para cargar al menos un bean (POJO) y demostrar el uso del framework IoC.
4. **Atención de Solicitudes**: El servidor debe ser capaz de manejar múltiples solicitudes de manera no concurrente.

### Entregables

- **Prototipo Mínimo (Fase 1)**: Debe demostrarse al final del laboratorio un prototipo que implemente las capacidades reflexivas de Java, permitiendo cargar un bean (POJO) y derivar una aplicación web a partir de él.
- **Entrega Final (Fase 2)**: En un plazo de 8 días, se debe completar el servidor y la aplicación web. Se verificará el commit del inicio del laboratorio y se comparará con el commit de la entrega final para evaluar el progreso.

### Empezando
Estas instrucciones le permitirán obtener una copia del proyecto en funcionamiento en su máquina local para fines de desarrollo y prueba. Consulte la sección Implementación para obtener notas sobre cómo implementar el proyecto en un sistema en vivo.

### Requisitos

* [Git](https://git-scm.com/) - Control de versiones
* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [Java](https://www.oracle.com/java/technologies/downloads/#java17) - Lenguaje de programación

### Instalación

Realice los siguientes pasos para clonar el proyecto en su máquina local.

```bash
git clone https://github.com/RulosS290/AREP-Taller3.git
```

## Ejecutando la aplicación

Para ejecutar la aplicación, ejecute el siguiente comando:

* Construir el proyecto
```bash
mvn clean package
```
* Compilar el proyecto
```bash
mvn compile
```
* Ejecutar pruebas
```bash
mvn test
```
* Ejecutar el Proyecto(Preferiblemente un Git Bash)
```bash
java -cp target/classes/ co.edu.microspringboot.MicroSpringBoot co.edu.microspringboot.controllers.HelloController
```
