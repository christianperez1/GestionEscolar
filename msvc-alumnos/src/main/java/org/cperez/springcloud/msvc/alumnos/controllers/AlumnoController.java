package org.cperez.springcloud.msvc.alumnos.controllers;

import org.cperez.springcloud.msvc.alumnos.models.entity.Alumno;
import org.cperez.springcloud.msvc.alumnos.services.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class AlumnoController {

    @Autowired
    private AlumnoService service;

    @GetMapping
    public List<Alumno> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Alumno> alumnoOptional = service.porId(id);
        if (alumnoOptional.isPresent()) {
            return ResponseEntity.ok(alumnoOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Alumno alumno, BindingResult result) {

        if (result.hasErrors()) {
            return validar(result);
        }

        if (!alumno.getEmail().isEmpty() && service.existePorEmail(alumno.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(Collections
                            .singletonMap("mensaje", "Ya existe un alumno con ese correo electronico!"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(alumno));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Alumno alumno, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return validar(result);
        }

        Optional<Alumno> o = service.porId(id);
        if (o.isPresent()) {
            Alumno alumnoDb = o.get();
            if (!alumno.getEmail().isEmpty() &&
                    !alumno.getEmail().equalsIgnoreCase(alumnoDb.getEmail()) &&
                    service.porEmail(alumno.getEmail()).isPresent()) {
                return ResponseEntity.badRequest()
                        .body(Collections
                                .singletonMap("mensaje", "Ya existe un alumno con ese correo electronico!"));
            }

            alumnoDb.setNombre(alumno.getNombre());
            alumnoDb.setEmail(alumno.getEmail());
            alumnoDb.setTelefono(alumno.getTelefono());
            alumnoDb.setGrado(alumno.getGrado());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(alumnoDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Alumno> o = service.porId(id);
        if (o.isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/alumnos-por-materia")
    public ResponseEntity<?> obtenerAlumnosPorMateria(@RequestParam List<Long> ids){
        return ResponseEntity.ok(service.listarPorIds(ids));
    }

    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
