
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Audit;
import domain.Position;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AuditServiceTest extends AbstractTest {

	//Sentence coverage of 4.0%
	// 1339 instructions

	//SUT
	@Autowired
	private AuditService	auditService;

	@Autowired
	private PositionService	positionService;


	@Test
	public void createAndSaveDriver() {
		final Object testingData[][] = {
			{	// Audit correct creation
				"auditor1", "position1", "sampleDescription", 7, true, null
			}, {	// A user that is not an auditor cannot create an audit
				"company1", "position1", "sampleDescription", 7, true, IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.createAndSaveTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Integer) testingData[i][3], (boolean) testingData[i][4], (Class<?>) testingData[i][5]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void deleteDriver() {
		final Object testingData[][] = {
			{	// Audit correct delete
				"auditor1", "audit2", null
			}, { //An auditor cannot delete an audit saved in final mode
				"auditor1", "audit1", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------
	protected void createAndSaveTemplate(final String userName, final String positionBeanName, final String description, final Integer score, final boolean draftMode, final Class<?> expected) {
		Class<?> caught;
		Position position;
		Audit audit;

		caught = null;
		try {
			this.authenticate(userName);

			final int positionId = super.getEntityId(positionBeanName);
			position = this.positionService.findOne(positionId);
			audit = this.auditService.create();

			audit.setPosition(position);
			audit.setDescription(description);
			audit.setScore(score);
			audit.setDraftMode(draftMode);

			this.auditService.save(audit);
			this.auditService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	protected void deleteTemplate(final String userName, final String auditBeanName, final Class<?> expected) {
		Class<?> caught;
		int auditId;
		final Audit audit;

		caught = null;
		try {
			this.authenticate(userName);
			auditId = super.getEntityId(auditBeanName);
			audit = this.auditService.findOne(auditId);
			this.auditService.delete(audit);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
