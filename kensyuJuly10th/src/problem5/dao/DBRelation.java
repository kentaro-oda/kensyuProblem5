package problem5.dao;

import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * DBの接続・切断処理のクラス
 * @author k_oda
 *
 */
public class DBRelation extends DBFields{

	/**
	 * DBに接続する処理
	 */
	public static void getConnection(){
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url, user, password);//SQLへの接続
			conn.setAutoCommit(false);//commit:上書き不能にする機能
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}



	/**
	 * DBとの接続をきるメソッド
	 */
	public static void closeConnection(){
		try {
		if(rset != null)rset.close();
		if(ps != null)ps.close();
		if(conn != null)conn.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
