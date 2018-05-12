package be.rebero.phonebook;

import be.rebero.phonebook.models.PhoneEntry;
import be.rebero.phonebook.repositories.PhoneRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class PhonebookApplicationTests {

	@Autowired
	private PhoneRepository phoneRepository;

	@Before
	public void init() {
		PhoneEntry phoneEntry = new PhoneEntry();
		phoneEntry.setLastName("LastNameTest");
		phoneEntry.setFirstName("FirstNameTest");
		phoneEntry.setPhoneNumber("+39 02 1234567");
		phoneRepository.save(phoneEntry);
	}

	@Test
	public void shouldCountAtLeastOneEntry(){
		List<PhoneEntry> phoneEntries = new ArrayList<>();
		for (PhoneEntry phoneEntry : phoneRepository.findAll()) {
			phoneEntries.add(phoneEntry);
		}
		Assert.assertTrue(phoneEntries.size() >= 1);
	}

	@Test
	public void shouldFindOneEntry(){
		List<PhoneEntry> phoneEntries = phoneRepository.findMatchingPhoneEntries("meT");
		Assert.assertEquals(1, phoneEntries.size());
	}

	@Test
	public void shouldBeInvalidPhoneNumber() {
		PhoneEntry phoneEntry = new PhoneEntry();
		phoneEntry.setFirstName("Test1");
		phoneEntry.setLastName("Test2");
		phoneEntry.setPhoneNumber("+39 02 1234");

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<PhoneEntry>> errors = validator.validate(phoneEntry);

		Assert.assertTrue(errors.size() > 0);

		phoneEntry.setPhoneNumber("+39 021234567");
		errors = validator.validate(phoneEntry);
		Assert.assertTrue(errors.size() > 0);
	}

	@Test
	public void shouldBeValidPhoneNumber() {
		PhoneEntry phoneEntry = new PhoneEntry();
		phoneEntry.setFirstName("Test1");
		phoneEntry.setLastName("Test2");
		phoneEntry.setPhoneNumber("+39 02 1234567");

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<PhoneEntry>> errors = validator.validate(phoneEntry);

		Assert.assertTrue(errors.isEmpty());
	}
}
