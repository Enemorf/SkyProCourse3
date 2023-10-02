package ru.hogwarts.school;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.hogwarts.school.TestCONSTANS.*;

@WebMvcTest
public class StudentControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private FacultyRepository facultyRepository;
    @MockBean
    private AvatarRepository avatarRepository;

    @SpyBean
    private StudentService studentService;

    @SpyBean
    private FacultyService facultyService;
    @SpyBean
    private AvatarService avatarService;

    @InjectMocks
    private StudentController studentController;

    @Test //GET
    void getStudent() throws Exception {
        Student student = new Student();
        student.setId(MOCK_STUDENT_ID);
        student.setName(MOCK_STUDENT_NAME);
        student.setAge(MOCK_STUDENT_AGE);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/student/" + MOCK_STUDENT_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(MOCK_STUDENT_ID))
                .andExpect(jsonPath("$.name").value(MOCK_STUDENT_NAME))
                .andExpect(jsonPath("$.age").value(MOCK_STUDENT_AGE));
    }

    @Test //GET
    void findFacultyByStudent() throws Exception
    {
        Faculty faculty = new Faculty();
        faculty.setId(MOCK_FACULTY_ID);
        faculty.setName(MOCK_FACULTY_NAME);
        faculty.setColor(MOCK_FACULTY_COLOR);

        Student student = new Student();
        student.setId(MOCK_STUDENT_ID);
        student.setName(MOCK_STUDENT_NAME);
        student.setAge(MOCK_STUDENT_AGE);
        student.setFaculty(faculty);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + MOCK_STUDENT_ID +"/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(MOCK_FACULTY_ID))
                .andExpect(jsonPath("$.name").value(MOCK_FACULTY_NAME))
                .andExpect(jsonPath("$.color").value(MOCK_FACULTY_COLOR));
    }

    @Test //POST
    void postStudent() throws Exception
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", MOCK_STUDENT_NAME);
        jsonObject.put("age", MOCK_STUDENT_AGE);

        Student student = new Student();
        student.setId(MOCK_STUDENT_ID);
        student.setName(MOCK_STUDENT_NAME);
        student.setAge(MOCK_STUDENT_AGE);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/student")
                .content(jsonObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(MOCK_STUDENT_ID))
                .andExpect(jsonPath("$.name").value(MOCK_STUDENT_NAME))
                .andExpect(jsonPath("$.age").value(MOCK_STUDENT_AGE));
    }

    @Test //PUT
    void putStudent() throws Exception
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Andrey");
        jsonObject.put("age", 25);

        Student student = new Student();
        student.setId(MOCK_STUDENT_ID);
        student.setName(MOCK_STUDENT_NAME);
        student.setAge(MOCK_STUDENT_AGE);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                .put("/student")
                .content(jsonObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(MOCK_STUDENT_ID))
                .andExpect(jsonPath("$.name").value("Andrey"))
                .andExpect(jsonPath("$.age").value(25));
    }

    @Test //DELETE
    void deleteStudent() throws Exception
    {
        Student student = new Student();
        student.setId(MOCK_STUDENT_ID);
        student.setName(MOCK_STUDENT_NAME);
        student.setAge(MOCK_STUDENT_AGE);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/student/"+ MOCK_STUDENT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(MOCK_STUDENT_ID))
                .andExpect(jsonPath("$.name").value(MOCK_STUDENT_NAME))
                .andExpect(jsonPath("$.age").value(MOCK_STUDENT_AGE));

    }
}
