package esperengine.stock;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.lang.Thread;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import db.DataBaseAccess;
import java.sql.*;
import java.io.*;
import login.person.*;
import helper.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StockRuleDAOImpl implements StockRuleDAO
{
	// private static final int itemPerPage = 10;
    static Log log = LogFactory.getLog(StockRuleDAOImpl.class);
	public boolean insert(String epl, String ruleDescription,
	                      String [] argExample, String [] argDescription,
	                      List<String> eventIdList)
	{
		String sql = "INSERT INTO stock_epl_template(epl_str, "+
		             "rule_description, rule_args_example, rule_args_description, event_id_list) "+
					 "VALUES(?, ?, ?, ?, ?)";
		List<Object> args = new ArrayList<Object>();
		args.add(epl);
		args.add(ruleDescription);
		List<String> argExampleList = Helper.getList(argExample);
		List<String> argDescriptionList = Helper.getList(argDescription);
		args.add(argExampleList);
		args.add(argDescriptionList);
		args.add(eventIdList);
		int rs = DataBaseAccess.executeUpdate(sql, args);
		return rs > 0 ? true: false;
	}

	public int getAllRulesCount() {
		String sql = "SELECT COUNT(epl_id) as epl_count "+
					 "FROM stock_epl_template";
		List<Object> lo = DataBaseAccess.executeQuery(sql, null);
		ResultSet rs = (ResultSet)lo.get(0);
		int count = 0;
		try {
			if (rs.next())
				count = Integer.parseInt(rs.getString("epl_count"));
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			DataBaseAccess.close(lo.get(1));
		}
		return count;
		// return (count + itemPerPage - 1) / itemPerPage;
	}

	public StockRuleVo getRuleWithId(String id) {
		String sql;
		sql = "SELECT epl_id, epl_str, rule_description, rule_args_description, rule_args_example, event_id_list "+
					 "FROM stock_epl_template WHERE epl_id=?";
		List<Object> args = new ArrayList<Object>();
		args.add(id);
		List<Object> lo = DataBaseAccess.executeQuery(sql, args);
		ResultSet rs = (ResultSet)lo.get(0);
		StockRuleVo sv = null;
		try {
			if (rs.next()) {
    			List<String> ruleArgsDescription = (List<String>)DataBaseAccess.getBlobObj(rs.getBinaryStream(4));
    			List<String> ruleArgsExample = (List<String>)DataBaseAccess.getBlobObj(rs.getBinaryStream(5));
    			List<String> eventIdList = (List<String>)DataBaseAccess.getBlobObj(rs.getBinaryStream("event_id_list"));
				sv = new StockRuleVo(Integer.parseInt(rs.getString("epl_id")),
				                                 rs.getString("epl_str"),
				                                 rs.getString("rule_description"),
				                                 ruleArgsDescription,
				                                 ruleArgsExample,
				                                 eventIdList);

			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DataBaseAccess.close(lo.get(1));
		}
		return sv;
	}

	public List<StockRuleVo> getAllRulesWithPage(int page, int itemPerPage) {
		String sql;
		sql = "SELECT epl_id, epl_str, rule_description, rule_args_description, rule_args_example "+
					 "FROM stock_epl_template LIMIT ?, ?";
		List<Object> args = new ArrayList<Object>();
		args.add((page - 1) * itemPerPage);
		args.add(itemPerPage);
		List<Object> lo = DataBaseAccess.executeQuery(sql, args);
		ResultSet rs = (ResultSet)lo.get(0);
		List<StockRuleVo> ruleContainer = new ArrayList<StockRuleVo>();
		try {
			while (rs.next()) {
    			List<String> ruleArgsDescription = (List<String>)DataBaseAccess.getBlobObj(rs.getBinaryStream(4));
    			List<String> ruleArgsExample = (List<String>)DataBaseAccess.getBlobObj(rs.getBinaryStream(5));
				StockRuleVo sv = new StockRuleVo(Integer.parseInt(rs.getString("epl_id")),
				                                 rs.getString("epl_str"),
				                                 rs.getString("rule_description"),
				                                 ruleArgsDescription,
				                                 ruleArgsExample);
				ruleContainer.add(sv);
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DataBaseAccess.close(lo.get(1));
		}
		return ruleContainer;
	}
}