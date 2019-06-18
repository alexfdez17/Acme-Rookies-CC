
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select count(a.rookie) from Application a group by a.rookie")
	Collection<Long> getApplicationsPerRookie();

	@Query("select a from Application a where a.rookie.id = ?1")
	Collection<Application> findByRookie(int id);

	@Query("select a from Application a where a.positionProblem.position.company.id = ?1")
	Collection<Application> findByCompanyId(int id);

	@Query("select a from Application a where a.positionProblem.position.id = ?1")
	Collection<Application> findByPositionId(int id);

	@Query("select a from Application a where a.positionProblem.position.id = ?1 and a.status='ACCEPTED'")
	Collection<Application> findAcceptedByPositionId(int id);
}
