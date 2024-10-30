package de.szut.lf8_starter.project;

//import de.szut.lf8_starter.employeeTest.EmployeeEntity;
//import de.szut.lf8_starter.employeeTest.EmployeeRepository;
import de.szut.lf8_starter.project.dto.ProjectGetDTO;
import de.szut.lf8_starter.project.dto.ProjectPostDTO;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public ProjectGetDTO projectEntityToDTO(ProjectEntity entity) {
        if (entity == null) {
            return null;
        }

        ProjectGetDTO dto = new ProjectGetDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setResponsibleEmployee(entity.getResponsibleEmployeeId());
        dto.setCustomerId(entity.getCustomerId());
        dto.setCustomerContactName(entity.getCustomerContactName());
        dto.setDescription(entity.getComment());
        dto.setStartDate(entity.getStartDate());
        dto.setPlannedDate(entity.getPlannedEndDate());
        dto.setCustomerName(entity.getCustomerName());
        dto.setEndDate(entity.getActualEndDate());
        dto.setEmployeeIds(entity.getEmployeeIds());
        dto.setProjectQualificationIds(entity.getProjectQualificationIds());

        return dto;
    }

    public ProjectEntity projectDTOToEntity(ProjectPostDTO dto) {
        if (dto == null) {
            return null;
        }

        ProjectEntity entity = new ProjectEntity();
        entity.setName(dto.getName());
        entity.setResponsibleEmployeeId(dto.getResponsibleEmployeeId());
        entity.setCustomerId(dto.getCustomerId());
        entity.setCustomerContactName(dto.getCustomerContactName());
        entity.setCustomerName(dto.getCustomerName());
        entity.setComment(dto.getComment());
        entity.setStartDate(dto.getStartDate());
        entity.setPlannedEndDate(dto.getPlannedEndDate());
        entity.setActualEndDate(dto.getActualEndDate());
        if (dto.getProjectQualificationIds() != null && !dto.getProjectQualificationIds().isEmpty()){
            entity.setProjectQualificationIds(dto.getProjectQualificationIds());
        }

        return entity;
    }
}
