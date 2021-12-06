package me.deit.server.browse;

import me.deit.server.like.Like;
import me.deit.server.like.LikeRepository;
import me.deit.server.match.Match;
import me.deit.server.match.MatchRepository;
import me.deit.server.hobby.Hobby;
import me.deit.server.user.User;
import me.deit.server.utility.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static me.deit.server.user.Gender.FEMALE;
import static me.deit.server.user.Gender.MALE;

@Component
public class BrowseService {
    private static final int TOKEN_LENGTH = 16;

    @Autowired
    BrowseUserRepository browseUserRepository;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    MatchRepository matchRepository;

    private Map<String, Long> tokenUserIdMap = new HashMap<>();

    private boolean matchesOurPreference(User ourUser, BrowseUser theirUser) {
        switch (ourUser.getPreference()) {
            case MEN:
                return theirUser.getGender() == MALE;
            case WOMEN:
                return theirUser.getGender() == FEMALE;
            case BOTH:
                return true;
            default:
                return false;
        }
    }

    private boolean matchesOurHobbies(User ourUser, BrowseUser theirUser, boolean shouldMatchHobbies) {
        if (!shouldMatchHobbies) {
            return true;
        }

        for (Hobby ourHobby : ourUser.getHobby()) {
            for (Hobby theirHobby : theirUser.getHobby()) {
                if (Objects.equals(ourHobby.getHobby(), theirHobby.getHobby())) {
                    return true;
                }
            }
        }
        return false;
    }


    public TokenedUser getRandomUserBasedOnOurUser(User ourUser, boolean shouldMatchHobbies) {
        String token;
        long randomId;
        BrowseUser theirUser;

        long userCount = browseUserRepository.count();

        do {
            do {
                randomId = (long) Math.floor(Math.random() * userCount + 1);
            } while (randomId == ourUser.getId() ||
                     likeRepository.checkIfWeHaveLikedThem(ourUser.getId(), randomId) ||
                     matchRepository.checkIfWeHaveMatchedWithThem(ourUser.getId(), randomId));

            theirUser = browseUserRepository.findById(randomId).get();
        } while (!matchesOurPreference(ourUser, theirUser) &&
                 !matchesOurHobbies(ourUser, theirUser, shouldMatchHobbies));

        do {
            token = TokenGenerator.generateToken(TOKEN_LENGTH);
        } while (tokenUserIdMap.containsKey(tokenUserIdMap));

        tokenUserIdMap.put(token, randomId);

        TokenedUser tokenedUser = new TokenedUser(token, theirUser);

        return tokenedUser;
    }

    @Transactional
    public MatchResponse likeSelectedUser(User ourUser, TokenedLike tokenedLike) throws NoSuchTokenException {
        if (!tokenUserIdMap.containsKey(tokenedLike.getToken())) {
            throw new NoSuchTokenException();
        }

        if (!tokenedLike.isLiked()) {
            tokenUserIdMap.remove(tokenedLike.getToken());
            return new MatchResponse(false);
        }

        Long ourUserId = ourUser.getId();
        Long theirUserId = tokenUserIdMap.remove(tokenedLike.getToken());
        likeRepository.save(new Like(ourUser.getId(), theirUserId));
        likeRepository.flush();

        if (likeRepository.checkIfTheyHaveLikedUs(theirUserId, ourUserId) &&
                likeRepository.checkIfWeHaveLikedThem(ourUserId, theirUserId)) {
            likeRepository.deleteLike(ourUserId, theirUserId);
            likeRepository.deleteLike(theirUserId, ourUserId);

            matchRepository.save(new Match(theirUserId, ourUserId));
            return new MatchResponse(true);
        }
        return new MatchResponse(false);

    }
}
