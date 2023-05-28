package assignment.chatlog.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import assignment.chatlog.models.ChatlogEntity;

@Repository
public interface ChatlogRepository
		extends PagingAndSortingRepository<ChatlogEntity, String>, JpaRepository<ChatlogEntity, String> {

	List<ChatlogEntity> findByUser(String user);

	Page<ChatlogEntity> findByUser(String user, Pageable paging);

	Page<ChatlogEntity> findByUserAndTimestampLessThanEqual(String user, Long timestamp, Pageable paging);

	ChatlogEntity findByMessageIdAndUser(String messageId, String user);

}
