package problem5.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * おみくじテーブル関連のDB処理を行うメソッドを集めたDAOクラス
 * @author k_oda
 *
 */
public class OmikujiDao extends DBFields{
	/**
	 * おみくじテーブルの行数を取得するメソッド
	 * @return	rset.getInt("count")：おみくじテーブルの行数/0:エラー発生時
	 */
	public static int getOmikujiCount(){
		try {
			/**
			 * DBと接続
			 */
			DBRelation.getConnection();

			/**
			 * SQL文の実行
			 */
			String sql = "SELECT COUNT(*) FROM omikuji";
			ps = conn.prepareStatement(sql);
			rset = ps.executeQuery();
			rset.next();
			return rset.getInt("count");
		}
		catch(SQLException e) {
			return 0;
		}

		/**
		 * DBとの接続を切断
		 */
		finally {
			DBRelation.closeConnection();
		}
	}

	/**
	 * おみくじコードから運勢名、願い事、商い、学問を取得するメソッド
	 * @param omikujiId	WHERE句の条件として使うおみくじコード
	 * @return	omikuji(上記の4つの項目が格納されたおみくじ結果)/null(エラー発生時)
	 */
	public static String[] findByOmikujiId(int omikujiId){
		try {
			/**
			 * DBと接続
			 */
			DBRelation.getConnection();

			/**
			 * SQL文の実行
			 */
			String sql = "SELECT f.fortune_name, o.negaigoto, o.akinai, o.gakumon FROM fortune f INNER JOIN omikuji o "
					+ "ON f.fortune_id = o.fortune_id WHERE omikuji_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, omikujiId);
			rset = ps.executeQuery();
			rset.next();

			String[] omikuji = new String[4];
			omikuji[0] = rset.getString("fortune_name");
			omikuji[1] = rset.getString("negaigoto");
			omikuji[2] = rset.getString("akinai");
			omikuji[3] = rset.getString("gakumon");
			return omikuji;
		}

		/**
		 * エラー発生時にnullを返す
		 */
		catch(Exception e) {
			return null;
		}

		/**
		 * DBとの接続を切断
		 */
		finally {
			DBRelation.closeConnection();
		}
	}

	/**
	 * 過去半年間の入力誕生日の結果を取得するリスト
	 *
	 * @param birthday	誕生日
	 * @param today		今日の日付
	 * @return	omikujiList	 該当する結果を全て格納したリスト/ null	エラー発生時
	 */
	public static Map<Date, String[]> getOmikujiFindByBirthDay(Date birthday, Date today){
		try {
			/**
			 * DBと接続
			 */
			DBRelation.getConnection();

			/**
			 * おみくじの結果を占い日と紐づけて格納するためのマップを作成
			 */
			Map<Date, String[]> omikujiMap = new LinkedHashMap<>();

			/**
			 * 半年前の日付を取得
			 */
			Date halfAYearAgo = ResultDao.getHalfAYearAgo(today);

			/**
			 * SQL文の実行
			 */
			String sql = "SELECT r.fortune_day, f.fortune_name, o.negaigoto, o.akinai, o.gakumon FROM fortune f INNER JOIN omikuji o "
					+ "ON f.fortune_id = o.fortune_id INNER JOIN result r ON o.omikuji_id = r.omikuji_id"
					+ " WHERE birthday = ? AND fortune_day >= ? AND fortune_day <= ? ORDER BY fortune_day DESC";
			ps = conn.prepareStatement(sql);
			ps.setDate(1, birthday);
			ps.setDate(2, halfAYearAgo);
			ps.setDate(3, today);
			rset = ps.executeQuery();

			/**
			 * 該当の結果を全てマップに登録
			 */
			while(rset.next()) {

			String[] omikuji = new String[4];
			omikuji[0] = rset.getString("fortune_name");
			omikuji[1] = rset.getString("negaigoto");
			omikuji[2] = rset.getString("akinai");
			omikuji[3] = rset.getString("gakumon");

			omikujiMap.put(rset.getDate("fortune_day"), omikuji);

			}

			/**
			 * 全結果を格納したマップを返却
			 */
			return omikujiMap;
		}

		/**
		 * エラー発生時にnullを返す
		 */
		catch(Exception e) {
			return null;
		}

		/**
		 * DBとの接続を切断
		 */
		finally {
			DBRelation.closeConnection();
		}
	}


}
