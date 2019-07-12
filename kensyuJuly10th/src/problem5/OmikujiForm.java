package problem5;

import java.sql.Date;

import org.apache.struts.action.ActionForm;

public class OmikujiForm extends ActionForm{
	private Date today;
	private Integer omikujiId;
	private Date sqlBirthday;


	public Date getToday() {
		return today;
	}
	public void setToday(Date today) {
		this.today = today;
	}


	public Integer getOmikujiId() {
		return omikujiId;
	}
	public void setOmikujiId(Integer omikujiId) {
		this.omikujiId = omikujiId;
	}


	public Date getSqlBirthday() {
		return sqlBirthday;
	}
	public void setSqlBirthday(Date sqlBirthday) {
		this.sqlBirthday = sqlBirthday;
	}
}
