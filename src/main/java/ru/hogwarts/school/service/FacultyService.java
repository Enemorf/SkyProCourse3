package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FacultyService
{
    private final FacultyRepository facultyRepository;

    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService (FacultyRepository facultyRepository)
    {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty)
    {
        logger.info("Get Method AddFaculty");
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(Long id)
    {
        logger.info("Get Method GetFaculty");
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty removeFaculty (Long id)
    {
        logger.info("Get Method RemoveFaculty");
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
        logger.info("Get Method ChangeFaculty");
        return facultyRepository.findById(newFaculty.getId())
                .map(faculty ->
                {
                    faculty.setName(newFaculty.getName());
                    faculty.setColor(newFaculty.getColor());
                    return facultyRepository.save(faculty);
                })
                .orElse(null);
    }

    public List<Faculty> findByNameIgnoreCaseOrByColorIgnoreCase (String nameOrColor)
    {
        logger.info("Get Method FindByNameIgnoreCaseOrByColorIgnoreCase");
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase (nameOrColor,nameOrColor);
    }

    public Collection<Student> findStudentsByFaculty (Long id)
    {
        logger.info("Get Method FindStudentsByFaculty");
        return  facultyRepository.findById(id)
                .map(Faculty::getStudents)
                .orElse(null);
    }


}
