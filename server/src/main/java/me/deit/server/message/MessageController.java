package me.deit.server.message;

import me.deit.server.user.User;
import me.deit.server.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageService messageService;

    @GetMapping
    public List<MessageTypeGet> getMessagesByMatchId(Principal principal, @RequestParam Long matchId) {
        User ourUser = userRepository.findByEmail(principal.getName()).get();

        try {
            return messageService.getMessagesByMatchId(ourUser, matchId);
        } catch (UnauthorisedMessageException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void saveMessage(Principal principal, @RequestBody MessageTypePost message) {
        User ourUser = userRepository.findByEmail(principal.getName()).get();

        try {
            messageService.saveMessage(ourUser, message);
        } catch (UnauthorisedMessageException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }
}
