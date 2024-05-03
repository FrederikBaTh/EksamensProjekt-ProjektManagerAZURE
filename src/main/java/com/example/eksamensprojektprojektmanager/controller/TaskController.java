package com.example.eksamensprojektprojektmanager.controller;

import com.example.eksamensprojektprojektmanager.model.Task;
import com.example.eksamensprojektprojektmanager.service.TaskService;
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

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks/{projectId}")
    public String showTasks(@PathVariable Long projectId, Model model) {
        List<Task> tasks = taskService.getTasksByProjectId(projectId);
        model.addAttribute("tasks", tasks);
        return "task";
    }

    @GetMapping("/addTask/{projectId}")
    public String showAddTaskPage(@PathVariable Long projectId, Model model) {
        model.addAttribute("projectId", projectId);
        return "addTask";
    }

    @PostMapping("/addTask/{projectId}")
    public String addTask(@PathVariable Integer projectId,
                          @RequestParam("taskName") String taskName,
                          @RequestParam("taskDescription") String taskDescription,
                          @RequestParam("taskDate")
                          @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime taskDateTime,
                          @RequestParam("taskDeadline")
                          @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime taskDeadlineDateTime,
                          RedirectAttributes redirectAttributes) {

        Task task = new Task(taskName, taskDescription, taskDateTime, taskDeadlineDateTime);
        task.setProjectId(projectId);
        taskService.addTask(task, projectId);
        redirectAttributes.addFlashAttribute("successMessage", "Task added successfully.");
        return "redirect:/tasks/" + projectId;
    }


}