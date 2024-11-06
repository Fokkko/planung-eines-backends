package de.szut.lf8_starter.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProjectsByEmployeeIdDTO {
    private Integer employeeId;
    private Integer projectId;
    private String projectName;
    private LocalDate projectStartDate;
    private LocalDate plannedEndDate;
}
