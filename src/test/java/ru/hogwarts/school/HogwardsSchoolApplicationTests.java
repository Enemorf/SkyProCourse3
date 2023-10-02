package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HogwardsSchoolApplicationTests
{
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private final int id = 1;

    @Test
    void controllerLoad() throws Exception
    {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test //GET-test
    void getStudentsTest() throws Exception
    {
        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student", String.class))
                .isNotNull();
    }

    @Test //GET-test
    void getStudentTest() throws Exception
    {
        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/"+id, String.class))
                .isNotNull();
    }

    @Test //GET-test
    void getFacultyByStudent() throws Exception
    {
        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/"+id+"/faculty",
                String.class)).isNotNull();
    }

    @Test //GET-test
    void getAvatar() throws Exception
    {
        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/"+id+"/avatar",
                String.class)).isNotNull();
    }

    @Test //GET-test
    void getAvatarPreview() throws Exception
    {
        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/"+id+"/avatar/preview",
                String.class)).isNotNull();
    }

    @Test //POST-test
    void postStudent() throws Exception
    {
        Student student = new Student();
        student.setName("Primer");
        student.setAge(42);

        Assertions.assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/student",student,
                String.class)).isNotNull();
    }

    @Test //PUT-test
    void putStudent() throws Exception
    {
        Student student = new Student();
        student.setName("Primer2");
        student.setAge(40);

        Assertions.assertThat(this.testRestTemplate.exchange("http://localhost:" + port + "/student/"+id,
                HttpMethod.PUT, new HttpEntity<>(student),String.class)).isNotNull();
    }

    @Test //DELETE-test
    void deleteStudent() throws Exception
    {
        Assertions.assertThat(this.testRestTemplate.exchange("http://localhost:" + port + "/student/"+id,
                HttpMethod.DELETE, HttpEntity.EMPTY,String.class)).isNotNull();
    }

}
