package de.szut.lf8_starter.project;

import de.szut.lf8_starter.project.dto.ProjectGetDTO;
import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Date;

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
        var updatedProject = new ProjectGetDTO(1L, "Updated Project A", 101L, 201L, "Updated Customer X",
                "Updated description", new Date(), new Date(), new Date());

        this.mockMvc.perform(put("/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProject))
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "admin")
    void updateProject() throws Exception {
        var existingProject = projectRepository.save(new ProjectEntity(1L, "Project A", 101, 201, "Customer X",
                "Description for Project A", LocalDate.of(10,10,10), LocalDate.of(10,10,10), LocalDate.of(10,10,10)));

        var updatedProject = new ProjectGetDTO(existingProject.getId(), "Updated Project A", 102L, 202L, "Updated Customer X",
                "Updated description", new Date(), new Date(), new Date());

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
        var projectFromDb = projectRepository.findById(existingProject.getId()).orElseThrow();
        assert(projectFromDb.getName().equals("Updated Project A"));
        assert(projectFromDb.getCustomerContactName().equals("Updated Customer X"));
    }

}
