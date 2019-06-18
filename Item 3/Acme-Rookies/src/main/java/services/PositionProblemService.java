
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PositionProblemRepository;
import domain.Application;
import domain.Company;
import domain.Position;
import domain.PositionProblem;

@Service
@Transactional
public class PositionProblemService {

	//Managed Repository
	@Autowired
	private PositionProblemRepository	positionProblemRepository;

	//Supported services
	@Autowired
	private CompanyService				companyService;

	@Autowired
	private ApplicationService			applicationService;


	// Constructor ----------------------------------------------------

	public PositionProblemService() {
		super();
	}

	//CRUD

	public PositionProblem create() {
		PositionProblem result;

		result = new PositionProblem();
		result.setDraftMode(false);

		return result;

	}

	public PositionProblem save(final PositionProblem positionProblem) {
		Assert.notNull(positionProblem);
		final PositionProblem result;

		if (positionProblem.getId() != 0) {
			final PositionProblem retrieved = this.findOne(positionProblem.getId());
			Assert.isTrue(retrieved.isDraftMode());
		}

		final Company principal = this.companyService.findByPrincipal();
		final Company owner = positionProblem.getPosition().getCompany();
		Assert.isTrue(principal.equals(owner));

		result = this.positionProblemRepository.save(positionProblem);
		//this.positionProblemRepository.flush();
		return result;
	}
	public void delete(final PositionProblem positionProblem) {
		Assert.notNull(positionProblem);
		Assert.isTrue(positionProblem.getId() != 0);

		final PositionProblem retrieved = this.findOne(positionProblem.getId());
		Assert.isTrue(retrieved.isDraftMode());

		final Company principal = this.companyService.findByPrincipal();
		final Company owner = retrieved.getPosition().getCompany();
		Assert.isTrue(principal.equals(owner));

		this.positionProblemRepository.delete(positionProblem);
	}

	public Collection<PositionProblem> findAll() {
		Collection<PositionProblem> result;

		result = this.positionProblemRepository.findAll();

		return result;
	}

	public PositionProblem findOne(final int positionProblemId) {
		PositionProblem result;

		result = this.positionProblemRepository.findOne(positionProblemId);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.positionProblemRepository.flush();
	}

	// Other

	public Collection<PositionProblem> findByCompany(final int companyId) {
		Assert.isTrue(companyId != 0);
		Collection<PositionProblem> res;

		res = this.positionProblemRepository.findByCompanyId(companyId);
		Assert.notNull(res);

		return res;
	}


	// Reconstruct

	@Autowired
	private Validator	validator;


	public PositionProblem reconstruct(final PositionProblem problem, final BindingResult binding) {
		PositionProblem result;

		if (problem.getId() == 0)
			result = problem;
		else {
			final PositionProblem retrieved = this.positionProblemRepository.findOne(problem.getId());
			result = problem;

			result.setPosition(retrieved.getPosition());

		}

		this.validator.validate(result, binding);
		this.flush();
		return result;
	}

	public Collection<PositionProblem> findByPosition(final Position position) {
		final Integer positionId = position.getId();
		final Collection<PositionProblem> res = this.positionProblemRepository.findbyPositionID(positionId);
		return res;
	}

	// Clear

	public void clear(final PositionProblem positionProblem) {

		final Collection<Application> applications = this.applicationService.findByPositionId(positionProblem.getPosition().getId());
		for (final Application a : applications)
			this.applicationService.clear(a);

		this.positionProblemRepository.delete(positionProblem);
	}

	// Export

	public String export(final PositionProblem positionProblem) {

		final String result = "Problem\n\nTitle: " + positionProblem.getTitle() + "\nStatement: " + positionProblem.getStatement() + "\nHint: " + positionProblem.getHint() + "\nAttachments: " + positionProblem.getAttachments() + "\nDraft mode: "
			+ positionProblem.isDraftMode() + "\n\n";

		return result;
	}
}
