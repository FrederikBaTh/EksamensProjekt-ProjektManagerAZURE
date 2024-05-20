package com.example.eksamensprojektprojektmanager.ProjectTest;

import com.example.eksamensprojektprojektmanager.model.Project;
import com.example.eksamensprojektprojektmanager.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @Test
    void testDeleteById() {
        Project project = new Project();
        project.setProject_id(1L);

        when(jdbcTemplate.update("DELETE FROM projects WHERE project_id = ?", project.getProject_id())).thenReturn(1);
        when(jdbcTemplate.queryForObject("SELECT * FROM projects WHERE project_id = ?", new Object[]{project.getProject_id()}, (resultSet, rowNum) -> null)).thenReturn(null);

        projectRepository.deleteProjectById(project.getProject_id());

        Project deletedProject = jdbcTemplate.queryForObject("SELECT * FROM projects WHERE project_id = ?", new Object[]{project.getProject_id()}, (resultSet, rowNum) -> null);
        assertThat(deletedProject).isNull();
    }
}
