package de.szut.lf8_starter.project;

import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectDeleteEmployeeIT extends AbstractIntegrationTest {

    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(delete("/deleteEmployee/{projectId}/", 1)
                        .param("employeeId", "1")
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "admin")
    void deleteEmployeeFromProject() throws Exception {
        long projectId = 1L;
        long employeeId = 101L;

        this.mockMvc.perform(delete("/deleteEmployee/{projectId}/", projectId)
                        .param("employeeId", String.valueOf(employeeId))
                        .with(csrf()))
                .andExpect(status().isNoContent());

        // Additional checks can be added to verify that the employee was removed
    }
}
