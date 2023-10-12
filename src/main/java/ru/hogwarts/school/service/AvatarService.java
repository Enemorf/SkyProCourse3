package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService
{
    @Value("${avatars.dir.path}")
    private String avatarsDir;
    private final AvatarRepository avatarRepository;
    private final StudentService studentService;

    private final Logger logger = LoggerFactory.getLogger(AvatarService.class);

    public AvatarService (AvatarRepository avatarRepository, StudentService studentService)
    {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }
    public Avatar findAvatar(long studentId) {
        logger.info("Get Method FindAvatar");
        return avatarRepository.findByStudentId(studentId).orElseThrow();
    }
    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        logger.info("Get Method UploadAvatar");
        Student student = studentService.findStudent(studentId);

        Path filePath = Path.of(avatarsDir, studentId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = avatarRepository.findByStudentId(studentId).orElseGet(Avatar::new);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());

        avatarRepository.save(avatar);
    }

    public List<Avatar> findAll(Integer page, Integer size)
    {
        logger.info("Get Method findAll");
        PageRequest pageRequest = PageRequest.of(page-1,size);
        return avatarRepository.findAll(pageRequest).getContent();
    }

    private String getExtension(String fileName) {
        logger.info("Get Method getExtension");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
