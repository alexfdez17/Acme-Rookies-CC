
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Application;
import domain.PositionProblem;
import domain.Rookie;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	//SUT
	@Autowired
	private ApplicationService		applicationService;
	@Autowired
	private PositionProblemService	positionProblemService;
	@Autowired
	private RookieService			rookieService;


	//Sentence coverage of 3.7% -> 1236 instructions

	@Test
	public void createAndSaveDriver() {
		final Object testingData[][] = {
			{	//Normal creation of application
				new Date(), "Awnser", "https://www.github.com", new Date(), "PENDING", "problem1", "rookie1", null
			}, {//Normal creation of application
				new Date(), "", "", new Date(), "PENDING", "problem1", "rookie1", null
			}, {//Normal creation of application
				new Date(), null, null, null, "PENDING", "problem1", "rookie1", null
			}, {//Only a rookie or comapny can do applications
				new Date(), null, null, null, "PENDING", "problem1", "company1", IllegalArgumentException.class
			}, {//Only a rookie or comapny can do applications
				new Date(), null, null, null, "PENDING", "problem1", "admin", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.createAndSaveTemplate((Date) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
					(Class<?>) testingData[i][7]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------
	protected void createAndSaveTemplate(final Date creationMoment, final String answerExplanation, final String answerCode, final Date submittedMoment, final String status, final String poblemName, final String rookieName, final Class<?> expected) {
		Class<?> caught;
		final Application application = new Application();
		final int positionPoblemId;
		final int rookieId;
		PositionProblem positionProblem;
		Rookie rookie;

		caught = null;
		try {

			rookieId = super.getEntityId(rookieName);
			rookie = this.rookieService.findOne(rookieId);
			positionPoblemId = super.getEntityId(poblemName);
			positionProblem = this.positionProblemService.findOne(positionPoblemId);

			application.setCreationMoment(creationMoment);
			application.setAnswerExplanation(answerExplanation);
			application.setAnswerCode(answerCode);
			application.setSubmittedMoment(submittedMoment);
			application.setStatus(status);
			application.setPositionProblem(positionProblem);
			application.setRookie(rookie);
			this.applicationService.save(application);
			this.applicationService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
