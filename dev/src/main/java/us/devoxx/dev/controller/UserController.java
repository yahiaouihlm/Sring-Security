package us.devoxx.dev.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import us.devoxx.dev.services.UserServices;

@RestController
public class UserController {


    @GetMapping("/")
    public  String  publicpage (){
        return  "Welcome in  spring  boot Application  Test" ;
    }


@GetMapping("/private")
    public String  privatePage () {
        var  context = SecurityContextHolder.getContext();
    System.out.println(context.getAuthentication());
    return  "  Welcome to VIP area " ;
    }

}
