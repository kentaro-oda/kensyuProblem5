package problem5;

import java.sql.Date;

import org.apache.struts.action.ActionForm;

/**
 * 過去半年間の同誕生日の結果を表示させるときのアクション・フォームBeanクラス
 * @author k_oda
 *
 */
public class GetResultListForm extends ActionForm{
	/**
	 * today		今日の日付(これを元に半年前の日付を算出)
	 * sqlBirthday	sql.Date型に変換された誕生日
	 */
	private Date today;
	private Date sqlBirthday;


	public Date getToday() {
		return today;
	}
	public void setToday(Date today) {
		this.today = today;
	}


	public Date getSqlBirthday() {
		return sqlBirthday;
	}
	public void setSqlBirthday(Date sqlBirthday) {
		this.sqlBirthday = sqlBirthday;
	}
}
