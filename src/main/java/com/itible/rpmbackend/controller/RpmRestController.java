package com.itible.rpmbackend.controller;

import com.itible.rpmbackend.dto.PersonDto;
import com.itible.rpmbackend.dto.TrainingDto;
import com.itible.rpmbackend.entity.Person;
import com.itible.rpmbackend.entity.Training;
import com.itible.rpmbackend.repository.PersonRepository;
import com.itible.rpmbackend.repository.TrainingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class RpmRestController {
    private final TrainingRepository trainingRepository;
    private final PersonRepository personRepository;

    public RpmRestController(TrainingRepository trainingRepository, PersonRepository personRepository) {
        this.trainingRepository = trainingRepository;
        this.personRepository = personRepository;
    }


    @CrossOrigin(origins = "*")
    @GetMapping("/training")
    public List<TrainingDto> getAllTrainings() {
        log.info("All trainings");
        return trainingRepository.findAll().stream().map(temp -> {
            TrainingDto obj = new TrainingDto();
            obj.setDate(temp.getDate());
            obj.setAvgRpm(temp.getAverageRpm().setScale(1, RoundingMode.HALF_UP));
            obj.setAvgRpmTime(temp.getAverageRpmByTime().setScale(1, RoundingMode.HALF_UP));
            obj.setDuration(temp.getDuration());
            obj.setRpm(temp.getRpm());
            obj.setPersonName(temp.getPerson().getName());
            log.info("Training : " + obj);
            return obj;
        }).collect(Collectors.toList());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/training/{name}")
    public List<TrainingDto> getTrainingsByName(@PathVariable String name) {
        log.info("Training for user : " + name);
        return trainingRepository.findByPerson(name).stream().map(temp -> {
            TrainingDto obj = new TrainingDto();
            obj.setDate(temp.getDate());
            obj.setAvgRpm(temp.getAverageRpm());
            obj.setAvgRpmTime(temp.getAverageRpmByTime());
            obj.setDuration(temp.getDuration());
            obj.setRpm(temp.getRpm());
            obj.setPersonName(temp.getPerson().getName());
            log.info("trening : " + obj);
            return obj;
        }).collect(Collectors.toList());
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/training")
    public void deleteAllTrainings() {
        log.info("delete all trainings");
        trainingRepository.deleteAll();
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/training/{id}")
    public void deleteSingleTraining(@PathVariable Long id) {
        log.info("delete training with id : " + id);
        trainingRepository.deleteById(id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/training")
    @ResponseStatus(HttpStatus.OK)
    public void saveTraining(@RequestBody TrainingDto trainingDto) {
        Training training = new Training();
        training.setDate(trainingDto.getDate() == null ? new Date() : trainingDto.getDate());
        training.setAverageRpm(trainingDto.getAvgRpm());
        training.setAverageRpmByTime(trainingDto.getAvgRpmTime());
        training.setDuration(trainingDto.getDuration());
        training.setRpm(trainingDto.getRpm());
        Person person = personRepository.findByName(trainingDto.getPersonName());
        training.setPerson(person);
        trainingRepository.save(training);
        log.info("Saving new training for user: " + person.getName());
        log.info("getAverageRpmByTime " + training.getAverageRpmByTime() + " getAverageRpm" + training.getAverageRpm());
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/person")
    @ResponseStatus(HttpStatus.OK)
    public void createPerson(@RequestBody String name) {
        Person person = personRepository.findByName(name);
        if (person == null) {
            Person newPerson = new Person(name);
            personRepository.save(newPerson);
            log.info("Creating new user " + newPerson.getName());
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/training/import")
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody List<TrainingDto> trainingDtos) {
        log.info("Going to import  " + trainingDtos.size() + " trainings");
        for (TrainingDto trainingDto : trainingDtos) {
            Training training = new Training();
            training.setDate(trainingDto.getDate());
            training.setAverageRpm(trainingDto.getAvgRpm());
            training.setAverageRpmByTime(trainingDto.getAvgRpmTime());
            training.setDuration(trainingDto.getDuration());
            training.setRpm(trainingDto.getRpm());
            Person person = personRepository.findByName(trainingDto.getPersonName());
            training.setPerson(person);
            trainingRepository.save(training);
        }
        log.info("All trainings successfully imported.");
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/person")
    public List<PersonDto> getAllPerson() {
        log.info("Returning all person");
        return personRepository.findAll().stream().map(temp -> {
            PersonDto obj = new PersonDto();
            obj.setName(temp.getName());
            return obj;
        }).collect(Collectors.toList());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/person/{name}")
    public PersonDto getPersonByName(@PathVariable("name") String name) {
        Person person = personRepository.findByName(name);
        if (person == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person Not Found");
        }
        PersonDto dto = new PersonDto();
        dto.setName(person.getName());
        dto.setNumberOfTrainings(person.getTrainings().size());
        log.info("Returning person = " + name);
        return dto;
    }

    /**
     * Wake UP Endpoint. For heroku to wake up the containier
     *
     * @return should return 200
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/health")
    public ResponseEntity healthCheck() {
        log.info("Health check!");
        return new ResponseEntity(HttpStatus.OK);
    }
}
