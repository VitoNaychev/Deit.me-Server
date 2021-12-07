package me.deit.server.match;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match, Long> {
    @Query(
            value = "SELECT EXISTS(SELECT * from match " +
                    "WHERE (user_one = ?1 AND user_two = ?2) OR (user_one = ?2 AND user_two = ?1))",
            nativeQuery = true)
    boolean checkIfWeHaveMatchedWithThem(Long ourUserId, Long theirUserId);

    @Query("SELECT m FROM Match m WHERE (m.userOne = :id) OR (m.userTwo = :id)")
    List<Match> getMatchByUserId(Long id);
}
