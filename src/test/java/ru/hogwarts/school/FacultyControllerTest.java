package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest
{
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private final int id = 1;

    @Test
    void controllerLoad() throws Exception
    {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test //GET-test
    void getFaculty() throws Exception
    {
        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty/"+id, String.class))
                .isNotNull();
    }

    @Test //GET-test
    void getStudentsByFaculty() throws Exception
    {
        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty/"+id+"/students",
                        String.class)).isNotNull();
    }

    @Test //GET-test
    void findFaculty() throws Exception
    {
        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty",
                String.class)).isNotNull();
    }

    @Test //POST-test
    void postFaculty() throws Exception
    {
        Faculty faculty = new Faculty();
        faculty.setName("Another");
        faculty.setColor("Yellow");

        Assertions.assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/faculty",faculty,
                String.class)).isNotNull();
    }

    @Test //PUT-test
    void putFaculty() throws Exception
    {
        Faculty faculty = new Faculty();
        faculty.setName("Another43");
        faculty.setColor("Yellow");

        Assertions.assertThat(this.testRestTemplate.exchange("http://localhost:" + port + "/faculty/"+id,
                HttpMethod.PUT, new HttpEntity<>(faculty),String.class)).isNotNull();
    }

    @Test //DELETE-test
    void deleteFaculty() throws Exception
    {
        Assertions.assertThat(this.testRestTemplate.exchange("http://localhost:" + port + "/faculty/"+id,
                HttpMethod.DELETE, HttpEntity.EMPTY,String.class)).isNotNull();
    }

}
