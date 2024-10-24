package de.szut.lf8_starter.employeeTest.dto;

import lombok.Data;

import java.util.List;

@Data
public class SkillDTO {
    private Integer id;
    private List<String> skillSet;

}
