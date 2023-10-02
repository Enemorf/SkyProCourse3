package ru.hogwarts.school.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Avatar
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "avatar_seq")
    @SequenceGenerator(name = "avatar_seq", sequenceName = "avatar_seq", allocationSize = 1)
    private Long id;

    private String filePath;
    private long fileSize;
    private String mediaType;

    @Lob
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
