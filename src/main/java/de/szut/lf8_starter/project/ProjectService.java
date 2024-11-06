package de.szut.lf8_starter.project;

import de.szut.lf8_starter.employee.EmployeeService;
import de.szut.lf8_starter.employee.dto.AddEmployeeToProject;
import de.szut.lf8_starter.exceptionHandling.ResourceNotFoundException;
import de.szut.lf8_starter.project.dto.ProjectGetDTO;
import de.szut.lf8_starter.project.dto.ProjectPostDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository repository;
    private final ProjectMapper projectMapper;
    private final EmployeeService employeeService;

// TODO Update false

    public ProjectGetDTO create(ProjectPostDTO dto, String token) {

        if (employeeService.checkEmployeeExists(dto.getResponsibleEmployeeId(), token)) {

            ProjectEntity projectEntity = projectMapper.projectDTOToEntity(dto);

            repository.save(projectEntity);

            return projectMapper.projectEntityToDTO(projectEntity);
        } else {
            throw new EntityNotFoundException("Mitarbeiter mit ID " + dto.getResponsibleEmployeeId() + " nicht gefunden.");
        }
    }

    public ProjectGetDTO update(ProjectPostDTO dtoToUpdate, String token) {
        var id = dtoToUpdate.getId();
        Optional<ProjectEntity> entityOptional = repository.findById(id);

        if (entityOptional.isEmpty())
            throw new EntityNotFoundException("Projekt mit der ID " + id + " nicht gefunden.");
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
        } else {
            throw new ResourceNotFoundException("Projekt mit der ID " + id + " nicht gefunden.");
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
        if (projectEntityOpt.isEmpty()) return false;

        ProjectEntity project = projectEntityOpt.get();

        if (project.getEmployeeIds() == null)
            project.setEmployeeIds(new ArrayList<>());

        boolean isEmployeeExistsInProject = !project.getEmployeeIds().contains(addEmployeeToProject.getEmployeeId());
        boolean isQualifiedInProject = isQualifiedInProject(addEmployeeToProject.getSkillsId(), project);
        boolean isQualifiedInService = employeeService.isQualifiedInService(addEmployeeToProject.getEmployeeId(), addEmployeeToProject.getSkillsId(), token);
        boolean isEmployeeExists = employeeService.checkEmployeeExists(addEmployeeToProject.getEmployeeId(), token);
        boolean isEmployeeAvailable = isEmployeeAvailableInProjectPeriod(
                addEmployeeToProject.getEmployeeId(),
                project.getStartDate(),
                project.getPlannedEndDate()
        );

        if (isQualifiedInProject && isEmployeeAvailable && isQualifiedInService && isEmployeeExists && isEmployeeExistsInProject){
            project.getEmployeeIds().add(addEmployeeToProject.getEmployeeId());
            repository.save(project);
            return true;
        }
        return false;
    }

    private boolean isQualifiedInProject(Integer qualificationId, ProjectEntity project) {
        List<Integer> requiredQualifications = project.getProjectQualificationIds();

        if (requiredQualifications == null || qualificationId == null)
            return false;
            if (requiredQualifications.contains(qualificationId)) return true;

        return false;
    }

    private boolean isEmployeeAvailableInProjectPeriod(Integer employeeId, LocalDate projectStartDate, LocalDate projectEndDate) {
        List<ProjectEntity> project = repository.findProjectsByEmployeeId(employeeId);

        for (ProjectEntity assignedProject : project) {
            if (!(assignedProject.getPlannedEndDate().isBefore(projectStartDate) || assignedProject.getStartDate().isAfter(projectEndDate))) {
                return false;
            }
        }
        return true;
    }


    public void deleteEmployeeFromProject(Integer pid, Integer eid) {
        Optional<ProjectEntity> entityOptional = this.repository.findById(pid);

        if (entityOptional.isPresent()) {
            ProjectEntity entity = entityOptional.get();

            if (entity.getEmployeeIds() != null && entity.getEmployeeIds().contains(eid)) {
                entity.getEmployeeIds().remove(eid);
                this.repository.save(entity);
            } else {
                throw new ResourceNotFoundException("Mitarbeiter mit der ID " + eid + " nicht gefunden.");
            }
        } else {
            throw new ResourceNotFoundException("Projekt mit der ID " + pid + " nicht gefunden.");
        }
    }

    public List<ProjectGetDTO> findAllProjectsByEmployee(Integer employeeId) {
        List<ProjectEntity> projects = this.repository.findByEmployeeIdsContains(employeeId);
        return projects.stream()
                .map(project -> projectMapper.projectEntityToDTO(project))
                .collect(Collectors.toList());
    }

    public List<Integer> findAllEmployeesByProject(Integer projectId) {
        return repository.findById(projectId)
                .map(ProjectEntity::getEmployeeIds)
                .orElse(Collections.emptyList());


    }

}
