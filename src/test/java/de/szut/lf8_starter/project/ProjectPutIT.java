package de.szut.lf8_starter.project;

import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.MediaType;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

public class ProjectPutIT extends AbstractIntegrationTest {

    private int testProjectId = 1;
    private String token;

    @BeforeEach
    public void setUp() throws Exception {
        Util.createProjectTestData(projectRepository, testProjectId);
        this.token = Util.fetchAccessToken();
    }

    @Test
    void authorization() throws Exception {
        String projectJson1 = """
            {
                "name": "Verändertes Projekt",
                "responsibleEmployeeId": 297,
                "customerId": 5002,
                "customerName": "Happy People GmbH",
                "customerContactName": "Jane Smith",
                "comment": "High priority project",
                "startDate": "2024-11-01",
                "endDate": "2025-04-30",
                "projectQualificationIds": [
                 10
                ]
            }
        """;

        this.mockMvc.perform(put("/projects/update/" + testProjectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(projectJson1)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "admin")
    void updateProject() throws Exception {

        String projectJson1 = """
            {
                "name": "Verändertes Projekt",
                "responsibleEmployeeId": 297,
                "customerId": 5002,
                "customerName": "Happy People GmbH",
                "customerContactName": "Jane Smith",
                "comment": "High priority project",
                "startDate": "2024-11-01",
                "endDate": "2025-04-30",
                "projectQualificationIds": [
                 10
                ]
            }
        """;

        this.mockMvc.perform(put("/projects/update/" + testProjectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(projectJson1)
                        .header("Authorization", "Bearer " + token) // Set token here
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Verändertes Projekt")));

        var projectFromDb = projectRepository.findById((int) testProjectId).orElseThrow();
        Assertions.assertEquals("Verändertes Projekt", projectFromDb.getName());
    }


    @Test
    @WithMockUser(roles = "admin")
    void updateNonExistingProject() throws Exception {

        String projectJson1 = """
            {
                "name": "Verändertes Projekt",
                "responsibleEmployeeId": 297,
                "customerId": 5002,
                "customerName": "Happy People GmbH",
                "customerContactName": "Jane Smith",
                "comment": "High priority project",
                "startDate": "2024-11-01",
                "endDate": "2025-04-30",
                "projectQualificationIds": [
                 10
                ]
            }
        """;

        this.mockMvc.perform(put("/projects/update/" + 579)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(projectJson1)
                        .header("Authorization", "Bearer " + token) // Set token here
                        .with(csrf()))
                .andExpect(status().isNoContent());

    }

}
