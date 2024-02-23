package org.cperez.springcloud.msvc.materias.controllers;

import feign.FeignException;
import org.cperez.springcloud.msvc.materias.models.Alumno;
import org.cperez.springcloud.msvc.materias.models.entity.Materia;
import org.cperez.springcloud.msvc.materias.services.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class MateriaController {

    @Autowired
    private MateriaService service;

    @GetMapping
    public ResponseEntity<List<Materia>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Materia> o = service.porIdConAlumnos(id);//service.porId(id);
        if (o.isPresent()) {
            return ResponseEntity.ok(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> crear(@Valid @RequestBody Materia materia, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        Materia materiaDb = service.guardar(materia);
        return ResponseEntity.status(HttpStatus.CREATED).body(materiaDb);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Materia materia, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validar(result);
        }
        Optional<Materia> o = service.porId(id);
        if (o.isPresent()) {
            Materia materiaDb = o.get();
            materiaDb.setNombre(materia.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(materiaDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Materia> o = service.porId(id);
        if (o.isPresent()) {
            service.eliminar(o.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/asignar-alumno/{materiaId}")
    public ResponseEntity<?> asignarAlumno(@RequestBody Alumno alumno, @PathVariable Long materiaId) {
        Optional<Alumno> o;
        try {
            o = service.asignarAlumno(alumno, materiaId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No existe el alumno por " +
                            "el id o error en la comunicacion: " + e.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear-alumno/{materiaId}")
    public ResponseEntity<?> crearAlumno(@RequestBody Alumno alumno, @PathVariable Long materiaId) {
        Optional<Alumno> o;
        try {
            o = service.crearAlumno(alumno, materiaId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No se pudo crear el alumno " +
                            "o error en la comunicacion: " + e.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-alumno/{materiaId}")
    public ResponseEntity<?> eliminarAlumno(@RequestBody Alumno alumno, @PathVariable Long materiaId) {
        Optional<Alumno> o;
        try {
            o = service.eliminarAlumno(alumno, materiaId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No existe el alumno por " +
                            "el id o error en la comunicacion: " + e.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}