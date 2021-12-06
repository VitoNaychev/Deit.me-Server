package me.deit.server.match;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MatchRepository extends JpaRepository<Match, Long> {
    @Query(
            value = "SELECT EXISTS(SELECT * from match " +
                    "WHERE (user_one = ?1 AND user_two = ?2) OR (user_one = ?2 AND user_two = ?1))",
            nativeQuery = true)
    boolean checkIfWeHaveMatchedWithThem(Long ourUserId, Long theirUserId);
}
