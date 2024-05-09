package com.example.eksamensprojektprojektmanager.controller;

import com.example.eksamensprojektprojektmanager.model.Task;
import com.example.eksamensprojektprojektmanager.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TaskController {

    private final TaskService taskService;
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks/{projectId}/{subprojectId}")
    public String showTasks(@PathVariable(required = false) Long projectId,
                            @PathVariable(required = false) Long subprojectId,
                            Model model) {
        if (projectId == null || subprojectId == null) {
            // Handle null values gracefully, e.g., redirect to an error page or display a message
            return "error";
        }

        logger.info("Entering showTasks method with projectId: {} and subprojectId: {}", projectId, subprojectId);
        List<Task> tasks = taskService.getTasksByProjectIdAndSubprojectId(projectId, subprojectId);
        model.addAttribute("tasks", tasks);
        model.addAttribute("projectId", projectId);
        model.addAttribute("subprojectId", subprojectId);
        return "task";
    }


    @GetMapping("/addTask/{projectId}/{subprojectId}")
    public String showAddTaskPage(@PathVariable Long projectId,@PathVariable Long subprojectId, Model model) {
        model.addAttribute("projectId", projectId);
        model.addAttribute("subprojectId", subprojectId);

        return "addTask";
    }

    @PostMapping("/addTask/{projectId}/{subprojectId}")
    public String addTask(@PathVariable Long projectId,
                          @PathVariable Long subprojectId,
                          @RequestParam("taskName") String taskName,
                          @RequestParam("taskDescription") String taskDescription,
                          @RequestParam("taskDate")
                          @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime taskDateTime,
                          @RequestParam("taskDeadline")
                          @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime taskDeadlineDateTime,
                          RedirectAttributes redirectAttributes) {

        Task task = new Task(taskName, taskDescription, taskDateTime, taskDeadlineDateTime);
        task.setProjectId(projectId);
        task.setProjectId(subprojectId);
        taskService.addTask(task, projectId,subprojectId);
        redirectAttributes.addFlashAttribute("successMessage", "Task added successfully.");
        return "redirect:/tasks/" + projectId + "/" + subprojectId;
    }

    @PostMapping("/deleteTask/{projectId}/{taskId}")
    public String deleteTask(@PathVariable Long projectId, @PathVariable Long taskId, RedirectAttributes redirectAttributes) {
        Task task = taskService.getTaskById(taskId);
        if (task == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Task not found.");
            return "redirect:/tasks/" + projectId;
        }
        taskService.deleteTaskById(taskId);
        redirectAttributes.addFlashAttribute("successMessage", "Task deleted successfully.");
        return "redirect:/tasks/" + projectId;
    }
}