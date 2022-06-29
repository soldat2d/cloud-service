package ru.netology.diplom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String filename;
    @Column(nullable = false)
    private Long size;
    @Column(nullable = false)
    private String date;
    @Column(unique = true, nullable = false)
    private String key;
    @Column(nullable = false)
    private byte[] data;
}
