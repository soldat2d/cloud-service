package ru.netology.diplom.Repository;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import ru.netology.diplom.Repository.Marker.FullFile;
import ru.netology.diplom.Repository.Marker.ListFile;
import ru.netology.diplom.Repository.Marker.UpdateName;

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
    @JsonView(FullFile.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @JsonView(ListFile.class)
    @NotNull(groups = UpdateName.class)
    @NotBlank(groups = UpdateName.class)
    @ToString.Include
    @Column(nullable = false)
    private String filename;
    @JsonView(ListFile.class)
    @ToString.Include
    @Column(nullable = false)
    private String date;
    @JsonView(ListFile.class)
    @ToString.Include
    @Column(nullable = false)
    private Long size;
    @JsonView(FullFile.class)
    @Column(nullable = false, name = "data_id")
    private Long fileDataId;
}
