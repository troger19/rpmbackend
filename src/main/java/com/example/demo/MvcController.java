package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/")
public class MvcController {
    private static final Logger log = LoggerFactory.getLogger(MvcController.class);

    @Autowired
    private TrainingRepository trainingRepository;
    private List<Training> trainings;

    @RequestMapping
    public String handleRequest(Model model) {
        model.addAttribute("time", LocalTime.now());
        return "home";
    }

    @RequestMapping(value = {"/trainings"}, method = RequestMethod.GET)
    public String personList(Model model) {
        trainings = trainingRepository.findAll();
        model.addAttribute("trainings", trainings);
        return "trainingList";
    }
}
