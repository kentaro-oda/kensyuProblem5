package problem5.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
/**
 * rate.jspとlist.jspからomikuji.jspに戻るときのアクションクラス
 * @author k_oda
 *
 */
public class BackOmikujiAction extends Action {
	/**
	 * rate.jspとlist.jspからomikuji.jspに戻るメソッド
	 *
	 * @param mapping	struts-config.xmlの<action>タグ内の<forward>を指定するためのnameを指定した変数
	 * @param form		アクション・フォームBeanの値を格納した変数
	 * @param request	リクエスト情報
	 * @param response	レスポンス情報
	 * @return	mapping.findForward("omikuji")	struts-config.xmlの<action>タグ内のname属性が"omikuji"の<forward>に飛ぶ
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		/**
		 * omikuji.jspに遷移するようにフォワードのnameを指定
		 */
		return mapping.findForward("omikuji");
	}

}
