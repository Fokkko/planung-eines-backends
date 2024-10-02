package de.szut.lf8_starter.project;


import de.szut.lf8_starter.project.dto.ProjectGetDto;
import de.szut.lf8_starter.project.dto.ProjectPostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    ProjectGetDto projectEntityToDTO(ProjectEntity project);
    ProjectEntity projectDTOToEntity(ProjectPostDTO dto);

}
