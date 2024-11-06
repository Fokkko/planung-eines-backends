package de.szut.lf8_starter.employee;

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

    @Override
    @GetMapping("/{employeeId}")
    public ResponseEntity<Boolean> checkEmployeeExists(@PathVariable Integer employeeId) {

        String token = getJwtToken();

        Boolean employee = employeeService.checkEmployeeExists(employeeId, token);

        if (employee == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(employee);
    }
// welche Ressourcen braucht das projekt backend 4 Java, 2 C# usw.
private String getJwtToken() {
    JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
        Jwt jwt = (Jwt) authentication.getToken();
        return "Bearer " + jwt.getTokenValue();
    }
    return null;
}

}
