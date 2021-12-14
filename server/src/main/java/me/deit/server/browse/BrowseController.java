package me.deit.server.browse;

import me.deit.server.user.User;
import me.deit.server.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/browse")
public class BrowseController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BrowseService browseService;

    @GetMapping
    public TokenedUser getRandomUser(Principal principal, @RequestParam boolean shouldMatchHobbies) {
        User ourUser = userRepository.findByEmail(principal.getName()).get();

        try {
            return browseService.getRandomUserBasedOnOurUser(ourUser, shouldMatchHobbies);
        } catch (DailyLimitExceededException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (NoUsersLeftException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public MatchResponse likeSelectedUser(Principal principal, @Valid @RequestBody TokenedLike tokenedLike) {
        User ourUser = userRepository.findByEmail(principal.getName()).get();

        try {
            return browseService.likeSelectedUser(ourUser, tokenedLike);
        } catch (NoSuchTokenException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
