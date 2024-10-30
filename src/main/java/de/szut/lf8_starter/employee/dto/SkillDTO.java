package de.szut.lf8_starter.employee.dto;

import de.szut.lf8_starter.employee.Skill;
import lombok.Data;

import java.util.List;

@Data
public class SkillDTO {
    private Integer employeeId;
    private List<Skill> skillIds;

}
