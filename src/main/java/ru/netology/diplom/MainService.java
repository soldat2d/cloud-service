package ru.netology.diplom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.time.LocalDateTime;

@Service
public class MainService {
    final private MainRepository repository;

    public MainService(@Autowired MainRepository repository) {
        this.repository = repository;
    }

    public String login(User user) {
        return repository.login(user);
    }

    public boolean logout(String authToken) {
        return repository.logout(authToken);
    }

    public boolean file (MultipartRequest request) {
        MultipartFile multipartFile = request.getFile("file");
        LocalDateTime dateTime = LocalDateTime.now();
        String key = DigestUtils.md5DigestAsHex((multipartFile.getOriginalFilename() + dateTime.toString()).getBytes());
        File file = File.builder()
                .name(multipartFile.getOriginalFilename())
                .size(multipartFile.getSize())
                .date(dateTime.toLocalDate().toString())
                .key(key)
                .build();

        return false;
    }
}
