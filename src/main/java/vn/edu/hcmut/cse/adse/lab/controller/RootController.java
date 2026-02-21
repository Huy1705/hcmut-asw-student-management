package vn.edu.hcmut.cse.adse.lab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String index() {
        // Chuyển hướng tự động từ "/" sang "/students"
        return "redirect:/students";
    }
}