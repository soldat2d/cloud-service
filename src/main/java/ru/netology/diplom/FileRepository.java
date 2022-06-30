package ru.netology.diplom;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long>{
    File findByFilename(String fileName);
}
