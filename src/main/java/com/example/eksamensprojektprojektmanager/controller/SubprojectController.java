package com.example.eksamensprojektprojektmanager.controller;

import com.example.eksamensprojektprojektmanager.model.Subproject;
import com.example.eksamensprojektprojektmanager.service.SubprojectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class SubprojectController {

    private final SubprojectService subprojectService;

    @Autowired
    public SubprojectController(SubprojectService subprojectService) {
        this.subprojectService = subprojectService;
    }

    @GetMapping("/subprojects/{projectId}")
    public String showSubprojects(@PathVariable Long projectId, Model model) {
        List<Subproject> subprojects = subprojectService.getSubprojectsByProjectId(projectId);

        // Check if each subproject has a subproject_id
        for (Subproject subproject : subprojects) {
            if (subproject.getSubproject_id() == null) {
                // Handle the case where subproject_id is null
                // This could be throwing an exception, logging an error, etc.
                System.out.println("Subproject with null id found");
            }
        }

        model.addAttribute("subprojects", subprojects);
        model.addAttribute("projectId", projectId);
        return "subprojects";
    }

    @GetMapping("/addSubproject/{projectId}")
    public String showAddSubprojectPage(@PathVariable Long projectId, Model model) {
        model.addAttribute("projectId", projectId);
        return "addSubproject";
    }

    @PostMapping("/addSubproject/{projectId}")
    public String addSubproject(@PathVariable Long projectId,
                                @ModelAttribute Subproject subproject,
                                RedirectAttributes redirectAttributes) {

        subprojectService.addSubproject(subproject, projectId);
        redirectAttributes.addFlashAttribute("successMessage", "Subproject added successfully.");
        return "redirect:/subprojects/" + projectId;
    }

    @PostMapping("/deleteSubproject/{projectId}/{subprojectId}")
    public String deleteSubproject(@PathVariable Long projectId, @PathVariable Long subprojectId, RedirectAttributes redirectAttributes) {
        Subproject subproject = subprojectService.getSubprojectById(subprojectId);
        if (subproject == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Subproject not found.");
            return "redirect:/subprojects/" + projectId;
        }
        subprojectService.deleteSubprojectById(subprojectId);
        redirectAttributes.addFlashAttribute("successMessage", "Subproject deleted successfully.");
        return "redirect:/subprojects/" + projectId;
    }

    @GetMapping("/updateSubproject/{id}")
    public String showUpdateForm(@PathVariable("id") Long subprojectId, Model model) {
        Subproject subproject = subprojectService.getSubprojectById(subprojectId);

        model.addAttribute("subproject", subproject);

        return "addSubproject";
    }




}