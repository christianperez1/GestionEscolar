package org.cperez.springcloud.msvc.materias.services;

import org.cperez.springcloud.msvc.materias.clients.AlumnoClientRest;
import org.cperez.springcloud.msvc.materias.models.Alumno;
import org.cperez.springcloud.msvc.materias.models.entity.Materia;
import org.cperez.springcloud.msvc.materias.models.entity.MateriaAlumno;
import org.cperez.springcloud.msvc.materias.repositories.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MateriaServiceImpl implements MateriaService{

    @Autowired
    private MateriaRepository repository;

    @Autowired
    private AlumnoClientRest client;

    @Override
    @Transactional(readOnly = true)
    public List<Materia> listar() {
        return (List<Materia>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Materia> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Materia> porIdConAlumnos(Long id) {
        Optional<Materia> o = repository.findById(id);
        if (o.isPresent()) {
            Materia materia = o.get();
            if (!materia.getMateriaAlumnos().isEmpty()) {
                List<Long> ids = materia.getMateriaAlumnos().stream().map(cu -> cu.getAlumnoId())
                        .collect(Collectors.toList());

                List<Alumno> alumnos = client.obtenerAlumnosPorMateria(ids);
                materia.setAlumnos(alumnos);
            }
            return Optional.of(materia);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Materia guardar(Materia materia) {
        return repository.save(materia);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<Alumno> asignarAlumno(Alumno alumno, Long materiaId) {
        Optional<Materia> o = repository.findById(materiaId);
        if (o.isPresent()) {
            Alumno alumnoMsvc = client.detalle(alumno.getId());

            Materia materia = o.get();
            MateriaAlumno materiaAlumno = new MateriaAlumno();
            materiaAlumno.setAlumnoId(alumnoMsvc.getId());

            materia.addMateriaAlumno(materiaAlumno);
            repository.save(materia);
            return Optional.of(alumnoMsvc);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Alumno> crearAlumno(Alumno alumno, Long materiaId) {
        Optional<Materia> o = repository.findById(materiaId);
        if (o.isPresent()) {
            Alumno alumnoNuevoMsvc = client.crear(alumno);

            Materia materia = o.get();
            MateriaAlumno materiaAlumno = new MateriaAlumno();
            materiaAlumno.setAlumnoId(alumnoNuevoMsvc.getId());

            materia.addMateriaAlumno(materiaAlumno);
            repository.save(materia);
            return Optional.of(alumnoNuevoMsvc);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Alumno> eliminarAlumno(Alumno alumno, Long materiaId) {
        Optional<Materia> o = repository.findById(materiaId);
        if (o.isPresent()) {
            Alumno alumnoMsvc = client.detalle(alumno.getId());

            Materia materia = o.get();
            MateriaAlumno materiaAlumno = new MateriaAlumno();
            materiaAlumno.setAlumnoId(alumnoMsvc.getId());

            materia.removeMateriaAlumno(materiaAlumno);
            repository.save(materia);
            return Optional.of(alumnoMsvc);
        }

        return Optional.empty();
    }
}
