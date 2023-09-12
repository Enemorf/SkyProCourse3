package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
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

    @PostMapping("")
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty)
    {
        return ResponseEntity.ok(facultyService.addFaculty(faculty));
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getStudent(@PathVariable Long id)
    {
        return ResponseEntity.ok(facultyService.getFaculty(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteStudent(@PathVariable Long id)
    {
        return ResponseEntity.ok(facultyService.removeFaculty(id));
    }

    @PutMapping()
    public ResponseEntity<Faculty> updateStudent(@RequestBody Faculty faculty)
    {
        return ResponseEntity.ok(facultyService.changeFaculty(faculty));
    }

    @GetMapping("/sortByColor/{color}")
    public List<Faculty> sortByColor(@PathVariable String color)
    {
        return facultyService.sortByColor(color);
    }
}
