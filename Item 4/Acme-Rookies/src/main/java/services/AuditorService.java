
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AuditorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Audit;
import domain.Auditor;
import domain.CreditCard;
import domain.Message;
import domain.SocialProfile;
import forms.RegisterAuditorForm;

@Service
@Transactional
public class AuditorService {

	// Managed Repository
	@Autowired
	private AuditorRepository		auditorRepository;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private AuditService			auditService;

	@Autowired
	private ActorService			actorService;


	// Constructor ----------------------------------------------------

	public AuditorService() {
		super();
	}

	// CRUD

	// CRUD

	public Auditor registerAuditor(final RegisterAuditorForm registerAuditorForm) {
		final Auditor result = this.create();
		final CreditCard creditCard = this.creditCardService.create();

		final UserAccount userAccount = new UserAccount();
		final String password = registerAuditorForm.getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hashedPassword = encoder.encodePassword(password, null);
		Assert.isTrue(registerAuditorForm.getPassword().equals(registerAuditorForm.getPassword2()));
		userAccount.setPassword(hashedPassword);
		userAccount.setUsername(registerAuditorForm.getUsername());
		userAccount.setAccountNonLocked(true);

		//Seteo los atributos al resultado
		final Authority authority = new Authority();
		authority.setAuthority(Authority.AUDITOR);
		final List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		creditCard.setCvv(registerAuditorForm.getCvv());
		creditCard.setExpirationMonth(registerAuditorForm.getExpirationMonth());
		creditCard.setExpirationYear(registerAuditorForm.getExpirationYear());
		creditCard.setHolder(registerAuditorForm.getHolder());
		creditCard.setMake(registerAuditorForm.getMake());
		creditCard.setNumber(registerAuditorForm.getNumber());

		result.setUserAccount(userAccount);
		result.setAddress(registerAuditorForm.getAddress());
		result.setEmail(registerAuditorForm.getEmail());
		result.setName(registerAuditorForm.getName());
		result.setPhone(registerAuditorForm.getPhone());
		result.setPhoto(registerAuditorForm.getPhoto());
		result.setSurname(registerAuditorForm.getSurname());
		result.setCreditCard(creditCard);
		result.setVat(registerAuditorForm.getVAT());
		result.setSpammer(false);
		this.save(result);

		return result;
	}

	public Auditor create() {
		Auditor result;

		result = new Auditor();

		return result;
	}

	public Auditor save(final Auditor auditor) {
		Assert.notNull(auditor);
		Auditor result;
		result = this.auditorRepository.save(auditor);
		this.auditorRepository.flush();
		return result;
	}

	public void delete(final Auditor auditor) {
		Assert.notNull(auditor);
		Assert.isTrue(auditor.getId() != 0);
		Assert.isTrue(this.auditorRepository.exists(auditor.getId()));

		this.auditorRepository.delete(auditor);
	}

	public Collection<Auditor> findAll() {
		Collection<Auditor> result;

		result = this.auditorRepository.findAll();

		return result;
	}

	public Auditor findOne(final int auditorId) {
		Auditor result;

		result = this.auditorRepository.findOne(auditorId);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.auditorRepository.flush();
	}

	public Auditor findByPrincipal() {
		Auditor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Auditor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Auditor result;

		result = this.auditorRepository.findbyUserAccountID(userAccount.getId());

		return result;
	}

	public String export(final Auditor principal) {
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
		final Collection<Audit> audits = this.auditService.findByAuditor(principal);
		for (final Audit audit : audits)
			result = result + this.auditService.export(audit);
		return result;
	}

	public void clear(final Auditor principal) {
		// TODO Auto-generated method stub
		principal.setAddress("lorem ipsum");
		principal.setEmail("loremipsum@loremipsum.com");
		principal.setName("lorem ipsum");
		principal.setPhone("111111111");
		principal.setPhoto("http://photo.com");
		principal.setSpammer(false);
		principal.setSurname("lorem ipsum");
		principal.setVat(0);

		this.actorService.save(principal);

		final UserAccount userAccount = principal.getUserAccount();
		this.actorService.ban(userAccount);
	}
}
