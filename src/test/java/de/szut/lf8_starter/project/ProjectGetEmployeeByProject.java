package de.szut.lf8_starter.project;

import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

public class ProjectGetEmployeeByProject extends AbstractIntegrationTest {

    private int testProjectId = 1;

    @BeforeEach
    public void setUp() throws Exception {
        Util.createProjectTestData(projectRepository, testProjectId);
    }

    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(get("/projects/findAllEmployeesByProject/{projectId}", testProjectId)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "user")
    void getEmployeesByProjectId() throws Exception {

        this.mockMvc.perform(get("/projects/findAllEmployeesByProject/{projectId}", testProjectId)
                        .with(csrf()))
                .andExpect(status().isOk());
    }






}
