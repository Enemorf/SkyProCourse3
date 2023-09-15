package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;
import java.util.Optional;

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
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(Long id)
    {
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty removeFaculty (Long id)
    {
        return facultyRepository.findById(id)
                .map(faculty ->
                {
                    facultyRepository.deleteById(faculty.getId());
                    return faculty;
                })
                .orElse(null);
    }

    public Faculty changeFaculty(Faculty newFaculty)
    {
        return facultyRepository.findById(newFaculty.getId())
                .map(faculty ->
                {
                    faculty.setName(newFaculty.getName());
                    faculty.setColor(newFaculty.getColor());
                    return facultyRepository.save(faculty);
                })
                .orElse(null);
    }

    public List<Faculty> sortByColor(String color)
    {
        return facultyRepository.findByColor(color);
    }
}
