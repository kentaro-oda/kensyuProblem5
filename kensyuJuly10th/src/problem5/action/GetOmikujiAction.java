package problem5.action;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import problem5.GetOmikujiForm;
import problem5.dao.OmikujiDao;
import problem5.dao.ResultDao;
/**
 * おみくじの結果を取得するアクションクラス
 * @author k_oda
 *
 */
public class GetOmikujiAction extends Action {
	/**
	 * おみくじの結果を取得するメソッド
	 *
	 * @param mapping	struts-config.xmlの<action>タグ内の<forward>を指定するためのnameを指定した変数
	 * @param form		アクション・フォームBeanの値を格納した変数
	 * @param request	リクエスト情報
	 * @param response	レスポンス情報
	 * @return	mapping.findForward("omikuji")	struts-config.xmlの<action>タグ内のname属性が"omikuji"の<forward>に飛ぶ
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		/**
		 * HttpSession型の変数を作成
		 */
		HttpSession session = request.getSession();

		/**
		 * OmikujiFormで入力チェックを通った誕生日をformから取り出し、sql.Date型に変換
		 */
		GetOmikujiForm omikujiForm = (GetOmikujiForm) form;
		String birthday = omikujiForm.getBirthday();
		Date sqlBirthday = Date.valueOf(birthday);

		/**
		 * 当日の情報をutil.Date型で取得し、sql.Date型に変換
		 */
		java.util.Date now = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		String now2 = sdf.format(now);
		Date today = Date.valueOf(now2);

		/**
		 * 結果テーブルから過去に同日・同誕生日でおみくじを引いていないかを確認
		 * 引いていた場合にはそのおみくじコードを取得し、なかった場合はnullを取得
		 */
		Integer omikujiId = ResultDao.findByFortuneDayAndBirthday(today, sqlBirthday);

		/**
		 * 過去に同日・同誕生日でおみくじを引いていなかった場合、
		 * おみくじテーブルの行数から乱数を生成しおみくじコードを取得
		 */
		if(omikujiId == null) {
			Random r = new Random();
			int n = OmikujiDao.getOmikujiCount();
			omikujiId = r.nextInt(n);
		}

		/**
		 * おみくじコードを元におみくじテーブルからおみくじの結果を取得
		 */
		String[] omikuji = OmikujiDao.findByOmikujiId(omikujiId);

		/**
		 * 過去に同日・同誕生日でおみくじを引いていなかった場合、
		 * 結果テーブルに今回の結果を登録
		 */
		if(ResultDao.findByFortuneDayAndBirthday(today, sqlBirthday) == null) {
			ResultDao.insertResult(today, sqlBirthday, omikujiId);
		}

		/**
		 * セッションにおみくじの結果、今日の日付、入力した誕生日(変換後)をそれぞれ登録
		 */
		session.setAttribute("omikuji", omikuji);
		session.setAttribute("today", today);
		session.setAttribute("sqlBirthday", sqlBirthday);
		session.setAttribute("birthday", birthday);

		/**
		 * omikuji.jspに遷移するようにフォワードのnameを指定
		 */
		return mapping.findForward("omikuji");
	}
}
