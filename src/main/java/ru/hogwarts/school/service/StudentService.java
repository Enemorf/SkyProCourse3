package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

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
        return studentRepository.save(student);
    }

    public Student getStudent(Long id)
    {
        return studentRepository.findById(id).orElse(null);
    }

    public Student removeStudent(Long id)
    {
        return studentRepository.findById(id)
                .map(student -> {
                    studentRepository.deleteById(student.getId());
                    return student;
                })
                .orElse(null);
    }

    public Student changeStudent(Student newStudent)
    {
        return studentRepository.findById(newStudent.getId())
                .map( student ->
                {
                    student.setName(newStudent.getName());
                    student.setAge(newStudent.getAge());
                    return studentRepository.save(student);
                })
                .orElse(null);
    }

    public List<Student> sortByAge(int age)
    {
        return studentRepository.findByAge(age);
    }

    public List<Student> findByAgeBetween (int minAge, int maxAge)
    {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Faculty findStudentsFaculty(Long id)
    {
        return studentRepository.findById(id)
                .map(Student::getFaculty)
                .orElse(null);
    }
}