package org.cperez.springcloud.msvc.materias.clients;

import org.cperez.springcloud.msvc.materias.models.Alumno;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="msvc-alumnos", url="localhost:8001")
public interface AlumnoClientRest {

    @GetMapping("/{id}")
    Alumno detalle(@PathVariable Long id);

    @PostMapping("/")
    Alumno crear(@RequestBody Alumno alumno);

    @GetMapping("/alumnos-por-materia")
    List<Alumno> obtenerAlumnosPorMateria(@RequestParam Iterable<Long> ids);
}

