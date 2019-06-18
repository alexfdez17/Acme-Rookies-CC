
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curricula;

@Repository
public interface CurriculaRepository extends JpaRepository<Curricula, Integer> {

	@Query("select count(c.rookie) from Curricula c group by c.rookie")
	Collection<Long> getCurriculaPerRookie();

	@Query("select c from Curricula c where c.rookie.id = ?1")
	Collection<Curricula> getCurriculaByRookieId(int id);

	@Query("select c from Curricula c where c.isCopy = 0")
	Collection<Curricula> getOriginalCurricula();

	@Query("select c from Curricula c where c.personalData.id = ?1")
	Curricula getCurriculaByPersonalDataId(int id);

	@Query("select c from Curricula c join c.positionsData r where r.id= ?1")
	Curricula getCurriculaByPositionDataId(int id);

	@Query("select c from Curricula c join c.educationData r where r.id= ?1")
	Curricula getCurriculaByEducationDataId(int id);

	@Query("select c from Curricula c where c.miscellaneousData.id = ?1")
	Curricula getCurriculaByMiscellaneousDataId(int id);
}
