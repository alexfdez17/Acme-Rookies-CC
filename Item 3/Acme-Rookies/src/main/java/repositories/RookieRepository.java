
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Rookie;

@Repository
public interface RookieRepository extends JpaRepository<Rookie, Integer> {
	
	@Query("select h from Application a join a.rookie h group by a.rookie order by count (a.rookie) desc")
	Collection<Rookie> getRookiesMostApplications();

	@Query("select h from Rookie h where h.userAccount.id = ?1")
	Rookie findByUserAccountID(int id);

}
