package esperengine.stock;
import java.util.*;
import db.DataBaseAccess;
import java.sql.*;
import login.person.*;
import helper.*;
public class RuleSubscriptionDAOImpl implements RuleSubscriptionDAO
{
	public RuleSubscriptionVo getEPLWithSubId(int subId) {
		String sql = "SELECT stock_epl_template.epl_str as epl_str, person.id as userid, "+
					 "rule_subscription.epl_str AS epl_full_str, "+
					 "person.password as password, person.telephone as telephone, "+
					 "person.email as email, user_args, "+
					 "stock_epl_template.rule_description as rule_description, "+
					 "stock_epl_template.rule_args_description as rule_args_description, "+
					 "stock_epl_template.rule_args_example as rule_args_example, "+
					 "priority, "+
					 "event_args, "+
					 "event_id_list "+
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
	    			List<String> ruleArgsExample = (List<String>)DataBaseAccess.getBlobObj(rs.getBinaryStream("rule_args_example"));
	    			List<String> ruleArgsDescription = (List<String>)DataBaseAccess.getBlobObj(rs.getBinaryStream("rule_args_description"));
	    			List<Object> eventArgs = (List<Object>)DataBaseAccess.getBlobObj(rs.getBinaryStream("event_args"));
	    			List<String> eventIdList = (List<String>)DataBaseAccess.getBlobObj(rs.getBinaryStream("event_id_list"));

	    			PersonVo pv = new PersonVo();
	    			pv.setUserName(rs.getString("userid"));
	    			pv.setPassword(rs.getString("password"));
	    			pv.setTelephone(rs.getString("telephone"));
	    			pv.setEmail(rs.getString("email"));
	    			rule = new RuleSubscriptionVo(String.valueOf(subId), epl, userArgs, pv,
	    			                              ruleArgsDescription, ruleArgsExample,
	    			                              rs.getString("rule_description"),
	    			                              rs.getString("priority"),
	    			                              rs.getString("epl_full_str"),
	    			                              eventArgs,
	    			                              eventIdList);
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

	public List<RuleSubscriptionVo> getUserEPLWithUserNameAndPage(String userName, int curPage, int itemPerPage) {
		String sql = "SELECT stock_epl_template.epl_str as epl_str, rule_subscription.epl_str as epl_full_str, "+
					 "user_args, subscription_id, rule_subscription.create_time as create_time, "+
					 "stock_epl_template.rule_description as rule_description "+
					 "FROM rule_subscription, stock_epl_template "+
					 "WHERE rule_subscription.userid=? AND "+
					 "rule_subscription.epl_id=stock_epl_template.epl_id "+
					 "LIMIT ?, ?";
		List<Object> args = new ArrayList<Object>();
		args.add(userName);
		args.add((curPage - 1) * itemPerPage);
		args.add(itemPerPage);
		List<Object> lo = DataBaseAccess.executeQuery(sql, args);
		List<RuleSubscriptionVo> eplList = null;
		if (lo != null) {
			try {
				ResultSet rs = (ResultSet)lo.get(0);
				eplList = new ArrayList<RuleSubscriptionVo>();
				while (rs.next()) {
					String epl = rs.getString("epl_str");
	    		List<String> userArgs = (List<String>)DataBaseAccess.getBlobObj(rs.getBinaryStream("user_args"));
					eplList.add(new RuleSubscriptionVo(rs.getString("subscription_id"), epl, userArgs,
					            					   rs.getString("rule_description"),
					            					   rs.getString("create_time"),
					            					   rs.getString("epl_full_str")));
				}
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				DataBaseAccess.close(lo.get(1));
			}
		}
		return eplList;
	}
	public int getAllUserEPLWithUserName(String userName) {
		String sql = "SELECT COUNT(subscription_id) as count FROM rule_subscription WHERE userid=?";
		List<Object> args = new ArrayList<Object>();
		args.add(userName);
		int count = 0;
		List<Object> lo = DataBaseAccess.executeQuery(sql, args);
		if (lo != null) {
			try {
				ResultSet rs = (ResultSet)lo.get(0);
				if (rs.next()) {
					count = Integer.parseInt(rs.getString("count"));
				}
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				DataBaseAccess.close(lo.get(1));
			}
		}
		return count;
	}

	public int insertUserSubsciption(String eplId, String[] userArgs, List<Object> eventArgs, PersonVo pv, String priority) {
		String sql = "INSERT INTO rule_subscription(epl_id, user_args, userid, priority, event_args) "+
					 "VALUES(?, ?, ?, ?, ?)";
		List<Object> args = new ArrayList<Object>();
		args.add(eplId);
		args.add(Helper.getList(userArgs));
		args.add(pv.getUserName());
		args.add(priority);
		args.add(eventArgs);
		return DataBaseAccess.executeUpdate(sql, args);
	}

	public int updateUserSubscription(String subId, String [] userArgs, List<Object> eventArgs, PersonVo pv, String priority) {
		String sql = "UPDATE rule_subscription SET user_args=?, priority=?, event_args=? WHERE subscription_id=? AND userid=?";
		List<Object> args = new ArrayList<Object>();
		args.add(Helper.getList(userArgs));
		args.add(priority);
		args.add(eventArgs);
		args.add(subId);
		args.add(pv.getUserName());
		return DataBaseAccess.executeUpdate(sql, args);
	}

	public void delete(String subId) {
		String sql = "DELETE FROM rule_subscription WHERE subscription_id=?";
		List<Object> args = new ArrayList<Object>();
		args.add(subId);
		DataBaseAccess.executeUpdate(sql, args);
	}
}

