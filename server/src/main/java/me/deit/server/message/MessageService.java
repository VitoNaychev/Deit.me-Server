package me.deit.server.message;

import me.deit.server.match.Match;
import me.deit.server.match.MatchRepository;
import me.deit.server.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageService {

    @Autowired
    MessageTypeGetRepository messageTypeGetRepository;

    @Autowired
    MessageTypePostRepository messageTypePostRepository;

    @Autowired
    MatchRepository matchRepository;

    public List<MessageTypeGet> getMessagesByMatchId(User ourUser, Long matchId) throws UnauthorisedMessageException {
        boolean matchFound = false;
        List<Match> ourMatches = matchRepository.getMatchByUserId(ourUser.getId());

        for (Match match : ourMatches) {
            if (match.getId() == matchId) {
                matchFound = true;
                break;
            }
        }

        if (!matchFound) {
            throw new UnauthorisedMessageException("User is not part of this conversation.");
        }

        return messageTypeGetRepository.findMessageTypeGetByMatchIdOrderByCreatedAtAsc(matchId);
    }

    public boolean saveMessage(User ourUser, MessageTypePost message) throws UnauthorisedMessageException {
        if (ourUser.getId() == message.getId()) {
            throw new UnauthorisedMessageException("JWT does not correspond to the user specified in the message.");
        }

        boolean matchFound = false;
        List<Match> ourMatches = matchRepository.getMatchByUserId(ourUser.getId());

        for (Match match : ourMatches) {
            if (match.getId() == message.getMatchId()) {
                matchFound = true;
                break;
            }
        }

        if (!matchFound) {
            throw new UnauthorisedMessageException("User is not part of this conversation.");
        }

        messageTypePostRepository.save(message);

        return true;
    }
}
