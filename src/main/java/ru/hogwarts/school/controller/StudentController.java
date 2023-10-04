package ru.hogwarts.school.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.awt.font.MultipleMaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController
{
    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController(StudentService studentService, AvatarService avatarService)
    {
        this.studentService = studentService;
        this.avatarService = avatarService;
    }
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student)
    {
        if(student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.addStudent(student));
    }

    @GetMapping
    public ResponseEntity<List<Student>> getStudents (@RequestParam Integer minAge, @RequestParam Integer maxAge)
    {
        if(minAge != null)
        {
            if(maxAge != null)
            {
                return ResponseEntity.ok(studentService.findByAgeBetween(minAge, maxAge));
            }
            return ResponseEntity.ok(studentService.sortByAge(minAge));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id)
    {
        Student tmpStudent = studentService.getStudent(id);
        if(tmpStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tmpStudent);
    }

    @GetMapping("{id}/faculty")
    public ResponseEntity<Faculty> findFacultyByStudent(@PathVariable Long id)
    {
        var tmp = studentService.findStudentsFaculty(id);

        if(tmp == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tmp);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id)
    {
        var tmp = studentService.removeStudent(id);
        if(tmp == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tmp);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student)
    {
        var tmp = studentService.changeStudent(student);
        if(tmp == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tmp);
    }


    @GetMapping(value = "/{id}/avatar")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(id);

        Path path = Path.of(avatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @GetMapping(value = "/{id}/avatar/preview")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {
    Avatar avatar = avatarService.findAvatar(id);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
    headers.setContentLength(avatar.getData().length);

    return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @PostMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id, @RequestParam MultipartFile avatar) throws IOException {
    if (avatar.getSize() > 1024 * 400) {
        return ResponseEntity.badRequest().body("File is too big");
    }

    avatarService.uploadAvatar(id, avatar);
    return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/all")
    public ResponseEntity<Integer> findAllStudents()
    {
        var tmp = studentService.findAllStudents();
        if(tmp == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tmp);
    }
    @GetMapping(value = "/avg")
    public ResponseEntity<Double> findAVGStudents()
    {
        var tmp = studentService.findAVGStudents();
        if(tmp == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tmp);
    }

    @GetMapping(value = "/findFive")
    public ResponseEntity<List<Student>> findFiveLastStudents()
    {
        var tmp = studentService.findFiveLastStudents();
        if(tmp == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tmp);
    }

    @GetMapping(value = "/avatar")
    public ResponseEntity<List<Avatar>> findAll(@RequestParam("page") Integer page, @RequestParam("size") Integer size)
    {
        var tmp = avatarService.findAll(page, size);
        if(tmp == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tmp);
    }
}
