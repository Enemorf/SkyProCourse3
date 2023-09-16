package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty,Long>
{
    List<Faculty> findByColor(String color);
    List<Faculty> findByNameIgnoreCaseOrByColorIgnoreCase(String name, String color);

    @Query("SELECT name FROM Student student WHERE student.faculty = faculty_id")
    List<Student> findStudentsByFaculty(@Param("faculty_id") Long id);
}
