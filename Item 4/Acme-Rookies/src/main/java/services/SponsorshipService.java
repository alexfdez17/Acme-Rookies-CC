
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PositionRepository;
import repositories.ProviderRepository;
import repositories.SponsorshipRepository;
import domain.CreditCard;
import domain.Position;
import domain.Provider;
import domain.Sponsorship;
import forms.SponsorshipForm;

@Service
@Transactional
public class SponsorshipService {

	// Managed Repository
	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	@Autowired
	private ProviderService			providerService;
	@Autowired
	private CreditCardService		creditCardService;
	@Autowired
	private PositionService			positionService;
	@Autowired
	private SystemConfigService		systemConfigService;
	@Autowired
	private PositionRepository		positionRepository;
	@Autowired
	private ProviderRepository		providerRepository;


	// Constructor ----------------------------------------------------

	public SponsorshipService() {
		super();
	}

	// CRUD

	public Sponsorship create(final Position position) {
		Sponsorship result;

		result = new Sponsorship();
		result.setPosition(position);
		final CreditCard creditCard = new CreditCard();
		result.setcreditcard(creditCard);
		return result;
	}

	public Sponsorship save(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Sponsorship result;

		result = this.sponsorshipRepository.save(sponsorship);
		if (sponsorship.getId() == 0) {
			Provider provider = result.getProvider();
			result = this.sponsorshipRepository.save(result);
			Position position = result.getPosition();
			position.getSponsorships().add(result);
			position = this.positionRepository.save(position);
			provider.getSponsorships().add(result);
			provider = this.providerRepository.save(provider);
		}
		return result;
	}

	public Sponsorship update(final Sponsorship sponsorship) {
		if (sponsorship.getId() != 0)
			Assert.isTrue(sponsorship.getProvider().equals(this.providerService.findByPrincipal()));
		final Sponsorship result = this.save(sponsorship);
		return result;
	}

	public Sponsorship charge(final Sponsorship sponsorship) {
		final Sponsorship result;
		double chargedAmount = sponsorship.getChargedAmount();
		final double charge = this.systemConfigService.findSystemConfiguration().getCharge();
		chargedAmount = chargedAmount + charge;
		sponsorship.setChargedAmount(chargedAmount);
		result = this.save(sponsorship);
		return result;
	}

	public void delete(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Assert.isTrue(sponsorship.getId() != 0);
		Assert.isTrue(this.sponsorshipRepository.exists(sponsorship.getId()));
		Assert.isTrue(sponsorship.getProvider().equals(this.providerService.findByPrincipal()));
		final Position position = sponsorship.getPosition();
		position.getSponsorships().remove(sponsorship);
		this.positionRepository.save(position);
		final Provider provider = sponsorship.getProvider();
		provider.getSponsorships().remove(sponsorship);
		this.providerRepository.save(provider);
		this.sponsorshipRepository.delete(sponsorship);
	}

	public Collection<Sponsorship> findAll() {
		Collection<Sponsorship> result;

		result = this.sponsorshipRepository.findAll();

		return result;
	}

	public Collection<Sponsorship> getFromPosition(final Position position) {
		return this.sponsorshipRepository.getFromPosition(position.getId());
	}

	public Collection<Sponsorship> getFromProvider(final Provider provider) {
		return this.sponsorshipRepository.getFromProvider(provider.getId());
	}

	public Sponsorship findOne(final int sponsorshipId) {
		Sponsorship result;

		result = this.sponsorshipRepository.findOne(sponsorshipId);
		Assert.notNull(result);

		return result;
	}

	public Sponsorship charge(final Position position) {
		final List<Sponsorship> sponsorships = new ArrayList<Sponsorship>(position.getSponsorships());
		Sponsorship result = null;
		if (sponsorships.size() > 0) {
			final Random random = new Random();
			final int rnd = random.nextInt(sponsorships.size());
			final Sponsorship sponsorship = sponsorships.get(rnd);
			double chargedAmount = sponsorship.getChargedAmount();
			chargedAmount = chargedAmount + this.systemConfigService.findSystemConfiguration().getCharge();
			sponsorship.setChargedAmount(chargedAmount);
			result = this.save(sponsorship);
		}

		return result;
	}

	public void flush() {
		this.sponsorshipRepository.flush();
	}

	public Sponsorship reconstruct(final SponsorshipForm sponsorshipForm) {
		Sponsorship result;
		CreditCard creditCard;
		final Position position;
		if (sponsorshipForm.getId() == 0) {
			position = this.positionService.findOne(sponsorshipForm.getPositionId());
			result = this.create(position);
			final Provider provider = this.providerService.findByPrincipal();
			result.setProvider(provider);
			creditCard = result.getcreditcard();
		} else {
			result = this.findOne(sponsorshipForm.getId());
			position = result.getPosition();
			creditCard = result.getcreditcard();
		}

		creditCard.setCvv(sponsorshipForm.getCvv());
		creditCard.setExpirationMonth(sponsorshipForm.getExpirationMonth());
		creditCard.setExpirationYear(sponsorshipForm.getExpirationYear());
		creditCard.setHolder(sponsorshipForm.getHolder());
		creditCard.setMake(sponsorshipForm.getMake());
		creditCard.setNumber(sponsorshipForm.getNumber());

		creditCard = this.creditCardService.save(creditCard);
		result.setVersion(sponsorshipForm.getVersion());
		result.setcreditcard(creditCard);
		result.setBanner(sponsorshipForm.getBanner());
		result.setTargetPage(sponsorshipForm.getTargetPage());

		return result;

	}

	public SponsorshipForm toSponsorshipForm(final Sponsorship sponsorship) {

		final SponsorshipForm result = new SponsorshipForm();

		result.setBanner(sponsorship.getBanner());
		result.setCvv(sponsorship.getcreditcard().getCvv());
		result.setExpirationMonth(sponsorship.getcreditcard().getExpirationMonth());
		result.setExpirationYear(sponsorship.getcreditcard().getExpirationYear());
		result.setHolder(sponsorship.getcreditcard().getHolder());
		result.setId(sponsorship.getId());
		result.setMake(sponsorship.getcreditcard().getMake());
		result.setNumber(sponsorship.getcreditcard().getNumber());
		result.setPositionId(sponsorship.getPosition().getId());
		result.setTargetPage(sponsorship.getTargetPage());
		result.setVersion(sponsorship.getVersion());

		return result;

	}

	public String export(final Sponsorship sponsorship) {
		String result = "";
		result = result + "\nBanner: " + sponsorship.getBanner() + "\nTarget page: " + sponsorship.getTargetPage() + "\nCharged amount: " + sponsorship.getChargedAmount();
		return result;
	}

}
