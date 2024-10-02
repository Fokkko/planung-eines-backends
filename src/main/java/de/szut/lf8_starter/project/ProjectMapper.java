package de.szut.lf8_starter.project;


import de.szut.lf8_starter.project.dto.ProjectPostDTO;
import de.szut.lf8_starter.project.dto.ProjectGetDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    ProjectGetDTO projectEntityToDTO(ProjectEntity project);
    ProjectEntity projectDTOToEntity(ProjectPostDTO dto);

}
