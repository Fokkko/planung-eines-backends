package de.szut.lf8_starter.employee.dto;

import lombok.Data;

@Data
public class EmployeeToProjectResponseDto {
    private Integer employeeId;
    private Integer projectId;
    private Integer skillsId;
    private String ProjectName;
}
