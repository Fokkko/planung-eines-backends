
package de.szut.lf8_starter.project;

import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;


import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

        projectRepository.save(new ProjectEntity(1L, "Project A", 101, 201, "Customer X",
                "Description for Project A", LocalDate.of(10,10,10), LocalDate.of(10,10,10), LocalDate.of(10,10,10)));
        projectRepository.save(new ProjectEntity(2L, "Project B", 102, 202, "Customer Y",
                "Description for Project B", LocalDate.of(10,10,10), LocalDate.of(10,10,10), LocalDate.of(10,10,10)));

        this.mockMvc.perform(get("/projects/")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Project A")))
                .andExpect(jsonPath("$[0].customerName", is("Customer X")))
                .andExpect(jsonPath("$[1].name", is("Project B")))
                .andExpect(jsonPath("$[1].customerName", is("Customer Y")));
    }

}

