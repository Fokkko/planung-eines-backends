package de.szut.lf8_starter.project;

import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.Arrays;

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
        ProjectEntity project1 = new ProjectEntity(
                1,
                "HPG: SAP Einführung Warenwirtschaft",
                101,
                5001,
                "Happy People GmbH",
                "This is a priority project",
                LocalDate.of(2024, 10, 1),
                LocalDate.of(2025, 3, 30),
                null,
                Arrays.asList(101, 102, 103),
                Arrays.asList("Javascript", "ABAP", "SAPUI5")
        );
        var project = projectRepository.save(project1);
        this.mockMvc.perform(delete("/projects/{id}", project.getId())
                        .with(csrf()))
                .andExpect(status().isNoContent());

        // Check that the project no longer exists in the repository
        assert(!projectRepository.existsById((int) project.getId()));
    }

}
