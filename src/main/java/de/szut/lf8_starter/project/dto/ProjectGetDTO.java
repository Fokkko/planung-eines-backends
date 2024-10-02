package de.szut.lf8_starter.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.Date;

@AllArgsConstructor
@Data
public class ProjectGetDTO {

    private long id;

    private String name;

    private long responsibleEmployee;
    private long customer;

    private String customerName;

    private String description;

    private Date startDate;
    private Date plannedDate;
    private Date endDate;

}
