package com.example.eksamensprojektprojektmanager.controller;

import com.example.eksamensprojektprojektmanager.model.Task;
import com.example.eksamensprojektprojektmanager.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TaskController {

    private final TaskService taskService;
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /*@GetMapping("/tasks/{projectId}/{subprojectId}")
    public String showTasks(@PathVariable(required = false) Long projectId,
                            @PathVariable(required = false) Long subprojectId,
                            Model model) {
        if (projectId == null || subprojectId == null) {
            return "error";
        }

        logger.info("Entering showTasks method with projectId: {} and subprojectId: {}", projectId, subprojectId);
        List<Task> tasks = taskService.getTasksByProjectIdAndSubprojectId(projectId, subprojectId);
        model.addAttribute("tasks", tasks);
        model.addAttribute("projectId", projectId);
        model.addAttribute("subprojectId", subprojectId);
        return "seeTask";
    } */


    @GetMapping("/tasks/{projectId}/{subprojectId}")
    public String viewTasksByStatus(Model model) {
        String[] statuses = {"Backlog", "Sprint Backlog", "In Progress", "In Review", "Completed"};
        Map<String, List<Task>> tasksMap = new HashMap<>();

        for (String status : statuses) {
            List<Task> tasks = taskService.findByStatus(status);
            tasksMap.put(status.replace(" ", ""), tasks);
            System.out.println("Tasks for " + status + ": " + tasks.size()); // Add log here
        }

        model.addAttribute("status", statuses);
        model.addAttribute("tasks", tasksMap);
        return "seeTask"; // Ensure this matches your Thymeleaf template name
    }


    @GetMapping("/addTask/{projectId}/{subprojectId}")
    public String showAddTaskPage(@PathVariable(required = false) Long projectId,
                                  @PathVariable(required = false) Long subprojectId, Model model) {
        if (projectId == null || subprojectId == null) {
            // Handle missing ids appropriately
            return "redirect:/seeTask";
        }
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
        Task task = new Task(taskName, taskDescription, taskDateTime, taskDeadlineDateTime, "backlog");
        task.setProjectId(projectId);
        task.setSubprojectId(subprojectId);
        task.setStatus("backlog"); // replace "default status" with the actual default status you want to use
        taskService.addTask(task, projectId, subprojectId);
        redirectAttributes.addFlashAttribute("successMessage", "Task added successfully.");
        return "redirect:/tasks/" + projectId + "/" + subprojectId;
    }

    @PostMapping("/deleteTask/{projectId}/{taskId}/{subprojectId}")
    public String deleteTask(@PathVariable Long projectId, @PathVariable Long taskId, @PathVariable Long subprojectId, RedirectAttributes redirectAttributes) {
        Task task = taskService.getTaskById(taskId);
        if (task == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Task not found.");
            return "redirect:/tasks/" + projectId;
        }
        taskService.deleteTaskById(taskId);
        redirectAttributes.addFlashAttribute("successMessage", "Task deleted successfully.");
        return "redirect:/tasks/" + projectId + "/" + subprojectId;
    }

    @GetMapping("/updateTask/{id}")
    public String showUpdateForm(@PathVariable("id") Long taskId, Model model) {
        logger.info("Task ID: " + taskId);
        if (taskId == null) {
            logger.error("Task ID is null in showUpdateForm");
        }
        logger.info("Task ID: " + taskId);
        Task task = taskService.getTaskById(taskId);
        logger.info("Retrieved task: " + task);
        model.addAttribute("task", task);
        model.addAttribute("projectId", task.getProjectId());
        model.addAttribute("subprojectId", task.getSubprojectId());
        return "addTask";
    }


    @PostMapping("/updateTask/{id}")
public String updateTask(@PathVariable("id") Long taskId,
                         @ModelAttribute Task updatedTask,
                         RedirectAttributes redirectAttributes) {
    try {
        Task existingTask = taskService.getTaskById(taskId);
        if (existingTask == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Task not found.");
            return "redirect:/tasks";
        }

        // Update fields other than IDs
        existingTask.setName(updatedTask.getName());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setDate(updatedTask.getDate());
        existingTask.setDeadline(updatedTask.getDeadline());

        Task updatedTaskInDb = taskService.updateTask(existingTask);
        redirectAttributes.addFlashAttribute("successMessage", "Task updated successfully with ID: " + updatedTaskInDb.getTask_id());
        return "redirect:/tasks/" + existingTask.getProjectId() + "/" + existingTask.getSubprojectId();
    } catch (IllegalArgumentException e) {
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/tasks";
    }
}

    @PostMapping("/updateTaskStatus/{taskId}")
    public String updateTaskStatus(@PathVariable("taskId") Long taskId, @RequestParam("status") String status, RedirectAttributes redirectAttributes) {
        try {
            taskService.updateTaskStatus(taskId, status);
            redirectAttributes.addFlashAttribute("successMessage", "Task status updated successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating task status.");
        }
        return "redirect:/seeTask"; // Adjust the redirect view as necessary
    }



}