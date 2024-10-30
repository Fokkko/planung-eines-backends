package de.szut.lf8_starter.project;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "projects")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Projektname darf nicht leer sein")
    private String name;

    @NotNull(message = "Verantwortlicher Mitarbeiter-ID ist erforderlich")
    private Integer responsibleEmployeeId;

    @NotNull(message = "Kunden-ID ist erforderlich")
    private Integer customerId;

    @NotNull(message = "Kunden-Name ist erforderlich")
    private String customerName;

    @NotBlank(message = "Name der Kundenkontaktperson darf nicht leer sein")
    private String customerContactName;

    private String comment;

    @NotNull(message = "Startdatum ist erforderlich")
    private LocalDate startDate;

    @NotNull(message = "Geplantes Enddatum ist erforderlich")
    private LocalDate plannedEndDate;

    private LocalDate actualEndDate;

    @ElementCollection
    @CollectionTable(name = "project_employee", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "employee_id")
    private List<Integer> employeeIds;

    @ElementCollection
    @CollectionTable(name = "project_qualification", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "qualification")
    private List<Integer> projectQualificationIds;

    // übe die Abschlussprüfung gesprochen, uns mit Projektdokumentation beschäftigt, Gruppenarbeit aus 6 Azubi

}

