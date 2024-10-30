package de.szut.lf8_starter.project;

import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Arrays;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

public class ProjectPutIT extends AbstractIntegrationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void authorization() throws Exception {
        // Attempt to update a project without authentication
        // Creating two ProjectEntity objects
        ProjectEntity project1 = new ProjectEntity(
                1,
                "Project Alpha",
                297,
                5001,
                "John Doe",
                "",
                "This is a priority project",
                LocalDate.of(2024, 10, 1),
                LocalDate.of(2025, 3, 30),
                null,
                Arrays.asList(298),
                Arrays.asList(10,207)
        );

        this.mockMvc.perform(put("/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(project1))
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "admin")
    void updateProject() throws Exception {

        // Creating two ProjectEntity objects
        ProjectEntity project1 = new ProjectEntity(
                2,
                "Project Alpha",
                297,
                5001,
                "John Doe",
                "",
                "This is a priority project",
                LocalDate.of(2024, 10, 1),
                LocalDate.of(2025, 3, 30),
                null,
                Arrays.asList(298),
                Arrays.asList(10,207)
        );

        ProjectEntity project2 = new ProjectEntity(
                3,
                "Project Alpha",
                297,
                5001,
                "John Doe",
                "",
                "This is a priority project",
                LocalDate.of(2024, 10, 1),
                LocalDate.of(2025, 3, 30),
                null,
                Arrays.asList(298),
                Arrays.asList(10,207)
        );

        var existingProject = projectRepository.save(project1);

        var updatedProject = projectRepository.save(project2);

        this.mockMvc.perform(put("/projects/{id}", existingProject.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProject))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Project A")))
                .andExpect(jsonPath("$.customerName", is("Updated Customer X")))
                .andExpect(jsonPath("$.responsibleEmployee", is(102)))
                .andExpect(jsonPath("$.customer", is(202)))
                .andExpect(jsonPath("$.description", is("Updated description")));

        // Verify that the project is updated in the repository
        var projectFromDb = projectRepository.findById((int) existingProject.getId()).orElseThrow();
        assert(projectFromDb.getName().equals("Updated Project A"));
        assert(projectFromDb.getCustomerContactName().equals("Updated Customer X"));
    }

}
