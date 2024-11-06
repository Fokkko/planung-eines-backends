package de.szut.lf8_starter.employee.dto;

import lombok.Data;

@Data
public class DeleteEmployeeDTO {
    private Integer projectId;
    private Integer employeeId;
}
