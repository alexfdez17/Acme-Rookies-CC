
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("select m from Message m where m.id = ?1")
	Message findbyMessageID(int id);

	@Query("select m from Message m where m.sender.id = ?1")
	Collection<Message> findbySender(int id);

	@Query("select m from Message m where m.recipient.id = ?1")
	Collection<Message> findbyRecipient(int id);

	@Query("select m from Message m where m.sender.id = ?1 and m.spam=0")
	Collection<Message> findNonSpamBySender(int id);

	@Query("select m from Message m where m.sender.id = ?1 and m.spam=1")
	Collection<Message> findSpamBySender(int id);

	@Query("select m from Message m where m.recipient.id = ?1 and m.tags like ?2")
	Collection<Message> findbyRecipientAndTag(int id, String tag);

}
