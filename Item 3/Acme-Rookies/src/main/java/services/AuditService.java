
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AuditRepository;
import domain.Audit;
import domain.Auditor;
import domain.Company;

@Service
@Transactional
public class AuditService {

	// Managed Repository
	@Autowired
	private AuditRepository	auditRepository;

	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private CompanyService	companyService;


	// Constructor ----------------------------------------------------

	public AuditService() {
		super();
	}

	// CRUD

	public Audit create() {
		Audit result;
		Auditor auditor;

		result = new Audit();
		auditor = this.auditorService.findByPrincipal();

		result.setMoment(new Date(System.currentTimeMillis() - 1000));
		result.setAuditor(auditor);

		return result;
	}

	public Audit save(final Audit audit) {
		Assert.notNull(audit);
		Audit result;

		if (audit.getId() != 0) {
			final Audit retrieved = this.findOne(audit.getId());
			Assert.isTrue(retrieved.isDraftMode());
		}

		final Auditor principal = this.auditorService.findByPrincipal();
		final Auditor owner = audit.getAuditor();
		Assert.isTrue(principal.equals(owner));

		result = this.auditRepository.save(audit);
		return result;
	}
	public void delete(final Audit audit) {
		Assert.notNull(audit);
		Assert.isTrue(audit.getId() != 0);

		final Audit retrieved = this.findOne(audit.getId());
		Assert.isTrue(retrieved.isDraftMode());

		final Auditor principal = this.auditorService.findByPrincipal();
		final Auditor owner = retrieved.getAuditor();
		Assert.isTrue(principal.equals(owner));

		this.auditRepository.delete(audit);
	}

	public Collection<Audit> findAll() {
		Collection<Audit> result;

		result = this.auditRepository.findAll();

		return result;
	}

	public Audit findOne(final int auditId) {
		Audit result;

		result = this.auditRepository.findOne(auditId);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.auditRepository.flush();
	}

	public Collection<Audit> findByAuditor(final Auditor auditor) {
		return this.auditRepository.findByAuditorId(auditor.getId());
	}

	public Collection<Audit> findByPosition(final int positionId) {
		return this.auditRepository.findByPositionId(positionId);
	}

	public Collection<Audit> findAuditByCompanyId(final Integer companyId) {
		final Company company = this.companyService.findOne(companyId);
		Assert.notNull(company);
		final Collection<Audit> res = this.auditRepository.findByCompanyId(companyId);
		return res;
	}

	public Double[] getAuditScore() {
		return this.auditRepository.getScore();
	}


	// Reconstruct

	@Autowired
	private Validator	validator;


	public Audit reconstruct(final Audit audit, final BindingResult binding) {
		Audit result;

		if (audit.getId() == 0)
			result = audit;
		else {
			final Audit retrieved = this.auditRepository.findOne(audit.getId());
			result = audit;

			result.setAuditor(retrieved.getAuditor());
			result.setPosition(retrieved.getPosition());
			result.setMoment(retrieved.getMoment());

		}

		this.validator.validate(result, binding);
		this.flush();
		return result;
	}

	public String export(final Audit audit) {
		String result = "";
		result = result + "\nDescription: " + audit.getDescription() + "\nMoment: " + audit.getMoment() + "\nScore: " + audit.getScore();
		return result;
	}
}
