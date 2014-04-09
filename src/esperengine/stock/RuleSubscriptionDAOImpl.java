package esperengine.stock;
import java.util.*;
import db.DataBaseAccess;
import java.sql.*;
import login.person.*;
public class RuleSubscriptionDAOImpl implements RuleSubscriptionDAO
{
	public RuleSubscriptionVo getEPLWithSubId(int subId) {
		String sql = "SELECT stock_epl_template.epl_str as epl_str, person.id as userid, "+
					 "person.password as password, person.telephone as telephone, "+
					 "person.email as email, user_args "+
					 "FROM rule_subscription, stock_epl_template, person "+
					 "WHERE rule_subscription.userid=person.id AND "+
					 "rule_subscription.epl_id=stock_epl_template.epl_id AND "+
					 "rule_subscription.subscription_id=?";
		List<Object> args = new ArrayList<Object>();
		args.add(String.valueOf(subId));
		List<Object> lo = DataBaseAccess.executeQuery(sql, args);
		RuleSubscriptionVo rule = null;
		if (lo != null) {
			try {
				ResultSet rs = (ResultSet)lo.get(0);
				if (rs.next()) {
					String epl = rs.getString("epl_str");
	    			List<String> userArgs = (List<String>)DataBaseAccess.getBlobObj(rs.getBinaryStream("user_args"));
	    			PersonVo pv = new PersonVo();
	    			pv.setUserName(rs.getString("userid"));
	    			pv.setPassword(rs.getString("password"));
	    			pv.setTelephone(rs.getString("telephone"));
	    			pv.setEmail(rs.getString("email"));
	    			rule = new RuleSubscriptionVo(String.valueOf(subId), epl, userArgs, pv);
					// for (String arg : userArgs) {
					// 	epl = epl.replace("\\?", arg);
					// }
				}
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				DataBaseAccess.close(lo.get(1));
			}
		}
		return rule;
	}
	public List<RuleSubscriptionVo> getAllEPLRules() {
		String sql = "SELECT stock_epl_template.epl_str as epl_str, person.id as userid, "+
			 "person.password as password, person.telephone as telephone, "+
			 "person.email as email, user_args, subscription_id "+
			 "FROM rule_subscription, stock_epl_template, person "+
			 "WHERE rule_subscription.userid=person.id AND "+
			 "rule_subscription.epl_id=stock_epl_template.epl_id";
		List<Object> lo = DataBaseAccess.executeQuery(sql, null);
		List<RuleSubscriptionVo> eplList = null;
		if (lo != null) {
			try {
				ResultSet rs = (ResultSet)lo.get(0);
				eplList = new ArrayList<RuleSubscriptionVo>();
				while (rs.next()) {
					String epl = rs.getString("epl_str");
	    			List<String> userArgs = (List<String>)DataBaseAccess.getBlobObj(rs.getBinaryStream("user_args"));
	    			PersonVo pv = new PersonVo();
	    			pv.setUserName(rs.getString("userid"));
	    			pv.setPassword(rs.getString("password"));
	    			pv.setTelephone(rs.getString("telephone"));
	    			pv.setEmail(rs.getString("email"));
					// for (String arg : userArgs) {
					// 	epl = epl.replace("\\?", arg);
					// }
					eplList.add(new RuleSubscriptionVo(rs.getString("subscription_id"), epl, userArgs, pv));
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

