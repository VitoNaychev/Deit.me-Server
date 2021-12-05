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
    private ZonedDateTime createdAt;

    public Message() {
    }

    public Message(Long id, Match match, User user, String content, ZonedDateTime createdAt) {
        this.id = id;
        this.match = match;
        this.user = user;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
