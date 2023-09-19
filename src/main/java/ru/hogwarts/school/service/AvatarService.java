package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService
{
    @Value("${path.to.avatars.folder}")
    private String covers;
    private final AvatarRepository avatarRepository;
    private final StudentService studentService;

    public AvatarService (AvatarRepository avatarRepository, StudentService studentService)
    {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    public void uploadAvatar(Long id, MultipartFile file) throws IOException
    {
        Student student = studentService.getStudent(id);

        Path filePath = Path.of(covers,id + "." + getExtention(Objects.requireNonNull(file.getOriginalFilename())));
        Files.createDirectory(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath,CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os,1024);
        )
        {
            bis.transferTo(bos);
        }

        Avatar avatar = findAvatarID(id);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(generateImage(filePath));

        avatarRepository.save(avatar);
    }

    public Avatar findAvatarID(Long id)
    {
        return avatarRepository.findById(id).orElseThrow();
    }

    private String getExtention(String fileName)
    {
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }

    private byte[] generateImage(Path filePath) throws IOException
    {
        try(
                InputStream is = Files.newInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                ByteArrayOutputStream baos = new ByteArrayOutputStream())
        {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height,image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image,0,0,100,height,null);
            graphics.dispose();

            ImageIO.write(preview,getExtention(filePath.getFileName().toString()),baos);
            return baos.toByteArray();
        }
    }
}
