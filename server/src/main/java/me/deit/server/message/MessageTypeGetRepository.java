package me.deit.server.message;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageTypeGetRepository extends JpaRepository<MessageTypeGet, Long> {
    List<MessageTypeGet> findMessageTypeGetByMatchIdOrderByCreatedAtAsc(Long matchId);
}
