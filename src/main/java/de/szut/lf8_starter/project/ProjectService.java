package de.szut.lf8_starter.project;

import de.szut.lf8_starter.employee.EmployeeService;
import de.szut.lf8_starter.employee.Skill;
import de.szut.lf8_starter.employee.dto.SkillDTO;
import de.szut.lf8_starter.project.dto.AddEmployeeToProject;
import de.szut.lf8_starter.project.dto.ProjectGetDTO;
import de.szut.lf8_starter.project.dto.ProjectPostDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository repository;
    private final ProjectMapper projectMapper;
    private final EmployeeService employeeService;


    public ProjectGetDTO create(ProjectPostDTO dto, String token) {

        if (employeeService.checkEmployeeExists(dto.getResponsibleEmployeeId(), token)) {

            ProjectEntity projectEntity = projectMapper.projectDTOToEntity(dto);

//            projectEntity.setEmployeeIds(dto.getEmployeeIds());

            repository.save(projectEntity);

            return projectMapper.projectEntityToDTO(projectEntity);
        } else {
            throw new EntityNotFoundException("Mitarbeiter mit ID " + dto.getResponsibleEmployeeId() + " nicht gefunden.");
        }
    }

    public ProjectGetDTO update(Integer id, ProjectPostDTO dtoToUpdate, String token) {
        Optional<ProjectEntity> entityOptional = repository.findById(id);

        if (entityOptional.isEmpty())
            throw new EntityNotFoundException("Projekt mit der ID " + id + " nicht gefunden.");
        ProjectEntity existingEntity = entityOptional.get();
        if (!employeeService.checkEmployeeExists(dtoToUpdate.getResponsibleEmployeeId(), token))
            throw new IllegalArgumentException("Verantwortlicher Mitarbeiter existiert nicht.");

        ProjectEntity updateDtoToEntity = this.projectMapper.projectDTOToEntity(dtoToUpdate);
        repository.save(updateDtoToEntity);

        return this.projectMapper.projectEntityToDTO(updateDtoToEntity);
    }

    public void deleteById(Integer id){
        var entity = this.repository.findById(id);
        if (entity.isPresent()){
            this.repository.deleteById(id);
        }
    }

    public List<ProjectGetDTO> findAll() {
        List<ProjectEntity> entityList = this.repository.findAll();
        return entityList.stream()
                .map(projectMapper::projectEntityToDTO)
                .toList();
    }

    public ProjectGetDTO findById(Integer id){
        Optional<ProjectEntity> entity = this.repository.findById(id);
        if (entity.isEmpty()) {
            return null;
        }
        return this.projectMapper.projectEntityToDTO(entity.get());
    }

    public boolean addEmployeeToProject(AddEmployeeToProject addEmployeeToProject, String token) {
        Optional<ProjectEntity> projectEntityOpt = repository.findById(addEmployeeToProject.getProjectId());
        if (!projectEntityOpt.isPresent()) return false;

        ProjectEntity project = projectEntityOpt.get();

        if (project.getEmployeeIds() == null)
            project.setEmployeeIds(new ArrayList<>());

        var isQualified = isQualifiedForProject(addEmployeeToProject.getSkillsId(), project);
        var isEmployeeExists = employeeService.checkEmployeeExists(addEmployeeToProject.getEmployeeId(), token);
        var isEmployeeExistsInProject = !project.getEmployeeIds().contains(addEmployeeToProject.getEmployeeId());

        if (isQualified && isEmployeeExists && isEmployeeExistsInProject){
            project.getEmployeeIds().add(addEmployeeToProject.getEmployeeId());
            repository.save(project);
            return true;
        }
        return false;
    }

    private boolean isQualifiedForProject(Integer skillId, ProjectEntity project) {
        List<Integer> requiredQualifications = project.getProjectQualificationIds();

        if (requiredQualifications == null || skillId == null)
            return false;
            if (requiredQualifications.contains(skillId)) return true;

        return false;
    }

    public void deleteEmployeeFromProject(Integer pid, Integer eid) {
        Optional<ProjectEntity> entityOptional = this.repository.findById(pid);

        if (entityOptional.isPresent()) {
            ProjectEntity entity = entityOptional.get();

            if (entity.getEmployeeIds() != null && entity.getEmployeeIds().contains(eid)) {
                entity.getEmployeeIds().remove(eid);
                this.repository.save(entity);
            }
        } else {
            throw new EntityNotFoundException("Projekt mit der ID " + pid + " nicht gefunden.");
        }
    }

    public List<ProjectGetDTO> findAllEmployeesByQualification(String message) {
//        // Assuming you have a method in the repository to find projects by qualification
//        List<ProjectEntity> projects = this.repository.findByQualification(message);
//        return projects.stream()
//                .map(ProjectMapper.INSTANCE::projectEntityToDTO)
//                .collect(Collectors.toList());
        return null;
    }
}
