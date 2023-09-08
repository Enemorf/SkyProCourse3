package ru.hogwarts.school.model;

import lombok.Data;

@Data
public class Faculty
{
    private long id;
    private String name;
    private String color;

    public Faculty (long id, String name, String color)
    {
        this.id = id;
        this.name = name;
        this.color = color;
    }
}
