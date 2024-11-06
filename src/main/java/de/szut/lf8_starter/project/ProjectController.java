package de.szut.lf8_starter.project;

import de.szut.lf8_starter.employee.dto.AddEmployeeToProject;
import de.szut.lf8_starter.employee.dto.DeleteEmployeeDTO;
import de.szut.lf8_starter.project.dto.ProjectPostDTO;
import de.szut.lf8_starter.project.dto.ProjectGetDTO;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "projects")
@PreAuthorize("hasAnyAuthority('user')")
@RequiredArgsConstructor
@Getter
@Setter
public class ProjectController implements ProjectControllerOpenAPI {
    private final ProjectService service;
    private final ProjectMapper projectMapper;

    // TODO (/deleteEmployee/{projectId}/{employeeId} check RequestParam) & Qualifikation ist falsch
    @Override
    @PostMapping("/create")
    public ResponseEntity<ProjectGetDTO> create(@RequestBody @Valid ProjectPostDTO projectCreateDto,
                                                @RequestHeader(name = "Authorization") String token) {
        return new ResponseEntity<>(this.service.create(projectCreateDto, token), HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<ProjectGetDTO> update(@PathVariable Integer id,
                                                @RequestBody @Valid ProjectPostDTO projectUpdateDto,
                                                @RequestHeader(name = "Authorization") String token) {
        return new ResponseEntity<>(this.service.update(id, projectUpdateDto, token), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProjectById(@PathVariable Integer id) {
        this.service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ProjectGetDTO>> findAllProjects() {
        return new ResponseEntity<>(this.service.findAll(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProjectGetDTO> getProjectById(@PathVariable Integer id) {
        return new ResponseEntity<>(this.service.findById(id), HttpStatus.OK);
    }

    @Override
    @PostMapping("/addEmployeeInProject")
    public ResponseEntity<AddEmployeeToProject> addEmployeeInProject(@RequestBody @Valid AddEmployeeToProject addEmployeeToProject,
                                                                     @RequestHeader(name = "Authorization") String token) {
        boolean isAdded = service.addEmployeeToProject(addEmployeeToProject, token);
        if (isAdded) {
            return new ResponseEntity<>(addEmployeeToProject, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    @DeleteMapping("/deleteEmployee")
    public ResponseEntity<Void> deleteEmployeeFromProject(@RequestBody DeleteEmployeeDTO deleteEmployeeDTO) {
        this.service.deleteEmployeeFromProject(deleteEmployeeDTO.getProjectId(), deleteEmployeeDTO.getEmployeeId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @GetMapping("/findAllProjectsByEmployee/{employeeId}")
    public ResponseEntity<List<ProjectGetDTO>> findAllProjectsByEmployee(@PathVariable Integer employeeId, @RequestHeader(name = "Authorization") String token) {
        var getAllProjectsByEmployee = this.service.findAllProjectsByEmployee(employeeId);
        return new ResponseEntity<>(getAllProjectsByEmployee, HttpStatus.OK);
    }
    @Override
    @GetMapping("/findAllEmployeesByProject/{projectId}")
    public ResponseEntity<List<ProjectGetDTO>> findAllEmployeesByProject(@PathVariable Integer projectId, @RequestHeader(name = "Authorization") String token) {
        List<ProjectGetDTO> employeesByProject = service.findAllEmployeesByProject(projectId);
        return new ResponseEntity<>(employeesByProject, HttpStatus.OK);
    }

}
