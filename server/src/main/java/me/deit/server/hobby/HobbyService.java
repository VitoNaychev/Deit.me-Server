package me.deit.server.hobby;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HobbyService {
    @Autowired
    HobbyRepository hobbyRepository;

    public List<Hobby> getAllHobbies() {
        return hobbyRepository.findAll();
    }
}
