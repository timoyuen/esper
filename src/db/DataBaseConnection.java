// 本类只用于数据库连接及关闭操作
package db;
import java.sql.*;

public class DataBaseConnection
{
	private final String DBDRIVER	= "com.mysql.jdbc.Driver";
	private final String DBURL		= "jdbc:mysql://127.0.0.1:3306/esper_t?characterEncoding=utf8";
	private final String DBUSER		= "root";
	private final String DBPASSWORD	= "314159zlc";
	private Connection conn			= null;

	public DataBaseConnection()
	{
		try {
			// 加载驱动程序
			Class.forName(DBDRIVER);
			// 连接数据库
			conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

	// 返回一个数据库连接
	public Connection getConnection()
	{
		/// 返回连接对象
		return this.conn;
	}

	// 关闭数据库连接
	public void close()
	{
		try {
			this.conn.close();
		}
		catch (Exception e) {
		}
	}
}
