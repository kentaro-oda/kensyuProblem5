package problem5.action;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import problem5.dao.OmikujiDao;
import problem5.form.GetResultListForm;

/**
 * 過去半年間の入力誕生日と同日の結果を全て取得するアクションクラス
 * @author k_oda
 *
 */
public class GetResultListAction extends Action {

	/**
	 * 過去半年間の入力誕生日と同日の結果を全て取得するメソッド
	 *
	 * @param mapping	struts-config.xmlのactionタグ内のforwardを指定するためのnameを指定した変数
	 * @param form		アクション・フォームBeanの値を格納した変数
	 * @param request	リクエスト情報
	 * @param response	レスポンス情報
	 * @return	mapping.findForward("list")	struts-config.xmlの<action>タグ内のname属性が"list"の<forward>に飛ぶ
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		/**
		 * HttpSession型の変数を作成
		 */
		HttpSession session = request.getSession();

		/**
		 * 今日の日付と入力した誕生日をformから取り出し、sql.Date型に変換
		 */
		GetResultListForm resultListForm = (GetResultListForm) form;
		Date today = resultListForm.getToday();
		Date birthday = resultListForm.getSqlBirthday();


		/**
		 * 入力された誕生日と同じ日付で過去半年間に引いたおみくじの結果とその日付を取得
		 */
		Map<Date, String[]> omikujiMap = OmikujiDao.getOmikujiFindByBirthDay(birthday, today);

		/**
		 * JSPで日付を表示させるために上記で取得したMapに登録されている全てのキー(占い日)をリストに格納
		 */
		List<Date> fortuneDayList = new ArrayList<>();
		for(Date fortuneDay : omikujiMap.keySet()) {
			fortuneDayList.add(fortuneDay);
		}

		/**
		 * 占い日のリスト、半年分の同日おみくじのマップ、JSP用ループカウンタをそれぞれセッションに登録
		 */
		session.setAttribute("fortuneDay", fortuneDayList);
		session.setAttribute("omikujiMap", omikujiMap);
		session.setAttribute("loopCounter", omikujiMap.size() - 1);

		/**
		 * list.jspに遷移するようにフォワードのnameを指定
		 */
		return (mapping.findForward("list"));
	}
}
