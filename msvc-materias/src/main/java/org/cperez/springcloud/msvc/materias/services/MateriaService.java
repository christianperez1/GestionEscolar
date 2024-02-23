package org.cperez.springcloud.msvc.materias.services;

import org.cperez.springcloud.msvc.materias.models.Alumno;
import org.cperez.springcloud.msvc.materias.models.entity.Materia;

import java.util.List;
import java.util.Optional;

public interface MateriaService {
    List<Materia> listar();
    Optional<Materia> porId(Long id);
    Optional<Materia> porIdConAlumnos(Long id);
    Materia guardar(Materia materia);
    void eliminar(Long id);

    Optional<Alumno> asignarAlumno(Alumno alumno, Long materiaId);
    Optional<Alumno> crearAlumno(Alumno alumno, Long materiaId);
    Optional<Alumno> eliminarAlumno(Alumno alumno, Long materiaId);
}
