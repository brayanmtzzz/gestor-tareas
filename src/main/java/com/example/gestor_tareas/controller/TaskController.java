package com.example.gestor_tareas.controller;

import com.example.gestor_tareas.dto.PostDTO;
import com.example.gestor_tareas.model.Task;
import com.example.gestor_tareas.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/tasks")
    public String getAllTasks(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        model.addAttribute("newTask", new Task());
        return "tasks";
    }

    @PostMapping("/tasks/save")
    public String saveTask(@ModelAttribute Task task) {
        if (task.getId() != null) {
            Task existingTask = taskRepository.findById(task.getId()).orElse(null);
            if (existingTask != null) {
                existingTask.setCompleted(task.isCompleted());
                taskRepository.save(existingTask);
                return "redirect:/tasks";
            }
        }
        taskRepository.save(task);
        return "redirect:/tasks";
    }
    
    @GetMapping("/tasks/toggle/{id}")
    public String toggleTaskComplete(@PathVariable Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null) {
            task.setCompleted(!task.isCompleted());
            taskRepository.save(task);
        }
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/tasks";
    }
    
    @GetMapping("/external-posts")
    public String getExternalPosts(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://jsonplaceholder.typicode.com/posts?_limit=12";
        
        PostDTO[] posts = restTemplate.getForObject(apiUrl, PostDTO[].class);
        
        model.addAttribute("posts", posts);
        return "external-posts";
    }
}