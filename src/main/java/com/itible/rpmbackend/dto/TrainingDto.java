package com.itible.rpmbackend.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class TrainingDto {
    private Date date;
    private List<Integer> rpm;
    private Integer duration;
    private BigDecimal avgRpm;
    private BigDecimal avgRpmTime;
    private String personName;
}
