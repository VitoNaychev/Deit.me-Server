package me.deit.server.match;

import me.deit.server.user.User;

import javax.persistence.*;

@Entity
@Table(name="match")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_one")
    private User user_one;

    @OneToOne
    @JoinColumn(name="user_two")
    private User user_two;

    public Match() {
    }

    public Match(Long id, User user_one, User user_two) {
        this.id = id;
        this.user_one = user_one;
        this.user_two = user_two;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser_one() {
        return user_one;
    }

    public void setUser_one(User user_one) {
        this.user_one = user_one;
    }

    public User getUser_two() {
        return user_two;
    }

    public void setUser_two(User user_two) {
        this.user_two = user_two;
    }
}
