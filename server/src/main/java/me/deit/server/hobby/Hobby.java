package me.deit.server.hobby;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Entity
@Table(name = "hobby")
public class Hobby {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "hobby")
    private String hobby;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
}
