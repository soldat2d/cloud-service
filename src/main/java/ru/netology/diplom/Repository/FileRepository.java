package ru.netology.diplom.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long>{
    Optional<File> findFirstByFilename(String fileName);
    @Modifying
    @Query("update File f set f.filename = ?2 where f.id = ?1")
    int updateFileName(Long id, String fileName);

}
