package com.example.eksamensprojektprojektmanager.test.integrationTest;

import com.example.eksamensprojektprojektmanager.model.Account;
import com.example.eksamensprojektprojektmanager.model.Project;
import com.example.eksamensprojektprojektmanager.model.Subproject;
import com.example.eksamensprojektprojektmanager.repository.AccountRepository;
import com.example.eksamensprojektprojektmanager.repository.ProjectRepository;
import com.example.eksamensprojektprojektmanager.repository.SubprojectRepository;
import com.example.eksamensprojektprojektmanager.testDatabase.SubprojectTestDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class SubprojectTest {

    @Autowired
    SubprojectRepository subprojectRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    private SubprojectTestDB subprojectTestDB;
    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    public void setup() {
        subprojectTestDB.createSubprojectTestDB();
    }

    @Test
    void createSubproject(){
        Account accountTest = new Account();
        accountTest.setUsername("mikkelqw");
        accountTest.setPassword("TestPassword");
        accountTest.setAdmin(true);
        accountTest.setName("Test Name");
        accountTest.setCompany("Test Company");
        accountTest.setJobTitle("Test Job Title");
        accountTest.setDescription("Test Description");
        accountRepository.save(accountTest);



        Project projectTest = new Project();
        projectTest.setProjectName("Test Project");
        projectTest.setStartDate(LocalDate.of(2024,5,5));
        projectTest.setProjectDeadline(LocalDate.of(2024,5,9));
        projectTest.setProjectStatus("done");
        projectRepository.addProject(projectTest);

        Subproject subprojectTest = new Subproject("testnavn", "et stort hus", LocalDate.of(2024,12,12), LocalDate.of(2024,12,16));
        subprojectRepository.addSubproject(subprojectTest, projectTest.getProject_id());

        Subproject retrievedSubproject = subprojectRepository.findById(subprojectTest.getSubproject_id());

        assertNotNull(retrievedSubproject);
        assertEquals(subprojectTest.getSubproject_id(),retrievedSubproject.getSubproject_id());
        assertEquals(subprojectTest.getSubprojectname(),retrievedSubproject.getSubprojectname());
    }
}