package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PositionRepository;
import domain.Administrator;
import domain.Application;
import domain.Company;
import domain.Finder;
import domain.Position;
import domain.PositionProblem;

@Service
@Transactional
public class PositionService {

	// Managed Repository
	@Autowired
	private PositionRepository positionRepository;

	@Autowired
	private CompanyService companyService;
	@Autowired
	private PositionProblemService positionProblemService;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private Validator validator;
	@Autowired
	private SystemConfigService systemConfigService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private AdministratorService administratorService;

	// Constructor ----------------------------------------------------

	public PositionService() {
		super();
	}

	// CRUD

	public Position create() {

		final Position result = new Position();

		final Company company = this.companyService.findByPrincipal();
		final String comercialName = company.getComercialName();
		String tickerString;
		String letters;
		int i = comercialName.length();
		if (i > 3)
			letters = comercialName.substring(0, 4);
		else {
			letters = comercialName;
			while (i < 4) {
				letters = letters + "X";
				i++;
			}
		}

		final String tickerNumber = RandomStringUtils.randomNumeric(4);
		tickerString = letters.toUpperCase() + "-" + tickerNumber;
		result.setTicker(tickerString);
		result.setCompany(company);

		return result;

	}

	public Position save(final Position position) {
		final Company company = this.companyService.findByPrincipal();
		Boolean isPublished = false;
		if (position.getId() != 0) {
			final Position currentPosition = this.positionRepository
					.findOne(position.getId());
			if (position.getStatus().equals("final")
					&& currentPosition.getStatus().equals("draft"))
				isPublished = true;
		}

		if (position.getId() != 0) {
			Assert.isTrue(position.getCompany().equals(company));
			final Position db = this.positionRepository.findOne(position
					.getId());
			Assert.isTrue(!db.getStatus().equals("final"));
		}
		Assert.notNull(position);
		final Boolean isDraft = position.getStatus().equals("draft");
		final Boolean isFinal = position.getStatus().equals("final");
		Assert.isTrue(isFinal || isDraft);
		Assert.isTrue(position.getCompany().equals(company));
		if (isFinal == true)
			Assert.isTrue(this.positionProblemService.findByPosition(position)
					.size() >= 2);
		Position result;
		Assert.isTrue(position.getDeadLine().after(
				Calendar.getInstance().getTime()));
		result = this.positionRepository.save(position);
		if (isPublished)
			this.messageService.newPositionNotification(result);
		this.positionRepository.flush();
		return result;
	}

	public void delete(final Position position) {
		Assert.notNull(position);
		Assert.isTrue(position.getId() != 0);
		Assert.isTrue(position.getStatus().equals("draft"));
		Assert.isTrue(this.positionRepository.exists(position.getId()));
		final Company company = this.companyService.findByPrincipal();
		Assert.isTrue(position.getCompany().equals(company));
		final Collection<PositionProblem> problems = this.positionProblemService
				.findByPosition(position);
		for (final PositionProblem p : problems)
			this.positionProblemService.delete(p);

		this.positionRepository.delete(position);
	}

	public void cancel(final Position position) {
		Assert.notNull(position);
		Assert.isTrue(position.getId() != 0);
		Assert.isTrue(this.positionRepository.exists(position.getId()));
		final Company company = this.companyService.findByPrincipal();
		Assert.isTrue(position.getCompany().equals(company));
		final Collection<Application> applications = this.applicationService
				.findByPositionId(position.getId());
		for (final Application ap : applications) {
			Assert.isTrue(!ap.getStatus().equals("accepted"));
			ap.setStatus("rejected");
			this.applicationService.save(ap);
		}
		position.setStatus("cancelled");
		this.positionRepository.save(position);
	}

	public Collection<Position> findAll() {
		Collection<Position> result;

		result = this.positionRepository.findAll();

		return result;
	}

	public Position findOne(final int positionId) {
		Position result;

		result = this.positionRepository.findOne(positionId);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.positionRepository.flush();
	}

	public Double[] positionsPerCompanyStats() {
		final Administrator logged = this.administratorService
				.findByPrincipal();

		Assert.isTrue(logged instanceof Administrator);

		final Double[] res = new Double[4];
		final Collection<Long> stats = this.positionRepository
				.getPositionsPerCompany();

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

	public Double[] getSalariesOfferedStats() {
		final Administrator logged = this.administratorService
				.findByPrincipal();

		Assert.isTrue(logged instanceof Administrator);

		return this.positionRepository.getSalaryOffered();
	}

	public Position getTopPosition() {
		final Administrator logged = this.administratorService
				.findByPrincipal();

		Assert.isTrue(logged instanceof Administrator);

		final List<Position> result = new ArrayList<Position>(
				this.positionRepository.getTopSalary());
		if (result.isEmpty())
			return null;
		else
			return result.get(0);
	}

	public Position getBotPosition() {
		final Administrator logged = this.administratorService
				.findByPrincipal();

		Assert.isTrue(logged instanceof Administrator);

		final List<Position> result = new ArrayList<Position>(
				this.positionRepository.getBotSalary());
		if (result.isEmpty())
			return null;
		else
			return result.get(0);
	}

	public Double getAvgTopPositionsSalary() {
		return this.positionRepository.getAvgSalaryTopScore();
	}

	public Double[] getSponsorshipsPositionStats() {
		return this.positionRepository.getSponsorshipsPositions();
	}

	// Other
	public Collection<Position> findByCompany(final Company company) {
		final int companyId = company.getId();
		Collection<Position> result;
		result = this.positionRepository.findbyCompanyID(companyId);
		return result;
	}

	public Collection<Position> findbyFinalMode() {
		Collection<Position> res;
		res = this.positionRepository.findbyFinalMode();
		return res;
	}

	public Position reconstruct(final Position position,
			final BindingResult bindingResult) {
		Position result;

		if (position.getId() == 0)
			result = position;
		else {
			final Position p = this.positionRepository
					.findOne(position.getId());
			result = new Position(p);
			result.setId(p.getId());
			result.setVersion(p.getVersion());
			result.setTitle(position.getTitle());
			result.setDescription(position.getDescription());
			result.setStatus(position.getStatus());
			result.setCompany(position.getCompany());
			result.setDeadLine(position.getDeadLine());
			result.setProfileRequired(position.getProfileRequired());
			result.setSalaryOffered(position.getSalaryOffered());
			result.setSkillsRequired(position.getSkillsRequired());
			result.setTechnologiesRequired(position.getTechnologiesRequired());
			result.setTicker(position.getTicker());
			this.validator.validate(result, bindingResult);

		}
		return result;

	}

	// Finder Methods

	public Collection<Position> findByKeyword(final Finder finder) {
		List<Position> result = new ArrayList<Position>();
		if (finder.getKeyword().isEmpty()) {
			result.addAll(this.findAll());
			if (result.size() > this.systemConfigService
					.findSystemConfiguration().getFinderMaxResults())
				result = result.subList(0, this.systemConfigService
						.findSystemConfiguration().getFinderMaxResults());
		} else
			result.addAll(this.positionRepository.findByKeyword("%"
					+ finder.getKeyword() + "%"));

		return result;
	}

	public Collection<Position> findByMinimumSalary(final Finder finder) {
		List<Position> result = new ArrayList<Position>();
		if (finder.getMinimumSalary() == 0) {
			result.addAll(this.findAll());
			if (result.size() > this.systemConfigService
					.findSystemConfiguration().getFinderMaxResults())
				result = result.subList(0, this.systemConfigService
						.findSystemConfiguration().getFinderMaxResults());
		} else
			result.addAll(this.positionRepository.findByMinimumSalary(finder
					.getMinimumSalary()));

		return result;
	}

	public Collection<Position> findByMaximumDeadline(final Finder finder) {
		List<Position> result = new ArrayList<Position>();
		if (finder.getMaximumDeadLine() == null) {
			result.addAll(this.findAll());
			if (result.size() > this.systemConfigService
					.findSystemConfiguration().getFinderMaxResults())
				result = result.subList(0, this.systemConfigService
						.findSystemConfiguration().getFinderMaxResults());
		} else
			result.addAll(this.positionRepository.findByMaximumDeadline(finder
					.getMaximumDeadLine()));

		return result;
	}

	// Clear

	public void clear(final Position position) {

		final Collection<PositionProblem> problems = this.positionProblemService
				.findByPosition(position);
		for (final PositionProblem pp : problems)
			this.positionProblemService.clear(pp);

		this.positionRepository.delete(position);
	}

	// Export

	public String export(final Position position) {

		String result = "Position\n\nTitle: " + position.getTitle()
				+ "\nDescription: " + position.getDescription()
				+ "\nDeadline: " + position.getDeadLine()
				+ "\nSalary offered: " + position.getSalaryOffered()
				+ "\nProfile required: " + position.getProfileRequired()
				+ "\nSkills required: " + position.getSkillsRequired()
				+ "\nTechnologies required: "
				+ position.getTechnologiesRequired() + "\nTicker: "
				+ position.getTicker() + "\nStatus: " + position.getStatus()
				+ "\n\n";

		final Collection<PositionProblem> problems = this.positionProblemService
				.findByPosition(position);
		for (final PositionProblem pp : problems)
			result = result + this.positionProblemService.export(pp);

		return result;
	}

}
