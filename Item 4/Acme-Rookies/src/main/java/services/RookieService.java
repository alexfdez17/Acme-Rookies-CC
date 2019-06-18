
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RookieRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Application;
import domain.CreditCard;
import domain.Curricula;
import domain.Finder;
import domain.Message;
import domain.Rookie;
import domain.SocialProfile;
import forms.RegisterRookieForm;

@Service
@Transactional
public class RookieService {

	//Managed Repository
	@Autowired
	private RookieRepository		rookieRepository;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private CurriculaService		curriculaService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private ActorService			actorService;


	// Constructor ----------------------------------------------------

	public RookieService() {
		super();
	}

	//CRUD

	public Rookie registerRookie(final RegisterRookieForm registerRookieForm) {
		final Rookie result = this.create();
		final CreditCard creditCard = this.creditCardService.create();

		final UserAccount userAccount = new UserAccount();
		final String password = registerRookieForm.getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hashedPassword = encoder.encodePassword(password, null);
		Assert.isTrue(registerRookieForm.getPassword().equals(registerRookieForm.getPassword2()));
		userAccount.setPassword(hashedPassword);
		userAccount.setUsername(registerRookieForm.getUsername());
		userAccount.setAccountNonLocked(true);

		//Seteo los atributos al resultado
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ROOKIE);
		final List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		creditCard.setCvv(registerRookieForm.getCvv());
		creditCard.setExpirationMonth(registerRookieForm.getExpirationMonth());
		creditCard.setExpirationYear(registerRookieForm.getExpirationYear());
		creditCard.setHolder(registerRookieForm.getHolder());
		creditCard.setMake(registerRookieForm.getMake());
		creditCard.setNumber(registerRookieForm.getNumber());

		result.setUserAccount(userAccount);
		result.setAddress(registerRookieForm.getAddress());
		result.setEmail(registerRookieForm.getEmail());
		result.setName(registerRookieForm.getName());
		result.setPhone(registerRookieForm.getPhone());
		result.setPhoto(registerRookieForm.getPhoto());
		result.setSurname(registerRookieForm.getSurname());
		result.setCreditCard(creditCard);
		result.setVat(registerRookieForm.getVAT());
		result.setSpammer(false);
		this.save(result);

		return result;
	}

	public Rookie create() {
		Rookie result;

		result = new Rookie();

		return result;

	}

	public Rookie save(final Rookie rookie) {
		Assert.notNull(rookie);
		Rookie result;
		result = this.rookieRepository.save(rookie);
		this.rookieRepository.flush();
		return result;
	}

	public void delete(final Rookie rookie) {
		Assert.notNull(rookie);
		Assert.isTrue(rookie.getId() != 0);
		Assert.isTrue(this.rookieRepository.exists(rookie.getId()));

		this.rookieRepository.delete(rookie);
	}

	public Collection<Rookie> findAll() {
		Collection<Rookie> result;

		result = this.rookieRepository.findAll();

		return result;
	}

	public Rookie findOne(final int rookieId) {
		Rookie result;

		result = this.rookieRepository.findOne(rookieId);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.rookieRepository.flush();
	}

	public Collection<Rookie> getRookiesMoreApplications() {

		final Administrator logged = this.administratorService.findByPrincipal();

		Assert.isTrue(logged instanceof Administrator);

		final List<Rookie> result = new ArrayList<Rookie>(this.rookieRepository.getRookiesMostApplications());

		if (result.size() > 3)
			return result.subList(0, 3);
		return result;
	}

	//-----------------------------------------------
	public Rookie findByPrincipal() {
		Rookie result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		result = this.findByUserAccount(userAccount);
		return result;
	}

	public Rookie findByUserAccount(final UserAccount userAccount) {
		Rookie result;
		result = this.rookieRepository.findByUserAccountID(userAccount.getId());

		return result;
	}

	// Clear

	public void clear(final Rookie rookie) {

		rookie.setAddress("lorem ipsum");
		rookie.setEmail("loremipsum@loremipsum.com");
		rookie.setName("lorem ipsum");
		rookie.setPhone("111111111");
		rookie.setPhoto("http://photo.com");
		rookie.setSpammer(false);
		rookie.setSurname("lorem ipsum");
		rookie.setVat(0);

		this.actorService.save(rookie);

		final UserAccount userAccount = rookie.getUserAccount();
		this.actorService.ban(userAccount);

	}

	// Export

	public String export(final Rookie rookie) {

		String result = "Rookie\n\nName: " + rookie.getName() + "\nSurname: " + rookie.getSurname() + "\nVAT: " + rookie.getVat() + "\nPhoto: " + rookie.getPhoto() + "\nEmail: " + rookie.getEmail() + "\nPhone: " + rookie.getPhone() + "\nAddress: "
			+ rookie.getAddress() + "\nSpammer: " + rookie.isSpammer() + "\n\n";

		final Collection<Message> received = this.messageService.findByRecipient(rookie);
		for (final Message m1 : received)
			result = result + this.messageService.export(m1);
		final Collection<Message> sent = this.messageService.findBySender(rookie);
		for (final Message m2 : sent)
			result = result + this.messageService.export(m2);

		final Collection<SocialProfile> socialProfiles = this.socialProfileService.findByActor(rookie);
		for (final SocialProfile sp : socialProfiles)
			result = result + this.socialProfileService.export(sp);

		final Collection<Application> applications = this.applicationService.findByRookie(rookie);
		for (final Application a : applications)
			result = result + this.applicationService.export(a);

		final Finder f = this.finderService.findByRookie(rookie);
		if (f != null)
			result = result + this.finderService.export(f);

		final Collection<Curricula> curriculas = this.curriculaService.getCurriculaByRookie(rookie);
		for (final Curricula c : curriculas)
			this.curriculaService.clear(c);

		return result;
	}
}
