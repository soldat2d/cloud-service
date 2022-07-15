package ru.netology.diplom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.diplom.repository.AuthToken;
import ru.netology.diplom.repository.File;
import ru.netology.diplom.repository.MainRepository;
import ru.netology.diplom.repository.User;

import javax.validation.constraints.NotBlank;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class MainService {

    final private MainRepository repository;

    public MainService(@Autowired MainRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<AuthToken> login(User user) {
        return new ResponseEntity<>(new AuthToken(repository.login(user)), HttpStatus.OK);
    }

    public ResponseEntity<Boolean> logout(String authToken) {
        return new ResponseEntity<>(repository.logout(authToken), HttpStatus.OK);
    }

    public ResponseEntity<List<File>> list(Integer limit) {
        return new ResponseEntity<>(repository.list(limit), HttpStatus.OK);
    }

    public ResponseEntity<Boolean> saveFile(MultipartFile file) throws IOException {
        return new ResponseEntity<>(repository.saveFile(file), HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> updateFile(String fileNameOld, File fileNameNew) {
        return repository.updateFile(fileNameOld, fileNameNew.getFilename()) ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<byte[]> getFile(@RequestParam(name = "filename") @NotBlank String filename) throws FileNotFoundException {
        return new ResponseEntity<>(repository.getFile(filename), HttpStatus.OK);
    }

    public ResponseEntity<Boolean> deleteFile(@RequestParam(name = "filename") @NotBlank String fileName) throws FileNotFoundException {
        return new ResponseEntity<>(repository.deleteFile(fileName), HttpStatus.OK);
    }
}
