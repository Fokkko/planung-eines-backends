package de.szut.lf8_starter.employee;

import de.szut.lf8_starter.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/employees")
public class EmployeeController implements EmployeeControllerOpenAPI{

    private final EmployeeService employeeService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @GetMapping("/{employeeId}")
    public ResponseEntity<Boolean> checkEmployeeExists(@PathVariable Integer employeeId) {

        String token = jwtTokenProvider.getJwtToken();

        Boolean employee = employeeService.checkEmployeeExists(employeeId, token);

        if (employee == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(employee);
    }

}
