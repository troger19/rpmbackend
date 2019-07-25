package com.itible.rpmbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Training implements Serializable {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", unique = true, nullable = false)
    private Long trainingId;

    private Date date; //TODO specify format in which to save the date  "yyyy-MM-dd HH:mm:ss")

    @ElementCollection(targetClass = Integer.class)
    private List<Integer> rpm;

    private Integer duration;

    private Double average;

    @JsonIgnore
    @ManyToOne
    private Person person;

    public Training() {
    }

    public Long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Long trainingId) {
        this.trainingId = trainingId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Integer> getRpm() {
        return rpm;
    }

    public void setRpm(List<Integer> rpm) {
        this.rpm = rpm;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
