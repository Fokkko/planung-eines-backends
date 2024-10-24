package de.szut.lf8_starter.employeeTest;

import de.szut.lf8_starter.employeeTest.dto.SkillDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    public ResponseEntity<Boolean> getEmployeeById(@PathVariable Integer id, @RequestHeader (name="Authorization") String token) {

        Boolean employee = employeeService.getEmployeeById(id, token);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(employee);
    }
// welche Ressourcen braucht das projekt backend 4 Java, 2 C# usw.
// 

//    @GetMapping("/{id}/qualification")
//    public ResponseEntity<SkillDTO> getQualification(@PathVariable Integer id, @RequestHeader (name="Authorization") String token) {
//
//        var employee = employeeService.employeeForQualification(id, token);
//        if (employee == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(employee);
//    }
}
