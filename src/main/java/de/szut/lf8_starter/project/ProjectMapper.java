package de.szut.lf8_starter.project;

import de.szut.lf8_starter.project.dto.ProjectCreateDto;
import de.szut.lf8_starter.project.dto.ProjectGetDto;
import org.springframework.stereotype.Service;

@Service
public class ProjectMapper {

    public ProjectGetDto mapToGetDto(ProjectEntity entity) {
        return new ProjectGetDto(entity.getId(), entity.getName(), entity.getResponsibleEmployee(), entity.getCustomer(), entity.getCustomerName(), entity.getDescription(), entity.getStartDate(), entity.getPlannedDate(), entity.getEndDate() );

    }

    public ProjectEntity mapCreateDtoToEntity(ProjectCreateDto dto) {
        var entity = new ProjectEntity();
        entity.setName(dto.getName());
        entity.setResponsibleEmployee(dto.getResponsibleEmployee());
        entity.setCustomer(dto.getCustomer());
        entity.setCustomerName(dto.getCustomerName());
        entity.setDescription(dto.getDescription());
        entity.setStartDate(dto.getStartDate());
        entity.setPlannedDate(dto.getPlannedDate());
        entity.setEndDate(dto.getEndDate());
        return entity;
    }

}
