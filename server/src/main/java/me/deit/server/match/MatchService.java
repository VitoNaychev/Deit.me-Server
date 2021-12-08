package me.deit.server.match;

import me.deit.server.user.Picture;
import me.deit.server.user.PictureRepository;
import me.deit.server.user.User;
import me.deit.server.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("MatchService")
public class MatchService {
    @Autowired
    MatchRepository matchRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PictureRepository pictureRepository;


    public List<Map<String, Object>> buildMatchResponse(User ourUser) {
        Map<Integer,User> matchedUsers = getMatchedUsersIds(ourUser.getId());

        Map<Integer, ResponseUser> usersToBeReturned = getUsersByIds(matchedUsers);

        return transformUsersToResponse(usersToBeReturned);
    }

    private List<Map<String, Object>> transformUsersToResponse(Map<Integer, ResponseUser> usersToBeReturned) {
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

    private Map<Integer, ResponseUser> getUsersByIds(Map<Integer,User> matchedUsers) {
        Map<Integer, ResponseUser> usersToBeReturned = new HashMap<>();
        for (Map.Entry<Integer, User> mUser : matchedUsers.entrySet()) {
            Optional<User> user = userRepository.findById(Long.parseLong(mUser.getKey().toString()));
            ResponseUser userToPut = new ResponseUser(user.get().getId(), user.get().getFirstName(), user.get().getLastName());
            usersToBeReturned.putIfAbsent(mUser.getKey(), userToPut);
        }
        return usersToBeReturned;
    }

    private Map<Integer, User> getMatchedUsersIds(Long userId) {
        List<Match> matches = matchRepository.getMatchByUserId(userId);
        Map<Integer, User> matchedUsers = new HashMap<>();

        if (!matches.isEmpty()) {
            for (Match match : matches) {
                if (match.getUserOne() == userId) {
                    matchedUsers.putIfAbsent(Integer.parseInt(match.getUserTwo().toString()), new User());
                } else if (match.getUserTwo() == userId) {
                    matchedUsers.putIfAbsent(Integer.parseInt(match.getUserOne().toString()), new User());
                }
            }
        }

        return matchedUsers;
    }
}
