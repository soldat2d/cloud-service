package ru.netology.diplom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FileRepository extends JpaRepository<File, Long>{
    @Query(value = "select * from files f", nativeQuery = true)
    Page<File> findSome (Pageable pageable);
}
