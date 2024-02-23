package org.cperez.springcloud.msvc.materias.models.entity;

import javax.persistence.*;

@Entity
@Table(name = "materias_alumnos")
public class MateriaAlumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="alumno_id", unique = true)
    private Long alumnoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(Long alumnoId) {
        this.alumnoId = alumnoId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MateriaAlumno)) {
            return false;
        }
        MateriaAlumno o = (MateriaAlumno) obj;
        return this.alumnoId != null && this.alumnoId.equals(o.alumnoId);
    }
}
