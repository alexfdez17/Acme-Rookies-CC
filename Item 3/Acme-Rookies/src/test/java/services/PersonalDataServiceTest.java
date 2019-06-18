
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.PersonalData;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PersonalDataServiceTest extends AbstractTest {

	//Sentence coverage of 4.1%
	//1351 instructions

	//SUT
	@Autowired
	private PersonalDataService	personalDataService;


	@Test
	public void createAndSavePersonalDataDriver() {
		final Object testingData[][] = {
			{	//Create curricula with personal data
				"rookie2", null
			}, {//Anon cant create curricula nor personal data
				null, IllegalArgumentException.class
			}, {//Company cant create curricula nor personal data
				"company1", NullPointerException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.createAndSavePersonalDataTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void editDriver() {
		final Object testingData[][] = {
			{	//rookie updates personal data
				"rookie1", "personalData1", null
			}, { //Rookie cannot update another one's personal data
				"rookie2", "personalData2", IllegalArgumentException.class
			}, { //Only rookie can update personal data
				"company1", "personalData1", IllegalArgumentException.class
			}, { //Anon cannot update personal data
				null, "personalData1", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.editTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	//Ancillary method

	protected void createAndSavePersonalDataTemplate(final String userName, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(userName);
			final PersonalData personalData = this.personalDataService.create();
			personalData.setName("name");
			personalData.setMiddleName("middleName");
			personalData.setLinkedinProfile("https://www.linkedin.com");
			personalData.setGithubProfile("https:www.github.com/user");
			personalData.setSurname("surname");
			personalData.setStatement("statement");
			personalData.setPhone("656565656");
			this.personalDataService.save(personalData);
			this.personalDataService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void editTemplate(final String userName, final String personalDataBeanName, final Class<?> expected) {
		Class<?> caught;
		int personalDataId;
		PersonalData personalData;

		caught = null;
		try {
			this.authenticate(userName);
			personalDataId = super.getEntityId(personalDataBeanName);
			personalData = this.personalDataService.findOne(personalDataId);
			personalData.setName("modified title");
			this.personalDataService.save(personalData);
			this.personalDataService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
