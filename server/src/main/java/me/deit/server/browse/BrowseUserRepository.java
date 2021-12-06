package me.deit.server.browse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrowseUserRepository extends JpaRepository<BrowseUser, Long> {
}
