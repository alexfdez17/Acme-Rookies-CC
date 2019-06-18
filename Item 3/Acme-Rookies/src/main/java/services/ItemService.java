
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ItemRepository;
import security.Authority;
import domain.Item;
import domain.Provider;

@Service
@Transactional
public class ItemService {

	// Managed Repository
	@Autowired
	private ItemRepository	itemRepository;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private ProviderService	providerService;


	// Constructor ----------------------------------------------------

	public ItemService() {
		super();
	}

	// CRUD

	public Item create() {
		final Authority authP = new Authority();
		authP.setAuthority(Authority.PROVIDER);
		Assert.isTrue(this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authP));
		Assert.notNull(this.providerService.findByPrincipal());
		Item result;

		result = new Item();

		result.setProvider(this.providerService.findByPrincipal());

		return result;
	}

	public Item save(final Item item) {
		final Authority authP = new Authority();
		authP.setAuthority(Authority.PROVIDER);
		Assert.isTrue(this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authP));
		Assert.notNull(this.providerService.findByPrincipal());
		Assert.notNull(item);
		Item result;
		result = this.itemRepository.save(item);
		this.itemRepository.flush();
		return result;
	}

	public void delete(final Item item) {
		final Authority authP = new Authority();
		authP.setAuthority(Authority.PROVIDER);
		Assert.isTrue(this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authP));
		Assert.notNull(this.providerService.findByPrincipal());
		Assert.notNull(item);
		Assert.isTrue(item.getId() != 0);
		Assert.isTrue(this.itemRepository.exists(item.getId()));

		this.itemRepository.delete(item);
	}

	public Collection<Item> findAll() {
		Collection<Item> result;

		result = this.itemRepository.findAll();

		return result;
	}

	public Item findOne(final int itemId) {
		Item result;

		result = this.itemRepository.findOne(itemId);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.itemRepository.flush();
	}

	public Collection<Item> findCurrentProviderItems() {
		final Authority authP = new Authority();
		authP.setAuthority(Authority.PROVIDER);
		Assert.isTrue(this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authP));
		Assert.notNull(this.providerService.findByPrincipal());
		Collection<Item> result;

		result = this.itemRepository.findByProviderId(this.providerService.findByPrincipal().getId());

		return result;
	}

	public Double[] getItemsProviderStats() {

		final Double[] res = new Double[4];
		final Collection<Long> stats = this.itemRepository.getItemsProvider();

		if (!stats.isEmpty()) {
			Double sum = 0.0;
			for (final Long n : stats)
				sum += n;
			res[0] = sum / stats.size(); // Average

			res[1] = Collections.min(stats).doubleValue();

			res[2] = Collections.max(stats).doubleValue();

			Double num = 0.0;
			Double numi = 0.0;
			for (final Long n : stats) {
				numi = Math.pow(n - res[0], 2);
				num += numi;
			}
			if (num != 0)
				res[3] = Math.sqrt(num / (stats.size() - 1)); // Standard
																// deviation
			else
				res[3] = 0.;
		} else {
			res[0] = 0.;
			res[1] = 0.;
			res[2] = 0.;
			res[3] = 0.;
		}
		return res;
	}

	public Collection<Provider> getTopProviders() {

		final List<Provider> result = new ArrayList<Provider>(this.itemRepository.getTopProviders());

		if (result.size() > 5)
			return result.subList(0, 5);

		return result;

	}

	public Collection<Item> findProviderItems(final String providerId) {
		Collection<Item> result = new ArrayList<>();
		try {
			result = this.itemRepository.findByProviderId(Integer.parseInt(providerId));
		} catch (final NumberFormatException e) {
			result = this.itemRepository.findAll();
		}

		return result;
	}

	public String export(final Item item) {
		String result = "";
		result = result + "\nName: " + item.getName() + "\nDescription: " + item.getDescription();
		result = result + "\nLinks\n";
		final Collection<String> links = item.getLinks();
		for (final String string : links)
			result = result + "\n" + string;

		final Collection<String> pictures = item.getPictures();
		for (final String string : pictures)
			result = result + "\n" + string;
		return result;
	}

}
