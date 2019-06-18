
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.PositionProblem;

@Repository
public interface PositionProblemRepository extends JpaRepository<PositionProblem, Integer> {

	@Query("select pp from PositionProblem pp where pp.position.company.id = ?1")
	Collection<PositionProblem> findByCompanyId(int id);

	@Query("select p from PositionProblem p where p.position.id = ?1")
	Collection<PositionProblem> findbyPositionID(int id);

}
