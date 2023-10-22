package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long>
{
    List<Student> findByAge(int age);
    List<Student> findByAgeBetween(int minAge, int maxAge);

    @Query(value = "SELECT COUNT(id) FROM students",nativeQuery = true)
    Integer findAllStudents();

    @Query(value = "SELECT AVG(age) FROM students",nativeQuery = true)
    Double findAVGStudents();

    @Query(value = "SELECT * FROM students ORDER BY if DESC and LIMIT 5", nativeQuery = true)
    List<Student> findFiveLastStudents();

    @Query(value = "SELECT name FROM students LIMIT 6", nativeQuery = true)
    List<String> findFirstFiveStudentsName();
}