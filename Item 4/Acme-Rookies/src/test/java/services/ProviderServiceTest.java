
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Provider;
import forms.RegisterProviderForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProviderServiceTest extends AbstractTest {

	//Sentence coverage of 10.2%
	//3403 instructions

	//SUT
	@Autowired
	private ProviderService	providerService;


	@Test
	public void createAndSaveDriver() {
		final Object testingData[][] = {
			{	//Normal creation of actor
				"password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 3, 22, 333, "EA", null
			}, {//Passwords not matching
				"password", "notSamePassWord", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 3, 22, 333, "EA", IllegalArgumentException.class
			}, {//IncorrectEmail
				"password", "password", "usename", "name", "surname", "https://photo.com", "", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 3, 22, 333, "EA", ConstraintViolationException.class
			}, {//Incorrect name
				"password", "password", "usename", "", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 3, 22, 333, "EA", ConstraintViolationException.class
			}, {//Incorrect surname
				"password", "password", "usename", "name", "", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 3, 22, 333, "EA", ConstraintViolationException.class
			}, {//Incorrect photo
				"password", "password", "usename", "name", "surname", "thisIsNotAURL", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 3, 22, 333, "EA", ConstraintViolationException.class
			}, {//Incorrect holder
				"password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "", "make", "375855639752272", 3, 22, 333, "EA", ConstraintViolationException.class
			}, {//Incorrect make
				"password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "", "375855639752272", 3, 22, 333, "EA", ConstraintViolationException.class
			}, {//Incorrect number
				"password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "", 3, 22, 333, "EA", ConstraintViolationException.class
			}, {//Incorrect number
				"password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "87398469", 3, 22, 333, "EA", ConstraintViolationException.class
			}, {//ExpirationMonth 0
				"password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 0, 22, 333, "EA", ConstraintViolationException.class
			}, {//ExpirationMonth 13
				"password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 13, 22, 333, "EA", ConstraintViolationException.class
			}, {//ExpirationMonth 1
				"password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 1, 22, 333, "EA", null
			}, {//ExpirationMonth 12
				"password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 12, 22, 333, "EA", null
			}, {//ExpirationYear 0
				"password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 3, 0, 333, "EA", null
			}, {//ExpirationYear 99
				"password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 3, 99, 333, "EA", null
			}, {//ExpirationYear 100
				"password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 3, 100, 333, "EA", ConstraintViolationException.class
			}, {//CVV 99
				"password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 3, 22, 99, "EA", ConstraintViolationException.class
			}, {//CVV 100
				"password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 3, 22, 100, "EA", null
			}, {//CVV 999
				"password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 3, 22, 999, "EA", null
			}, {//CVV 1000
				"password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 3, 22, 1000, "EA", ConstraintViolationException.class
			}, {//Incorrect providerMake
				"password", "password", "usename", "name", "surname", "https://photo.com", "email@acme.com", "address", "+34 344654843", 2, "holder", "make", "375855639752272", 3, 22, 333, "", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.createAndSaveTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
					(String) testingData[i][7], (String) testingData[i][8], (int) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (int) testingData[i][13], (int) testingData[i][14],
					(int) testingData[i][15], (String) testingData[i][16], (Class<?>) testingData[i][17]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void EditAndSaveDriver() {
		final Object testingData[][] = {
			{	//Normal creation of actor
				"newName", "newSurname", "https://newPhoto.com", "newEmail@acme.com", "newAddress", "+34 555555555", 4, "newProviderMake", null
			}, { //Incorrect Name
				"", "newSurname", "https://newPhoto.com", "newEmail@acme.com", "newAddress", "+34 555555555", 4, "newProviderMake", ConstraintViolationException.class
			}, { //Incorrect Surname
				"newName", "", "https://newPhoto.com", "newEmail@acme.com", "newAddress", "+34 555555555", 4, "newProviderMake", ConstraintViolationException.class
			}, { //Incorrect Photo
				"newName", "newSurname", "thisIsNotAURL", "newEmail@acme.com", "newAddress", "+34 555555555", 4, "newProviderMake", ConstraintViolationException.class
			}, { //Incorrect email
				"newName", "newSurname", "https://newPhoto.com", "", "newAddress", "+34 555555555", 4, "newProviderMake", ConstraintViolationException.class
			}, { //Incorrect ComercialName
				"newName", "newSurname", "https://newPhoto.com", "newEmail@acme.com", "newAddress", "+34 555555555", 4, "", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.EditAndSaveTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (int) testingData[i][6],
					(String) testingData[i][7], (Class<?>) testingData[i][8]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------
	protected void createAndSaveTemplate(final String password, final String password2, final String userName, final String name, final String surname, final String photo, final String email, final String address, final String phone, final int vat,
		final String holder, final String make, final String number, final int expirationMonth, final int expirationYear, final int cvv, final String providermake, final Class<?> expected) {
		Class<?> caught;
		final RegisterProviderForm registerProviderForm = new RegisterProviderForm();

		caught = null;
		try {
			registerProviderForm.setPassword(password);
			registerProviderForm.setPassword2(password2);
			registerProviderForm.setUsername(userName);
			registerProviderForm.setName(name);
			registerProviderForm.setSurname(surname);
			registerProviderForm.setPhone(phone);
			registerProviderForm.setPhoto(photo);
			registerProviderForm.setAddress(address);
			registerProviderForm.setEmail(email);
			registerProviderForm.setVAT(vat);
			registerProviderForm.setHolder(holder);
			registerProviderForm.setMake(make);
			registerProviderForm.setNumber(number);
			registerProviderForm.setExpirationMonth(expirationMonth);
			registerProviderForm.setExpirationYear(expirationYear);
			registerProviderForm.setCvv(cvv);
			registerProviderForm.setProviderMake(providermake);
			this.providerService.registerProvider(registerProviderForm);
			this.providerService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void EditAndSaveTemplate(final String name, final String surname, final String photo, final String email, final String address, final String phone, final int vat, final String providerMake, final Class<?> expected) {
		Class<?> caught;
		Provider provider;
		int providerId;
		caught = null;
		try {
			providerId = this.getEntityId("provider1");
			provider = this.providerService.findOne(providerId);
			provider.setName(name);
			provider.setSurname(surname);
			provider.setPhone(phone);
			provider.setPhoto(photo);
			provider.setAddress(address);
			provider.setEmail(email);
			provider.setVat(vat);
			provider.setProviderMake(providerMake);
			this.providerService.save(provider);
			this.providerService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
