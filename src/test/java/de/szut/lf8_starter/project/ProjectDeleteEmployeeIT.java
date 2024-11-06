package de.szut.lf8_starter.project;

import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectDeleteEmployeeIT extends AbstractIntegrationTest {

    private String token;
    private int testProjectId = 1;

    @BeforeEach
    public void setUp() throws Exception {
        this.token = Util.fetchAccessToken();
        Util.createProjectTestData(projectRepository, testProjectId);
    }

    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(delete("/project/deleteEmployee/{projectId}", 1)
                        .param("employeeId", "207")
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "user")
    void deleteEmployeeFromProject() throws Exception {

        this.mockMvc.perform(delete("/deleteEmployee/{projectId}/", 1)
                        .param("employeeId", String.valueOf(207))
                        .with(csrf()))
                .andExpect(status().isOk());

    }
}
