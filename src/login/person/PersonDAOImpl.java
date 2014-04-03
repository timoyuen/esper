package login.person;
import java.sql.*;
import db.*;

public class PersonDAOImpl implements PersonDAO
{
	public boolean isLogin(PersonVo pv) {
		boolean flag = false;
		// 声明一个数据库操作对象
		PreparedStatement pstmt	= null;
		// 声明一个结果集对象
		ResultSet rs			= null;
		// 声明一个SQL变量，用于保存SQL语句
		String sql				= null;
		// DataBaseConnection为具体的数据库连接及关闭操作类
		DataBaseConnection dbc	= null;
		// 连接数据库
		dbc = new DataBaseConnection();

		// 编写SQL语句
		sql = "SELECT id FROM person WHERE id=? and password=?";
		try {
			// 实例化数据库操作对象
			pstmt = dbc.getConnection().prepareStatement(sql);

			// 设置pstmt的内容，是按ID和密码验证
			pstmt.setString(1, pv.getUserName());
			pstmt.setString(2, pv.getPassword());

			// 查询记录
			rs = pstmt.executeQuery(); // 执行select 用 executeQuery 返回ResultSet 类型
			// 判断是否有记录
			if(rs.next()) {
				flag = true;
				// pv.setName(rs.getString(1));
			}
			// 依次关闭
			rs.close();
			pstmt.close();
		} catch(Exception e) {
			System.out.println(e);
		} finally {
			// 最后一定要保证数据库已被关闭
			dbc.close();
		}
		return flag;
	}

	public boolean isUserExist(PersonVo pv) {
		DataBaseConnection dbc = new DataBaseConnection();
		boolean flag = false;
		try {
			String sql = "SELECT id FROM person WHERE id=?";
			PreparedStatement pstmt = dbc.getConnection().prepareStatement(sql);
			pstmt.setString(1, pv.getUserName());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				flag = true;
			}
			rs.close();
			pstmt.close();
		} catch(Exception e) {
			System.out.println(e);
		} finally {
			dbc.close();
		}
		return flag;
	}

	public int newUser(PersonVo pv) {
		DataBaseConnection dbc = new DataBaseConnection();
		int rs = -1;
		try {
			String sql = "INSERT INTO person values(?, ?, ?, ?)";
   			PreparedStatement pstmt = dbc.getConnection().prepareStatement(sql);
   			pstmt.setString(1, pv.getUserName());
   			pstmt.setString(2, pv.getPassword());
   			pstmt.setString(3, pv.getTelephone());
   			pstmt.setString(4, pv.getEmail());
   			rs = pstmt.executeUpdate(); // 执行UPDATE INSERT DELETE用 executeUpdate  返回INT类型
   			pstmt.close();
		} catch(Exception e) {
			System.out.println(e);
		} finally {
			// 最后一定要保证数据库已被关闭
			dbc.close();
		}
		System.out.println("ads");
   		return rs;

	}
}