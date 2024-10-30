
package de.szut.lf8_starter.project;

import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;


import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ProjectGetAllIT extends AbstractIntegrationTest {


    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(get("/project/")
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "user")
    void findAll() throws Exception {

        // Creating two ProjectEntity objects
        ProjectEntity project1 = new ProjectEntity(
                2,
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
                Arrays.asList(10,207)
        );

        ProjectEntity project2 = new ProjectEntity(
                3,
                "Project Beta",
                298,
                5002,
                "Jane Smith",
                "",
                "Follow-up project",
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2025, 4, 30),
                null,
                Arrays.asList(297),
                Arrays.asList(10,207)
        );

        projectRepository.save(project1);
        projectRepository.save(project2);
        this.mockMvc.perform(get("/projects/")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("HPG: SAP Einführung Warenwirtschaft")))
                .andExpect(jsonPath("$[0].customerName", is("Happy People GmbH")))
                .andExpect(jsonPath("$[1].name", is("Project B")))
                .andExpect(jsonPath("$[1].customerName", is("Customer Y")));
    }

}

