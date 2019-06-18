
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Item;
import domain.Provider;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	@Query("select count(i.provider) from Item i group by i.provider")
	Collection<Long> getItemsProvider();
	
	@Query("select i.provider from Item i group by i.provider order by count(i.provider) desc")
	Collection<Provider> getTopProviders();

	@Query("select i from Item i where i.provider.id = ?1")
	Collection<Item> findByProviderId(int id);

}
