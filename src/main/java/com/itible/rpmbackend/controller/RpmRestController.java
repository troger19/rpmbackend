package com.itible.rpmbackend.controller;

import com.itible.rpmbackend.dto.PersonDto;
import com.itible.rpmbackend.dto.TrainingDto;
import com.itible.rpmbackend.entity.Person;
import com.itible.rpmbackend.entity.Training;
import com.itible.rpmbackend.repository.PersonRepository;
import com.itible.rpmbackend.repository.TrainingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RpmRestController {
    private final TrainingRepository trainingRepository;
    private final PersonRepository personRepository;

    public RpmRestController(TrainingRepository trainingRepository, PersonRepository personRepository) {
        this.trainingRepository = trainingRepository;
        this.personRepository = personRepository;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/hello")
    public ResponseEntity hello() {
        return ResponseEntity.accepted().body("ahoooj");
    }


    @CrossOrigin(origins = "*")
    @GetMapping("/all")
    public List<TrainingDto> list() {
//        return personRepository.findByName("Jano").get(0).getTrainings();
//        trainingRepository.findAll().stream().map(
//                TrainingDto trainingDto = new TrainingDto();
//        trainingDto.setDate();
//                t->
//        )

        return trainingRepository.findAll().stream().map(temp -> {
            TrainingDto obj = new TrainingDto();
            obj.setDate(temp.getDate());
            obj.setAverage(temp.getAverage());
            obj.setDuration(temp.getDuration());
            obj.setRpm(temp.getRpm());
            obj.setPersonName(temp.getPerson().getName());
            return obj;
        }).collect(Collectors.toList());
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/training")
    public void delete() {
        trainingRepository.deleteAll();
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/training/{id}")
    public void deleteSinleTraining(@PathVariable String id) {
        trainingRepository.deleteAll();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody TrainingDto trainingDto) {
        System.out.println(trainingDto.getRpm());
        Training training = new Training();
        training.setDate(trainingDto.getDate()==null? new Date() : trainingDto.getDate());
        training.setAverage(trainingDto.getAverage());
        training.setDuration(trainingDto.getDuration());
        training.setRpm(trainingDto.getRpm());
        Person person = personRepository.findByName(trainingDto.getPersonName());
        training.setPerson(person);
        trainingRepository.save(training);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/createPerson")
    @ResponseStatus(HttpStatus.OK)
    public void createPerson(@RequestBody String name) {
        Person person = personRepository.findByName(name);
        if (person == null) {
            Person s = new Person(name);
            personRepository.save(s);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public Training get(@PathVariable("id") long id) {
        return trainingRepository.getOne(id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/import")
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody List<TrainingDto> trainingDtos) {
        for (TrainingDto trainingDto : trainingDtos) {
            Training training = new Training();
            training.setDate(trainingDto.getDate());
            training.setAverage(trainingDto.getAverage());
            training.setDuration(trainingDto.getDuration());
            training.setRpm(trainingDto.getRpm());
            Person person = personRepository.findByName(trainingDto.getPersonName());
            training.setPerson(person);
            trainingRepository.save(training);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/allPerson")
    public List<PersonDto> getAllPerson() {
        return personRepository.findAll().stream().map(temp -> {
            PersonDto obj = new PersonDto();
            obj.setName(temp.getName());
            return obj;
        }).collect(Collectors.toList());
    }
}
