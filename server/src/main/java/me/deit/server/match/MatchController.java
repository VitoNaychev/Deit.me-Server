package me.deit.server.match;

import me.deit.server.user.User;
import me.deit.server.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api/match")
public class MatchController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    MatchService matchService;

    @GetMapping
    public List<Map<String,Object>> getMatchedUsers(Principal principal) {
        User ourUser = userRepository.findByEmail(principal.getName()).get();

        return matchService.buildMatchResponse(ourUser);
    }
}
