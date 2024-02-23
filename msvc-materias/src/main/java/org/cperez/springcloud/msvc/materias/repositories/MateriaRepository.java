package org.cperez.springcloud.msvc.materias.repositories;

import org.cperez.springcloud.msvc.materias.models.entity.Materia;
import org.springframework.data.repository.CrudRepository;
public interface MateriaRepository extends CrudRepository<Materia, Long> {
}
