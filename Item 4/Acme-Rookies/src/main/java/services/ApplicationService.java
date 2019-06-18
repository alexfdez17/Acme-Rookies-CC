
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import security.Authority;
import domain.Administrator;
import domain.Application;
import domain.Company;
import domain.Position;
import domain.PositionProblem;
import domain.Rookie;

@Service
@Transactional
public class ApplicationService {

	// Managed Repository
	@Autowired
	private ApplicationRepository	applicationRepository;

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private PositionProblemService	positionProblemService;

	@Autowired
	private AdministratorService	administratorService;


	// Constructor ----------------------------------------------------

	public ApplicationService() {
		super();
	}

	// CRUD

	public Application create() {
		Application result;

		result = new Application();
		return result;

	}
	public Collection<Application> findAcceptedByPosition(final Position position) {
		final Integer positionId = position.getId();
		final Collection<Application> result = this.applicationRepository.findAcceptedByPositionId(positionId);
		return result;
	}

	public Application save(final Application application) {
		Assert.notNull(application);
		Application result;
		result = this.applicationRepository.save(application);
		this.applicationRepository.flush();
		return result;
	}

	public void delete(final Application application) {
		Assert.notNull(application);
		Assert.isTrue(application.getId() != 0);
		Assert.isTrue(this.applicationRepository.exists(application.getId()));

		this.applicationRepository.delete(application);
	}

	public Collection<Application> findAll() {
		Collection<Application> result;

		result = this.applicationRepository.findAll();

		return result;
	}

	public Application findOne(final int applicationId) {
		Application result;

		result = this.applicationRepository.findOne(applicationId);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.applicationRepository.flush();
	}

	//-------------------------------------------------------------

	public Collection<Application> findCurrentRookieApplications() {
		final Authority authH = new Authority();
		authH.setAuthority(Authority.ROOKIE);
		Assert.isTrue(this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authH));
		Assert.notNull(this.rookieService.findByPrincipal());
		Collection<Application> result;

		result = this.applicationRepository.findByRookie(this.rookieService.findByPrincipal().getId());

		return result;
	}
	
	public Collection<Application> findCurrentRookieApplicationsStatus(String a) {
		final Authority authH = new Authority();
		authH.setAuthority(Authority.ROOKIE);
		Assert.isTrue(this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authH));
		Assert.notNull(this.rookieService.findByPrincipal());
		Collection<Application> aux;
		Collection<Application> result = new ArrayList<>();

		aux = this.applicationRepository.findByRookie(this.rookieService.findByPrincipal().getId());
		
		for (Application application : aux) {
			if(application.getStatus().equals(a)){
				result.add(application);
			}
		}
		
		if (result.isEmpty()){
			result = aux;
		}

		return result;
	}

	public Collection<Application> findCurrentCompanyApplications() {
		final Authority authC = new Authority();
		authC.setAuthority(Authority.COMPANY);
		Assert.isTrue(this.companyService.findByPrincipal().getUserAccount().getAuthorities().contains(authC));
		Collection<Application> result;

		result = this.applicationRepository.findByCompanyId(this.companyService.findByPrincipal().getId());

		return result;
	}
	
	public Collection<Application> findCurrentCompanyApplicationsStatus(String a) {
		final Authority authC = new Authority();
		authC.setAuthority(Authority.COMPANY);
		Assert.isTrue(this.companyService.findByPrincipal().getUserAccount().getAuthorities().contains(authC));
		Collection<Application> aux;
		Collection<Application> result = new ArrayList<>();

		aux = this.applicationRepository.findByCompanyId(this.companyService.findByPrincipal().getId());
		
		for (Application application : aux) {
			if(application.getStatus().equals(a)){
				result.add(application);
			}
		}
		
		if (result.isEmpty()){
			result = aux;
		}

		return result;
	}

	public Collection<Application> findByPositionId(final int id) {
		Assert.notNull(id);
		Collection<Application> result;

		result = this.applicationRepository.findByPositionId(id);

		return result;
	}

	public Application createApplication(final Position position) {
		final Authority authH = new Authority();
		authH.setAuthority(Authority.ROOKIE);
		Assert.isTrue(this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authH));
		Assert.notNull(this.rookieService.findByPrincipal());
		Assert.notNull(this.positionProblemService.findByCompany(position.getCompany().getId()));

		Application result;
		Company company;
		Collection<PositionProblem> problems;
		Random r;
		int i;

		result = new Application();
		company = new Company();
		problems = new ArrayList<>();
		r = new Random();

		company = position.getCompany();
		problems = this.positionProblemService.findByCompany(company.getId());
		i = r.nextInt(problems.size());

		for (final PositionProblem positionProblem : problems) {
			if (i == 0) {
				result.setPositionProblem(positionProblem);
				break;
			}
			i--;
		}

		result.setCreationMoment(new Date());
		result.setStatus("PENDING");
		result.setRookie(this.rookieService.findByPrincipal());

		this.save(result);

		return result;
	}

	public Application submitApplication(final Application application, final String answerCode, final String answerExplanation) {

		application.setAnswerCode(answerCode);
		application.setAnswerExplanation(answerExplanation);
		application.setSubmittedMoment(new Date());
		application.setStatus("SUBMITTED");

		return application;
	}

	public Double[] applicationsPerRookieStats() {

		final Administrator logged = this.administratorService.findByPrincipal();

		Assert.isTrue(logged instanceof Administrator);

		final Double[] res = new Double[4];
		final Collection<Long> stats = this.applicationRepository.getApplicationsPerRookie();

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
			if (num != 0) {
				res[3] = Math.sqrt(num / (stats.size() - 1)); // Standard
																// deviation
			} else {
				res[3] = 0.;
			}
		} else {
			res[0] = 0.;
			res[1] = 0.;
			res[2] = 0.;
			res[3] = 0.;
		}

		return res;
	}

	public Collection<Application> findByRookie(final Rookie rookie) {
		Assert.notNull(rookie);
		Collection<Application> result;

		result = this.applicationRepository.findByRookie(rookie.getId());

		return result;
	}

	// Clear

	public void clear(final Application application) {

		this.applicationRepository.delete(application);
	}

	// Export

	public String export(final Application application) {

		final String result = "Application\n\nProblem: " + application.getPositionProblem().getTitle() + "\nCreation moment: " + application.getCreationMoment() + "\nAnswer explanation: " + application.getAnswerExplanation() + "\nAnswer code: "
			+ application.getAnswerCode() + "\nSubmitted moment: " + application.getSubmittedMoment() + "\nStatus: " + application.getStatus() + "\n\n";

		return result;
	}
}
