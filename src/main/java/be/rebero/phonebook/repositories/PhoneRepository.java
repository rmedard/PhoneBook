package be.rebero.phonebook.repositories;

import be.rebero.phonebook.models.PhoneEntry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhoneRepository extends CrudRepository<PhoneEntry, Integer> {

	@Query(value = "SELECT e from PhoneEntry e where e.firstName like CONCAT('%',:token,'%') " +
			"or e.lastName like CONCAT('%',:token,'%') " +
			"or e.phoneNumber like CONCAT('%',:token,'%')")
	List<PhoneEntry> findMatchingPhoneEntries(@Param("token") String token);
}
