package org.cperez.springcloud.msvc.alumnos.repositories;

import org.cperez.springcloud.msvc.alumnos.models.entity.Alumno;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AlumnoRepository extends CrudRepository<Alumno, Long> {
    Optional<Alumno> findByEmail(String email);

    @Query("select u from Alumno u where u.email=?1")
    Optional<Alumno> porEmail(String email);

    boolean existsByEmail(String email);

}