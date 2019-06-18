
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Administrator;
import domain.Finder;
import domain.Rookie;
import domain.Position;

@Service
@Transactional
public class FinderService {

	// Managed Repository
	@Autowired
	private FinderRepository		finderRepository;

	//Supporting services
	@Autowired
	private RookieService			rookieService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private SystemConfigService		systemConfigService;

	@Autowired
	private AdministratorService	administratorService;


	// Constructor ----------------------------------------------------

	public FinderService() {
		super();
	}

	// CRUD

	public Finder create() {
		Finder result;
		Rookie rookie;

		result = new Finder();
		result.setPositions(new HashSet<Position>());
		result.setFinderDate(new Date());

		rookie = this.rookieService.findByPrincipal();
		result.setRookie(rookie);

		result = new Finder();

		return result;

	}

	public Finder findByRookie(final Rookie rookie) {
		return this.finderRepository.findByRookieId(rookie.getId());
	}

	public Finder save(final Finder finder) {
		Finder result;

		Assert.notNull(finder);
		List<Position> findByKeyword = new ArrayList<Position>(this.positionService.findByKeyword(finder));
		final List<Position> findByMinimumSalary = new ArrayList<Position>(this.positionService.findByMinimumSalary(finder));
		final List<Position> findByMaximumDeadline = new ArrayList<Position>(this.positionService.findByMaximumDeadline(finder));
		findByKeyword.retainAll(findByMinimumSalary);
		findByKeyword.retainAll(findByMaximumDeadline);
		if (findByKeyword.size() > this.systemConfigService.findSystemConfiguration().getFinderMaxResults())
			findByKeyword = findByKeyword.subList(0, this.systemConfigService.findSystemConfiguration().getFinderMaxResults());
		finder.setPositions(findByKeyword);
		finder.setRookie(this.rookieService.findByPrincipal());
		result = this.finderRepository.save(finder);
		return result;
	}

	public void delete(final Finder finder) {
		Assert.notNull(finder);
		Assert.isTrue(finder.getId() != 0);
		Assert.isTrue(this.finderRepository.exists(finder.getId()));

		this.finderRepository.delete(finder);
	}

	public Collection<Finder> findAll() {
		Collection<Finder> result;

		result = this.finderRepository.findAll();

		return result;
	}

	public Finder findOne(final int finderId) {
		Finder result;

		result = this.finderRepository.findOne(finderId);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.finderRepository.flush();
	}

	public Double[] getFinderResultsStats() {
		final Administrator logged = this.administratorService.findByPrincipal();

		Assert.isTrue(logged instanceof Administrator);

		return this.finderRepository.getFinderResults();
	}

	public Double emptyVsFullRatio() {
		final Administrator logged = this.administratorService.findByPrincipal();

		Assert.isTrue(logged instanceof Administrator);

		final Double empty = this.finderRepository.countEmptyFinders();
		final Double full = this.finderRepository.countNotEmptyFinders();

		if (!full.equals(0.))
			return empty / full;
		else
			return 0.;

	}

	// Clear

	public void clear(final Finder finder) {

		this.finderRepository.delete(finder);
	}

	// Export

	public String export(final Finder finder) {

		final String result = "Finder\n\nKeyword: " + finder.getKeyword() + "\nDate: " + finder.getFinderDate() + "\nMaximum deadline: " + finder.getMaximumDeadLine() + "\nMinimum salary: " + finder.getMinimumSalary() + "\n\n";

		return result;
	}
}
