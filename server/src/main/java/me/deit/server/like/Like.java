package me.deit.server.like;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "\"like\"")
public class Like {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "liking_user")
    private Long likingUser;

    @Column(name = "liked_user")
    private Long likedUser;

    public Like() {
    }

    public Like(Long likingUser, Long likedUser) {
        this.likingUser = likingUser;
        this.likedUser = likedUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLikingUser() {
        return likingUser;
    }

    public void setLikingUser(Long likingUser) {
        this.likingUser = likingUser;
    }

    public Long getLikedUser() {
        return likedUser;
    }

    public void setLikedUser(Long likedUser) {
        this.likedUser = likedUser;
    }
}
