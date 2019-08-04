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

    private Date date;

    @ElementCollection(targetClass = Integer.class)
    private List<Integer> rpm;

    private Integer duration;

    private Double averageRpm;
    private Double averageRpmByTime;

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

    public Double getAverageRpm() {
        return averageRpm;
    }

    public void setAverageRpm(Double averageRpm) {
        this.averageRpm = averageRpm;
    }

    public Double getAverageRpmByTime() {
        return averageRpmByTime;
    }

    public void setAverageRpmByTime(Double averageRpmByTime) {
        this.averageRpmByTime = averageRpmByTime;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
