package db;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.*;
public class DataBaseAccess
{
	/// TODO something Wrong!!
    static Log log = LogFactory.getLog(DataBaseAccess.class);
	public static List<Object> executeQuery(String sql, List<Object> args) {
		DataBaseConnection dbc = new DataBaseConnection();
		List<Object> ret = new ArrayList<Object>();
		ResultSet rs;
		try {
			PreparedStatement pstmt = dbc.getConnection().prepareStatement(sql);
			int count = 0;
			if (args != null && !args.isEmpty()) {
				for (Object arg : args) {
					if (arg instanceof String) {
						pstmt.setString(++count, (String)arg);
					}
					else {
						pstmt.setObject(++count, arg);
					}
				}
			}
			rs = pstmt.executeQuery();
			ret.add(rs);
			List<Object> cl = new ArrayList<Object>();
			cl.add(rs);
			cl.add(pstmt);
			cl.add(dbc);
			ret.add(cl);
		} catch(Exception e) {
			log.error(sql);
			System.out.println(e);
		}
		return ret;
	}

	public static void close(Object st) {
		List<Object> stmt = (List<Object>)st;
		try {
			for (Object o : stmt) {
				if (o instanceof Statement) {
					((Statement)o).close();
				} else if (o instanceof PreparedStatement) {
					((PreparedStatement)o).close();
				} else if (o instanceof DataBaseConnection) {
					((DataBaseConnection)o).close();
				} else if (o instanceof ResultSet) {
					((ResultSet)o).close();
				} else {
					log.error("Error Calling DataBaseAccess::close()");
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static int executeUpdate(String sql, List<Object> args) {
		DataBaseConnection dbc = new DataBaseConnection();
		PreparedStatement stmt = null;
		ResultSet generatedKeys = null;
		int rs = -1;
		try {
			stmt = dbc.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int count = 0;
			if (args != null && !args.isEmpty()) {
				for (Object arg : args) {
					if (arg instanceof String) {
						log.info(arg);
						stmt.setString(++count, (String)arg);
					}
					else {
						log.info(arg);
						stmt.setObject(++count, arg);
					}
				}
			}
			rs = stmt.executeUpdate();
			generatedKeys = stmt.getGeneratedKeys();
			if (generatedKeys.next()) {
            	rs = generatedKeys.getInt(1);
            } else {
            	rs = -1;
            }
			stmt.close();
		} catch(Exception e) {
			log.error(sql);
			System.out.println(e);
		} finally {
			dbc.close();
		}
		return rs;
	}

	public static boolean isExist(String sql, List<Object> args) {
		List<Object> lo = DataBaseAccess.executeQuery(sql, args);
		ResultSet rs = (ResultSet)(lo.get(0));
		boolean flag = false;
		try {
			flag = rs.next() ? true: false;
			DataBaseAccess.close(lo.get(1));
		} catch(Exception e) {
			System.out.println(e);
		}
		return flag;
	}


	public static Object getBlobObj(InputStream input) {
		ObjectInputStream in = null;
		Object o = null;
		try {
			in = new ObjectInputStream(input);
	    	o = (Object)in.readObject();
	    	in.close();
	    } catch (Exception e) {
	    	System.out.println(e);
	    } finally {
	    }
	    return o;
	}
}