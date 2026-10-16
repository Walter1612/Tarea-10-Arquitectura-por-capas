package com.umg.semana_13_biblioteca_api.controller;

import com.umg.semana_13_biblioteca_api.entity.Libro;
import com.umg.semana_13_biblioteca_api.repository.LibroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroRepository libroRepository;

    public LibroController(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @GetMapping
    public ResponseEntity<List<Libro>> listarTodos() {
        return ResponseEntity.ok(libroRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> buscarPorId(@PathVariable Long id) {
        Optional<Libro> libro = libroRepository.findById(id);

        if (libro.isPresent()) {
            return ResponseEntity.ok(libro.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Libro> registrar(@RequestBody Libro libro) {
        libro.setId(null);
        Libro libroGuardado = libroRepository.save(libro);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(libroGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizar(
            @PathVariable Long id,
            @RequestBody Libro libroActualizado) {

        Optional<Libro> libroExistente = libroRepository.findById(id);

        if (libroExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Libro libro = libroExistente.get();

        libro.setCodigo(libroActualizado.getCodigo());
        libro.setTitulo(libroActualizado.getTitulo());
        libro.setAutor(libroActualizado.getAutor());
        libro.setCategoria(libroActualizado.getCategoria());
        libro.setAnioPublicacion(libroActualizado.getAnioPublicacion());
        libro.setPrecio(libroActualizado.getPrecio());
        libro.setDisponible(libroActualizado.getDisponible());

        Libro libroGuardado = libroRepository.save(libro);

        return ResponseEntity.ok(libroGuardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        if (!libroRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        libroRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Libro>> buscarPorCategoria(
            @PathVariable String categoria) {

        return ResponseEntity.ok(
                libroRepository.findByCategoria(categoria)
        );
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Libro>> listarDisponibles() {

        return ResponseEntity.ok(
                libroRepository.findByDisponibleTrue()
        );
    }
}