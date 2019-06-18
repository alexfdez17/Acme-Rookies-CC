
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Company;
import domain.CreditCard;
import domain.Message;
import domain.Notification;
import domain.SocialProfile;
import forms.RegisterAdminForm;

@Service
@Transactional
public class AdministratorService {

	// Managed Repository
	@Autowired
	private AdministratorRepository	administratorRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private NotificationService		notificationService;
	@Autowired
	private CompanyService			companyService;


	// CRUD

	public Administrator registerAdministrator(final RegisterAdminForm registerAdminForm) {
		final Administrator result = this.create();
		final CreditCard creditCard = this.creditCardService.create();

		final UserAccount userAccount = new UserAccount();
		final String password = registerAdminForm.getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hashedPassword = encoder.encodePassword(password, null);
		Assert.isTrue(registerAdminForm.getPassword().equals(registerAdminForm.getPassword2()));
		userAccount.setPassword(hashedPassword);
		userAccount.setUsername(registerAdminForm.getUsername());
		userAccount.setAccountNonLocked(true);

		//Seteo los atributos al resultado
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		creditCard.setCvv(registerAdminForm.getCvv());
		creditCard.setExpirationMonth(registerAdminForm.getExpirationMonth());
		creditCard.setExpirationYear(registerAdminForm.getExpirationYear());
		creditCard.setHolder(registerAdminForm.getHolder());
		creditCard.setMake(registerAdminForm.getMake());
		creditCard.setNumber(registerAdminForm.getNumber());

		result.setUserAccount(userAccount);
		result.setAddress(registerAdminForm.getAddress());
		result.setEmail(registerAdminForm.getEmail());
		result.setName(registerAdminForm.getName());
		result.setPhone(registerAdminForm.getPhone());
		result.setPhoto(registerAdminForm.getPhoto());
		result.setSurname(registerAdminForm.getSurname());
		result.setCreditCard(creditCard);
		result.setVat(registerAdminForm.getVAT());
		result.setSpammer(false);
		this.save(result);

		return result;
	}

	public Administrator create() {
		Administrator result;

		result = new Administrator();

		return result;

	}

	public Administrator save(final Administrator administrator) {
		Assert.notNull(administrator);
		Administrator result;
		result = this.administratorRepository.save(administrator);
		this.administratorRepository.flush();
		return result;
	}

	public Administrator findOne(final int administratorId) {
		return this.administratorRepository.findOne(administratorId);
	}

	public UserAccount findByActor(final int actorId) {
		UserAccount result;
		result = this.actorService.findOne(actorId).getUserAccount();

		return result;

	}

	public Collection<Administrator> findAll() {
		return this.administratorRepository.findAll();
	}

	public Administrator findByPrincipal() {
		// TODO Auto-generated method stub
		return this.administratorRepository.findbyUserAccountID(LoginService.getPrincipal().getId());
	}

	// Clear

	public void clear(final Administrator administrator) {

		final Collection<Message> received = this.messageService.findByRecipient(administrator);
		for (final Message m1 : received)
			this.messageService.clear(m1);
		final Collection<Message> sent = this.messageService.findBySender(administrator);
		for (final Message m2 : sent)
			this.messageService.clear(m2);

		final Collection<SocialProfile> socialProfiles = this.socialProfileService.findByActor(administrator);
		for (final SocialProfile sp : socialProfiles)
			this.socialProfileService.clear(sp);

		final Collection<Notification> notifications = this.notificationService.findByAdministrator(administrator);
		for (final Notification n : notifications)
			this.notificationService.clear(n);

		this.administratorRepository.delete(administrator);
	}

	public String export(final Administrator administrator) {

		String result = "Administrator\n\nName: " + administrator.getName() + "\nSurname: " + administrator.getSurname() + "\nVAT: " + administrator.getVat() + "\nPhoto: " + administrator.getPhoto() + "\nEmail: " + administrator.getEmail() + "\nPhone: "
			+ administrator.getPhone() + "\nAddress: " + administrator.getAddress() + "\nSpammer: " + administrator.isSpammer() + "\n\n";

		final Collection<Message> received = this.messageService.findByRecipient(administrator);
		for (final Message m1 : received)
			result = result + this.messageService.export(m1);
		final Collection<Message> sent = this.messageService.findBySender(administrator);
		for (final Message m2 : sent)
			result = result + this.messageService.export(m2);

		final Collection<SocialProfile> socialProfiles = this.socialProfileService.findByActor(administrator);
		for (final SocialProfile sp : socialProfiles)
			result = result + this.socialProfileService.export(sp);

		final Collection<Notification> notifications = this.notificationService.findByAdministrator(administrator);
		for (final Notification n : notifications)
			result = result + this.notificationService.export(n);

		return result;
	}

	public void calculateEveryAuditScore() {
		final Collection<Company> companies = this.companyService.findAll();
		Assert.notEmpty(companies);
		for (final Company c : companies) {
			final Integer companyId = c.getId();
			this.companyService.auditScore(companyId);
		}
	}
}
