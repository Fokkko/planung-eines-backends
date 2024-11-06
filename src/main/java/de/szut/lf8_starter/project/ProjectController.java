package de.szut.lf8_starter.project;

import de.szut.lf8_starter.employee.dto.AddEmployeeToProject;
import de.szut.lf8_starter.employee.dto.DeleteEmployeeDTO;
import de.szut.lf8_starter.project.dto.ProjectPostDTO;
import de.szut.lf8_starter.project.dto.ProjectGetDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "projects")
@PreAuthorize("hasAnyAuthority('user')")
@RequiredArgsConstructor
public class ProjectController implements ProjectControllerOpenAPI {
    private final ProjectService service;
    private final ProjectMapper projectMapper;

    // TODO Mitarbeiter verplanen fehlt!
    @Override
    @PostMapping("/create")
    public ResponseEntity<ProjectGetDTO> create(@RequestBody @Valid ProjectPostDTO projectCreateDto) {
        String token = getJwtToken();
        return new ResponseEntity<>(this.service.create(projectCreateDto, token), HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/update")
    public ResponseEntity<ProjectGetDTO> update(@RequestBody @Valid ProjectPostDTO projectUpdateDto) {
        String token = getJwtToken();
        return new ResponseEntity<>(this.service.update(projectUpdateDto, token), HttpStatus.OK);
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
    public ResponseEntity<AddEmployeeToProject> addEmployeeInProject(@RequestBody @Valid AddEmployeeToProject addEmployeeToProject) {
        String token = getJwtToken();
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
    public ResponseEntity<List<ProjectGetDTO>> findAllProjectsByEmployee(@PathVariable Integer employeeId) {
        String token = getJwtToken();
        var getAllProjectsByEmployee = this.service.findAllProjectsByEmployee(employeeId);
        return new ResponseEntity<>(getAllProjectsByEmployee, HttpStatus.OK);
    }


    private String getJwtToken() {
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Jwt jwt = (Jwt) authentication.getToken();
            return "Bearer " + jwt.getTokenValue();
        }
        return null;
    }

    @Override
    @GetMapping("/findAllEmployeesByProject/{projectId}")
    public ResponseEntity<List<Integer>> findAllEmployeesByProject(@PathVariable Integer projectId) {
        String token = getJwtToken();
        var getAllEmployeesByProject = this.service.findAllEmployeesByProject(projectId);
        return new ResponseEntity<>(getAllEmployeesByProject, HttpStatus.OK);
    }


}
