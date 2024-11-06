package de.szut.lf8_starter.project;

import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectAddEmployeeIT extends AbstractIntegrationTest {

    private String token;
    private int testProjectId = 1;

    @BeforeEach
    public void setUp() throws Exception {
        this.token = Util.fetchAccessToken();
        Util.createProjectTestData(projectRepository, testProjectId);
    }


    @Test
    void authorization() throws Exception {
        String employeeJson1 = """
            {
                "employeeId": 297,
                "projectId": 1,
                "skillsId": [
                 10
                ]
            }
        """;

        this.mockMvc.perform(post("/project/addEmployeeInProject", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson1)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "user")
    void addEmployeeInProject() throws Exception {
        String employeeJson1 = """
            {
                "employeeId": 297,
                "projectId": 1,
                "skillsId": [
                 10
                ]
            }
        """;

        this.mockMvc.perform(post("/project/addEmployeeInProject", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson1)
                        .header("Authorization", "Bearer " + token) // Set token here
                        .with(csrf()))
                .andExpect(status().isCreated());
    }
}
