
package services;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Finder;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FinderServiceTest extends AbstractTest {

	//Sentence coverage of 4.7%
	//1568 instructions

	//SUT
	@Autowired
	private FinderService	finderService;

	Calendar				calendar		= new GregorianCalendar(2019, 05, 20);
	Date					maximumDeadline	= this.calendar.getTime();


	@Test
	public void createAndSaveDriver() {
		final Object testingData[][] = {
			{	// Finder creation
				"rookie1", "sampleKeyword", 0, this.maximumDeadline, "sampleArea", null
			}, {	// Company can't create a finder
				"company1", "sampleKeyword", 0, this.maximumDeadline, "sampleArea", DataIntegrityViolationException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.createAndSaveTemplate((String) testingData[i][0], (String) testingData[i][1], (int) testingData[i][2], (Date) testingData[i][3], (Class<?>) testingData[i][5]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------
	protected void createAndSaveTemplate(final String userName, final String keyword, final int minimumSalary, final Date maximumDeadline, final Class<?> expected) {
		Class<?> caught;
		Finder finder;

		caught = null;
		try {
			this.authenticate(userName);
			finder = this.finderService.create();
			finder.setKeyword(keyword);
			finder.setMinimumSalary(minimumSalary);
			finder.setMaximumDeadLine(maximumDeadline);

			this.finderService.save(finder);
			this.finderService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// FinderResults

	protected void templateFinderResults(final String adminName, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(adminName);

			this.finderService.getFinderResultsStats();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	@Test
	public void finderResultsDriver() {
		final Object testingData[][] = {
			{
				// Rookie can't display dashboard
				"rookie1", IllegalArgumentException.class
			}, {
				// Company can't display dashboard

				"company1", IllegalArgumentException.class
			}, {

				// Admin can display dashboard
				"admin", null
			}, {
				// Anónimo can't display dashboard
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateFinderResults((String) testingData[i][0], (Class<?>) testingData[i][1]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// EmptyvsFullRatioFinders

	protected void templateEmtyVSFullRatioFinders(final String adminName, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(adminName);

			this.finderService.emptyVsFullRatio();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	@Test
	public void emptyVSFullRatioFindersDriver() {
		final Object testingData[][] = {
			{
				// Rookie can't display dashboard
				"rookie1", IllegalArgumentException.class
			}, {
				// Company can't display dashboard

				"company1", IllegalArgumentException.class
			}, {

				// Admin can display dashboard
				"admin", null
			}, {
				// Anónimo can't display dashboard
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateEmtyVSFullRatioFinders((String) testingData[i][0], (Class<?>) testingData[i][1]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}
}
