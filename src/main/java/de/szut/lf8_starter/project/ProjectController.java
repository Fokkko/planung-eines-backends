package de.szut.lf8_starter.project;

import de.szut.lf8_starter.project.dto.AddEmployeeToProject;
import de.szut.lf8_starter.project.dto.ProjectPostDTO;
import de.szut.lf8_starter.project.dto.ProjectGetDTO;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
@Getter
@Setter
public class ProjectController implements ProjectControllerOpenAPI {
    private final ProjectService service;
    private final ProjectMapper projectMapper;

    @Override
    @PostMapping("/create")
    public ResponseEntity<ProjectGetDTO> create(@RequestBody @Valid ProjectPostDTO projectCreateDto) {
        String token = getJwtToken();
        return new ResponseEntity<>(this.service.create(projectCreateDto, token), HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<ProjectGetDTO> update(@PathVariable Integer id,
                                                @RequestBody @Valid ProjectPostDTO projectUpdateDto) {
        String token = getJwtToken();
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
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    @DeleteMapping("/deleteEmployee/{projectId}/{employeeId}")
    public ResponseEntity<Void> deleteEmployeeFromProject(@PathVariable Integer projectId,
                                                          @PathVariable Integer employeeId) {
        this.service.deleteEmployeeFromProject(projectId, employeeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @GetMapping("/findByQualification")
    public ResponseEntity<List<ProjectGetDTO>> findAllEmployeesByQualification(@RequestParam String qualificationMessage) {
//        List<ProjectGetDTO> projects = service.findProjectsByQualification(qualificationMessage);
//        if (projects.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(projects, HttpStatus.OK);
        return null;
    }

    private String getJwtToken() {
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Jwt jwt = (Jwt) authentication.getToken();
            return "Bearer " + jwt.getTokenValue();
        }
        return null;
    }
}
