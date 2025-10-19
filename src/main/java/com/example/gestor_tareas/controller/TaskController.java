package com.example.gestor_tareas.controller;

import com.example.gestor_tareas.model.Task;
import com.example.gestor_tareas.service.TaskService;
import com.example.gestor_tareas.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private QuoteService quoteService;

    @GetMapping({"/", "/tasks"})
    public String getAllTasks(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        model.addAttribute("newTask", new Task());
        model.addAttribute("pageTitle", "Mis Tareas");
        return "tasks";
    }

    @PostMapping("/tasks/save")
    public String saveTask(@ModelAttribute Task task) {
        taskService.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/toggle/{id}")
    public String toggleTaskComplete(@PathVariable Long id) {
        taskService.toggleTaskCompletion(id);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteById(id);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/find/{id}")
    @ResponseBody
    public Task findTaskById(@PathVariable Long id) {
        return taskService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de tarea inválido:" + id));
    }

    @GetMapping("/quote")
    public String getInspirationalQuote(Model model) {
        model.addAttribute("quote", quoteService.getQuoteOfTheDay());
        model.addAttribute("pageTitle", "Frase del Día");
        return "quote";
    }
}