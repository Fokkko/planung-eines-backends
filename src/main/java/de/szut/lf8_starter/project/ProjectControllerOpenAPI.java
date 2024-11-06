package de.szut.lf8_starter.project;

import de.szut.lf8_starter.employee.dto.AddEmployeeToProject;
import de.szut.lf8_starter.employee.dto.DeleteEmployeeDTO;
import de.szut.lf8_starter.project.dto.ProjectGetDTO;
import de.szut.lf8_starter.project.dto.ProjectPostDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Erstellt ein neues Projekt",
            description = "Erstellt ein neues Projekt mit den angegebenen Daten.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Projekt erfolgreich erstellt",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProjectGetDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Ungültiges JSON-Format", content = @Content),
            @ApiResponse(responseCode = "401", description = "Nicht autorisiert", content = @Content)})
    ResponseEntity<ProjectGetDTO> create(
            @Parameter(description = "Die Daten des zu erstellenden Projekts") @RequestBody @Valid ProjectPostDTO projectPostDto,
            @Parameter(description = "JWT Token für die Authentifizierung") @RequestHeader(name = "Authorization") String token);


    @Operation(summary = "Aktualisiert ein Projekt mit der angegebenen ID",
            description = "Aktualisiert ein Projekt anhand der ID mit den neuen Informationen.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projekt erfolgreich aktualisiert",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProjectGetDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Ungültiges JSON-Format", content = @Content),
            @ApiResponse(responseCode = "401", description = "Nicht autorisiert", content = @Content)})
    ResponseEntity<ProjectGetDTO> update(
            @Parameter(description = "Die ID des zu aktualisierenden Projekts") @PathVariable Integer id,
            @Parameter(description = "Die neuen Projektinformationen") @RequestBody @Valid ProjectPostDTO projectUpdateDto,
            @Parameter(description = "JWT Token für die Authentifizierung") @RequestHeader(name = "Authorization") String token);


    @Operation(summary = "Löscht ein Projekt anhand der ID",
            description = "Löscht das Projekt mit der angegebenen ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Projekt erfolgreich gelöscht", content = @Content),
            @ApiResponse(responseCode = "404", description = "Projekt nicht gefunden", content = @Content),
            @ApiResponse(responseCode = "401", description = "Nicht autorisiert", content = @Content)})
    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteProjectById(
            @Parameter(description = "Die ID des zu löschenden Projekts") @PathVariable Integer id);

    @Operation(summary = "Liefert eine Liste von Projekten",
            description = "Gibt eine Liste aller verfügbaren Projekte zurück.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste der Projekte",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProjectGetDTO.class)))}),
            @ApiResponse(responseCode = "401", description = "Nicht autorisiert", content = @Content)})
    ResponseEntity<List<ProjectGetDTO>> findAllProjects();

    @Operation(summary = "Gibt ein Projekt anhand der ID zurück",
            description = "Liefert das Projekt mit der angegebenen ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projekt erfolgreich gefunden",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProjectGetDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Projekt nicht gefunden", content = @Content),
            @ApiResponse(responseCode = "401", description = "Nicht autorisiert", content = @Content)})
    @GetMapping("/{id}")
    ResponseEntity<ProjectGetDTO> getProjectById(
            @Parameter(description = "Die ID des Projekts") @PathVariable Integer id);

    @Operation(summary = "Fügt einem Projekt einen Mitarbeiter hinzu",
            description = "Fügt den angegebenen Mitarbeiter dem Projekt hinzu.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mitarbeiter erfolgreich zum Projekt hinzugefügt",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AddEmployeeToProject.class))}),
            @ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten", content = @Content),
            @ApiResponse(responseCode = "404", description = "Projekt oder Mitarbeiter nicht gefunden", content = @Content),
            @ApiResponse(responseCode = "401", description = "Nicht autorisiert", content = @Content)})
    @PostMapping("/addEmployeeInProject")
    ResponseEntity<AddEmployeeToProject> addEmployeeInProject(
            @Parameter(description = "Die zu hinzufügende Mitarbeiter-ID und Projekt-ID") @RequestBody @Valid AddEmployeeToProject addEmployeeToProject,
            @Parameter(description = "JWT Token für die Authentifizierung") @RequestHeader(name = "Authorization") String token);

    @Operation(summary = "Entfernt einen Mitarbeiter aus einem Projekt",
            description = "Entfernt den angegebenen Mitarbeiter aus dem Projekt.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Mitarbeiter erfolgreich aus Projekt entfernt", content = @Content),
            @ApiResponse(responseCode = "404", description = "Projekt oder Mitarbeiter nicht gefunden", content = @Content),
            @ApiResponse(responseCode = "401", description = "Nicht autorisiert", content = @Content)})
    @DeleteMapping("/deleteEmployee")
    ResponseEntity<Void> deleteEmployeeFromProject(
            @Parameter(description = "Die Projekt- und Mitarbeiter-ID, die entfernt werden sollen") @RequestBody DeleteEmployeeDTO deleteEmployeeDTO);

    @Operation(summary = "Finde alle Projekte nach Mitarbeiter",
            description = "Gibt eine Liste aller Projekte zurück, an denen der angegebene Mitarbeiter beteiligt ist.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste der Projekte mit dem angegebenen Mitarbeiter",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProjectGetDTO.class)))}),
            @ApiResponse(responseCode = "404", description = "Mitarbeiter existiert nicht", content = @Content),
            @ApiResponse(responseCode = "401", description = "Nicht autorisiert", content = @Content)})
    ResponseEntity<List<ProjectGetDTO>> findAllProjectsByEmployee(
            @Parameter(description = "Die ID des Mitarbeiters") @PathVariable Integer employeeId,
            @Parameter(description = "JWT Token für die Authentifizierung") @RequestHeader(name = "Authorization") String token);

    @Operation(summary = "Finde alle Mitarbeiter nach Projekt",
            description = "Gibt eine Liste aller Mitarbeiter zurück, die dem angegebenen Projekt zugeordnet sind.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste der Mitarbeiter mit dem angegebenen Projekt",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProjectGetDTO.class)))}),
            @ApiResponse(responseCode = "404", description = "Projekt existiert nicht", content = @Content),
            @ApiResponse(responseCode = "401", description = "Nicht autorisiert", content = @Content)})
    ResponseEntity<List<Integer>> findAllEmployeesByProject(
            @Parameter(description = "Die ID des Projekts") @PathVariable Integer projectId,
            @Parameter(description = "JWT Token für die Authentifizierung") @RequestHeader(name = "Authorization") String token);
}
