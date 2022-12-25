package us.devoxx.dev.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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

    return  "  Welcome to VIP area " ;
    }
   /* @GetMapping("/add")
    public String createUser (){
        userServices.addUser();
        return  "User has been  Successfuly  created";
    }*/

}
