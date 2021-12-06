package me.deit.server.user;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "picture")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    @NotNull
    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "type")
    private String type;

    @Column(name = "user_id")
    private Integer userId;

    public Picture() {
    }

    public Picture(byte[] picture, String type, Integer userId) {
        this.picture = picture;
        this.type = type;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
