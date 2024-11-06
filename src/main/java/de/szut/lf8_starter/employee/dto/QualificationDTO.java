package de.szut.lf8_starter.employee.dto;

import de.szut.lf8_starter.employee.Qualification;
import lombok.Data;

import java.util.List;

@Data
public class QualificationDTO {
//    private Integer employeeId;
    private List<Qualification> skillSet;

}
