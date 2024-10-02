package de.szut.lf8_starter.project;

<<<<<<< HEAD

import de.szut.lf8_starter.project.dto.ProjectPostDTO;
import de.szut.lf8_starter.project.dto.ProjectGetDTO;
=======
import de.szut.lf8_starter.exceptionHandling.ResourceNotFoundException;
import de.szut.lf8_starter.project.dto.ProjectCreateDto;
import de.szut.lf8_starter.project.dto.ProjectGetDto;
>>>>>>> origin/main
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
<<<<<<< HEAD
import org.springframework.http.ResponseEntity;
=======
>>>>>>> origin/main
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
<<<<<<< HEAD

@RestController
@RequestMapping(value = "hello")
@PreAuthorize("hasAnyAuthority('user')")
public class ProjectController {
    private final ProjectService service;
    private final ProjectMapper helloMapper;

    public ProjectController(ProjectService service, ProjectMapper mappingService) {
        this.service = service;
        this.helloMapper = mappingService;
    }

    @Operation(summary = "create a new project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created project",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectGetDTO.class))}),
=======
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "project")
@PreAuthorize("hasAnyAuthority('user')")
public class ProjectController {
    private final ProjectService service;
    private final ProjectMapper projectMapper;

    public ProjectController(ProjectService service, ProjectMapper mappingService) {
        this.service = service;
        this.projectMapper = mappingService;
    }

    @Operation(summary = "creates a new project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created project",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectGetDto.class))}),
>>>>>>> origin/main
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content)})
    @PostMapping
<<<<<<< HEAD
    public ResponseEntity<ProjectGetDTO> create(@RequestBody @Valid ProjectPostDTO projectCreateDto) {
        return new ResponseEntity<>(this.service.create(projectCreateDto), HttpStatus.CREATED);
=======
    public ProjectGetDto create(@RequestBody @Valid ProjectCreateDto projectCreateDto) {
        ProjectEntity projectEntity = this.projectMapper.mapCreateDtoToEntity(projectCreateDto);
        projectEntity = this.service.create(projectEntity);
        return this.projectMapper.mapToGetDto(projectEntity);
>>>>>>> origin/main
    }

    @Operation(summary = "delivers a list of projects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "list of projects",
                    content = {@Content(mediaType = "application/json",
<<<<<<< HEAD
                            schema = @Schema(implementation = ProjectGetDTO.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<List<ProjectGetDTO>> findAll() {
        return new ResponseEntity<>(this.service.readAll(), HttpStatus.OK);
    }

//    @Operation(summary = "deletes project id")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "204", description = "delete successful"),
//            @ApiResponse(responseCode = "401", description = "not authorized",
//                    content = @Content),
//            @ApiResponse(responseCode = "404", description = "resource not found",
//                    content = @Content)})
//    @DeleteMapping("/{id}")
//    @ResponseStatus(code = HttpStatus.NO_CONTENT)
//    public void deleteById(@PathVariable long id) {
//        var entity = this.service.readById(id);
//        if (entity == null) {
//            throw new ResourceNotFoundException("ProjectEntity not found on id = " + id);
//        } else {
//            this.service.delete(entity);
//        }
//    }
//
//    @Operation(summary = "find hellos by message")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "List of hellos who have the given message",
//                    content = {@Content(mediaType = "application/json",
//                            schema = @Schema(implementation = ProjectDto.class))}),
//            @ApiResponse(responseCode = "404", description = "qualification description does not exist",
//                    content = @Content),
//            @ApiResponse(responseCode = "401", description = "not authorized",
//                    content = @Content)})
//    @GetMapping("/findByMessage")
//    public List<ProjectDto> findAllEmployeesByQualification(@RequestParam String message) {
//        return this.service
//                .findByMessage(message)
//                .stream()
//                .map(e -> this.helloMapper.mapToGetDto(e))
//                .collect(Collectors.toList());
//    }
=======
                            schema = @Schema(implementation = ProjectGetDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content)})
    @GetMapping
    public List<ProjectGetDto> findAll() {
        return this.service
                .readAll()
                .stream()
                .map(e -> this.projectMapper.mapToGetDto(e))
                .collect(Collectors.toList());
    }

    @Operation(summary = "deletes a project by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "delete successful"),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "resource not found",
                    content = @Content)})
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteProjectById(@PathVariable long id) {
        var entity = this.service.readById(id);
        if (entity == null) {
            throw new ResourceNotFoundException("ProjectEntity not found on id = " + id);
        } else {
            this.service.delete(entity);
        }
    }

>>>>>>> origin/main
}
