package de.szut.lf8_starter.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProjectGetDTO {

    private long id;

    private String name;
//
//    private long responsibleEmployee;
//    private long customer;
//
//    private String customerName;
//
//    private String description;
//
//    private LocalDate startDate;
//    private LocalDate plannedDate;
//    private LocalDate endDate;
    private List<Integer> employeeIds;
    private List<String> qualificationIds;
}
