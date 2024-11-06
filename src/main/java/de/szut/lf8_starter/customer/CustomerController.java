package de.szut.lf8_starter.customer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "customer")
@PreAuthorize("hasAnyAuthority('user')")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/{customerId}")
    public ResponseEntity<Boolean> checkEmployeeExists(@PathVariable Integer customerId, @RequestHeader(name="Authorization") String token) {

        Boolean customer = customerService.checkCustomerExists(customerId, token);

        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customer);
    }
}
