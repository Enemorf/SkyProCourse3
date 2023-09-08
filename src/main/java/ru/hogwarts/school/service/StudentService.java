package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService
{
    private final Map<Long,Student> studentBase = new HashMap<>();
    private int count = 0;

    public Student addStudent(Student student)
    {
        studentBase.put(student.getId(), student);
        count++;
        return student;
    }

    public Student addStudent(long id, String name, int age)
    {
        Student tmpStd = new Student(id,name,age);
        studentBase.put(id, tmpStd);
        count++;
        return tmpStd;
    }

    public Student getStudent(long id)
    {
        return studentBase.get(id);
    }

    public Student removeStudent(long id)
    {
        Student tmpStd = studentBase.get(id);
        studentBase.remove(id);
        return tmpStd;
    }

    public Student changeStudent(long id, Student newStudent)
    {
        studentBase.replace(id, newStudent);
        return newStudent;
    }

    public List<Student> sortByAge(int age)
    {
        return studentBase.values().stream().filter(std -> std.getAge()>=age).toList();
    }
}