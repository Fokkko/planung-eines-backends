package de.szut.lf8_starter.project;

import de.szut.lf8_starter.employee.dto.AddEmployeeToProject;
import de.szut.lf8_starter.employee.dto.DeleteEmployeeDTO;
import de.szut.lf8_starter.project.dto.ProjectGetDTO;
import de.szut.lf8_starter.project.dto.ProjectPostDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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

    @Operation(summary = "Aktualisiert ein Projekt mit der angegebenen ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projekt erfolgreich aktualisiert",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectGetDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Ungültiges JSON-Format",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Nicht autorisiert",
                    content = @Content)})
    ResponseEntity<ProjectGetDTO> update (Integer id, @RequestBody @Valid ProjectPostDTO projectUpdateDto, String token);

    @Operation(summary = "Löscht ein Projekt anhand der ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Projekt erfolgreich gelöscht",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Projekt nicht gefunden",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Nicht autorisiert",
                    content = @Content)})
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

    @Operation(summary = "Gibt ein Projekt anhand der ID zurück")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projekt erfolgreich gefunden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectGetDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Projekt nicht gefunden",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Nicht autorisiert",
                    content = @Content)})
    @GetMapping("/{id}")
    ResponseEntity<ProjectGetDTO> getProjectById(@PathVariable Integer id);

    @Operation(summary = "Fügt einem Projekt einen Mitarbeiter hinzu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mitarbeiter erfolgreich zum Projekt hinzugefügt",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddEmployeeToProject.class))}),
            @ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Projekt oder Mitarbeiter nicht gefunden",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Nicht autorisiert",
                    content = @Content)})
    @PostMapping("/addEmployeeInProject")
    ResponseEntity<AddEmployeeToProject> addEmployeeInProject(AddEmployeeToProject addEmployeeToProject, String token);

    @Operation(summary = "Entfernt einen Mitarbeiter aus einem Projekt")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Mitarbeiter erfolgreich aus Projekt entfernt",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Projekt oder Mitarbeiter nicht gefunden",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Nicht autorisiert",
                    content = @Content)})
    @DeleteMapping("/deleteEmployee")
    ResponseEntity<Void> deleteEmployeeFromProject(@RequestBody DeleteEmployeeDTO deleteEmployeeDTO);

    @Operation(summary = "Finde all Projekte nach Mitarbeiter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste der Projekte mit der angegebenen Mitarbeiter",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProjectGetDTO.class)))}),
            @ApiResponse(responseCode = "404", description = "Mitarbeiter existiert nicht",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Nicht autorisiert",
                    content = @Content)})
    ResponseEntity<List<ProjectGetDTO>> findAllProjectsByEmployee(@PathVariable Integer id);
}
