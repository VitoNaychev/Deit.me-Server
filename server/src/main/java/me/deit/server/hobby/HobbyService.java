package me.deit.server.hobby;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("HobbyService")
public class HobbyService {
    @Autowired
    HobbyRepository hobbyRepository;

    public List<Hobby> getAllHobbies() {
        return hobbyRepository.findAll();
    }

    public HobbyService() {
    }
}