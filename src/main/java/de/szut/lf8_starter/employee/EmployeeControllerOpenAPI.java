package de.szut.lf8_starter.employee;

import de.szut.lf8_starter.project.dto.ProjectGetDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface EmployeeControllerOpenAPI {

    @Operation(summary = "Überprüfe ob der Mitarbeiter existiert.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mitarbeiter erfolgreich erstellt",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectGetDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Ungültiges JSON-Format",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Nicht autorisiert",
                    content = @Content)})
    ResponseEntity<Boolean> checkEmployeeExists(@PathVariable Integer employeeId);
}
