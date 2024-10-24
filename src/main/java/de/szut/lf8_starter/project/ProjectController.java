package de.szut.lf8_starter.project;



import de.szut.lf8_starter.employeeTest.dto.SkillDTO;
import de.szut.lf8_starter.project.dto.AddEmployeeInProject;
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
public class ProjectController implements ProjectControllerOpenAPI{
    private final ProjectService service;
    private ProjectMapper projectMapper;

    @Override
    @PostMapping("/create")
    public ResponseEntity<ProjectGetDTO> create(@RequestBody @Valid ProjectPostDTO projectCreateDto, @RequestHeader (name="Authorization") String token) {
        return new ResponseEntity<>(this.service.create(projectCreateDto, token), HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<ProjectGetDTO> update(@PathVariable Integer id, @RequestBody @Valid ProjectPostDTO projectUpdateDto, @RequestHeader (name="Authorization") String token){
        return new ResponseEntity<>(this.service.update(id, projectUpdateDto, token), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProjectById(@PathVariable Integer id){
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
    public ResponseEntity<ProjectGetDTO> getProjectById(@PathVariable Integer id){
        return new ResponseEntity<>(this.service.findById(id), HttpStatus.OK);
    }

    @PostMapping("/addEmployeeInProject")
    public ResponseEntity<AddEmployeeInProject> addEmployeeInProject(@RequestBody @Valid AddEmployeeInProject addEmployeeInProject, @RequestHeader(name = "Authorization") String token) {
        boolean isAdded = service.addEmployeeToProject(addEmployeeInProject, token);
        if (isAdded) {
            return new ResponseEntity<>(addEmployeeInProject, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @DeleteMapping("/deleteEmployee/{pid}/{eid}")
    public ResponseEntity<Void> deleteEmployeeFromProject(@PathVariable Integer pid, @PathVariable Integer eid){
        this.service.deleteEmployeeFromProject(pid, eid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ProjectGetDTO>> findAllEmployeesByQualification(String qualificationMessage) {
        return null;
    }
}
