package ru.netology.diplom.repository;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import ru.netology.diplom.repository.marker.FileFull;
import ru.netology.diplom.repository.marker.FileList;
import ru.netology.diplom.repository.marker.UpdateName;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "file")
public class File {
    @JsonView(FileFull.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @JsonView(FileList.class)
    @NotNull(groups = UpdateName.class)
    @NotBlank(groups = UpdateName.class)
    @ToString.Include
    @Column(nullable = false)
    private String filename;
    @JsonView(FileList.class)
    @ToString.Include
    @Column(nullable = false)
    private String date;
    @JsonView(FileList.class)
    @ToString.Include
    @Column(nullable = false)
    private Long size;
    @JsonView(FileFull.class)
    @Column(nullable = false, name = "data_id")
    private Long fileDataId;
}
