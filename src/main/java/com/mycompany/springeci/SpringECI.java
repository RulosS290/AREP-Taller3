package com.mycompany.springeci;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.net.URL;
import java.util.Map;
import java.net.MalformedURLException;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author daniel.torres-ac
 */
public class SpringECI {

    public static void main(String[] args) throws ClassNotFoundException, MalformedURLException, IllegalAccessException,InvocationTargetException {
        System.out.println("Hello World!");
        Class c = Class.forName(args[0]);
        Map<String, Method> services = new HashMap();        
        if(c.isAnnotationPresent(RestController.class)){
            Method[] methods = c.getDeclaredMethods();
            for(Method m : methods){
                if(m.isAnnotationPresent(GetMapping.class)){
                    String key = m.getAnnotation(GetMapping.class).value();
                    services.put(key, m);
                }
            }
        }
        
        // Codigo quemado para ejemplo
        URL serviceurl = new URL("http://localhost:8080/App/hello");
        String path = serviceurl.getPath();
        System.out.println("Path: " + path);
        String servicename = path.substring(4);
        System.out.println("Service name: " + servicename);
        
        serviceurl = new URL("http://localhost:8080/App/PI");
        path = serviceurl.getPath();
        System.out.println("Path: " + path);
        servicename = path.substring(4);
        System.out.println("Service name: " + servicename);
        
        serviceurl = new URL("http://localhost:8080/App/light-speed");
        path = serviceurl.getPath();
        System.out.println("Path: " + path);
        servicename = path.substring(4);
        System.out.println("Service name: " + servicename);
        
        serviceurl = new URL("http://localhost:8080/App/planck-constant");
        path = serviceurl.getPath();
        System.out.println("Path: " + path);
        servicename = path.substring(4);
        System.out.println("Service name: " + servicename);
        
        serviceurl = new URL("http://localhost:8080/App/gravitational-constant");
        path = serviceurl.getPath();
        System.out.println("Path: " + path);
        servicename = path.substring(4);
        System.out.println("Service name: " + servicename);
        
        serviceurl = new URL("http://localhost:8080/App/avogadro-number");
        path = serviceurl.getPath();
        System.out.println("Path: " + path);
        servicename = path.substring(4);
        System.out.println("Service name: " + servicename);
        
        Method ms = services.get(servicename);
        ms.invoke(ms);
    }
}
