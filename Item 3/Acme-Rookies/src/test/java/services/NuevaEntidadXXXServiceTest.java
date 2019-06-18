
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Audit;
import domain.NuevaEntidadXXX;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class NuevaEntidadXXXServiceTest extends AbstractTest {

	//SUT
	@Autowired
	private NuevaEntidadXXXService	nuevaEntidadXXXService;

	@Autowired
	private AuditService			auditService;


	@Test
	public void createAndSaveDriver() {
		final Object testingData[][] = {
			{	// NuevaEntidadXXX correct creation
				"company1", "audit1", "sampleBody", "https://samplePicture.jpge", true, null
			}, {	// An user that is not authenticated cannot create a NuevaEntidadXXX
				null, "audit1", "sampleBody", "https://samplePicture.jpge", true, IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.createAndSaveTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (boolean) testingData[i][4], (Class<?>) testingData[i][5]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void deleteDriver() {
		final Object testingData[][] = {
			{	// NuevaEntidadXXX correct delete
				"company1", "nuevaEntidadXXX2", null
			}, { //A company cannot delete a NuevaEntidadXXX saved in final mode
				"company1", "nuevaEntidadXXX1", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void createAndSaveTemplate(final String userName, final String auditBeanName, final String body, final String picture, final boolean draftMode, final Class<?> expected) {
		Class<?> caught;
		Audit audit;
		NuevaEntidadXXX nuevaEntidadXXX;

		caught = null;
		try {
			this.authenticate(userName);

			final int auditId = super.getEntityId(auditBeanName);
			audit = this.auditService.findOne(auditId);
			nuevaEntidadXXX = this.nuevaEntidadXXXService.create();

			nuevaEntidadXXX.setAudit(audit);
			nuevaEntidadXXX.setBody(body);
			nuevaEntidadXXX.setPicture(picture);
			nuevaEntidadXXX.setDraftMode(draftMode);

			this.nuevaEntidadXXXService.save(nuevaEntidadXXX);
			this.nuevaEntidadXXXService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void deleteTemplate(final String userName, final String nuevaEntidadXXXBeanName, final Class<?> expected) {
		Class<?> caught;
		int nuevaEntidadXXXId;
		final NuevaEntidadXXX nuevaEntidadXXX;

		caught = null;
		try {
			this.authenticate(userName);
			nuevaEntidadXXXId = super.getEntityId(nuevaEntidadXXXBeanName);
			nuevaEntidadXXX = this.nuevaEntidadXXXService.findOne(nuevaEntidadXXXId);
			this.nuevaEntidadXXXService.delete(nuevaEntidadXXX);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
