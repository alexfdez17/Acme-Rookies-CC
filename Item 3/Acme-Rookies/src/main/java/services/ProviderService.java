
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProviderRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.CreditCard;
import domain.Item;
import domain.Message;
import domain.Provider;
import domain.SocialProfile;
import domain.Sponsorship;
import forms.RegisterProviderForm;

@Service
@Transactional
public class ProviderService {

	// Managed Repository
	@Autowired
	private ProviderRepository		providerRepository;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private ItemService				itemService;

	@Autowired
	private SponsorshipService		sponsorshipService;

	@Autowired
	private ActorService			actorService;


	// Constructor ----------------------------------------------------

	public ProviderService() {
		super();
	}

	// CRUD

	public Provider registerProvider(final RegisterProviderForm registerProviderForm) {
		final Provider result = this.create();
		final CreditCard creditCard = this.creditCardService.create();

		final UserAccount userAccount = new UserAccount();
		final String password = registerProviderForm.getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hashedPassword = encoder.encodePassword(password, null);
		Assert.isTrue(registerProviderForm.getPassword().equals(registerProviderForm.getPassword2()));
		userAccount.setPassword(hashedPassword);
		userAccount.setUsername(registerProviderForm.getUsername());
		userAccount.setAccountNonLocked(true);

		//Seteo los atributos al resultado
		final Authority authority = new Authority();
		authority.setAuthority(Authority.PROVIDER);
		final List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		creditCard.setCvv(registerProviderForm.getCvv());
		creditCard.setExpirationMonth(registerProviderForm.getExpirationMonth());
		creditCard.setExpirationYear(registerProviderForm.getExpirationYear());
		creditCard.setHolder(registerProviderForm.getHolder());
		creditCard.setMake(registerProviderForm.getMake());
		creditCard.setNumber(registerProviderForm.getNumber());

		result.setUserAccount(userAccount);
		result.setAddress(registerProviderForm.getAddress());
		result.setEmail(registerProviderForm.getEmail());
		result.setName(registerProviderForm.getName());
		result.setPhone(registerProviderForm.getPhone());
		result.setPhoto(registerProviderForm.getPhoto());
		result.setSurname(registerProviderForm.getSurname());
		result.setCreditCard(creditCard);
		result.setVat(registerProviderForm.getVAT());
		result.setSpammer(false);
		result.setProviderMake(registerProviderForm.getProviderMake());
		this.save(result);

		return result;
	}

	public Provider create() {
		Provider result;

		result = new Provider();

		return result;
	}

	public Provider save(final Provider provider) {
		Assert.notNull(provider);
		Provider result;
		result = this.providerRepository.save(provider);
		this.providerRepository.flush();
		return result;
	}

	public void delete(final Provider provider) {
		Assert.notNull(provider);
		Assert.isTrue(provider.getId() != 0);
		Assert.isTrue(this.providerRepository.exists(provider.getId()));

		this.providerRepository.delete(provider);
	}

	public Collection<Provider> findAll() {
		Collection<Provider> result;

		result = this.providerRepository.findAll();

		return result;
	}

	public Provider findOne(final int providerId) {
		Provider result;

		result = this.providerRepository.findOne(providerId);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.providerRepository.flush();
	}

	public Provider findByPrincipal() {
		Provider result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		result = this.findByUserAccount(userAccount);
		return result;
	}

	public Provider findByUserAccount(final UserAccount userAccount) {
		Provider result;
		result = this.providerRepository.findByUserAccountID(userAccount.getId());

		return result;
	}

	public Double[] getSponsorshipProviderStats() {
		return this.providerRepository.getSponsorshipsProviders();
	}

	public Collection<Provider> getSponsorshipProviderMAVG() {
		return this.providerRepository.getSponsorshipsMAVGProviders();
	}

	public String export(final Provider principal) {
		// TODO Auto-generated method stub
		String result = "Rookie\n\nName: " + principal.getName() + "\nSurname: " + principal.getSurname() + "\nVAT: " + principal.getVat() + "\nPhoto: " + principal.getPhoto() + "\nEmail: " + principal.getEmail() + "\nPhone: " + principal.getPhone()
			+ "\nAddress: " + principal.getAddress() + "\nSpammer: " + principal.isSpammer() + "\n\n";
		final Collection<Message> received = this.messageService.findByRecipient(principal);
		for (final Message m1 : received)
			result = result + this.messageService.export(m1);
		final Collection<Message> sent = this.messageService.findBySender(principal);
		for (final Message m2 : sent)
			result = result + this.messageService.export(m2);
		final Collection<SocialProfile> socialProfiles = this.socialProfileService.findByActor(principal);
		for (final SocialProfile sp : socialProfiles)
			result = result + this.socialProfileService.export(sp);
		final Integer providerId = principal.getId();
		final Collection<Item> items = this.itemService.findProviderItems(providerId.toString());
		for (final Item item : items)
			result = result + this.itemService.export(item);
		final Collection<Sponsorship> sponsorships = this.sponsorshipService.getFromProvider(principal);
		for (final Sponsorship sponsorship : sponsorships)
			result = result + this.sponsorshipService.export(sponsorship);
		return result;
	}

	public void clear(final Provider principal) {
		principal.setAddress("lorem ipsum");
		principal.setEmail("loremipsum@loremipsum.com");
		principal.setName("lorem ipsum");
		principal.setPhone("111111111");
		principal.setPhoto("http://photo.com");
		principal.setSpammer(false);
		principal.setSurname("lorem ipsum");
		principal.setVat(0);

		principal.setProviderMake("lorem ipsum");

		this.actorService.save(principal);

		final UserAccount userAccount = principal.getUserAccount();
		this.actorService.ban(userAccount);

	}
}
