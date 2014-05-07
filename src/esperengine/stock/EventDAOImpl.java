package esperengine.stock;
// import java.util.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.Thread;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.text.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.espertech.esper.client.*;
import db.DataBaseAccess;
import java.sql.*;

public class EventDAOImpl implements EventDAO
{
	public int insertEvent(List<Object> newData, List<Object> oldData, String subscriptionId) {
		String sql = "INSERT INTO event(stock_new_bean, stock_old_bean, subscription_id) VALUES(?, ?, ?)";
		List<Object> args = new ArrayList<Object>();
		args.add(newData);
		args.add(oldData);
		args.add(subscriptionId);
		int rs = DataBaseAccess.executeUpdate(sql, args);
		return rs;
	}

	public EventVo getEventWithEventId(String eventId) {
		String sql = "SELECT stock_new_bean, stock_old_bean, create_time, subscription_id "+
					 "FROM event WHERE event_id=?";
		List<Object> args = new ArrayList<Object>();
		args.add(eventId);
		List<Object> lo = DataBaseAccess.executeQuery(sql, args);
		EventVo event = null;
		if (lo != null) {
			try {
				ResultSet rs = (ResultSet)lo.get(0);
				if (rs.next()) {
					Object o = DataBaseAccess.getBlobObj(rs.getBinaryStream("stock_new_bean"));
					List<Object> stock_new_bean = null, stock_old_bean = null;
					if (o != null)
	    				stock_new_bean = (List<Object>) o;
	    			o = DataBaseAccess.getBlobObj(rs.getBinaryStream("stock_old_bean"));
					if (o != null)
	    				stock_old_bean = (List<Object>) o;
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date createTime = dateFormat.parse(rs.getString("create_time")); // string to date use format

	    			event = new EventVo(eventId, stock_new_bean, stock_old_bean,
	    			                    createTime, rs.getString("subscription_id"));
				}
			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			} finally {
				DataBaseAccess.close(lo.get(1));
			}
		}
		return event;
	}

	public List<EventVo> getAllEventWithSubId(String subId) {
		String sql = "SELECT event_id, stock_new_bean, stock_old_bean, create_time "+
					 "FROM event WHERE subscription_id=? AND UNIX_TIMESTAMP(create_time) > UNIX_TIMESTAMP(curdate())";
		List<Object> args = new ArrayList<Object>();
		args.add(subId);
		List<Object> lo = DataBaseAccess.executeQuery(sql, args);
		List<EventVo> eventList = new ArrayList<EventVo>();
		if (lo != null) {
			try {
				ResultSet rs = (ResultSet)lo.get(0);
				while (rs.next()) {
	    			Object o = DataBaseAccess.getBlobObj(rs.getBinaryStream("stock_new_bean"));
					List<Object> stock_new_bean = null, stock_old_bean = null;
					if (o != null)
	    				stock_new_bean = (List<Object>) o;
	    			o = DataBaseAccess.getBlobObj(rs.getBinaryStream("stock_old_bean"));
					if (o != null)
	    				stock_old_bean = (List<Object>) o;
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date createTime = dateFormat.parse(rs.getString("create_time")); // string to date use format
	    			EventVo event = new EventVo(rs.getString("event_id"), stock_new_bean, stock_old_bean,
	    			                    createTime, subId);
	    			eventList.add(event);
				}
			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			} finally {
				DataBaseAccess.close(lo.get(1));
			}
		}
		return eventList;
	}
}