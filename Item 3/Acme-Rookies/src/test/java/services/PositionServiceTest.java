
package services;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Position;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PositionServiceTest extends AbstractTest {

	//Sentence coverage of 5.1%
	//1703 instructions

	//SUT
	@Autowired
	private PositionService	positionService;


	@Test
	public void createAndSavePositionDriver() {
		final Object testingData[][] = {
			{	//Create a position
				"company1", null
			}, {//Anon cant create a position
				null, IllegalArgumentException.class
			}, {//Rookie cant create a position
				"rookie1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.createAndSavePositionTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void editDriver() {
		final Object testingData[][] = {
			{	//Company updates position
				"company1", "position3", null
			}, { //Company cannot update another one's position
				"rookie2", "position5", IllegalArgumentException.class
			}, { //Only company can update position
				"rookie1", "position3", IllegalArgumentException.class
			}, { //Anon cannot update position
				null, "position3", IllegalArgumentException.class
			}, { //Company cannot update position in final mode
				"company1", "position1", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.editTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	//Ancillary method

	protected void createAndSavePositionTemplate(final String userName, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(userName);
			final Position position = this.positionService.create();
			final Calendar cal1 = new GregorianCalendar(2021, 2, 21);
			final Date end = cal1.getTime();
			position.setDeadLine(end);
			position.setDescription("descr");
			position.setProfileRequired("prof");
			position.setSalaryOffered(344);
			position.setSkillsRequired("skills");
			position.setStatus("draft");
			position.setTechnologiesRequired("techno");
			position.setTitle("title");
			this.positionService.save(position);
			this.positionService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void editTemplate(final String userName, final String positionBeanName, final Class<?> expected) {
		Class<?> caught;
		int positionId;
		Position position;

		caught = null;
		try {
			this.authenticate(userName);
			positionId = super.getEntityId(positionBeanName);
			position = this.positionService.findOne(positionId);
			position.setDescription("mod desc");
			this.positionService.save(position);
			this.positionService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// PositionsPerCompany

	protected void templatePositionsPerCompany(final String adminName, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(adminName);

			this.positionService.positionsPerCompanyStats();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	@Test
	public void positionsPerCompanyDriver() {
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
				this.templatePositionsPerCompany((String) testingData[i][0], (Class<?>) testingData[i][1]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// SalariesOffered

	protected void templateSalariesOffered(final String adminName, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(adminName);

			this.positionService.getSalariesOfferedStats();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	@Test
	public void salariesOfferedDriver() {
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
				this.templateSalariesOffered((String) testingData[i][0], (Class<?>) testingData[i][1]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}
}
