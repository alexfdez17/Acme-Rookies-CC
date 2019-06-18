
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	@Query("select count(p.company) from Position p group by p.company")
	Collection<Long> getPositionsPerCompany();

	@Query("select avg(p.salaryOffered),min(p.salaryOffered), max(p.salaryOffered), stddev(p.salaryOffered) from Position p ")
	Double[] getSalaryOffered();

	@Query("select p from Position p  order by p.salaryOffered desc")
	Collection<Position> getTopSalary();

	@Query("select p from Position p  order by p.salaryOffered asc")
	Collection<Position> getBotSalary();

	@Query("select p from Position p where p.company.id = ?1")
	Collection<Position> findbyCompanyID(int id);

	@Query("select p from Position p where p.status = 'final'")
	Collection<Position> findbyFinalMode();

	@Query("select p from Position p where p.ticker like :keyword or p.title like :keyword or p.description like :keyword or p.profileRequired like :keyword or p.skillsRequired like :keyword or p.technologiesRequired like :keyword")
	Collection<Position> findByKeyword(@Param("keyword") String keyword);

	@Query("select p from Position p where p.deadLine <= :maximumDeadline")
	Collection<Position> findByMaximumDeadline(@Param("maximumDeadline") @DateTimeFormat(pattern = "dd/MM/yyyy") Date maximumDeadline);

	@Query("select p from Position p where p.salaryOffered >= ?1")
	Collection<Position> findByMinimumSalary(int minimumSalary);

	@Query("select avg(p.salaryOffered) from Audit a join a.position p where a.score= (select max(a.score) from Audit a)")
	Double getAvgSalaryTopScore();

	@Query("select avg(p.sponsorships.size),min(p.sponsorships.size), max(p.sponsorships.size), stddev(p.sponsorships.size) from Position p")
	Double[] getSponsorshipsPositions();
}
