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
    public ResponseEntity<List<Faculty>> findFaculty (@RequestParam(required = false) String name,
                                                      @RequestParam(required = false) String color)
    {
        if(color != null)
        {
            if(name != null)
            {
                return ResponseEntity.ok(facultyService.findByNameIgnoreCaseOrByColorIgnoreCase(name, color));
            }
            return ResponseEntity.ok(facultyService.sortByColor(color));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("{id}/students")
    public ResponseEntity<List<Student>> getStudentsByFaculty(@PathVariable Long id) {
        if(facultyService.getFaculty(id) == null)
        {
          return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyService.findStudentsByFaculty(id));
    }
}
