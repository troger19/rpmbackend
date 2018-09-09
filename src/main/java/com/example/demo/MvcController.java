package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping
    public String handleRequest(Model model) {
        model.addAttribute("time", LocalTime.now());
        return "home";
    }

    @RequestMapping(value = {"/trainings"}, method = RequestMethod.GET)
    public String trainingList(Model model) {
        List<Training> trainings = trainingRepository.findAll();
        model.addAttribute("trainings", trainings);
        return "trainingList";
    }

    @RequestMapping(value = {"/persons"}, method = RequestMethod.GET)
    public String personList(Model model) {
        List<Person> persons = personRepository.findAll();
        model.addAttribute("person1", new Person());
        model.addAttribute("persons", persons);
        return "personList";
    }

    @RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.GET)
    public String trainingDelete(Model model, @PathVariable String id) {
        trainingRepository.deleteById(Long.parseLong(id));
        List<Training> trainings = trainingRepository.findAll();
        model.addAttribute("trainings", trainings);
        return "trainingList";
    }

    @RequestMapping(value = {"/person-delete/{id}"}, method = RequestMethod.GET)
    public String personDelete(Model model, @PathVariable String id) {
        personRepository.deleteById(Long.parseLong(id));
        List<Person> persons = personRepository.findAll();
        model.addAttribute("persons", persons);
        model.addAttribute("person1", new Person());
        return "personList";
    }

    @RequestMapping(value = "/addPerson", method = RequestMethod.POST)
    public String createPerson(@ModelAttribute Person person1, BindingResult bindingResult, Model model) {
//        Person o = new Person();
        if (bindingResult.hasErrors()) {
            return "personList";
        }

        personRepository.save(person1);
        model.addAttribute("personList", personRepository.findAll());
        return "redirect:/persons";
    }
}
