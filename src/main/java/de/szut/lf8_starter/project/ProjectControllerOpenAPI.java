package de.szut.lf8_starter.project;

import de.szut.lf8_starter.project.dto.AddEmployeeInProject;
import de.szut.lf8_starter.project.dto.ProjectGetDTO;
import de.szut.lf8_starter.project.dto.ProjectPostDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ProjectControllerOpenAPI {

    @Operation(summary = "Erstellt ein neues Projekt mit seiner ID und den Details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Projekt erfolgreich erstellt",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectGetDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Ungültiges JSON-Format",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Nicht autorisiert",
                    content = @Content)})
    ResponseEntity<ProjectGetDTO> create(ProjectPostDTO projectPostDto, String token);

    @Operation(summary = "aktualisieren des Projekt mit der ID ist ungültig")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projekt erfolgreich aktualisiert",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectGetDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Ungültiges JSON-Format",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Nicht autorisiert",
                    content = @Content)})
    ResponseEntity<ProjectGetDTO> update (Integer id, @RequestBody @Valid ProjectPostDTO projectUpdateDto, String token);

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteProjectById(@PathVariable Integer id);

    @Operation(summary = "Liefert eine Liste von Projekten")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste der Projekte",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProjectGetDTO.class)))}),
            @ApiResponse(responseCode = "401", description = "Nicht autorisiert",
                    content = @Content)})
    ResponseEntity<List<ProjectGetDTO>> findAllProjects();

    @GetMapping("/{id}")
    ResponseEntity<ProjectGetDTO> getProjectById(@PathVariable Integer id);

    @PostMapping("/addEmployeeInProject")
    ResponseEntity<AddEmployeeInProject> addEmployeeInProject(AddEmployeeInProject addEmployeeInProject, String token);

    @DeleteMapping("/deleteEmployee/{pid}/{eid}")
    ResponseEntity<Void> deleteEmployeeFromProject(@PathVariable Integer pid, @PathVariable Integer eid);

    @Operation(summary = "Findet Projekte nach Qualifikation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste der Projekte mit der angegebenen Qualifikation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectGetDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Qualifikationsbeschreibung existiert nicht",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Nicht autorisiert",
                    content = @Content)})
    ResponseEntity<List<ProjectGetDTO>> findAllEmployeesByQualification(String qualificationMessage);
}
