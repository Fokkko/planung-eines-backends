package de.szut.lf8_starter.project;

import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Arrays;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectAddEmployeeIT extends AbstractIntegrationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(post("/addEmployeeInProject/{projectId}/", 1)
                        .param("employeeId", "1")
                        .param("qualificationId", "1")
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "admin")
    void addEmployeeInProject() throws Exception {

        ProjectEntity project2 = new ProjectEntity(
                1,
                "Project Beta",
                102,
                5002,
                "Jane Smith",
                "Follow-up project",
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2025, 4, 30),
                null,
                Arrays.asList(104, 105),
                Arrays.asList("Python", "JavaScript")
        );

        this.mockMvc.perform(post("/projects/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(project2))
                .with(csrf()));

        long projectId = 1L;
        long employeeId = 101L;
        long qualificationId = 201L;

        this.mockMvc.perform(post("/addEmployeeInProject/{projectId}/", projectId)
                        .param("employeeId", String.valueOf(employeeId))
                        .param("qualificationId", String.valueOf(qualificationId))
                        .with(csrf()))
                .andExpect(status().isOk());

        // Additional checks can be added to verify that the employee was added
    }
}
