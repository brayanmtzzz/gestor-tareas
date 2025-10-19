package com.example.gestor_tareas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data // Anotación de Lombok para generar getters, setters, etc.
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único

    private String title; // Título de la tarea
    private boolean completed = false; // Estado de la tarea
}