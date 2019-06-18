
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CompanyRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Audit;
import domain.Company;
import domain.CreditCard;
import domain.Message;
import domain.Position;
import domain.SocialProfile;
import forms.RegisterCompanyForm;

@Service
@Transactional
public class CompanyService {

	// Managed Repository
	@Autowired
	private CompanyRepository		companyRepository;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private AuditService			auditService;

	@Autowired
	private ActorService			actorService;


	// Constructor ----------------------------------------------------

	public CompanyService() {
		super();
	}

	// CRUD

	public Company registerCompany(final RegisterCompanyForm registerCompanyForm) {
		final Company result = this.create();
		final CreditCard creditCard = this.creditCardService.create();

		final UserAccount userAccount = new UserAccount();
		final String password = registerCompanyForm.getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hashedPassword = encoder.encodePassword(password, null);
		Assert.isTrue(registerCompanyForm.getPassword().equals(registerCompanyForm.getPassword2()));
		userAccount.setPassword(hashedPassword);
		userAccount.setUsername(registerCompanyForm.getUsername());
		userAccount.setAccountNonLocked(true);

		//Seteo los atributos al resultado
		final Authority authority = new Authority();
		authority.setAuthority(Authority.COMPANY);
		final List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		creditCard.setCvv(registerCompanyForm.getCvv());
		creditCard.setExpirationMonth(registerCompanyForm.getExpirationMonth());
		creditCard.setExpirationYear(registerCompanyForm.getExpirationYear());
		creditCard.setHolder(registerCompanyForm.getHolder());
		creditCard.setMake(registerCompanyForm.getMake());
		creditCard.setNumber(registerCompanyForm.getNumber());

		result.setUserAccount(userAccount);
		result.setAddress(registerCompanyForm.getAddress());
		result.setEmail(registerCompanyForm.getEmail());
		result.setName(registerCompanyForm.getName());
		result.setPhone(registerCompanyForm.getPhone());
		result.setPhoto(registerCompanyForm.getPhoto());
		result.setSurname(registerCompanyForm.getSurname());
		result.setCreditCard(creditCard);
		result.setVat(registerCompanyForm.getVAT());
		result.setSpammer(false);
		result.setComercialName(registerCompanyForm.getComercialName());
		this.save(result);

		return result;
	}

	public Company create() {
		Company result;

		result = new Company();
		result.setAuditScore(0.0);

		return result;

	}

	public Company save(final Company company) {
		Assert.notNull(company);
		Company result;
		result = this.companyRepository.save(company);
		this.companyRepository.flush();
		return result;
	}

	public void delete(final Company company) {
		Assert.notNull(company);
		Assert.isTrue(company.getId() != 0);
		Assert.isTrue(this.companyRepository.exists(company.getId()));

		this.companyRepository.delete(company);
	}

	public Collection<Company> findAll() {
		Collection<Company> result;

		result = this.companyRepository.findAll();

		return result;
	}

	public Company findOne(final int companyId) {
		Company result;

		result = this.companyRepository.findOne(companyId);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.companyRepository.flush();
	}

	public Collection<Company> getCompaniesMorePositions() {

		final Administrator logged = this.administratorService.findByPrincipal();

		Assert.isTrue(logged instanceof Administrator);

		final List<Company> result = new ArrayList<Company>(this.companyRepository.getCompaniesMostPositions());

		if (result.size() > 3)
			return result.subList(0, 3);

		return result;

	}

	// Other 
	public Company findByPrincipal() {
		Company result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Company findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Company result;

		result = this.companyRepository.findbyUserAccountID(userAccount.getId());

		return result;
	}

	public Company auditScore(final Integer companyId) {
		final Company company = this.findOne(companyId);
		Assert.notNull(company);
		final Collection<Audit> audits = this.auditService.findAuditByCompanyId(companyId);
		if (audits.isEmpty())
			company.setAuditScore(null);
		else {
			Double score = 0.0;
			Integer i = 0;
			for (final Audit a : audits) {
				score = score + a.getScore();
				i++;
			}
			final Double averageScore = score / i;
			if (averageScore == 0.0)
				company.setAuditScore(0.0);
			else if (averageScore == 10.0)
				company.setAuditScore(1.0);
			else {
				final Double totalScore = (averageScore / 10);
				company.setAuditScore(totalScore);
			}

		}
		this.companyRepository.save(company);
		this.companyRepository.flush();
		return company;
	}

	public Double[] getAuditCompanyScore() {
		return this.companyRepository.getScoreData();
	}

	public Collection<Company> getTopScoreCompanies() {

		final List<Company> result = new ArrayList<Company>(this.companyRepository.getTopScoreCompanies());

		if (result.size() > 3)
			return result.subList(0, 3);

		return result;
	}
	// Clear

	public void clear(final Company company) {

		company.setAddress("lorem ipsum");
		company.setEmail("loremipsum@loremipsum.com");
		company.setName("lorem ipsum");
		company.setPhone("111111111");
		company.setPhoto("http://photo.com");
		company.setSpammer(false);
		company.setSurname("lorem ipsum");
		company.setVat(0);
		company.setComercialName("lorem ipsum");
		company.setAuditScore(0.);

		this.actorService.save(company);

		final UserAccount userAccount = company.getUserAccount();
		this.actorService.ban(userAccount);
	}

	// Export

	public String export(final Company company) {

		String result = "Company\n\nName: " + company.getName() + "\nSurname: " + company.getSurname() + "\nVAT: " + company.getVat() + "\nPhoto: " + company.getPhoto() + "\nEmail: " + company.getEmail() + "\nPhone: " + company.getPhone() + "\nAddress: "
			+ company.getAddress() + "\nSpammer: " + company.isSpammer() + "\n\n";

		final Collection<Message> received = this.messageService.findByRecipient(company);
		for (final Message m1 : received)
			result = result + this.messageService.export(m1);
		final Collection<Message> sent = this.messageService.findBySender(company);
		for (final Message m2 : sent)
			result = result + this.messageService.export(m2);

		final Collection<SocialProfile> socialProfiles = this.socialProfileService.findByActor(company);
		for (final SocialProfile sp : socialProfiles)
			result = result + this.socialProfileService.export(sp);

		final Collection<Position> positions = this.positionService.findByCompany(company);
		for (final Position p : positions)
			result = result + this.positionService.export(p);

		return result;
	}
}
