package com.itible.rpmbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Entity
@ToString
public class Training implements Serializable {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", unique = true, nullable = false)
    private Long trainingId;

    private Date date;

//    @ElementCollection(targetClass = Integer.class)
//    private List<Integer> rpm;

//    @ElementCollection(targetClass = Integer.class)
//    @CollectionTable(name = "TRAINING_RPMS", joinColumns = @JoinColumn(name = "ID"))
//    @Column(name = "RPM")
//    private List<Integer> rpm;

    @ElementCollection
    @CollectionTable(name = "RPM_BY_SECONDS", joinColumns = @JoinColumn(name = "ID"))
    @MapKeyColumn(name = "SECOND")
    @Column(name = "RPM")
    private Map<Integer, Integer> rpm;

    private Integer duration;

    private BigDecimal averageRpm;
    private BigDecimal averageRpmByTime;

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

//    public List<Integer> getRpm() {
//        return rpm;
//    }
//
//    public void setRpm(List<Integer> rpm) {
//        this.rpm = rpm;
//    }


    public Map<Integer, Integer> getRpm() {
        return rpm;
    }

    public void setRpm(Map<Integer, Integer> rpm) {
        this.rpm = rpm;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public BigDecimal getAverageRpm() {
        return averageRpm;
    }

    public void setAverageRpm(BigDecimal averageRpm) {
        this.averageRpm = averageRpm;
    }

    public BigDecimal getAverageRpmByTime() {
        return averageRpmByTime;
    }

    public void setAverageRpmByTime(BigDecimal averageRpmByTime) {
        this.averageRpmByTime = averageRpmByTime;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
