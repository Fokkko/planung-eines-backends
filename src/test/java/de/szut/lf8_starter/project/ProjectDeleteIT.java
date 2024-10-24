package de.szut.lf8_starter.project;

import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectDeleteIT extends AbstractIntegrationTest {

    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(delete("/projects/1")
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "admin")
    void deleteProject() throws Exception {
        var project = projectRepository.save(new ProjectEntity(1L, "Project A", 101, 201, "Customer X",
                "Description for Project A", LocalDate.of(10,10,10), LocalDate.of(10,10,10), LocalDate.of(10,10,10)));
        this.mockMvc.perform(delete("/projects/{id}", project.getId())
                        .with(csrf()))
                .andExpect(status().isNoContent());

        // Check that the project no longer exists in the repository
        assert(!projectRepository.existsById(project.getId()));
    }

}
