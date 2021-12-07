package me.deit.server.match;

import me.deit.server.user.Picture;
import me.deit.server.user.PictureRepository;
import me.deit.server.user.User;
import me.deit.server.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.*;

//TODO: Put Business logic in MatchService

@RestController
@RequestMapping("/api/match")
public class MatchController {
    @Autowired
    MatchRepository matchRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PictureRepository pictureRepository;

    @GetMapping
    public List<Map<String,Object>> getMatchedUsers(Principal principal) {
        User ourUser = userRepository.findByEmail(principal.getName()).get();

        List<Match> matches = matchRepository.getMatchByUserId(ourUser.getId());
        Map<Integer, User> matchedUsers = new HashMap<>();

        if (!matches.isEmpty()) {
            for (Match match : matches) {
                if (match.getUserOne() == ourUser.getId()) {
                    matchedUsers.putIfAbsent(Integer.parseInt(match.getUserTwo().toString()), new User());
                } else if (match.getUserTwo() == ourUser.getId()) {
                    matchedUsers.putIfAbsent(Integer.parseInt(match.getUserOne().toString()), new User());
                }
            }
        }

        Map<Integer, ResponseUser> usersToBeReturned = new HashMap<>();
        for (Map.Entry<Integer, User> mUser : matchedUsers.entrySet()) {
            Optional<User> user = userRepository.findById(Long.parseLong(mUser.getKey().toString()));
            ResponseUser userToPut = new ResponseUser(user.get().getId(), user.get().getFirstName(), user.get().getLastName());
            usersToBeReturned.putIfAbsent(mUser.getKey(), userToPut);
        }

        List<Map<String,Object>> maps = new ArrayList<Map<String,Object>>();

        for (Map.Entry<Integer, ResponseUser> rUser : usersToBeReturned.entrySet())  {
            Map<String, Object> response = new HashMap<>();

            response.put("matchId", rUser.getKey());
            response.put("user", rUser.getValue());

            Picture picture = new Picture();
            List<Picture> pictures = pictureRepository.findPicturesByUserId(Integer.parseInt(rUser.getValue().getId().toString()));
            if (!pictures.isEmpty()) {
                picture = pictures.get(0);
            }

            response.put("picture", picture.getPicture());

            maps.add(response);
        }

        return maps;
    }
}
