package com.umg.semana_13_biblioteca_api.repository;

import com.umg.semana_13_biblioteca_api.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findByCategoria(String categoria);

    List<Libro> findByDisponibleTrue();
}