
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
import domain.EducationData;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EducationDataServiceTest extends AbstractTest {

	//Sentence coverage of 4.3%
	//1433 instructions

	//SUT
	@Autowired
	private EducationDataService	educationDataService;
	@Autowired
	private CurriculaService		curriculaService;


	@Test
	public void createAndSaveEducationDataDriver() {
		final Object testingData[][] = {
			{	//Create education DATA
				"rookie1", "curricula1", null
			}, {//Anon cant create  education data
				null, "curricula1", IllegalArgumentException.class
			}, {//Rookie cant create education data for another curricula
				"rookie2", "curricula1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.createAndSaveEducationDataTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void editDriver() {
		final Object testingData[][] = {
			{	//rookie updates education data
				"rookie1", "educationData1", "curricula1", null
			}, { //Rookie cannot update another one's education data
				"rookie2", "educationData2", "curricula1", IllegalArgumentException.class
			}, { //Only rookie can update education data
				"company1", "educationData1", "curricula1", IllegalArgumentException.class
			}, { //Anon cannot update education data
				null, "educationData1", "curricula1", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.editTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	//Ancillary method

	protected void createAndSaveEducationDataTemplate(final String userName, final String curricula, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(userName);
			final EducationData educationData = this.educationDataService.create();
			educationData.setDegree("degree");
			final Calendar cal1 = new GregorianCalendar(2001, 12, 12);
			final Calendar cal2 = new GregorianCalendar(2010, 12, 12);
			final Date start = cal1.getTime();
			final Date end = cal2.getTime();
			educationData.setEndDate(end);
			educationData.setStartDate(start);
			educationData.setMark("2");
			educationData.setInstitution("insti");
			final Integer curriculaId = super.getEntityId(curricula);
			final Curricula curricula1 = this.curriculaService.findOne(curriculaId);
			this.educationDataService.save(educationData, curricula1);
			this.educationDataService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void editTemplate(final String userName, final String educationDataBeanName, final String curricula, final Class<?> expected) {
		Class<?> caught;
		int educationDataId;
		EducationData educationData;

		caught = null;
		try {
			this.authenticate(userName);
			educationDataId = super.getEntityId(educationDataBeanName);
			educationData = this.educationDataService.findOne(educationDataId);
			educationData.setInstitution("mod inst");
			final Integer curriculaId = super.getEntityId(curricula);
			final Curricula curricula1 = this.curriculaService.findOne(curriculaId);
			this.educationDataService.save(educationData, curricula1);
			this.educationDataService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
