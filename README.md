# AREP-Taller3: Taller de Arquitecturas de Servidores de Aplicaciones, Meta protocolos de objetos, Patrón IoC, Reflexión

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

1. Clone the repository.

```bash
git clone https://github.com/RulosS290/AREP-Taller3.git
```

2. Navigate to the project directory.

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

o

```bash
java -cp target/classes/ co.edu.microspringboot.MicroSpringBoot co.edu.microspringboot.controllers.GreetingController
```

## Funcionalidad

### Controller - HelloController

1. [http://localhost:8080/index](http://localhost:8080/index)

![imagen](https://github.com/user-attachments/assets/c82bfacc-0712-4830-b76a-fc2237e6c214)

![imagen](https://github.com/user-attachments/assets/d4e694ff-be93-4988-9f88-5bd12eb214f0)

Por medio de este tenemos accesos a los demas servicios brindados por este controlador como por ejemplo(/).

![imagen](https://github.com/user-attachments/assets/1a9ccae1-c28a-4edf-b69a-92a40bdd0a8b)

### Controller - GreetingController

1. [http://localhost:8080/greeting ](http://localhost:8080/greeting) - En caso de no poner parametros

![imagen](https://github.com/user-attachments/assets/984b85cc-40ea-4772-ba7a-79fa4bdf3de9)

2. [http://localhost:8080/greeting?name=Daniel](http://localhost:8080/greeting?name=Daniel)

![imagen](https://github.com/user-attachments/assets/70e3a33d-8095-45f4-9170-bc5c2a20dcca)

## Ejecutando pruebas

```bash
mvn test
```
Vemos las pruebas unitarias de las 2 clases controladores (HelloController - GreetingController).

![imagen](https://github.com/user-attachments/assets/23658a04-01d5-4cd7-8309-c4321b07cebf)

### Arquitectura del Proyecto

Componentes Principales

    MicroSpringBoot.java
        Clase principal que inicializa el servidor web y maneja las solicitudes HTTP. Utiliza reflexión para mapear rutas a métodos de controladores y ejecutar los métodos correspondientes.

    controllers
        Carpeta que contiene los controladores de la aplicación.
            GreetingController.java: Controlador que maneja solicitudes a la ruta /greeting. Devuelve un objeto Greeting con un mensaje de saludo.
            HelloController.java: Controlador que maneja varias rutas, incluyendo la raíz (/), /hello, /index, y /image. Devuelve mensajes de texto o archivos estáticos (HTML y PNG).

    test
        Carpeta que contiene las pruebas unitarias para los controladores.
            GreetingControllerTest.java: Contiene pruebas unitarias para el GreetingController, verificando que la respuesta del método greeting sea la esperada.
            HelloControllerTest.java: Contiene pruebas unitarias para el HelloController, verificando respuestas para las rutas /, /hello, /index, y /image.

    resources
        Carpeta que contiene los recursos estáticos que son servidos por el servidor.
            index.html: Archivo HTML que se sirve en la ruta /index.
            png.png: Archivo de imagen PNG que se sirve en la ruta /image.

Anotaciones Personalizadas

    @RequestMapping: Anotación para mapear métodos a rutas específicas. Se utiliza para rutas que no tienen un método específico como GET.
    @GetMapping: Anotación para mapear métodos a rutas que responden a solicitudes HTTP GET.
    @RequestParam: Anotación para inyectar parámetros de consulta en métodos del controlador.
    @RestController: Anotación que indica que una clase es un controlador REST y que sus métodos manejan solicitudes HTTP.

Funcionamiento del Servidor

    Inicialización:
        El servidor se inicializa con el nombre de una clase de controlador que debe estar anotada con @RestController.
        El servidor utiliza reflexión para cargar el controlador y mapear las rutas a los métodos anotados.

    Manejo de Solicitudes:
        El servidor escucha en un puerto específico y acepta conexiones de clientes.
        Procesa solicitudes GET, mapea las rutas a los métodos del controlador, e invoca el método adecuado.
        Genera respuestas de acuerdo con el tipo de contenido (texto o binario) y envía la respuesta al cliente.

    Respuestas:
        Las respuestas pueden ser de tipo texto (por defecto) o binario (para archivos como HTML e imágenes).

- **Annotations**: Anotaciones personalizadas para imitar el comportamiento de Spring Boot.
  - `GetMapping.java`
  - `RequestMapping.java`
  - `RequestParam.java`
  - `RestController.java`
  
- **Classes**: Core classes for handling requests.
  - `Greeting.java`
  
- **Controllers**: Clases básicas para la gestión de solicitudes.
  - `GreetingController.java`
  - `HelloController.java`
  
- **Web Resources**: Recursos estáticos servidos por el Framework.
  - `index.html`
  - `png.png`
  - `imagepaths.png`
  
- **Tests**: Pruebas unitarias para los controladores.
  - `GreetingControllerTest.java`
  - `HelloControllerTest.java`


## Autores

Daniel Santiago Torres Acosta [https://github.com/RulosS290](https://github.com/RulosS290)

## Agradecimientos
Daniel Benavides
