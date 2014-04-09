package esperengine.stock;
import java.util.*;
import db.DataBaseAccess;
import java.sql.*;
import login.person.*;
public interface RuleSubscriptionDAOImpl
{
	public String getEPLWithSubId(long subId, PersonVo pv) {
		String sql = "SELECT stock_epl_template.epl_str as epl_str, person.id as userid, "
					 "person.password as password, person.telephone as telephone, "+
					 "person.email as email, user_args "+
					 "FROM rule_subscription, stock_epl_template, person "+
					 "WHERE rule_subscription.userid=person.id AND "
					 "rule_subscription.epl_id=stock_epl_template.epl_id AND "
					 "rule_subscription.subscription_id=?";
		List<Object> args = new ArrayList<String>();
		args.add(String.valueOf(subId));
		List<Object> lo = DataBaseAccess.executeQuery(sql, args);
		String epl = null;
		if (lo != null) {
			try {
				ResultSet rs = lo.get(0);
				if (rs.next()) {
					epl = rs.getString(epl_str);
	    			String [] userArgs = (String [])DataBaseAccess.getBlobObj(rs.getBinaryStream("user_args"));
	    			pv.setUserName(rs.getString("userid"));
	    			pv.setPassword(rs.getString("password"));
	    			pv.setTelephone(rs.getString("telephone"));
	    			pv.setEmail(rs.getString("email"));
					for (String arg : userArgs) {
						epl = epl.replace("\\?", arg);
					}
				}
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				DataBaseAccess.close(lo.get(1));
			}
		}
		return epl;
	}
	public List<String> getAllEPLRules() {
		String sql = "SELECT stock_epl_template.epl_str as epl_str, person.id as userid, "
			 "person.password as password, person.telephone as telephone, "+
			 "person.email as email, user_args "+
			 "FROM rule_subscription, stock_epl_template, person "+
			 "WHERE rule_subscription.userid=person.id AND "
			 "rule_subscription.epl_id=stock_epl_template.epl_id";
		List<Object> lo = DataBaseAccess.executeQuery(sql, null);
		List<String> eplList = null;
		if (lo != null) {
			try {
				ResultSet rs = lo.get(0);
				eplList = new ArrayList<String>();
				while (rs.next()) {
					String epl = rs.getString(epl_str);
	    			String [] userArgs = (String [])DataBaseAccess.getBlobObj(rs.getBinaryStream("user_args"));
	    			pv.setUserName(rs.getString("userid"));
	    			pv.setPassword(rs.getString("password"));
	    			pv.setTelephone(rs.getString("telephone"));
	    			pv.setEmail(rs.getString("email"));
					for (String arg : userArgs) {
						epl = epl.replace("\\?", arg);
					}
					eplList.add(epl);
				}
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				DataBaseAccess.close(lo.get(1));
			}
		}
		return eplList;
	}
}

