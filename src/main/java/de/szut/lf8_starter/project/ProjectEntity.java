package de.szut.lf8_starter.project;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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

    @NotBlank(message = "Name der Kundenkontaktperson darf nicht leer sein")
    private String customerContactName;

    private String comment;

    @NotNull(message = "Startdatum ist erforderlich")
    private LocalDate startDate;

    @NotNull(message = "Geplantes Enddatum ist erforderlich")
    private LocalDate plannedEndDate;

    private LocalDate actualEndDate;

}

