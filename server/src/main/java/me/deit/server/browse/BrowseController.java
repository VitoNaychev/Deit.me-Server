package me.deit.server.browse;

import me.deit.server.user.User;
import me.deit.server.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/browse")
public class BrowseController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BrowseService browseService;

    @GetMapping
    public TokenedUser getRandomUser(@RequestParam boolean shouldMatchHobbies) {
        User ourUser = userRepository.findById(4L).get();

        return browseService.getRandomUserBasedOnOurUser(ourUser, shouldMatchHobbies);
    }

    @PostMapping
    public MatchResponse likeSelectedUser(@Valid @RequestBody TokenedLike tokenedLike) {
        User ourUser = userRepository.findById(4L).get();

        try {
            return browseService.likeSelectedUser(ourUser, tokenedLike);
        } catch (NoSuchTokenException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such token");
        }
    }
}
