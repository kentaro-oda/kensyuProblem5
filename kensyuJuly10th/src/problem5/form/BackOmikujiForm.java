package problem5.form;

import org.apache.struts.validator.ValidatorForm;

/**
 * rate.jspとlist.jspからomikuji.jspに戻るときのアクション・フォームBeanクラス
 * @author k_oda
 *
 */
public class BackOmikujiForm extends ValidatorForm {

	/**
	 * おみくじ結果表示画面で表示される要素を含んだ配列
	 */
	private String[] omikuji;

	public String[] getOmikuji() {
		return omikuji;
	}

	public void setOmikuji(String[] omikuji) {
		this.omikuji = omikuji;
	}
}
