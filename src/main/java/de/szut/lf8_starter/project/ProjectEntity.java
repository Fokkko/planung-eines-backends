package de.szut.lf8_starter.project;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "project")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(max = 50, message = "name cannot exceed 50 characters")
    private String name;

    private long responsibleEmployee;
    private long customer;

    @NotBlank
    @Size(max = 50, message = "name cannot exceed 50 characters")
    private String customerName;

    @NotBlank
    private String description;

    private Date startDate;
    private Date plannedDate;
    private Date endDate;

}
