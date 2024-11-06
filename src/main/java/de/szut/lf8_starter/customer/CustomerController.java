// CustomerController.java
package de.szut.lf8_starter.customer;

import de.szut.lf8_starter.customer.customerDto.CustomerControllerOpenAPI;
import de.szut.lf8_starter.customer.customerDto.CustomerRequestDto;
import de.szut.lf8_starter.customer.customerDto.CustomerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customer")
@PreAuthorize("hasAnyAuthority('user')")
@RequiredArgsConstructor
public class CustomerController implements CustomerControllerOpenAPI {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(
            @RequestBody CustomerRequestDto customerRequest,
            @RequestHeader(name="Authorization") String token) {
        return ResponseEntity.ok(customerService.createCustomer(customerRequest, token));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(
            @PathVariable Integer customerId,
            @RequestHeader(name="Authorization") String token) {
        return ResponseEntity.ok(customerService.getCustomerById(customerId, token));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(
            @PathVariable Integer customerId,
            @RequestBody CustomerRequestDto customerRequest,
            @RequestHeader(name="Authorization") String token) {
        return ResponseEntity.ok(customerService.updateCustomer(customerId, customerRequest, token));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable Integer customerId,
            @RequestHeader(name="Authorization") String token) {
        customerService.deleteCustomer(customerId, token);
        return ResponseEntity.noContent().build();
    }
}
