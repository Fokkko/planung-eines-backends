package de.szut.lf8_starter.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProjectGetDTO {

    private long projectId;

    private String projectName;

    private Integer responsibleEmployee;
    private Integer customerId;
    private String customerName;
    private String comment;
    private LocalDate startDate;
    private LocalDate plannedDate;
    private LocalDate endDate;
    private List<Integer> employeeIds;
    private List<Integer> projectQualificationIds;
}
