
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Integer> {
	
	@Query("select avg(p.sponsorships.size),min(p.sponsorships.size), max(p.sponsorships.size), stddev(p.sponsorships.size) from Provider p")
	Double[] getSponsorshipsProviders();
	
	@Query("select p from Provider p where p.sponsorships.size>( select 1.0*(avg(po.sponsorships.size)) from Provider po)")
	Collection <Provider> getSponsorshipsMAVGProviders();

	@Query("select p from Provider p where p.userAccount.id = ?1")
	Provider findByUserAccountID(int id);

}
