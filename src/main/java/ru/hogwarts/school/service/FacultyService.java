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
        if(faculty == null)
            return null;

        facultyRepository.save(faculty);
        return faculty;
    }

    public Faculty getFaculty(Long id)
    {
        if(facultyRepository.findById(id).isEmpty())
            return null;
        return facultyRepository.findById(id).get();
    }

    public Faculty removeFaculty(long id)
    {
        if(facultyRepository.findById(id).isEmpty())
            return null;
        Faculty tmp = facultyRepository.findById(id).get();
        facultyRepository.deleteById(id);
        return tmp;
    }

    public Faculty changeFaculty(Faculty newFaculty)
    {
        if(facultyRepository.findById(newFaculty.getId()).isEmpty())
            return null;
        Faculty tmp = facultyRepository.findById(newFaculty.getId()).get();
        tmp.setName(newFaculty.getName());
        tmp.setColor(newFaculty.getColor());
        facultyRepository.save(tmp);
        return tmp;
    }

    public List<Faculty> sortByColor(String color)
    {
        return facultyRepository.findByColor(color);
    }
}
