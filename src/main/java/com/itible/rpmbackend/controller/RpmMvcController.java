package com.itible.rpmbackend.controller;

import com.itible.rpmbackend.entity.Person;
import com.itible.rpmbackend.entity.Training;
import com.itible.rpmbackend.repository.PersonRepository;
import com.itible.rpmbackend.repository.TrainingRepository;
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
public class RpmMvcController {

    private final TrainingRepository trainingRepository;
    private final PersonRepository personRepository;

    public RpmMvcController(TrainingRepository trainingRepository, PersonRepository personRepository) {
        this.trainingRepository = trainingRepository;
        this.personRepository = personRepository;
    }

    @RequestMapping
    public String handleRequest(Model model) {
        model.addAttribute("time", LocalTime.now());
        return "home";
    }

    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public String handleHomeRequest() {
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

    @RequestMapping(value = {"/deleteAll"}, method = RequestMethod.GET)
    public String deleteAllTrainings() {
        trainingRepository.deleteAll();
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
        if (bindingResult.hasErrors()) {
            return "personList";
        }

        personRepository.save(person1);
        model.addAttribute("personList", personRepository.findAll());
        return "redirect:/persons";
    }
}
