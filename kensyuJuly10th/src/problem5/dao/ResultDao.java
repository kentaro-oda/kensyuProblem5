package problem5.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * 結果テーブル関連のDB処理を行うメソッドを集めたDAOクラス
 * @author k_oda
 *
 */
public class ResultDao extends DBFields{

	/**
	 * 占い日と誕生日からおみくじコードを取得するメソッド
	 *
	 * @param today		占い日
	 * @param birthday	入力された誕生日
	 * @return	rset.getInt("omikuji_id")(合致するものがあった場合)/null(合致するものがなかった場合)
	 */
	public static Integer findByFortuneDayAndBirthday(Date today, Date birthday) {
		try {
			/**
			 * DBと接続
			 */
			DBRelation.getConnection();

			/**
			 * SQL文の実行
			 */
			String sql = "SELECT omikuji_id FROM result WHERE fortune_day = ? AND birthday = ?";
			ps = conn.prepareStatement(sql);
			ps.setDate(1, today);
			ps.setDate(2, birthday);
			rset = ps.executeQuery();

			if(rset.next()) {
				return rset.getInt("omikuji_id");
			} else {
				return null;
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
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
	 * 結果テーブルに情報を登録するメソッド
	 *
	 * @param today		占い日
	 * @param birthday	誕生日
	 * @param omikujiId	おみくじコード
	 */
	public static void insertResult(Date today, Date birthday, int omikujiId){
		try {
			/**
			 * DBと接続
			 */
			DBRelation.getConnection();

			/**
			 * SQL文の実行
			 */
			String sql = "INSERT INTO result(fortune_day, birthday, omikuji_id, creater, create_day) VALUES (?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setDate(1, today);
			ps.setDate(2, birthday);
			ps.setInt(3, omikujiId);
			ps.setString(4, "小田健太郎");
			ps.setDate(5, today);
			ps.executeUpdate();
			conn.commit();
		}

		catch(SQLException e) {
			e.printStackTrace();
		}

		/**
		 * DBとの接続を切断
		 */
		finally {
			DBRelation.closeConnection();
		}
	}

	/**
	 * 半年前の日付を取得するメソッド
	 * @param today
	 * @return
	 */
	protected static Date getHalfAYearAgo(Date today) {
		LocalDate ldToday = today.toLocalDate();
		LocalDate halfAYearAgo = ldToday.minusMonths(6);

		return Date.valueOf(halfAYearAgo);
	}

	/**
	 * 過去半年分の全結果数を返すメソッド
	 * @param today	今日の日付
	 * @return	rset.getInt("count")	過去半年分のおみくじの結果数/0	エラー発生時
	 */
	public static double getResultCountForHalfAYear(Date today) {
		try {
			/**
			 * DBと接続
			 */
			DBRelation.getConnection();

			/**
			 * 半年前の日付を取得
			 */
			Date halfAYearAgo = getHalfAYearAgo(today);

			/**
			 * SQL文の実行
			 */
			String sql = "SELECT COUNT(*) FROM result WHERE fortune_day >= ? AND fortune_day <= ?";
			ps = conn.prepareStatement(sql);
			ps.setDate(1, halfAYearAgo);
			ps.setDate(2, today);
			rset = ps.executeQuery();
			rset.next();
			return rset.getDouble("count");

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
	 * 過去半年分の各運勢(大吉など)の結果数を返すメソッド
	 *
	 * @param fortuneId	運勢コード
	 * @param today		今日の日付
	 * @return	rset.getInt("count")	過去半年分の該当運勢の結果数/ 0	エラー発生時
	 */
	public static Map<String, Double> getResultCountFindByFortuneIdForHalfAYear(Date today) {

		/**
		 * 運勢名とその行数を格納するMapを作成
		 */
		Map<String, Double> halfAYearMap = new LinkedHashMap<>();


		/**
		 * 半年前の日付を取得
		 */
		Date halfAYearAgo = getHalfAYearAgo(today);

		/**
		 * for分用のループカウンタの作成(運勢テーブルの行数を取得)
		 */
		int loopCounter = FortuneDao.getFortuneCount();

		/**
		 * 各運勢ごとに半年分の件数をだしマップに格納するfor文
		 */
		for(int i = 1; i <= loopCounter; i++) {

			try {

				/**
				 * DBと接続
				 */
				DBRelation.getConnection();

				/**
				 * SQL文の実行
				 */
				String sql = "SELECT f.fortune_name, COUNT(*) FROM result r INNER JOIN omikuji o ON r.omikuji_id = o.omikuji_id "
						+ "INNER JOIN fortune f ON o.fortune_id = f.fortune_id WHERE f.fortune_id = ? AND fortune_day >= ? AND fortune_day <= ? "
						+ "GROUP BY fortune_name";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, i);
				ps.setDate(2, halfAYearAgo);
				ps.setDate(3, today);
				rset = ps.executeQuery();
				rset.next();



				/**
				 * SQL文で取得した名前を変数に代入
				 */
				String fortuneName = rset.getString("fortune_name");

				/**
				 * マップに値を格納し返却
				 */
				halfAYearMap.put(fortuneName, rset.getDouble("count"));
			}

			/**
			 * 名前を代入するときに値がなかった場合(一度もその運勢が惹かれていない場合)
			 * 改めて名前を取得し、それを0.0とともにマップに格納し返却
			 */
			catch(SQLException e) {
				String fortuneName = FortuneDao.getFortuneNameFindByFortuneId(i);

				halfAYearMap.put(fortuneName, 0.0);
			}

			/**
			 * DBとの接続を切断
			 */
			finally {
				DBRelation.closeConnection();
			}

		}

		return halfAYearMap;

		}

	/**
	 * 今日一日の全結果数を返すメソッド
	 * @param today	今日の日付
	 * @return	rset.getInt("count")	今日一日のおみくじの結果数/0	エラー発生時
	 */
	public static double getResultCountForToday(Date today) {
		try {
			/**
			 * DBと接続
			 */
			DBRelation.getConnection();

			/**
			 * SQL文の実行
			 */
			String sql = "SELECT COUNT(*) FROM result WHERE fortune_day = ?";
			ps = conn.prepareStatement(sql);
			ps.setDate(1, today);
			rset = ps.executeQuery();
			rset.next();
			return rset.getDouble("count");

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
	 * 今日一日の各運勢(大吉など)の結果数を返すメソッド
	 *
	 * @param fortuneId	運勢コード
	 * @param today		今日の日付
	 * @return	rset.getInt("count")	今日一日の該当運勢の結果数/ 0	エラー発生時
	 */

	public static Map<String, Double> getResultCountFindByFortuneIdForToday(Date today) {

		/**
		 * 運勢名とその行数を格納するMapを作成
		 */
		Map<String, Double> todayMap = new LinkedHashMap<>();

		/**
		 * for分用のループカウンタの作成(運勢テーブルの行数を取得)
		 */
		int loopCounter = FortuneDao.getFortuneCount();

		/**
		 * 各運勢ごとに今日一日の件数をだしマップに格納するfor文
		 */
		for(int i = 1; i <= loopCounter; i++) {

			try {
				/**
				 * DBと接続
				 */
				DBRelation.getConnection();

				/**
				 * SQL文の実行
				 */
				String sql = "SELECT f.fortune_name, COUNT(*) FROM result r INNER JOIN omikuji o ON r.omikuji_id = o.omikuji_id "
						+ "INNER JOIN fortune f ON o.fortune_id = f.fortune_id WHERE f.fortune_id = ? AND r.fortune_day = ? "
						+ "GROUP BY fortune_name";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, i);
				ps.setDate(2, today);
				rset = ps.executeQuery();
				rset.next();



				/**
				 * SQL文で取得した名前を変数に代入
				 */
				String fortuneName = rset.getString("fortune_name");

				/**
				 * マップに値を格納
				 */
				todayMap.put(fortuneName, rset.getDouble("count"));

			}

			/**
			 * 名前を代入するときに値がなかった場合(一度もその運勢が惹かれていない場合)
			 * 改めて名前を取得し、それを0.0とともにマップに格納し返却
			 */
			catch(SQLException e) {

				String fortuneName = FortuneDao.getFortuneNameFindByFortuneId(i);

				todayMap.put(fortuneName, 0.0);
			}

			/**
			 * DBとの接続を切断
			 */
			finally {
				DBRelation.closeConnection();
			}
		}

		return todayMap;
	}
}
