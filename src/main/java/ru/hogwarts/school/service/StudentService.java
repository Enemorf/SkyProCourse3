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
        if(student == null)
            return null;

        studentRepository.save(student);
        return student;
    }

    public Student getStudent(Long id)
    {
        if(studentRepository.findById(id).isEmpty())
            return null;

        return studentRepository.findById(id).get();
    }

    public Student removeStudent(Long id)
    {
        if(studentRepository.findById(id).isEmpty())
            return null;

        Student tmp = studentRepository.findById(id).get();
        studentRepository.deleteById(id);
        return tmp;
    }

    public Student changeStudent(Student newStudent)
    {
        if(studentRepository.findById(newStudent.getId()).isEmpty())
            return null;
        Student tmp = studentRepository.findById(newStudent.getId()).get();
        tmp.setName(newStudent.getName());
        tmp.setAge(newStudent.getAge());

        studentRepository.save(tmp);
        return tmp;
    }

    public List<Student> sortByAge(int age)
    {
        return studentRepository.findByAge(age);
    }
}