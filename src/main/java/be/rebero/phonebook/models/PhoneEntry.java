package be.rebero.phonebook.models;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "phoneEntries")
public class PhoneEntry {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String firstName;

	private String lastName;

	@Pattern(regexp = "^\\+[0-9]+\\s[0-9]+\\s[0-9]{6,}$")
	private String phoneNumber;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
