package esperengine.stock;
import java.util.List;
import java.util.ArrayList;
import login.person.*;
import com.espertech.esper.client.*;
import helper.*;
import db.DataBaseAccess;
import java.sql.*;

public class StockInsertEventDAOImpl implements StockInsertEventDAO {
	public boolean insert(String eventStr, String eventName, String eventDescription,
	                             String [] argExample, String [] argDescription)
	{
		String sql = "INSERT INTO stock_insert_event_template(event_str, "+
		             "event_description, event_args_example, event_args_description, event_name) "+
					 "VALUES(?, ?, ?, ?, ?)";
		List<Object> args = new ArrayList<Object>();
		args.add(eventStr);
		args.add(eventDescription);
		List<String> argExampleList = Helper.getList(argExample);
		List<String> argDescriptionList = Helper.getList(argDescription);
		args.add(argExampleList);
		args.add(argDescriptionList);
		args.add(eventName);
		int rs = DataBaseAccess.executeUpdate(sql, args);
		return rs > 0 ? true: false;
	}

	public List<StockInsertEventVo> getAllInsertEvent() {
		String sql = "SELECT event_id, event_name, event_str, event_description, "+
					 "event_args_description, event_args_example "+
					 "FROM stock_insert_event_template";
		List<Object> lo = DataBaseAccess.executeQuery(sql, null);
		ResultSet rs = (ResultSet)lo.get(0);
		List<StockInsertEventVo> sv = new ArrayList<StockInsertEventVo>();
		try {
			while (rs.next()) {
    			List<String> eventArgsDescription = (List<String>)
    								DataBaseAccess.getBlobObj(rs.getBinaryStream("event_args_description"));
    			List<String> eventArgsExample = (List<String>)
    								DataBaseAccess.getBlobObj(rs.getBinaryStream("event_args_example"));
				sv.add(new StockInsertEventVo((rs.getString("event_id")),
				                      rs.getString("event_name"),
				                      rs.getString("event_str"),
				                      rs.getString("event_description"),
				                      eventArgsDescription,
				                      eventArgsExample));

			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DataBaseAccess.close(lo.get(1));
		}
		return sv;
	}

	public StockInsertEventVo getInsertEventWithId(String id) {
		String sql = "SELECT event_id, event_name, event_str, event_description, "+
					 "event_args_description, event_args_example "+
					 "FROM stock_insert_event_template WHERE event_id=?";
		List<Object> args = new ArrayList<Object>();
		args.add(id);
		List<Object> lo = DataBaseAccess.executeQuery(sql, args);
		ResultSet rs = (ResultSet)lo.get(0);
		StockInsertEventVo sv = null;
		try {
			if (rs.next()) {
    			List<String> eventArgsDescription = (List<String>)
    								DataBaseAccess.getBlobObj(rs.getBinaryStream("event_args_description"));
    			List<String> eventArgsExample = (List<String>)
    								DataBaseAccess.getBlobObj(rs.getBinaryStream("event_args_example"));
				sv = new StockInsertEventVo((rs.getString("event_id")),
				                      rs.getString("event_name"),
				                      rs.getString("event_str"),
				                      rs.getString("event_description"),
				                      eventArgsDescription,
				                      eventArgsExample);

			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DataBaseAccess.close(lo.get(1));
		}
		return sv;
	}
}
