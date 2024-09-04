// src/main/java/co/edu/microspringboot/MicroSpringBoot.java
package co.edu.microspringboot;

import co.edu.microspringboot.annotations.GetMapping;
import co.edu.microspringboot.annotations.RequestMapping;
import co.edu.microspringboot.annotations.RequestParam;
import co.edu.microspringboot.annotations.RestController;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MicroSpringBoot {

    private Map<String, Method> services = new HashMap<>();
    private Object controllerInstance;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java -cp target/classes co.edu.microspringboot.MicroSpringBoot <controlador>");
            return;
        }

        MicroSpringBoot server = new MicroSpringBoot();
        try {
            server.init(args[0]);
            server.startServer(8080);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init(String controllerClassName) throws ClassNotFoundException, IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        Class<?> controllerClass = Class.forName(controllerClassName);
        if (controllerClass.isAnnotationPresent(RestController.class)) {
            controllerInstance = controllerClass.getDeclaredConstructor().newInstance();
            Method[] methods = controllerClass.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(GetMapping.class)) {
                    String path = method.getAnnotation(GetMapping.class).value();
                    services.put(path, method);
                }
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    String path = method.getAnnotation(RequestMapping.class).value();
                    services.put(path, method);
                }
            }
            System.out.println("Servicios cargados: " + services.keySet());
        } else {
            System.out.println("La clase especificada no está anotada con @RestController.");
        }
    }
    

    public void startServer(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Servidor iniciado en el puerto " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            handleClient(clientSocket);
            clientSocket.close(); // Manejo no concurrente
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedOutputStream out = new BufferedOutputStream(clientSocket.getOutputStream());
    
            String requestLine = in.readLine();
            if (requestLine == null || requestLine.isEmpty()) {
                return;
            }
    
            System.out.println("Solicitud recibida: " + requestLine);
            String[] tokens = requestLine.split(" ");
            if (tokens.length < 2) {
                sendResponse(out, "400 Bad Request", "Bad Request");
                return;
            }
    
            String method = tokens[0];
            String path = tokens[1];
    
            if (!method.equals("GET")) {
                sendResponse(out, "405 Method Not Allowed", "Method Not Allowed");
                return;
            }
    
            String[] pathParts = path.split("\\?");
            String basePath = pathParts[0];
            Map<String, String> queryParams = new HashMap<>();
    
            if (pathParts.length > 1) {
                String queryString = pathParts[1];
                String[] params = queryString.split("&");
                for (String param : params) {
                    String[] keyValue = param.split("=");
                    if (keyValue.length == 2) {
                        queryParams.put(keyValue[0], keyValue[1]);
                    }
                }
            }
    
            Method serviceMethod = services.get(basePath);
            if (serviceMethod != null) {
                Object[] methodArgs = prepareMethodArguments(serviceMethod, queryParams);
                Object result = serviceMethod.invoke(controllerInstance, methodArgs);
    
                // Determinar el tipo de contenido usando serviceMethod
                String contentType = determineContentType(serviceMethod);
    
                if (result instanceof String) {
                    sendResponse(out, "200 OK", (String) result);
                } else if (result instanceof byte[]) {
                    sendBinaryResponse(out, "200 OK", (byte[]) result, contentType);
                }
    
            } else {
                sendResponse(out, "404 Not Found", "Not Found");
            }
    
        } catch (IOException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    
    

    private String determineContentType(Method serviceMethod) {
        if (serviceMethod.getReturnType() == byte[].class) {
            String methodName = serviceMethod.getName().toLowerCase();
            if (methodName.contains("image") || serviceMethod.isAnnotationPresent(GetMapping.class) && serviceMethod.getAnnotation(GetMapping.class).value().contains(".png")) {
                return "image/png";
            } else if (methodName.contains("jpg") || methodName.contains("jpeg")) {
                return "image/jpeg";
            } else if (methodName.contains("gif")) {
                return "image/gif";
            }
        }
        return "text/html"; // Por defecto, asume HTML
    }
    
    
    
    
    private void sendBinaryResponse(BufferedOutputStream out, String status, byte[] body, String contentType) throws IOException {
        out.write(("HTTP/1.1 " + status + "\r\n").getBytes());
        out.write(("Content-Type: " + contentType + "\r\n").getBytes());
        out.write(("Content-Length: " + body.length + "\r\n").getBytes());
        out.write("\r\n".getBytes()); // Línea en blanco separando cabeceras del cuerpo
        out.write(body);
        out.flush();
    }
    
    
    

    private void sendResponse(BufferedOutputStream out, String status, String body) throws IOException {
        out.write(("HTTP/1.1 " + status + "\r\n").getBytes());
        out.write("Content-Type: text/plain\r\n".getBytes());
        out.write(("Content-Length: " + body.length() + "\r\n").getBytes());
        out.write("\r\n".getBytes());
        out.write(body.getBytes());
        out.flush();
    }

    private Object[] prepareMethodArguments(Method method, Map<String, String> queryParams) {
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].isAnnotationPresent(RequestParam.class)) {
                RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
                String paramName = requestParam.value();
                args[i] = queryParams.get(paramName);
            }
        }

        return args;
    }
}


