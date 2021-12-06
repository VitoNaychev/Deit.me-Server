package me.deit.server.match;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "match")
public class Match {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "user_one")
    private Long userOne;

    @Column(name = "user_two")
    private Long userTwo;

    public Match() {
    }

    public Match(Long userOne, Long userTwo) {
        this.userOne = userOne;
        this.userTwo = userTwo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserOne() {
        return userOne;
    }

    public void setUserOne(Long userOne) {
        this.userOne = userOne;
    }

    public Long getUserTwo() {
        return userTwo;
    }

    public void setUserTwo(Long userTwo) {
        this.userTwo = userTwo;
    }
}
