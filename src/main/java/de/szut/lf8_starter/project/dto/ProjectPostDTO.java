package de.szut.lf8_starter.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class ProjectPostDTO {

    private Integer responsibleEmployeeId;

    private Integer setCustomerId;

    private String name;

    private String clientContactPerson;

    private String comment;

    private LocalDate startDate;

    private LocalDate plannedEndDate;

    private LocalDate actualEndDate;
    private List<String> qualificationIds;

}
