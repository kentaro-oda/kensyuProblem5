package problem5;

import org.apache.struts.validator.ValidatorForm;

public class BirthdayValidatorForm extends ValidatorForm {

	private String birthday;

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
}
