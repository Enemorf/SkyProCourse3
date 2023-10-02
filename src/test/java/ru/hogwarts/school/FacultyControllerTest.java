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
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
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
import static ru.hogwarts.school.TestCONSTANS.MOCK_STUDENT_AGE;

@WebMvcTest
public class FacultyControllerTest
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
    private FacultyController facultyController;

    @Test //GET-test
    void getFacultyStudent() throws Exception
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

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + MOCK_FACULTY_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(MOCK_FACULTY_ID))
                .andExpect(jsonPath("$.name").value(MOCK_FACULTY_NAME))
                .andExpect(jsonPath("$.color").value(MOCK_FACULTY_COLOR));
    }

    @Test //POST-test
    void postFaculty() throws Exception
    {
        Faculty faculty = new Faculty();
        faculty.setId(MOCK_FACULTY_ID);
        faculty.setName(MOCK_FACULTY_NAME);
        faculty.setColor(MOCK_FACULTY_COLOR);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", MOCK_FACULTY_NAME);
        jsonObject.put("color", MOCK_FACULTY_COLOR);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(MOCK_FACULTY_ID))
                .andExpect(jsonPath("$.name").value(MOCK_FACULTY_NAME))
                .andExpect(jsonPath("$.color").value(MOCK_FACULTY_COLOR));
    }

    @Test //PUT-test
    void putFaculty() throws Exception
    {
        Faculty faculty = new Faculty();
        faculty.setId(MOCK_FACULTY_ID);
        faculty.setName(MOCK_FACULTY_NAME);
        faculty.setColor(MOCK_FACULTY_COLOR);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Malliny");
        jsonObject.put("color", "Black");

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(MOCK_FACULTY_ID))
                .andExpect(jsonPath("$.name").value("Malliny"))
                .andExpect(jsonPath("$.color").value("Black"));
    }

    @Test //DELETE-test
    void deleteFaculty() throws Exception
    {
        Faculty faculty = new Faculty();
        faculty.setId(MOCK_FACULTY_ID);
        faculty.setName(MOCK_FACULTY_NAME);
        faculty.setColor(MOCK_FACULTY_COLOR);

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/"+ MOCK_FACULTY_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(MOCK_FACULTY_ID))
                .andExpect(jsonPath("$.name").value(MOCK_FACULTY_NAME))
                .andExpect(jsonPath("$.color").value(MOCK_FACULTY_COLOR));

    }



}
