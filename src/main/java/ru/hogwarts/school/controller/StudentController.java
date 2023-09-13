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
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student)
    {
        if(student == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(studentService.addStudent(student));
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id)
    {
        Student tmpStudent = studentService.getStudent(id);
        if(tmpStudent == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tmpStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id)
    {
        Student tmpStudent = studentService.getStudent(id);
        if(tmpStudent == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(studentService.removeStudent(id));
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student)
    {
        if(student == null || studentService.getStudent(student.getId()) == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(studentService.changeStudent(student));
    }

    @GetMapping("/sortByAge")
    public List<Student> getStudentByAge (@RequestParam(name = "age") int age)
    {
        return studentService.sortByAge(age);
    }
}
