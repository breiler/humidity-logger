package se.nimlab.hl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import se.nimlab.hl.repository.LogRepository;

@Controller
public class DefaultController {

    @Autowired
    private LogRepository logRepository;

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return logRepository.findAll().toString();
    }
}
