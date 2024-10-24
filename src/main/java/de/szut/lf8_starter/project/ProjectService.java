package de.szut.lf8_starter.project;

import de.szut.lf8_starter.employeeTest.EmployeeService;
import de.szut.lf8_starter.employeeTest.dto.SkillDTO;
import de.szut.lf8_starter.project.dto.AddEmployeeInProject;
import de.szut.lf8_starter.project.dto.ProjectGetDTO;
import de.szut.lf8_starter.project.dto.ProjectPostDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository repository;
    private final ProjectMapper projectMapper;
    private final EmployeeService employeeService;


    public ProjectGetDTO create(ProjectPostDTO dto, String token) {
        if (employeeService.getEmployeeById(dto.getResponsibleEmployeeId(), token)) {

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

        if (entityOptional.isPresent()) {
            ProjectEntity existingEntity = entityOptional.get();

            if (employeeService.getEmployeeById(dtoToUpdate.getResponsibleEmployeeId(), token)) {
                existingEntity.setName(dtoToUpdate.getName());
                existingEntity.setResponsibleEmployeeId(dtoToUpdate.getResponsibleEmployeeId());
                existingEntity.setCustomerId(dtoToUpdate.getSetCustomerId());
                existingEntity.setCustomerContactName(dtoToUpdate.getClientContactPerson());
                existingEntity.setComment(dtoToUpdate.getComment());
                existingEntity.setStartDate(dtoToUpdate.getStartDate());
                existingEntity.setPlannedEndDate(dtoToUpdate.getPlannedEndDate());
                existingEntity.setActualEndDate(dtoToUpdate.getActualEndDate());
                existingEntity.setQualificationIds(dtoToUpdate.getQualificationIds());

                repository.save(existingEntity);

                return projectMapper.projectEntityToDTO(existingEntity);
            } else {
                throw new IllegalArgumentException("Verantwortlicher Mitarbeiter existiert nicht.");
            }
        } else {
            throw new EntityNotFoundException("Projekt mit der ID " + id + " nicht gefunden.");
        }
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
                .collect(Collectors.toList());
    }

    public ProjectGetDTO findById(Integer id){
        Optional<ProjectEntity> entity = this.repository.findById(id);
        if (entity.isPresent()) {
            var dto = this.projectMapper.projectEntityToDTO(entity.get());
            return dto;
        }else {
            return null;
        }
    }

    public boolean addEmployeeToProject(AddEmployeeInProject addEmployeeInProject, String token) {
        Optional<ProjectEntity> projectEntityOpt = repository.findById(addEmployeeInProject.getPid());
        if (!projectEntityOpt.isPresent()) {
            return false;
        }

        ProjectEntity project = projectEntityOpt.get();

        SkillDTO skillDTO = employeeService.employeeForQualification(addEmployeeInProject.getEid(), token);
        if (skillDTO == null || !isQualifiedForProject(skillDTO, project)) {
            return false;
        }

        if (project.getEmployeeIds() == null) {
            project.setEmployeeIds(new ArrayList<>());
        }

        if (!project.getEmployeeIds().contains(addEmployeeInProject.getEid())) {
            project.getEmployeeIds().add(addEmployeeInProject.getEid());
            repository.save(project);
        }

        return true;
    }

    private boolean isQualifiedForProject(SkillDTO skillDTO, ProjectEntity project) {
        List<String> requiredQualifications = project.getQualificationIds();
        List<String> employeeQualifications = skillDTO.getSkillSet();

        if (requiredQualifications != null && employeeQualifications != null) {
            for (String requiredQualification : requiredQualifications) {
                if (!employeeQualifications.contains(requiredQualification)) {
                    return false;
                }
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
