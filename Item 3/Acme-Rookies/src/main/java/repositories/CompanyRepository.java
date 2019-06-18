
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
	
	@Query("select c from Position p join p.company c group by p.company order by count (p.company) desc")
	Collection<Company> getCompaniesMostPositions();

	@Query("select c from Company c where c.userAccount.id = ?1")
	Company findbyUserAccountID(int id);
	
	@Query("select avg(c.auditScore),min(c.auditScore), max(c.auditScore), stddev(c.auditScore) from Company c")
	Double[] getScoreData();
	
	@Query("select c from Company c  order by c.auditScore desc")
	Collection<Company> getTopScoreCompanies();
}
