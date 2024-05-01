package com.example.eksamensprojektprojektmanager.controller;

import com.example.eksamensprojektprojektmanager.model.Project;
import com.example.eksamensprojektprojektmanager.service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    public ProjectService projectService;

    @GetMapping("/seeProjects")
    public String showProjects(HttpServletRequest request, Model model) {
        String userIdString = (String) request.getSession().getAttribute("userId");
        if (userIdString == null) {
            return "redirect:/login";
        }

        int userId = Integer.parseInt(userIdString);

        List<Project> userProjects = projectService.getProjectsByUserId(userId);

        // Log the retrieved projects
        System.out.println("Retrieved projects: " + userProjects);

        model.addAttribute("projects", userProjects);

        return "projects"; // This should match the name of your HTML template file.
    }

    @PostMapping("/addProject")
    public String addProject(@RequestParam("projectName") String projectName,
                             @RequestParam("startDate") String startDate,
                             @RequestParam("projectDeadline") String projectDeadline,
                             @RequestParam("projectStatus") String projectStatus,
                             HttpServletRequest request,
                             RedirectAttributes redirectAttributes) {
        String userIdString = (String) request.getSession().getAttribute("userId");

        if (userIdString == null) {
            return "redirect:/login";
        }

        try {
            Project project = new Project();
            project.setProjectName(projectName);
            project.setStartDate(LocalDate.parse(startDate));
            project.setProjectDeadline(LocalDate.parse(projectDeadline));
            project.setProjectStatus(projectStatus);

            Project addedProject = projectService.addProject(project, userIdString);

            redirectAttributes.addFlashAttribute("successMessage", "Project added successfully with ID: " + addedProject.getProject_id());
            return "redirect:/seeProjects"; // Redirect to the projects page after adding project

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/seeProjects";
        }
    }

    @GetMapping("/project")
    public String showProjectPage() {
        return "project";
    }


}