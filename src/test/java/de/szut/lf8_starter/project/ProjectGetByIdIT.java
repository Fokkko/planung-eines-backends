package de.szut.lf8_starter.project;

import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.Arrays;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

public class ProjectGetByIdIT extends AbstractIntegrationTest {

    @Test
    void authorization() throws Exception {
        // Attempt to get a project by ID without authentication
        this.mockMvc.perform(get("/projects/1")
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "user")
    void getProjectById() throws Exception {


        // Creating two ProjectEntity objects
        ProjectEntity project1 = new ProjectEntity(
                2,
                "HPG: SAP Einführung Warenwirtschaft",
                101,
                5001,
                "Happy People GmbH",
                "",
                "This is a priority project",
                LocalDate.of(2024, 10, 1),
                LocalDate.of(2025, 3, 30),
                null,
                Arrays.asList(101, 102, 103),
                Arrays.asList(2, 2, 1)
        );

        var existingProject = projectRepository.save(project1);

        // Perform GET request to retrieve the project by ID
        this.mockMvc.perform(get("/projects/{id}", existingProject.getId())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Project A")))
                .andExpect(jsonPath("$.customerName", is("Customer X")))
                .andExpect(jsonPath("$.responsibleEmployee", is(101)))
                .andExpect(jsonPath("$.customer", is(201)))
                .andExpect(jsonPath("$.description", is("Description for Project A")));
    }

    @Test
    @WithMockUser(roles = "user")
    void getNonExistingProject() throws Exception {
        // Attempt to get a non-existing project
        this.mockMvc.perform(get("/projects/{id}", 999)
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

}
