package ru.netology.diplom.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.diplom.repository.File;
import ru.netology.diplom.repository.marker.FileList;
import ru.netology.diplom.repository.marker.UpdateName;
import ru.netology.diplom.service.FileService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Validated
@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@RequestMapping("/")
public class FileController {

    final private FileService service;

    public FileController(@Autowired FileService service) {
        this.service = service;
    }

    @JsonView(FileList.class)
    @GetMapping("/list")
    public ResponseEntity<List<File>> list(@RequestParam(name = "limit", defaultValue = "3") @NotNull @Min(1) @Max(9) Integer limit) {
        return service.list(limit);
    }

    @PostMapping("/file")
    public ResponseEntity<Boolean> saveFile(@RequestPart @NotNull MultipartFile file) throws IOException {
        return service.saveFile(file);
    }

    @PutMapping("/file")
    public ResponseEntity<HttpStatus> updateFile(@RequestParam(name = "filename") @NotBlank String fileNameOld,
                                                 @RequestBody @Validated(UpdateName.class) File fileNameNew) {
        return service.updateFile(fileNameOld, fileNameNew);
    }

    @GetMapping("/file")
    public ResponseEntity<byte[]> getFile(@RequestParam(name = "filename") @NotBlank String filename) throws FileNotFoundException {
        return service.getFile(filename);
    }

    @DeleteMapping("/file")
    public ResponseEntity<Boolean> deleteFile(@RequestParam(name = "filename") @NotBlank String fileName) throws FileNotFoundException {
        return service.deleteFile(fileName);
    }
}
