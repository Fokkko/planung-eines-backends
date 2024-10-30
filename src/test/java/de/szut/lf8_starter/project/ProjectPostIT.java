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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

public class ProjectPostIT extends AbstractIntegrationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void authorization() throws Exception {
        ProjectEntity project1 = new ProjectEntity(
                1,
                "HPG: SAP Einführung Warenwirtschaft",
                297,
                5001,
                "Happy People GmbH",
                "",
                "This is a priority project",
                LocalDate.of(2024, 10, 1),
                LocalDate.of(2025, 3, 30),
                null,
                Arrays.asList(298),
                Arrays.asList(10, 207)
        );


        this.mockMvc.perform(post("/projects/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(project1))
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "admin")
    void createProject() throws Exception {
        ProjectEntity project2 = new ProjectEntity(
                2,
                "Project Beta",
                297,
                5002,
                "Jane Smith",
                "Follow-up project",
                "",
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2025, 4, 30),
                null,
                Arrays.asList(298),
                Arrays.asList(10, 207)
        );

        this.mockMvc.perform(post("/projects/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(project2))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Project Beta")))
                .andExpect(jsonPath("$.customerName", is("Jane Smith")))
                .andExpect(jsonPath("$.responsibleEmployee", is(102)))
                .andExpect(jsonPath("$.customer", is(5002)))
                .andExpect(jsonPath("$.description", is("Follow-up project")));
    }

}
