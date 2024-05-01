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

        model.addAttribute("projects", userProjects);

        return "addProjects";
    }

    @PostMapping("/addProject")
    public String addProject(@RequestParam("projectName") String projectName, @RequestParam("startDate") String startDate, @RequestParam("projectDeadline") String projectDeadline, @RequestParam("projectStatus") String projectStatus, HttpServletRequest request, RedirectAttributes redirectAttributes){
        String user_idString = (String) request.getSession().getAttribute("user_id");

        if (user_idString == null) {
            return "redirect:/login";
        }

        try {
            Project project = new Project();
            project.setProjectName(projectName);
            project.setStartDate(LocalDate.parse(startDate));
            project.setProjectDeadline(LocalDate.parse(projectDeadline));
            project.setProjectStatus(projectStatus);

            Project addedProject = projectService.addProject(project, user_idString);

            redirectAttributes.addFlashAttribute("successMessage", "Project added successfully with ID: " + addedProject.getProject_id());
            return "redirect:/projects";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/projects";
        }
    }
}