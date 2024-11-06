package de.szut.lf8_starter.employee.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddEmployeeToProject {
    private Integer employeeId;
    private Integer projectId;
    private Integer skillsId;
}
