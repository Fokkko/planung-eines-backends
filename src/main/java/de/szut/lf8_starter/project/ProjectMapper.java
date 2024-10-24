package de.szut.lf8_starter.project;

//import de.szut.lf8_starter.employeeTest.EmployeeEntity;
//import de.szut.lf8_starter.employeeTest.EmployeeRepository;
import de.szut.lf8_starter.project.dto.ProjectGetDTO;
import de.szut.lf8_starter.project.dto.ProjectPostDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class ProjectMapper {

//    private final EmployeeRepository employeeRepository;
//
//    public ProjectMapper(EmployeeRepository employeeRepository) {
//        this.employeeRepository = employeeRepository;
//    }

    public ProjectGetDTO projectEntityToDTO(ProjectEntity entity) {
        if (entity == null) {
            return null;
        }

        ProjectGetDTO dto = new ProjectGetDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
//        dto.setResponsibleEmployee(entity.getResponsibleEmployeeId());
//        dto.setCustomer(entity.getCustomerId());
//        dto.setCustomerName(entity.getCustomerContactName());
//        dto.setDescription(entity.getComment());
//        dto.setStartDate(entity.getStartDate());
//        dto.setPlannedDate(entity.getPlannedEndDate());
//        dto.setEndDate(entity.getActualEndDate());
        dto.setEmployeeIds(entity.getEmployeeIds());
        dto.setQualificationIds(entity.getQualificationIds());

        return dto;
    }

    public ProjectEntity projectDTOToEntity(ProjectPostDTO dto) {
        if (dto == null) {
            return null;
        }

        ProjectEntity entity = new ProjectEntity();
        entity.setName(dto.getName());
        entity.setResponsibleEmployeeId(dto.getResponsibleEmployeeId());
        entity.setCustomerId(dto.getSetCustomerId());
        entity.setCustomerContactName(dto.getClientContactPerson());
        entity.setComment(dto.getComment());
        entity.setStartDate(dto.getStartDate());
        entity.setPlannedEndDate(dto.getPlannedEndDate());
        entity.setActualEndDate(dto.getActualEndDate());
        if (dto.getQualificationIds() != null && !dto.getQualificationIds().isEmpty()){
            entity.setQualificationIds(dto.getQualificationIds());
        }

//        if (dto.getEmployeeIds() != null) {
//            entity.setEmployeeIds(new ArrayList<>(dto.getEmployeeIds()));
//        }

        return entity;
    }
}
