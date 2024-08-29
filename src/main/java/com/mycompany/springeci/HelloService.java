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
   @GetMapping("/PI")
   public static String PI(){
       return "3.1416";
   }
   @GetMapping("/light-speed")
    public static String lightSpeed() {
        return "299,792,458 m/s";
    }

    @GetMapping("/planck-constant")
    public static String planckConstant() {
        return "6.62607015 × 10^-34 J·s";
    }

    @GetMapping("/gravitational-constant")
    public static String gravitationalConstant() {
        return "6.67430 × 10^-11 m^3 kg^-1 s^-2";
    }

    @GetMapping("/avogadro-number")
    public static String avogadroNumber() {
        return "6.02214076 × 10^23 mol^-1";
    }
}
