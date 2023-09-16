package ru.hogwarts.school.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Faculty
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faculty_seq")
    @SequenceGenerator(name = "faculty_seq",sequenceName = "faculty_seq",allocationSize = 1)
    private Long id;

    private String name;
    private String color;

    @OneToMany(mappedBy = "faculty")
    private Set<Student> students;

    public Faculty (Long id, String name, String color)
    {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public Faculty(){
    }
}
