package de.szut.lf8_starter.project.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.util.Date;

@Data
public class ProjectCreateDto {

    @NotBlank
    @Size(max = 50, message = "name cannot exceed 50 characters")
    private String name;

    private long responsibleEmployee;
    private long customer;

    @NotBlank
    @Size(max = 50, message = "name cannot exceed 50 characters")
    private String customerName;

    @NotBlank
    private String description;

    private Date startDate;
    private Date plannedDate;
    private Date endDate;

    @JsonCreator
    public ProjectCreateDto(String name, long responsibleEmployee, long customer, String customerName, String description, Date startDate, Date plannedDate, Date endDate) {
        this.name = name;
        this.responsibleEmployee = responsibleEmployee;
        this.customer = customer;
        this.customerName = customerName;
        this.description = description;
        this.startDate = startDate;
        this.plannedDate = plannedDate;
        this.endDate = endDate;
    }
}
