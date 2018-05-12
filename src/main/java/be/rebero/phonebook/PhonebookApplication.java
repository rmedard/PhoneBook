package be.rebero.phonebook;

import be.rebero.phonebook.models.PhoneEntry;
import be.rebero.phonebook.repositories.PhoneRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
public class PhonebookApplication {

	private PhoneRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(PhonebookApplication.class, args);
	}

	@RequestMapping("/")
	public ModelAndView index() {
		return new ModelAndView("redirect:/phones");
	}

	@Bean
	InitializingBean createInitData() {
		if (!repository.findAll().iterator().hasNext()) {

			PhoneEntry phoneEntry = new PhoneEntry();
			phoneEntry.setFirstName("Medard");
			phoneEntry.setLastName("Rebero");
			phoneEntry.setPhoneNumber("+39 02 1234567");

			return () -> repository.save(phoneEntry);
		}
		return null;
	}

	@Autowired
	public void setRepository(PhoneRepository repository) {
		this.repository = repository;
	}
}
