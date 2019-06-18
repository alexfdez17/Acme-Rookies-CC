
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select avg(f.positions.size), max(f.positions.size), min(f.positions.size),stddev(f.positions.size) from Finder f")
	Double[] getFinderResults();

	@Query("select count(f)*1.00 from Finder f where f.positions is empty")
	Double countEmptyFinders();

	@Query("select count(f)*1.00 from Finder f where f.positions is not empty")
	Double countNotEmptyFinders();

	@Query("select f from Finder f where f.rookie.id = ?1")
	Finder findByRookieId(int rookieId);

}
