package com.itible.rpmbackend.controller;

import com.itible.rpmbackend.entity.Person;
import com.itible.rpmbackend.entity.Training;
import com.itible.rpmbackend.repository.PersonRepository;
import com.itible.rpmbackend.repository.TrainingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/")
@Slf4j
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
//        List<Training> trainings = trainingRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
        List<Training> trainings = trainingRepository.findAll().stream().sorted(Comparator.comparing(Training::getDate)).map(temp -> {
            Training obj = new Training();
            obj.setTrainingId(temp.getTrainingId());
            obj.setDate(temp.getDate());
            obj.setAverageRpm(temp.getAverageRpm() == null ? BigDecimal.ZERO : temp.getAverageRpm().setScale(1, RoundingMode.HALF_UP));
            obj.setAverageRpmByTime(temp.getAverageRpm() == null ? BigDecimal.ZERO : temp.getAverageRpmByTime().setScale(1, RoundingMode.HALF_UP));
            obj.setDuration(temp.getDuration());
            obj.setRpm(temp.getRpm());
            obj.setPerson(temp.getPerson());
            log.info("Training : " + obj);
            return obj;
        }).collect(Collectors.toList());

        model.addAttribute("trainings", trainings);
        return "trainingList";
    }

    @RequestMapping(value = {"/persons"}, method = RequestMethod.GET)
    public String personList(Model model) {
        List<Person> persons = personRepository.findAll();
        model.addAttribute("person", new Person());
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

    @RequestMapping(value = {"/detail/{id}"}, method = RequestMethod.GET)
    public String trainingDetail(Model model, @PathVariable String id) {
        Training training = trainingRepository.getOne(Long.parseLong(id));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(training.getDate());
        List<Integer> axisX = IntStream.rangeClosed(1, training.getRpm().size()).boxed().collect(Collectors.toList());
        model.addAttribute("rpms", training.getRpm());
        model.addAttribute("axisX", axisX);
        model.addAttribute("name", training.getPerson().getName());
        model.addAttribute("date", date);
        model.addAttribute("averageRpm", training.getAverageRpm());
        model.addAttribute("averageRpmByTime", training.getAverageRpmByTime());
        model.addAttribute("duration", training.getDuration().toString());
        model.addAttribute("showButton", true);
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
        model.addAttribute("person", new Person());
        return "personList";
    }

    @RequestMapping(value = "/addPerson", method = RequestMethod.POST)
    public String createPerson(@ModelAttribute Person person, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "personList";
        }
        personRepository.save(person);
        model.addAttribute("personList", personRepository.findAll());
        return "redirect:/persons";
    }
}
