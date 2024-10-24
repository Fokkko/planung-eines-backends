//package de.szut.lf8_starter.employeeTest;
//
//import de.szut.lf8_starter.employeeTest.dto.SkillDTO;
//import de.szut.lf8_starter.project.ProjectEntity;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.Size;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Entity
//public class EmployeeEntity {
//    @Id
//    private Long id;
//
//    private String lastName;
//    private String firstname;
//    private String street;
//    @Size(min = 5, max = 5, message = "Die Postleitzahl muss genau 5 Ziffern lang sein.")
//    private String postcode;
//    private String city;
//    private String phone;
//
////    @ElementCollection
////    @CollectionTable(name = "employee_project", joinColumns = @JoinColumn(name = "employee_id"))
////    @Column(name = "project_id")
////    private List<Integer> projectIds;
//
//}
//
