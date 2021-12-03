package me.deit.server.hobby;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/hobby")
public class HobbyController {

    @Autowired
    HobbyRepository hobbyRepository;

    @GetMapping
    public List<Hobby> getAllHobbies() {
        return getAllHobbies();
    }
}
