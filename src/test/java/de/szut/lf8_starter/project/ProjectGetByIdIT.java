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

public class ProjectGetByIdIT extends AbstractIntegrationTest {

    private int testProjectId = 1;

    @BeforeEach
    public void setUp() throws Exception {
        Util.createProjectTestData(projectRepository, testProjectId);
    }

    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(get("/projects/" + testProjectId)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "user")
    void getProjectById() throws Exception {

        this.mockMvc.perform(get("/projects/{id}", testProjectId)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("ERP System Implementation")))
                .andExpect(jsonPath("$.customerName", is("Tech Innovators Ltd.")))
                .andExpect(jsonPath("$.responsibleEmployee", is(299)))
                .andExpect(jsonPath("$.customerId", is(5003)))
                .andExpect(jsonPath("$.description", is("Core project for business automation")));
    }

    @Test
    @WithMockUser(roles = "user")
    void getNonExistingProject() throws Exception {
        this.mockMvc.perform(get("/projects/{id}", 999)
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }







}
