package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController
{
    private final StudentService studentService;

    public StudentController(StudentService studentService)
    {
        this.studentService = studentService;
    }
    @PostMapping ()
    public ResponseEntity<Student> createStudent(@RequestBody Student student)
    {
        studentService.addStudent(student);
        return ResponseEntity.ok(student);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id)
    {
        return ResponseEntity.ok(studentService.getStudent(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id)
    {
        return ResponseEntity.ok(studentService.removeStudent(id));
    }

    @PutMapping()
    public ResponseEntity<Student> updateStudent(@RequestBody Student student)
    {
        return ResponseEntity.ok(studentService.changeStudent(student.getId(), student));
    }

    @GetMapping("/sort/{age}")
    public List<Student> getStudentByAge(@PathVariable int age)
    {
        return studentService.sortByAge(age);
    }
}
