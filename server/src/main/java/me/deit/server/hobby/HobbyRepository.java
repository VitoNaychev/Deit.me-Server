package me.deit.server.hobby;

import me.deit.server.hobby.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HobbyRepository extends JpaRepository<Hobby, Long> {

}
