package login.person;
import java.util.*;
import db.*;
import java.sql.*;

public class PersonDAOImpl implements PersonDAO
{
	public boolean isLogin(PersonVo pv) {
		String sql = "SELECT id FROM person WHERE id=? and password=?";
		List<Object> args = new ArrayList<Object>();
		args.add(pv.getUserName());
		args.add(pv.getPassword());
		return DataBaseAccess.isExist(sql, args);
		// boolean flag = false;
		// // 声明一个数据库操作对象
		// PreparedStatement pstmt	= null;
		// // 声明一个结果集对象
		// ResultSet rs			= null;
		// // 声明一个SQL变量，用于保存SQL语句
		// String sql				= null;
		// // DataBaseConnection为具体的数据库连接及关闭操作类
		// DataBaseConnection dbc	= null;
		// // 连接数据库
		// dbc = new DataBaseConnection();

		// // 编写SQL语句
		// sql =
		// try {
		// 	// 实例化数据库操作对象
		// 	pstmt = dbc.getConnection().prepareStatement(sql);

		// 	// 设置pstmt的内容，是按ID和密码验证
		// 	pstmt.setString(1, pv.getUserName());
		// 	pstmt.setString(2, pv.getPassword());

		// 	// 查询记录
		// 	rs = pstmt.executeQuery(); // 执行select 用 executeQuery 返回ResultSet 类型
		// 	// 判断是否有记录
		// 	if(rs.next()) {
		// 		flag = true;
		// 		// pv.setName(rs.getString(1));
		// 	}
		// 	// 依次关闭
		// 	rs.close();
		// 	pstmt.close();
		// } catch(Exception e) {
		// 	System.out.println(e);
		// } finally {
		// 	// 最后一定要保证数据库已被关闭
		// 	dbc.close();
		// }
		// return flag;
	}

	public boolean isUserExist(PersonVo pv) {
		String sql = "SELECT id FROM person WHERE id=?";
		List<Object> args = new ArrayList<Object>();
		args.add(pv.getUserName());
		return DataBaseAccess.isExist(sql, args);
	}

	public boolean newUser(PersonVo pv) {
		String sql = "INSERT INTO person values(?, ?, ?, ?)";
		List<Object> args = new ArrayList<Object>();
		args.add(pv.getUserName());
		args.add(pv.getPassword());
		args.add(pv.getTelephone());
		args.add(pv.getEmail());
		return DataBaseAccess.executeUpdate(sql, args) > 0;
	}

	/**
	 * conn.createStatement
	 * Statement st = conn.createStatement();
	 * st.executeUpdate();
	 * st.executeQuery();
	 */
}