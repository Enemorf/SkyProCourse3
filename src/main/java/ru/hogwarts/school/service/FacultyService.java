package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FacultyService
{
    private final FacultyRepository facultyRepository;

    public FacultyService (FacultyRepository facultyRepository)
    {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty)
    {
        facultyRepository.save(faculty);
        return faculty;
    }

    public Faculty getFaculty(long id)
    {
        return facultyRepository.findById(id).get();
    }

    public Faculty removeFaculty(long id)
    {
        Faculty tmpFacl = facultyRepository.findById(id).get();
        facultyRepository.deleteById(id);
        return tmpFacl;
    }

    public Faculty changeFaculty(Faculty newFaculty)
    {
        facultyRepository.save(newFaculty);
        return newFaculty;
    }

    public List<Faculty> sortByColor(String color)
    {
        return facultyRepository.findByColor(color);
    }
}
