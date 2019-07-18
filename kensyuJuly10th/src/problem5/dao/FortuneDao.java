package problem5.dao;

import java.sql.SQLException;

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
	 * 運勢テーブルの運勢名を取得するメソッド
	 * @return	fortuneNameList	全運勢名が登録されたリスト
	 */
	public static String getFortuneNameFindByFortuneId(int i){
		try {

			/**
			 * DBと接続
			 */
			DBRelation.getConnection();

			/**
			 * SQL文の実行
			 */
			String sql = "SELECT fortune_name FROM fortune WHERE fortune_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, i);
			rset = ps.executeQuery();
			rset.next();
			return rset.getString("fortune_name");
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
