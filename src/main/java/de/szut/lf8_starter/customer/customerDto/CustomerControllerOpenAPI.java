package de.szut.lf8_starter.customer.customerDto;

import de.szut.lf8_starter.customer.customerDto.CustomerRequestDto;
import de.szut.lf8_starter.customer.customerDto.CustomerResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface CustomerControllerOpenAPI {

    @Operation(summary = "Erstellt einen neuen Kunden")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Kunde erfolgreich erstellt",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Ungültiges JSON-Format",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Nicht autorisiert",
                    content = @Content)
    })
    @PostMapping
    ResponseEntity<CustomerResponseDto> createCustomer(
            @RequestBody CustomerRequestDto customerRequest,
            @RequestHeader(name = "Authorization") String token);

    @Operation(summary = "Gibt den Kunden mit der angegebenen ID zurück")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Kunde erfolgreich gefunden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Kunde nicht gefunden",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Nicht autorisiert",
                    content = @Content)
    })
    @GetMapping("/{customerId}")
    ResponseEntity<CustomerResponseDto> getCustomerById(
            @PathVariable Integer customerId,
            @RequestHeader(name = "Authorization") String token);

    @Operation(summary = "Aktualisiert die Daten eines bestehenden Kunden")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Kunde erfolgreich aktualisiert",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Ungültiges JSON-Format",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Nicht autorisiert",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Kunde nicht gefunden",
                    content = @Content)
    })
    @PutMapping("/{customerId}")
    ResponseEntity<CustomerResponseDto> updateCustomer(
            @PathVariable Integer customerId,
            @RequestBody CustomerRequestDto customerRequest,
            @RequestHeader(name = "Authorization") String token);

    @Operation(summary = "Löscht einen Kunden anhand der ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Kunde erfolgreich gelöscht",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Kunde nicht gefunden",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Nicht autorisiert",
                    content = @Content)
    })
    @DeleteMapping("/{customerId}")
    ResponseEntity<Void> deleteCustomer(
            @PathVariable Integer customerId,
            @RequestHeader(name = "Authorization") String token);
}
