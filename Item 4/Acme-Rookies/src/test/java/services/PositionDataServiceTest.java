
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
import domain.Curricula;
import domain.PositionData;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PositionDataServiceTest extends AbstractTest {

	//Sentence coverage of 4.3%
	//1423 sentences

	//SUT
	@Autowired
	private PositionDataService	positionDataService;
	@Autowired
	private CurriculaService	curriculaService;


	@Test
	public void createAndSavePositionDataDriver() {
		final Object testingData[][] = {
			{	//Create position DATA
				"rookie1", "curricula1", null
			}, {//Anon cant create  position data
				null, "curricula1", IllegalArgumentException.class
			}, {//Rookie cant create position data for another curricula
				"rookie2", "curricula1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.createAndSavePositionDataTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void editDriver() {
		final Object testingData[][] = {
			{	//rookie updates position data
				"rookie1", "positionData1", "curricula1", null
			}, { //Rookie cannot update another one's position data
				"rookie2", "positionData2", "curricula1", IllegalArgumentException.class
			}, { //Only rookie can update position data
				"company1", "positionData1", "curricula1", IllegalArgumentException.class
			}, { //Anon cannot update position data
				null, "positionData1", "curricula1", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.editTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	//Ancillary method

	protected void createAndSavePositionDataTemplate(final String userName, final String curricula, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(userName);
			final PositionData positionData = this.positionDataService.create();
			positionData.setDescription("description");
			final Calendar cal1 = new GregorianCalendar(2001, 12, 12);
			final Calendar cal2 = new GregorianCalendar(2010, 12, 12);
			final Date start = cal1.getTime();
			final Date end = cal2.getTime();
			positionData.setEndDate(end);
			positionData.setStartDate(start);
			positionData.setTitle("title");
			final Integer curriculaId = super.getEntityId(curricula);
			final Curricula curricula1 = this.curriculaService.findOne(curriculaId);
			this.positionDataService.save(positionData, curricula1);
			this.positionDataService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void editTemplate(final String userName, final String positionDataBeanName, final String curricula, final Class<?> expected) {
		Class<?> caught;
		int positionDataId;
		PositionData positionData;

		caught = null;
		try {
			this.authenticate(userName);
			positionDataId = super.getEntityId(positionDataBeanName);
			positionData = this.positionDataService.findOne(positionDataId);
			positionData.setTitle("modified title");
			final Integer curriculaId = super.getEntityId(curricula);
			final Curricula curricula1 = this.curriculaService.findOne(curriculaId);
			this.positionDataService.save(positionData, curricula1);
			this.positionDataService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
