package problem5.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FortuneDao extends DBFields{
	/**
	 * 運勢テーブルの行数を取得するメソッド
	 * @return	rset.getInt("count")：運勢テーブルの行数/0:エラー発生時
	 */
	public static int getFortuneCount(){
		try {
			/**
			 * DBと接続
			 */
			DBRelation.getConnection();

			/**
			 * SQL文の実行
			 */
			String sql = "SELECT COUNT(*) FROM fortune";
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
	 * 運勢テーブルの全運勢名を取得するメソッド
	 * @return	fortuneNameList	全運勢名が登録されたリスト
	 */
	public static List<String> getAllFortuneNameFindByFortuneId(){
		try {

			/**
			 * 運勢名を格納するためのリストを作成
			 */
			List<String> fortuneNameList = new ArrayList<>();

			/**
			 * for分用のループカウンタの作成(運勢テーブルの行数を取得)
			 */
			int loopCounter = getFortuneCount();

			/**
			 * DBと接続
			 */
			DBRelation.getConnection();

			/**
			 * 運勢テーブルの各行の運勢名を取得しリストに格納するループ
			 */
			for(int i = 1; i <= loopCounter; i++) {
				/**
				 * SQL文の実行
				 */
				String sql = "SELECT fortune_name FROM fortune WHERE fortune_id = ?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, i);
				rset = ps.executeQuery();
				rset.next();

				/**
				 * 取得した運勢名をリストに格納
				 */
				fortuneNameList.add(rset.getString("fortune_name"));
			}

			/**
			 * 全運勢名が登録されたリストを返す
			 */
			return fortuneNameList;
		}
		catch(SQLException e) {
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
