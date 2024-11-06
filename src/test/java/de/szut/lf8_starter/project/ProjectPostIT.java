package de.szut.lf8_starter.project;

import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

public class ProjectPostIT extends AbstractIntegrationTest {

    private String token;


    @BeforeEach
    public void setUp() throws Exception {
        this.token = Util.fetchAccessToken();
    }




    @Test
    void authorization() throws Exception {
        String projectJson1 = """
            {
                "name": "HPG: SAP Einführung Warenwirtschaft",
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

        this.mockMvc.perform(post("/projects/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(projectJson1)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "user")
    void createProject() throws Exception {
        String projectJson2 = """
            {
                "name": "HPG: SAP Einführung Warenwirtschaft",
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

        this.mockMvc.perform(post("/projects/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(projectJson2)
                        .header("Authorization", "Bearer " + token) // Set token here
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("HPG: SAP Einführung Warenwirtschaft")))
                .andExpect(jsonPath("$.customerName", is("Happy People GmbH")))
                .andExpect(jsonPath("$.responsibleEmployee", is(297)))
                .andExpect(jsonPath("$.customerId", is(5002)))
                .andExpect(jsonPath("$.description", is("High priority project")));
    }


    @Test
    @WithMockUser(roles = "user")
    void createProjectInvalidResponsibleEmployee() throws Exception {
        String projectJson2 = """
            {
                "name": "HPG: SAP Einführung Warenwirtschaft",
                "responsibleEmployeeId": 3568,
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

        this.mockMvc.perform(post("/projects/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(projectJson2)
                        .header("Authorization", "Bearer " + token) // Set token here
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }





}
