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
    private User userOne;

    @OneToOne
    @JoinColumn(name="user_two")
    private User userTwo;

    public Match() {
    }

    public Match(Long id, User userOne, User userTwo) {
        this.id = id;
        this.userOne = userOne;
        this.userTwo = userTwo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserOne() {
        return userOne;
    }

    public void setUserOne(User userOne) {
        this.userOne = userOne;
    }

    public User getUserTwo() {
        return userTwo;
    }

    public void setUserTwo(User userTwo) {
        this.userTwo = userTwo;
    }
}
