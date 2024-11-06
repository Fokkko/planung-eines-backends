// CustomerController.java
package de.szut.lf8_starter.customer;

import de.szut.lf8_starter.customer.customerDto.CustomerControllerOpenAPI;
import de.szut.lf8_starter.customer.customerDto.CustomerRequestDto;
import de.szut.lf8_starter.customer.customerDto.CustomerResponseDto;
import de.szut.lf8_starter.security.JwtTokenProvider;
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
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(
            @RequestBody CustomerRequestDto customerRequest) {
        return ResponseEntity.ok(customerService.createCustomer(customerRequest, jwtTokenProvider.getJwtToken()));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(
            @PathVariable Integer customerId) {
        return ResponseEntity.ok(customerService.getCustomerById(customerId, jwtTokenProvider.getJwtToken()));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(
            @PathVariable Integer customerId,
            @RequestBody CustomerRequestDto customerRequest) {
        return ResponseEntity.ok(customerService.updateCustomer(customerId, customerRequest, jwtTokenProvider.getJwtToken()));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable Integer customerId) {
        customerService.deleteCustomer(customerId, jwtTokenProvider.getJwtToken());
        return ResponseEntity.noContent().build();
    }
}
