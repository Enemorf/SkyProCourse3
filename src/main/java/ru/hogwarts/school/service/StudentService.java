package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService
{
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository)
    {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student)
    {
        studentRepository.save(student);
        return student;
    }

    public Student getStudent(long id)
    {
        return studentRepository.findById(id).get();
    }

    public Student removeStudent(long id)
    {
        Student tmpStd = studentRepository.findById(id).get();
        studentRepository.deleteById(id);
        return tmpStd;
    }

    public Student changeStudent( Student newStudent)
    {
        studentRepository.save(newStudent);
        return newStudent;
    }

    public List<Student> sortByAge(int age)
    {
        return studentRepository.findByAge(age);
    }
}