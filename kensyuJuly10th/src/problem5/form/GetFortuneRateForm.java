package problem5.form;

import java.sql.Date;

import org.apache.struts.action.ActionForm;

/**
 * 各運勢の割合を出すときのアクション・フォームBeanクラス
 *
 * @author k_oda
 *
 */
public class GetFortuneRateForm extends ActionForm{
	/**
	 * 当日の日付(これを元に半年前の日付をアクションクラスで算出)
	 */
	private Date today;

	public Date getToday() {
		return today;
	}
	public void setToday(Date today) {
		this.today = today;
	}

}
