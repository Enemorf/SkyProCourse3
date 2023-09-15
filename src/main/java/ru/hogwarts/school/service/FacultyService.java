package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FacultyService
{
    private final Map<Long, Faculty> facultyBase = new HashMap<>();
    private int count;

    public Faculty addFaculty(Faculty faculty)
    {
        facultyBase.put(faculty.getId(), faculty);
        count++;
        return faculty;
    }

    public Faculty addFaculty(long id, String name, String color)
    {
        Faculty tmpFacl = new Faculty(id,name,color);
        facultyBase.put(id,tmpFacl);
        count++;
        return tmpFacl;
    }

    public Faculty getFaculty(long id)
    {
        return facultyBase.get(id);
    }

    public Faculty removeFaculty(long id)
    {
        Faculty tmpFacl = facultyBase.get(id);
        facultyBase.remove(id);
        return tmpFacl;
    }

    public Faculty changeFaculty(long id, Faculty newFaculty)
    {
        facultyBase.replace(id, newFaculty);
        return newFaculty;
    }

    public List<Faculty> sortByColor(String color)
    {
        return facultyBase.values().stream().filter(fcl -> fcl.getColor().contains(color)).toList();
    }
}
