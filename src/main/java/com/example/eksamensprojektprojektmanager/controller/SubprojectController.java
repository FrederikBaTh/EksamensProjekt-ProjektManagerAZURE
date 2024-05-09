package com.example.eksamensprojektprojektmanager.controller;

import com.example.eksamensprojektprojektmanager.model.Subproject;
import com.example.eksamensprojektprojektmanager.service.SubprojectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class SubprojectController {

    private final SubprojectService subprojectService;

    private static final Logger logger = LoggerFactory.getLogger(SubprojectController.class);



    @Autowired
    public SubprojectController(SubprojectService subprojectService) {
        this.subprojectService = subprojectService;
    }

    @GetMapping("/subprojects/{projectId}")
    public String showSubprojects(@PathVariable Long projectId, Model model) {
        List<Subproject> subprojects = subprojectService.getSubprojectsByProjectId(projectId);
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
        logger.info("Subproject ID: " + subprojectId);
        Subproject subproject = subprojectService.getSubprojectById(subprojectId);
        logger.info("Retrieved subproject: " + subproject);
        model.addAttribute("subproject", subproject);
        return "addSubproject";
    }

    @PostMapping("/updateSubproject/{id}")
    public String updateSubproject(@PathVariable("id") Long subprojectId,
                                   @ModelAttribute Subproject updatedSubproject,
                                   RedirectAttributes redirectAttributes) {
        Subproject existingSubproject = subprojectService.getSubprojectById(subprojectId);
        if (existingSubproject == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Subproject not found.");
            return "redirect:/subprojects/" + updatedSubproject.getProject_id();
        }
        updatedSubproject.setSubproject_id(subprojectId);
        updatedSubproject.setProject_id(existingSubproject.getProject_id());
        Subproject updatedSubprojectInDb = subprojectService.updateSubproject(updatedSubproject);
        redirectAttributes.addFlashAttribute("successMessage", "Subproject updated successfully with ID: " + updatedSubprojectInDb.getSubproject_id());
        return "redirect:/subprojects/" + updatedSubproject.getProject_id();
    }


}