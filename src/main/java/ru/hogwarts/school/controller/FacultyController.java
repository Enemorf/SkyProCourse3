package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController
{
    private final FacultyService facultyService;

    public FacultyController (FacultyService facultyService)
    {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty)
    {
        return ResponseEntity.ok(facultyService.addFaculty(faculty));
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getStudent(@PathVariable Long id)
    {
        var tmpFaculty = facultyService.getFaculty(id);
        if(tmpFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tmpFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteStudent(@PathVariable Long id)
    {
        var tmpFaculty = facultyService.getFaculty(id);
        if(tmpFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyService.removeFaculty(id));
    }

    @PutMapping
    public ResponseEntity<Faculty> updateStudent(@RequestBody Faculty faculty)
    {
        var tmp = facultyService.changeFaculty(faculty);
        if(tmp == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(tmp);
        }
    }

    @GetMapping
    public ResponseEntity<List<Faculty>> findFaculty (@RequestParam String nameOrColor)
    {
        var tmp = facultyService.findByNameIgnoreCaseOrByColorIgnoreCase(nameOrColor);
        if(tmp == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tmp);
    }

    @GetMapping("{id}/students")
    public ResponseEntity<List<Student>> getStudentsByFaculty(@PathVariable Long id) {

        var tmp = facultyService.findStudentsByFaculty(id);
        if(tmp == null)
        {
          return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tmp);
    }
}
