
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {

	@Query("select a from Audit a where a.auditor.id=?1")
	Collection<Audit> findByAuditorId(int id);

	@Query("select a from Audit a where a.position.id=?1")
	Collection<Audit> findByPositionId(int id);

	@Query("select avg(a.score),min(a.score), max(a.score), stddev(a.score) from Audit a")
	Double[] getScore();

	@Query("select a from Audit a where a.position.company.id=?1")
	Collection<Audit> findByCompanyId(int id);
}
