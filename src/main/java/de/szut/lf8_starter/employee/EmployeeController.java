package de.szut.lf8_starter.employee;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/employees")
public class EmployeeController implements EmployeeControllerOpenAPI{

    private final EmployeeService employeeService;

    @Override
    @GetMapping("/{employeeId}")
    public ResponseEntity<Boolean> checkEmployeeExists(@PathVariable Integer employeeId, @RequestHeader (name="Authorization") String token) {

        Boolean employee = employeeService.checkEmployeeExists(employeeId, token);

        if (employee == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(employee);
    }
// welche Ressourcen braucht das projekt backend 4 Java, 2 C# usw.

}
