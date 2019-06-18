
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Position;
import domain.PositionProblem;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PositionProblemServiceTest extends AbstractTest {

	//Sentence coverage of 4.1%
	//1368 sentences

	//SUT
	@Autowired
	private PositionProblemService	positionProblemService;

	@Autowired
	private PositionService			positionService;


	@Test
	public void createAndSaveDriver() {
		final Object testingData[][] = {
			{	// Problem correct creation
				"company1", "position2", "sampleTitle", "sampleStatement", "sampleHint", "https://sampleAttachment.com", true, null
			}, {	// A user that is not a company cannot create a problem
				"rookie1", "position2", "sampleTitle", "sampleStatement", "sampleHint", "https://sampleAttachment.com", true, IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.createAndSaveTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (boolean) testingData[i][6],
					(Class<?>) testingData[i][7]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void deleteDriver() {
		final Object testingData[][] = {
			{	// Problem correct delete
				"company1", "problem4", null
			}, { //A company cannot delete a problem from another company
				"company1", "problem1", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------
	protected void createAndSaveTemplate(final String userName, final String positionBeanName, final String title, final String statement, final String hint, final String attachment, final boolean draftMode, final Class<?> expected) {
		Class<?> caught;
		Position position;
		PositionProblem problem;

		caught = null;
		try {
			this.authenticate(userName);

			final int positionId = super.getEntityId(positionBeanName);
			position = this.positionService.findOne(positionId);
			problem = this.positionProblemService.create();

			final Collection<String> attachments = new ArrayList<>();
			attachments.add(attachment);

			problem.setPosition(position);
			problem.setTitle(title);
			problem.setStatement(statement);
			problem.setHint(hint);
			problem.setAttachments(attachments);
			problem.setDraftMode(draftMode);

			this.positionProblemService.save(problem);
			this.positionProblemService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	protected void deleteTemplate(final String userName, final String positionProblemBeanName, final Class<?> expected) {
		Class<?> caught;
		int positionProblemId;
		final PositionProblem positionProblem;

		caught = null;
		try {
			this.authenticate(userName);
			positionProblemId = super.getEntityId(positionProblemBeanName);
			positionProblem = this.positionProblemService.findOne(positionProblemId);
			this.positionProblemService.delete(positionProblem);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
