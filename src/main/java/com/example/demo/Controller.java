package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {
    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private PersonRepository personRepository;


    @CrossOrigin(origins = "*")
    @RequestMapping("/hello")
    public ResponseEntity hello() {
        return ResponseEntity.accepted().body("ahoooj");
    }


    @CrossOrigin(origins = "*")
    @GetMapping("/all")
    public List<Training> list() {
        return trainingRepository.findAll();
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/delete")
    public void delete() {
        trainingRepository.deleteAll();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody TrainingDto trainingDto) {
        System.out.println(trainingDto.getRpm());
        Training training = new Training();
        training.setDate(trainingDto.getDate());
        training.setAverage(trainingDto.getAverage());
        training.setDuration(trainingDto.getDuration());
        training.setRpm(trainingDto.getRpm());
        List<Person> byName = personRepository.findByName(trainingDto.getPersonName());
        training.setPerson(byName.isEmpty() ? null : byName.get(0));
        trainingRepository.save(training);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/createPerson")
    @ResponseStatus(HttpStatus.OK)
    public void createPerson(@RequestBody String name) {
        List<Person> person = personRepository.findByName(name);
        if (person.isEmpty()) {
            personRepository.save(new Person(name));
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public Training get(@PathVariable("id") long id) {
        return trainingRepository.getOne(id);
    }

}
