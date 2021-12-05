package me.deit.server.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PictureRepository extends JpaRepository<Picture, Integer> {

    @Query("SELECT p FROM Picture p WHERE p.userId = :userId")
    List<Picture> findPicturesByUserId(Integer userId);

    @Transactional
    @Modifying
    @Query("UPDATE Picture SET picture = :picture WHERE userId = :userId")
    void serPicture (Integer userId, byte[] picture);
}
