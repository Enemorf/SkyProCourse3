package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
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
        if(student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.addStudent(student));
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id)
    {
        Student tmpStudent = studentService.getStudent(id);
        if(tmpStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tmpStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id)
    {
        var tmp = studentService.removeStudent(id);
        if(tmp == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tmp);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student)
    {
        var tmp = studentService.changeStudent(student);
        if(tmp == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tmp);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getStudents (@RequestParam Integer minAge, @RequestParam Integer maxAge)
    {
        if(minAge != null)
        {
            if(maxAge != null)
            {
                return ResponseEntity.ok(studentService.findByAgeBetween(minAge, maxAge));
            }
            return ResponseEntity.ok(studentService.sortByAge(minAge));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("{id}/faculty")
    public ResponseEntity<Faculty> findFacultyByStudent(@PathVariable Long id)
    {
        var tmp = studentService.findStudentsFaculty(id);

        if(tmp == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tmp);
    }
}
