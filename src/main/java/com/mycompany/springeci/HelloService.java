package com.mycompany.springeci;

/**
 *
 * @author daniel.torres-ac
 */
@RestController
public class HelloService {
    
    @GetMapping("/hello")
   public static String hello(){
       return "Hello World!";
   }
}
