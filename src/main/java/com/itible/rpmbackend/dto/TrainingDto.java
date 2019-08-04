package com.itible.rpmbackend.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TrainingDto {
    private Date date;
    private List<Integer> rpm;
    private Integer duration;
    private Double avgRpm;
    private Double avgRpmByTime;
    private String personName;
}
