package ru.hogwarts.school.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Student
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
    @SequenceGenerator(name = "student_seq",sequenceName = "student_seq",allocationSize = 1)
    private Long id;

    private String name;
    private int age;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    public Student(Long id, String name, int age)
    {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Student() {}
}
