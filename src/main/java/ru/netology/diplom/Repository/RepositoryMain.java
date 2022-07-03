package ru.netology.diplom.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import ru.netology.diplom.ExceptionHandler.BadRequestException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class RepositoryMain {
    final private List<String> authorizedUsers;
    final private UserRepository userRepository;
    final private FileRepository fileRepository;
    final private FileDataRepository fileDataRepository;


    public RepositoryMain(@Autowired UserRepository userRepository, @Autowired FileRepository fileRepository, @Autowired FileDataRepository fileDataRepository) {
        this.authorizedUsers = new ArrayList<>();
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
        this.fileDataRepository = fileDataRepository;
    }

    public String login(User user) {
        userRepository.findByLoginAndPassword(user.getLogin(), user.getPassword())
                .orElseThrow(()-> new BadRequestException("Bad credentials"));
        String uuid = UUID.randomUUID().toString();
        authorizedUsers.add(uuid);
        return uuid;
    }

    public boolean logout(String authToken) {
        return authorizedUsers.remove(authToken);
    }

    public boolean isAuthorized(String authToken) {
        return authorizedUsers.contains(authToken);
    }

    @Transactional
    public boolean saveFile(MultipartRequest request) throws IOException {
        MultipartFile multipartFile = request.getFile("file");
        LocalDateTime dateTime = LocalDateTime.now();
        FileData fileData = FileData.builder()
                .data(multipartFile.getBytes())
                .build();
        fileData = fileDataRepository.save(fileData);
        File file = File.builder()
                .filename(multipartFile.getOriginalFilename())
                .size(multipartFile.getSize())
                .date(dateTime.toLocalDate().toString())
                .fileDataId(fileData.getId())
                .build();
        fileRepository.save(file);
        return true;
    }

    public List<File> list(Integer limit) {
        return fileRepository.findAll(PageRequest.of(0, limit)).toList();
    }

    @Transactional
    public boolean deleteFile(String fileName) throws IllegalArgumentException {
        File file = fileRepository.findFirstByFilename(fileName);
        FileData fileData = fileDataRepository.getReferenceById(file.getFileDataId());
        if (file != null) {
            fileRepository.delete(file);
            fileDataRepository.delete(fileData);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updateFile(String fileNameOld, String fileName) {
        File file = fileRepository.findFirstByFilename(fileNameOld);
        if (file != null) {
            fileRepository.updateFileName(file.getId(), fileName);
            return true;
        }
        return false;
    }
}
