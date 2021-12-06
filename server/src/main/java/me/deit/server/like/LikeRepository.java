package me.deit.server.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query(
            value = "SELECT EXISTS(SELECT * from \"like\" WHERE liking_user = ?1 AND liked_user = ?2)",
            nativeQuery = true)
    boolean checkIfWeHaveLikedThem(Long ourUserId, Long theirUserId);

    @Query(
            value = "SELECT EXISTS(SELECT * from \"like\" WHERE liking_user = ?1 AND liked_user = ?2)",
            nativeQuery = true)
    boolean checkIfTheyHaveLikedUs(Long theirUserId, Long ourUserId);

    @Modifying
    @Query(
            value = "DELETE FROM \"like\" WHERE (liked_user = ?1 AND liking_user = ?2)",
            nativeQuery = true)
    void deleteLike(Long ourId, Long theirId);
}
