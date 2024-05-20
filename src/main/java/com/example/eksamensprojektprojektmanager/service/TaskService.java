package com.example.eksamensprojektprojektmanager.service;

import com.example.eksamensprojektprojektmanager.model.Task;
import com.example.eksamensprojektprojektmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void addTask(Task task, Long projectId, Long subprojectId) {
        taskRepository.addTask(task, projectId,subprojectId);
    }

    public List<Task> getTasksByProjectIdAndSubprojectId(Long projectId,Long subprojectId) {
        return taskRepository.findByProjectIdAndSubprojectId(projectId,subprojectId);
    }

    public void deleteTaskById(Long task_id) {
        taskRepository.deleteTaskById(task_id);
    }


    public Task getTaskById(Long task_id) {
        return taskRepository.findById(task_id);
    }

    public Task updateTask(Task task) {
        return taskRepository.updateTask(task);
    }

    public void updateTaskStatus(Long task_id, String status) {
        taskRepository.updateTaskStatus(task_id, status);
    }


}