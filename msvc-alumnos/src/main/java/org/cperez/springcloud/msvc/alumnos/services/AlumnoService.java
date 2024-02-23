package org.cperez.springcloud.msvc.alumnos.services;

import org.cperez.springcloud.msvc.alumnos.models.entity.Alumno;

import java.util.List;
import java.util.Optional;

public interface AlumnoService {
    List<Alumno> listar();
    Optional<Alumno> porId(Long id);
    Alumno guardar(Alumno alumno);
    void eliminar(Long id);
    List<Alumno> listarPorIds(Iterable<Long> ids);

    Optional<Alumno> porEmail(String email);
    boolean existePorEmail(String email);
}
