package be.rebero.phonebook.controllers;

import be.rebero.phonebook.models.PhoneEntry;
import be.rebero.phonebook.repositories.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "phones")
public class PhoneController {

	private PhoneRepository repository;

	@GetMapping
	public ModelAndView getAll(){
		ModelAndView modelAndView = new ModelAndView();
		List<PhoneEntry> phoneEntries = new ArrayList<>();
		for (PhoneEntry phoneEntry : repository.findAll()) {
			phoneEntries.add(phoneEntry);
		}
		modelAndView.setViewName("index");
		modelAndView.addObject("phones", phoneEntries);
		return modelAndView;
	}

	@GetMapping(value = "create-update")
	public ModelAndView addNew(@RequestParam(value = "id", required = false) String id){
		ModelAndView modelAndView = new ModelAndView();
		if (StringUtils.hasText(id)){
			modelAndView.addObject("phoneEntry", repository.findById(Integer.parseInt(id)));
		} else {
			modelAndView.addObject("phoneEntry", new PhoneEntry());
		}
		modelAndView.setViewName("createOrUpdate");
		return modelAndView;
	}

	@PostMapping
	public ModelAndView saveEntry(@ModelAttribute PhoneEntry phoneEntry, RedirectAttributes redirectAttributes) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<PhoneEntry>> errors = validator.validate(phoneEntry);
		if (errors.size() > 0) {
			redirectAttributes.addFlashAttribute("errorMessage", "Invalid phone entry!!");
		} else {
			if (repository.existsById(phoneEntry.getId())) {
				redirectAttributes.addFlashAttribute("successMessage", "Phone entry updated successfully");
			} else {
				redirectAttributes.addFlashAttribute("successMessage", "New phone entry created successfully");
			}
			repository.save(phoneEntry);
		}
		return new ModelAndView("redirect:/phones");
	}

	@PostMapping(value = "search")
	public ResponseEntity<?> search(@RequestBody(required = false) String token, Errors errors) {
		if (!StringUtils.hasText(token)){
			return ResponseEntity.ok(repository.findAll());
		}

		if (errors.hasErrors()) {
			return ResponseEntity.badRequest().body("Bad request");
		}
		List<PhoneEntry> phoneEntries = repository.findMatchingPhoneEntries(token);
		return ResponseEntity.ok(phoneEntries);
	}

	@Autowired
	public void setRepository(PhoneRepository repository) {
		this.repository = repository;
	}

}
