package co.edu.microspringboot;

import co.edu.microspringboot.annotations.*;
import co.edu.microspringboot.classes.Greeting;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * A basic implementation of a micro web server similar to Spring Boot.
 * It initializes with a controller class and handles HTTP GET requests,
 * mapping them to controller methods using reflection.
 */
public class MicroSpringBoot {

    private Map<String, Method> services = new HashMap<>();
    private Object controllerInstance;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java -cp target/classes co.edu.microspringboot.MicroSpringBoot <controller>");
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

    /**
     * Initializes the server with the specified controller class.
     * 
     * @param controllerClassName The name of the controller class
     * @throws Exception If there's an error loading the controller or its methods
     */
    public void init(String controllerClassName) throws Exception {
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
            System.out.println("Loaded services: " + services.keySet());
        } else {
            System.out.println("The specified class is not annotated with @RestController.");
        }
    }

    /**
     * Starts the server and listens for incoming connections.
     * 
     * @param port The port to listen on
     * @throws IOException If an I/O error occurs while starting the server
     */
    public void startServer(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            handleClient(clientSocket);
            clientSocket.close(); // Non-concurrent handling
        }
    }

    /**
     * Handles a client request and sends an appropriate response.
     * 
     * @param clientSocket The client socket to handle
     */
    private void handleClient(Socket clientSocket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedOutputStream out = new BufferedOutputStream(clientSocket.getOutputStream());

            String requestLine = in.readLine();
            if (requestLine == null || requestLine.isEmpty()) {
                return;
            }

            System.out.println("Request received: " + requestLine);
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

                String contentType = determineContentType(serviceMethod);

                if (result instanceof String) {
                    sendResponse(out, "200 OK", (String) result);
                } else if (result instanceof byte[]) {
                    sendBinaryResponse(out, "200 OK", (byte[]) result, contentType);
                } else {
                    String jsonResponse = convertObjectToJson(result);
                    sendResponse(out, "200 OK", jsonResponse);
                }

            } else {
                sendResponse(out, "404 Not Found", "Not Found");
            }

        } catch (IOException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Determines the content type based on the return type of the method.
     * 
     * @param serviceMethod The service method to check
     * @return The content type as a String
     */
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
        return "text/html"; // Default assumes HTML
    }

    /**
     * Sends a binary response to the client.
     * 
     * @param out The output stream to send the response
     * @param status The HTTP status code
     * @param body The response body as a byte array
     * @param contentType The content type of the response
     * @throws IOException If an I/O error occurs
     */
    private void sendBinaryResponse(BufferedOutputStream out, String status, byte[] body, String contentType) throws IOException {
        out.write(("HTTP/1.1 " + status + "\r\n").getBytes());
        out.write(("Content-Type: " + contentType + "\r\n").getBytes());
        out.write(("Content-Length: " + body.length + "\r\n").getBytes());
        out.write("\r\n".getBytes()); // Blank line separating headers and body
        out.write(body);
        out.flush();
    }

    /**
     * Sends a text response to the client.
     * 
     * @param out The output stream to send the response
     * @param status The HTTP status code
     * @param body The response body as a String
     * @throws IOException If an I/O error occurs
     */
    private void sendResponse(BufferedOutputStream out, String status, String body) throws IOException {
        out.write(("HTTP/1.1 " + status + "\r\n").getBytes());
        out.write("Content-Type: text/plain\r\n".getBytes());
        out.write(("Content-Length: " + body.length() + "\r\n").getBytes());
        out.write("\r\n".getBytes());
        out.write(body.getBytes());
        out.flush();
    }

    /**
     * Prepares the arguments for a method based on query parameters.
     * 
     * @param method The method to prepare arguments for
     * @param queryParams The query parameters from the request
     * @return An array of arguments to pass to the method
     */
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

    /**
     * Converts an object to a JSON string.
     * 
     * @param obj The object to convert
     * @return The JSON representation of the object
     */
    private String convertObjectToJson(Object obj) {
        if (obj instanceof Greeting) {
            Greeting greeting = (Greeting) obj;
            return "{\"content\": \"" + greeting.getContent() + "\"}";
        }
        return "{}";  // Default empty JSON
    }
}