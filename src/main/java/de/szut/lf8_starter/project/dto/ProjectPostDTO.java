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
    private Integer id;

    private Integer responsibleEmployeeId;

    private Integer customerId;
    private String customerName;

    private String name;

    private String customerContactName;

    private String comment;

    private LocalDate startDate;

    private LocalDate plannedEndDate;

    private LocalDate actualEndDate;
    private List<Integer> projectQualificationIds;

}
