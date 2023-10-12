package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InfoController
{
    @Value("${server.port}")
    private String res;

    @GetMapping(value = "/getPort")
    public ResponseEntity<String> getPort()
    {
        return ResponseEntity.ok(res);
    }
}
