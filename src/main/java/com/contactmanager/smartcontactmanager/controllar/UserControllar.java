package com.contactmanager.smartcontactmanager.controllar;

import com.contactmanager.smartcontactmanager.dao.ContactRepository;
import com.contactmanager.smartcontactmanager.dao.UserRepository;
import com.contactmanager.smartcontactmanager.entities.Contact;
import com.contactmanager.smartcontactmanager.entities.User;
import helper.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserControllar {
    @Autowired

    private UserRepository userRepository;
    @Autowired
    private ContactRepository contactRepository;

    @ModelAttribute
    public void addCommonData(Model model, Principal principal){
        String uname = principal.getName();
        User user = userRepository.GetUserByUserName(uname);
        model.addAttribute("user",user);
    }


    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal){
        return "simple/dashbord";
    }

    @GetMapping("/addcontact")
    public String addcontact(Model model){
        model.addAttribute("contact",new Contact());
        return "simple/addcontact";
    }

    @PostMapping("/process-contact")
    public String addProcess_contact(@ModelAttribute Contact contact, Principal principal, HttpSession session){
        try {
//            if(file.isEmpty()){
//
//            }else{
//                contact.setUimage(file.getOriginalFilename());
//                File file1 = new ClassPathResource("/static/img/").getFile();
//                Path path = Paths.get(file1.getAbsolutePath() + File.separator + file.getOriginalFilename());
//                Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
//                System.out.println("Image is Uploaded");
//            }
            String name = principal.getName();
            User user = this.userRepository.GetUserByUserName(name);
            contact.setUser(user);
            user.getContacts().add(contact);
            this.userRepository.save(user);
            //success message
            session.setAttribute("message",new Message("Contact Saved!!","success"));

        }catch (Exception e)
        {
            System.out.println("Error"+e.getMessage());
            //Error message
            session.setAttribute("message",new Message("Contact Not Saved!!","danger"));
        }
        return "simple/addcontact";
    }

    @GetMapping("/showcontact")
    public String showcontact(Model model,Principal principal)
    {
        String name = principal.getName();
        User user = userRepository.GetUserByUserName(name);
        int id = user.getId();
        List<Contact> contactByUser = this.contactRepository.findContactsByUser(id);

        model.addAttribute("contacts",contactByUser);
        model.addAttribute("title","Show Contact");
        return "simple/show_contact";
    }

    //show particular person
    @GetMapping("/contacts/{id}")
    public String particular(@PathVariable("id")Integer id,Model model,Principal principal){
        Optional<Contact> byId = this.contactRepository.findById(id);
        Contact contact = byId.get();
        String name = principal.getName();
        User user = this.userRepository.GetUserByUserName(name);
        if(user.getId()==contact.getUser().getId())
        {
            model.addAttribute("c",contact);
        }
        return "simple/particular_contact";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id,HttpSession session)
    {
        Optional<Contact> byId = this.contactRepository.findById(id);
        Contact contact = byId.get();
        contactRepository.delete(contact);
        session.setAttribute("message",new Message("Contact Delete Sucessfully","success"));
        return "redirect:/user/showcontact";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id")Integer id,Model model)
    {

        Contact user = this.contactRepository.findById(id).get();

       model.addAttribute("user",user);
        return "simple/update_contact";

    }

    @PostMapping("/process-update")
    public String update(@ModelAttribute Contact contact,Model model,Principal principal){
        User user1 = this.userRepository.GetUserByUserName(principal.getName());
        contact.setUser(user1);
    this.contactRepository.save(contact);
    return "redirect:/user/contacts/"+contact.getCid();
    }

    @GetMapping("/profile")
    public String yourProfile(){
        return "simple/profile";
    }
}
