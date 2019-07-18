package problem5.action;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import problem5.dao.ResultDao;
import problem5.form.GetFortuneRateForm;

/**
 * 過去半年と本日の占い結果の割合を取得するアクションクラス
 * 例：大吉：A%、中吉：B%のような感じ
 * @author k_oda
 *
 */
public class GetFortuneRateAction extends Action{
	/**
	 * 過去半年と本日の占い結果の割合を取得するメソッド
	 *
	 * @param mapping	struts-config.xmlのactionタグ内のforwardを指定するためのnameを指定した変数
	 * @param form		アクション・フォームBeanの値を格納した変数
	 * @param request	リクエスト情報
	 * @param response	レスポンス情報
	 * @return	mapping.findForward("rate")	struts-config.xmlの<action>タグ内のname属性が"rate"の<forward>に飛ぶ
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		/**
		 * HttpSession型の変数を作成
		 */
		HttpSession session = request.getSession();

		/**
		 * 今日の日付をformから取り出し、sql.Date型に変換
		 */
		GetFortuneRateForm fortuneRateForm = (GetFortuneRateForm) form;
		Date today = fortuneRateForm.getToday();

		/**
		 * 割合計算時の分母となる過去半年分の全結果と今日１日の全結果をdouble型で取得
		 */
		double resultHalfAYear = ResultDao.getResultCountForHalfAYear(today);
		double resultToday = ResultDao.getResultCountForToday(today);

		/**
		 * 半年分、当日分の各運勢の割合を格納するマップと運勢名を格納するリストを作成
		 */
		Map<String, Integer> rateHalfAYear = new LinkedHashMap<>();
		Map<String, Integer> rateToday = new LinkedHashMap<>();
		List<String> fortuneNameList = new ArrayList<>();


		/**
		 * 過去半年分の各運勢の件数を取得
		 * 過去半年分の各運勢の件数を過去半年分の全結果で割り、100倍した後にint型に変換(%で表示させるため)
		 * 求めた割合をリストに格納
		 */
		Map<String, Double> resultIdHalfAYear = ResultDao.getResultCountFindByFortuneIdForHalfAYear(today);
		for(String fortuneName: resultIdHalfAYear.keySet()) {
			fortuneNameList.add(fortuneName);
			double countFortuneNameHalfAYear = resultIdHalfAYear.get(fortuneName);
			int rateIdHalfAYear = (int)((countFortuneNameHalfAYear / resultHalfAYear) * 100);
			rateHalfAYear.put(fortuneName, rateIdHalfAYear);
		}
		/**
		 * 当日分の各運勢の件数を取得
		 * 当日分の各運勢の件数を当日分の全結果で割り、100倍した後にint型に変換(%で表示させるため)
		 * 求めた割合をリストに格納
		 */
		Map<String, Double> resultIdToday = ResultDao.getResultCountFindByFortuneIdForToday(today);
		for(String fortuneName: resultIdToday.keySet()) {
			double countFortuneNameToday = resultIdToday.get(fortuneName);
			int rateIdToday = (int)((countFortuneNameToday / resultToday) * 100);
			rateToday.put(fortuneName, rateIdToday);
		}


		/**
		 * 各割合のリストと運勢名のリスト、JSP用ループカウンタをそれぞれセッションに登録
		 */
		session.setAttribute("rateHalfAYearMap", rateHalfAYear);
		session.setAttribute("rateTodayMap", rateToday);
		session.setAttribute("fortuneName", fortuneNameList);

		/**
		 * rate.jspに遷移するようにフォワードのnameを指定
		 */
		return (mapping.findForward("rate"));
	}
}
