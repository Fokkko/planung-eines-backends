package de.szut.lf8_starter.project;

import de.szut.lf8_starter.exceptionHandling.ResourceNotFoundException;
import de.szut.lf8_starter.project.dto.ProjectPostDTO;
import de.szut.lf8_starter.project.dto.ProjectGetDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProjectRepository repository;

    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    public ProjectGetDTO create(ProjectPostDTO dto) {
        ProjectEntity getProjectDto = ProjectMapper.INSTANCE.projectDTOToEntity(dto);
        this.repository.save(getProjectDto);
        return ProjectMapper.INSTANCE.projectEntityToDTO(getProjectDto);
    }

    public List<ProjectGetDTO> readAll() {
        List<ProjectEntity> entityList = this.repository.findAll();
        return entityList.stream().map(ProjectMapper.INSTANCE::projectEntityToDTO).collect(Collectors.toList());
    }

    public ProjectEntity readById(long id) {
        Optional<ProjectEntity> optionalQualification = this.repository.findById(id);
        if (optionalQualification.isPresent()){
            return optionalQualification.get();
        }
        throw new ResourceNotFoundException("Project not found on id " + id);
    }

    public ProjectEntity getProjectById(Long projectId) {
        return readById(projectId);
    }

    public void deleteById (long id){
        Optional<ProjectEntity> entity = this.repository.findById(id);
        if (entity.isPresent()){
            this.repository.deleteById(id);
        }
    }
//
//    public ProjectDto update(CreateProjectDto dto){
//        Optional<ProjectEntity> entityOptional = this.repository.findById(dto.getId());
//        if (entityOptional.isPresent()){
//            ProjectEntity entity = entityOptional.get();
//
//            entity.setCustomerId(dto.getSetCustomerId());
//            entity.setComment(dto.getComment());
//            entity.setName(dto.getName());
//            entity.setCustomerContactName(dto.getClientContactPerson());
//            entity.setStartDate(dto.getStartDate());
//            entity.setActualEndDate(dto.getActualEndDate());
//            entity.setPlannedEndDate(dto.getPlannedEndDate());
//            return ProjectMapper.INSTANCE.projectEntityToDTO(entity);
//        }
//        return null;
//    }


}
