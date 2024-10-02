package de.szut.lf8_starter.employee.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class EmployeeResponseDTO {
    private Integer id;
    private String lastName;
    private String firstname;
    private String street;
    @Size(min = 5, max = 5, message = "Die Postleitzahl muss genau 5 Ziffern lang sein.")
    private String postcode;
    private String city;
    private String phone;
    Set<SkillDTO> skillDTOSet = new HashSet<>();

}
