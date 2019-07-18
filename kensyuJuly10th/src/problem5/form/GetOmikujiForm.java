package problem5.form;

import org.apache.struts.validator.ValidatorForm;

/**
 * おみくじの結果を表示させるときのアクション・フォームBeanクラス
 * 入力チェックも同時に行う
 * @author k_oda
 *
 */
public class GetOmikujiForm extends ValidatorForm {

	/**
	 * 入力された誕生日
	 */
	private String birthday;

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
}
