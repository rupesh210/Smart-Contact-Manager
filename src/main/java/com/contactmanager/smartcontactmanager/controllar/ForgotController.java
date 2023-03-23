package com.contactmanager.smartcontactmanager.controllar;

import com.contactmanager.smartcontactmanager.dao.UserRepository;
import com.contactmanager.smartcontactmanager.entities.User;
import com.contactmanager.smartcontactmanager.services.EmailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;
import java.util.Random;

@Controller
public class ForgotController {
    @Autowired
    private EmailServices emailServices;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    Random random = new Random(1000);
    @RequestMapping("/forgot")
    public String openEmailForm(){
        return "forgotEmail";
    }
    @PostMapping("/sendOtp")
    public String SendOtp(@RequestParam("email") String email, HttpSession httpSession){
        System.out.println("Your Email id : "+email);

        int otp = random.nextInt(99999);
        System.out.println("Your Otp is : "+otp);
        String subject = "OTP From Smart Contact Manager(SCM)";
        String message ="<div style='border:2px solid red; padding:5px; background-color:grey;'>"+
                "<h1>"+
                "<b> Your OTP is : "+otp+
                "</b>"+
                "</h1>"+
                "</div>";
        boolean flag = emailServices.sendEmail(message, subject, email);
        if(flag)
        {
            httpSession.setAttribute("myOtp",otp);
            httpSession.setAttribute("email",email);
            return "verifyOtp";
        }
        else{
            httpSession.setAttribute("message","Please Check Your Email Id !!");
            return "forgotEmail";
        }
    }
    @PostMapping("/verifyOtp")
    public String verifyOtp(@RequestParam("otp") int otp,HttpSession httpSession){
        int myOtp= (int) httpSession.getAttribute("myOtp");
        String myEmail= (String) httpSession.getAttribute("email");
        if(myOtp==otp)
        {
            User user = userRepository.GetUserByUserName(myEmail);
            if(user==null)
            {
                httpSession.setAttribute("message","User Not Fount !!");
                return "forgotEmail";
            }else{
                return "changePassword";
            }
        }else{
            httpSession.setAttribute("message","Your OTP is Wrong !! Please try Again");
            return "forgotEmail";
        }

    }
    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("newPassword") String newPassword,HttpSession httpSession)
    {
        Object email = httpSession.getAttribute("email");
        User user = userRepository.GetUserByUserName((String) email);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
       httpSession.setAttribute("message","Password Change Successful !");
        return "login";
    }

}
