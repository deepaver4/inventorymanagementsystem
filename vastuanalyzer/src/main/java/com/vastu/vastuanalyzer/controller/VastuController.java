package com.vastu.vastuanalyzer.controller;

import com.vastu.vastuanalyzer.service.VastuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/vastu")
public class VastuController {

    @Autowired
    private VastuService vastuService;

    @GetMapping({"/", ""})
    public String home() {
        return "index"; // templates/vastuUI.html
    }

    @GetMapping("/check")
    public String checkVastu(
            @RequestParam String room,
            @RequestParam double degree,
            Model model) {

        String result = vastuService.evaluateVastu(room, degree);
        model.addAttribute("result", result);

        return "index";
    }

}