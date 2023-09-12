package ru.hogwarts.school.model;

import jakarta.persistence.*;
import lombok.Data;

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

    public Faculty (Long id, String name, String color)
    {
        this.id = id;
        this.name = name;
        this.color = color;
    }
}
