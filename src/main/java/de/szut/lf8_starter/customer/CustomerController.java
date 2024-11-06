package de.szut.lf8_starter.customer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "customer")
@PreAuthorize("hasAnyAuthority('user')")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/{customerId}")
    public ResponseEntity<Boolean> checkEmployeeExists(@PathVariable Integer customerId) {

        String token = getJwtToken();

        Boolean customer = customerService.checkCustomerExists(customerId, token);

        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customer);
    }

    private String getJwtToken() {
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Jwt jwt = (Jwt) authentication.getToken();
            return "Bearer " + jwt.getTokenValue();
        }
        return null;
    }
}
