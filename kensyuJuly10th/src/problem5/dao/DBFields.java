package problem5.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * DB関係に用いられるフィールドを集めた抽象メソッド
 * @author k_oda
 *
 */
public abstract class DBFields {
	protected static Connection conn = null;
	protected static PreparedStatement ps = null;
	protected static ResultSet rset = null;

	protected static String url = "jdbc:postgresql://localhost:5432/problem3";
	protected static String user = "k_oda";
	protected static String password = "oda2005";
}
