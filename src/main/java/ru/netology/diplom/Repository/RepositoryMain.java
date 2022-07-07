package ru.netology.diplom.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.diplom.ExceptionHandler.BadRequestException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
                .orElseThrow(() -> new BadRequestException("Bad credentials"));
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

    @SuppressWarnings("SameReturnValue")
    @Transactional
    public boolean saveFile(MultipartFile multipartFile) throws IOException {
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

    @SuppressWarnings("SameReturnValue")
    @Transactional
    public boolean deleteFile(String fileName) throws FileNotFoundException {
        File file = fileRepository.findFirstByFilename(fileName).orElseThrow(() -> new FileNotFoundException("Error input data"));
        FileData fileData = fileDataRepository.getReferenceById(file.getFileDataId());
        fileRepository.delete(file);
        fileDataRepository.delete(fileData);
        return true;
    }

    @Transactional
    public boolean updateFile(String fileNameOld, String fileNameNew) {
        return fileRepository.updateFileName(fileRepository.findFirstByFilename(fileNameOld).get().getId(), fileNameNew) > 0;
    }

    public byte[] getFile(String fileName) throws FileNotFoundException {
        return fileDataRepository.findById(fileRepository.findFirstByFilename(fileName)
                        .orElseThrow(() -> new FileNotFoundException("Error input data")).getFileDataId())
                .orElseThrow(() -> new FileNotFoundException("Error input data")).getData();
    }
}
