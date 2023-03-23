package com.contactmanager.smartcontactmanager.controllar;

import com.contactmanager.smartcontactmanager.entities.User;
import com.contactmanager.smartcontactmanager.dao.UserRepository;
import helper.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class Smartcontrollar {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("title","This is Home page");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model){
        model.addAttribute("title","this is about page");
        return "about";
    }

    @GetMapping("/signin")
    public String signin(Model model){
        model.addAttribute("title","this is sign in page");
        return "signin";
    }

    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("title","this is sign up page");
        model.addAttribute("user",new User());
        return "signup";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user,BindingResult result, @RequestParam(value = "agreement",defaultValue = "false") boolean agreement, Model model, HttpSession session){
        try {
            if(!agreement)
            {
                throw new Exception();
            }
            if(result.hasErrors())
            {
                model.addAttribute("user",user);
                return "signup";
            }
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            session.setAttribute("message",new Message("Sucessfully Registration","alert-success"));
            model.addAttribute("user",new User());
        }catch(Exception e)
        {
            model.addAttribute("user",user);
            session.setAttribute("message", new Message("Something was wrong !!","alert-danger"));
            return "signup";
        }
        return "signup";
    }

    @GetMapping("/loginPage")
    public String login(Model model){
        return "login";
    }
}
