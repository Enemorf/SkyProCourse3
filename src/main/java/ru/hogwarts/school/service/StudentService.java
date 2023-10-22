package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
public class StudentService
{
    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository)
    {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student)
    {
        logger.info("Get Method AddStudent");
        return studentRepository.save(student);
    }

    public Student getStudent(Long id)
    {
        logger.info("Get Method GetStudent");
        return studentRepository.findById(id).orElse(null);
    }

    public Student removeStudent(Long id)
    {
        logger.info("Get Method RemoveStudent");
        return studentRepository.findById(id)
                .map(student -> {
                    studentRepository.deleteById(student.getId());
                    return student;
                })
                .orElse(null);
    }

    public Student changeStudent(Student newStudent)
    {
        logger.info("Get Method ChangeStudent");
        return studentRepository.findById(newStudent.getId())
                .map( student ->
                {
                    student.setName(newStudent.getName());
                    student.setAge(newStudent.getAge());
                    return studentRepository.save(student);
                })
                .orElse(null);
    }

    public List<Student> sortByAge(int age)
    {
        logger.info("Get Method SortByAge");
        return studentRepository.findByAge(age);
    }

    public List<Student> findByAgeBetween (int minAge, int maxAge)
    {
        logger.info("Get Method FindByAgeBetween");
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Faculty findStudentsFaculty(Long id)
    {
        logger.info("Get Method FindStudentsFaculty");
        return studentRepository.findById(id)
                .map(Student::getFaculty)
                .orElse(null);
    }

    public Student findStudent(long id) {
        logger.info("Get Method FindStudent");
        return studentRepository.findById(id).orElseThrow();
    }

    public Integer findAllStudents()
    {
        logger.info("Get Method FindAllStudents");
        return studentRepository.findAllStudents();
    }

    public List<Student> findAllStudentLitterAUpCase()
    {
        logger.info("Get Method FindAllStudentLitterAUpCase");
        return studentRepository.findAll()
                .stream()
                .parallel()
                .filter(student -> student.getName().toUpperCase().startsWith("A"))
                .sorted()
                .collect(toList());
    }


    public Double findAVGStudents()
    {
        logger.info("Get Method FindAVGStudents");
        return studentRepository.findAVGStudents();

    }

    public Double findAVGStudentsStream()
    {
        logger.info("Get Method FindAVGStudentsStream");
        return studentRepository.findAll()
                .stream()
                .parallel()
                .mapToInt(std -> std.getAge())
                .average()
                .getAsDouble();
    }

    public List<Student> findFiveLastStudents()
    {
        logger.info("Get Method FindFiveLastStudents");
        return studentRepository.findFiveLastStudents();
    }


    public Integer getBestRes()
    {
        //return Stream.iterate(1, a-> a+1).limit(1_000_000).reduce(0,(a,b) -> a+b);
        return IntStream.rangeClosed(1,1_000_000).sum();
    }

    public void getParallelSOUT()
    {
        List<String> studentsName = studentRepository.findFirstFiveStudentsName();
        System.out.println(studentsName.get(0));
        System.out.println(studentsName.get(1));

        new Thread(()->
        {
            System.out.println(studentsName.get(2));
            System.out.println(studentsName.get(3));
        }).start();

        new Thread(()->
        {
            System.out.println(studentsName.get(4));
            System.out.println(studentsName.get(5));
        }).start();
    }

    public void getSynchronizedSOUT() {
        List<String> studentsName = studentRepository.findFirstFiveStudentsName();
        Object lock = new Object();

        System.out.println(studentsName.get(0));
        System.out.println(studentsName.get(1));

        synchronized (lock) {
            new Thread(() -> {
                System.out.println(studentsName.get(2));
                System.out.println(studentsName.get(3));
            }).start();
        }
        synchronized (lock) {
            new Thread(() -> {
                System.out.println(studentsName.get(4));
                System.out.println(studentsName.get(5));
            }).start();
        }
    }

}