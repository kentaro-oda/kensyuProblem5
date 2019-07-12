package problem5.action;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import problem5.dao.FortuneDao;
import problem5.dao.ResultDao;

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
	 * @param mapping	struts-config.xmlの<action>タグ内の<forward>を指定するためのnameを指定した変数
	 * @param form		アクション・フォームBeanの値を格納した変数
	 * @param request	リクエスト情報
	 * @param response	レスポンス情報
	 * @return	mapping.findForward("rate")	struts-config.xmlの<action>タグ内のname属性が"rate"の<forward>に飛ぶ
	 */
	public ActionForward execute(ActionMapping mapping, ValidatorForm form, HttpServletRequest request, HttpServletResponse response) {

		/**
		 * HttpSession型の変数を作成
		 */
		HttpSession session = request.getSession();

		/**
		 * 今日の日付をsessionから取り出し、sql.Date型に変換
		 */
		Date today = (Date) session.getAttribute("today");

		/**
		 * 割合計算時の分母となる過去半年分の全結果と今日１日の全結果をdouble型で取得
		 */
		double resultHalfAYear = ResultDao.getResultCountForHalfAYear(today);
		double resultToday = ResultDao.getResultCountForToday(today);

		/**
		 * 半年分、当日分の各運勢の割合を格納するリストを作成
		 */
		List<Integer> rateHalfAYear = new ArrayList<>();
		List<Integer> rateToday = new ArrayList<>();

		/**
		 * for分用のループカウンタの作成(運勢テーブルの行数を取得)
		 */
		int loopCounter = FortuneDao.getFortuneCount();

		/**
		 * 各運勢ごとに半年分、当日分の件数をだし割合を求めリストに格納するfor文
		 */
		for(int i = 1; i <= loopCounter; i++) {

			/**
			 * 過去半年分の各運勢の件数を取得
			 * 過去半年分の各運勢の件数を過去半年分の全結果で割り、100倍した後にint型に変換(%で表示させるため)
			 * 求めた割合をリストに格納
			 */
			double resultIdHalfAYear = ResultDao.getResultCountFindByFortuneIdForHalfAYear(i, today);
			int rateIdHalfAYear = (int)((resultIdHalfAYear / resultHalfAYear) * 100);
			rateHalfAYear.add(rateIdHalfAYear);

			/**
			 * 当日分の各運勢の件数を取得
			 * 当日分の各運勢の件数を当日分の全結果で割り、100倍した後にint型に変換(%で表示させるため)
			 * 求めた割合をリストに格納
			 */
			double resultIdToday = ResultDao.getResultCountFindByFortuneIdForToday(i, today);
			int rateIdToday = (int)((resultIdToday / resultToday) * 100);
			rateToday.add(rateIdToday);
		}


		List<String> fortuneNameList = FortuneDao.getAllFortuneNameFindByFortuneId();
		/**
		 * 各割合のリストと運勢名のリスト、JSP用ループカウンタをそれぞれセッションに登録
		 */
		session.setAttribute("rateHalfAYear", rateHalfAYear);
		session.setAttribute("rateToday", rateToday);
		session.setAttribute("fortuneName", fortuneNameList);
		session.setAttribute("loopCounter", loopCounter - 1);

		/**
		 * rate.jspに遷移するようにフォワードのnameを指定
		 */
		return (mapping.findForward("rate"));
	}
}
