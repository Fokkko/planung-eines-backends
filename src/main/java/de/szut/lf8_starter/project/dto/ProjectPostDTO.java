package de.szut.lf8_starter.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@NoArgsConstructor
@Getter
@Setter
public class ProjectPostDTO {
    private long id;

    private Integer employeeId;

    private Integer setCustomerId;

    private String name;

    private String clientContactPerson;

    private String comment;

    private LocalDate startDate;

    private LocalDate plannedEndDate;

    private LocalDate actualEndDate;
}