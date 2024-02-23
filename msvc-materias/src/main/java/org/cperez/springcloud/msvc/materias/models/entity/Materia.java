package org.cperez.springcloud.msvc.materias.models.entity;

import org.cperez.springcloud.msvc.materias.models.Alumno;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "materias")
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "materia_id")
    private List<MateriaAlumno> materiaAlumnos;

    @Transient
    private List<Alumno> alumnos;

    public Materia() {
        materiaAlumnos = new ArrayList<>();
        alumnos = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void addMateriaAlumno(MateriaAlumno materiaAlumno) {
        materiaAlumnos.add(materiaAlumno);
    }

    public void removeMateriaAlumno(MateriaAlumno materiaAlumno) {
        materiaAlumnos.remove(materiaAlumno);
    }
    public List<MateriaAlumno> getMateriaAlumnos() {
        return materiaAlumnos;
    }

    public void setMateriaAlumnos(List<MateriaAlumno> materiaAlumnos) {
        this.materiaAlumnos = materiaAlumnos;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }
}