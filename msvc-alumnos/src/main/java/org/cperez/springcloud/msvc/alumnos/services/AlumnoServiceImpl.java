package org.cperez.springcloud.msvc.alumnos.services;

import org.cperez.springcloud.msvc.alumnos.models.entity.Alumno;
import org.cperez.springcloud.msvc.alumnos.repositories.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnoServiceImpl implements AlumnoService{

    @Autowired
    private AlumnoRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Alumno> listar() {
        return (List<Alumno>) repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Alumno> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Alumno guardar(Alumno alumno) {
        return repository.save(alumno);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alumno> listarPorIds(Iterable<Long> ids) {
        return (List<Alumno>) repository.findAllById(ids);
    }

    @Override
    public Optional<Alumno> porEmail(String email) {
        return repository.porEmail(email);
    }

    @Override
    public boolean existePorEmail(String email) {
        return repository.existsByEmail(email);
    }
}