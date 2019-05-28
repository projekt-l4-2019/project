package pl.com.project.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.com.project.Notices.NoticeFullRepository;
import pl.com.project.Users.PeopleRepository;

@RestController
public class HelloController {

    PeopleRepository peopleRepository;
    NoticeFullRepository noticeFullRepository;

    @Autowired
    public HelloController(PeopleRepository peopleRepository, NoticeFullRepository noticeFullRepository) {
        this.peopleRepository = peopleRepository;
        this.noticeFullRepository = noticeFullRepository;
    }

    @RequestMapping("/")
    @ResponseBody
    public String hello1Model (Model model_1, Model model_2) {
        model_1.addAttribute("people",peopleRepository.findAll());
        model_2.addAttribute("notice", noticeFullRepository.findAll());
        return "hello TeachMe";
    }

    @GetMapping("/addnotice")
    public String addnotice()
    {
        return "addnotice";
    }

    @GetMapping("/about")
    public String about()
    {
        return "app for school project, we don't use any of data outside";
    }

    @GetMapping("hi")
    public String hello(Model model_1, Model model_2) {
        model_1.addAttribute("people",peopleRepository.findAll());
        model_2.addAttribute("notice", noticeFullRepository.findAll());
        return "hello";
    }



}
