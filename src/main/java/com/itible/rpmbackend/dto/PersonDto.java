package com.itible.rpmbackend.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PersonDto {
    private String name;
    private Integer numberOfTrainings;
}
