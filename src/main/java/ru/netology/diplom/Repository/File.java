package ru.netology.diplom.Repository;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @ToString.Include
    @Column(nullable = false)
    private String filename;
    @ToString.Include
    @Column(nullable = false)
    private String date;
    @ToString.Include
    @Column(nullable = false)
    private Long size;
    @Column(nullable = false, name = "data_id")
    private Long fileDataId;
}
