package com.example.eksamensprojektprojektmanager.test;

import com.example.eksamensprojektprojektmanager.controller.ProjectController;
import com.example.eksamensprojektprojektmanager.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @Test
    void testDeleteProject() throws Exception {
        doNothing().when(projectService).deleteProjectById(1L);

        mockMvc.perform(post("/deleteProject/{id}", 1))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/seeProjects"));
    }
}