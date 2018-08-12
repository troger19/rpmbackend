package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    @RequestMapping("/hello")
    public ResponseEntity hello() {
        return ResponseEntity.accepted().body("ahoooj");
    }

    @Autowired
    private TrainingRepository trainingRepository;

    @GetMapping("/all")
    public List<Training> list() {
        return trainingRepository.findAll();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody Training training) {
        System.out.println(training.getRpm());
        trainingRepository.save(training);
    }

    @GetMapping("/{id}")
    public Training get(@PathVariable("id") long id) {
        return trainingRepository.getOne(id);
    }

}
