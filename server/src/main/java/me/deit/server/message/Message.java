package me.deit.server.message;

import me.deit.server.match.Match;
import me.deit.server.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;

@Entity
@Table(name="message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="match_id")
    private Match match;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    @NotBlank
    private String content;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private ZonedDateTime created_at;
}
