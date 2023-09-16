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
        Student tmpStudent = studentService.getStudent(id);
        if(tmpStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.removeStudent(id));
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student)
    {
        if(studentService.getStudent(student.getId()) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.changeStudent(student));
    }

    @GetMapping
    public ResponseEntity<List<Student>> getStudents (@RequestParam(required = false) Integer minAge,
                                                      @RequestParam(required = false) Integer maxAge)
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
    public ResponseEntity<Faculty> findFacultyByStudent (@PathVariable Long id)
    {
        if(studentService.getStudent(id) == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.findStudentsFaculty(id));
    }
}
