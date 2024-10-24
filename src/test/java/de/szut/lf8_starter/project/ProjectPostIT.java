package de.szut.lf8_starter.project;

import de.szut.lf8_starter.project.dto.ProjectGetDTO;
import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

public class ProjectPostIT extends AbstractIntegrationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void authorization() throws Exception {
        var newProject = new ProjectGetDTO(1L, "Project A", 101L, 201L, "Customer X",
                "Description for Project A", new Date(), new Date(), new Date());

        this.mockMvc.perform(post("/projects/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProject))
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "admin")
    void createProject() throws Exception {
        var newProject = new ProjectGetDTO(1L, "Project A", 101L, 201L, "Customer X",
                "Description for Project A", new Date(), new Date(), new Date());

        this.mockMvc.perform(post("/projects/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProject))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Project A")))
                .andExpect(jsonPath("$.customerName", is("Customer X")))
                .andExpect(jsonPath("$.responsibleEmployee", is(101)))
                .andExpect(jsonPath("$.customer", is(201)))
                .andExpect(jsonPath("$.description", is("Description for Project A")));
    }

}
