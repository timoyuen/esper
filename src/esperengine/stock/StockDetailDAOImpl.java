package esperengine.stock;
import java.util.*;
import db.DataBaseAccess;
import java.sql.*;
import login.person.*;
import helper.*;
import com.espertech.esper.client.*;

public class StockDetailDAOImpl implements StockDetailDAO {
	public int insertStockDetail(String stockCode, StockInfo si) {
		String sql = "INSERT INTO stock_detail(code, stock_bean) VALUES(?, ?)";
		List<Object> args = new ArrayList<Object>();
		args.add(stockCode);
		args.add(si);
		int rs = DataBaseAccess.executeUpdate(sql, args);
		return rs;
	}

	private List<StockInfo> getOneStockDetail(String stockCode) {
		System.out.println("SSS"+stockCode);
		String sql = "SELECT code, stock_bean, create_time FROM stock_detail "+
					 "WHERE UNIX_TIMESTAMP(curdate()) < UNIX_TIMESTAMP(create_time) AND code=?";
		List<Object> args = new ArrayList<Object>();
		args.add(stockCode);
		List<Object> lo = DataBaseAccess.executeQuery(sql, args);
		RuleSubscriptionVo rule = null;
		List<StockInfo> stockDetailList = new ArrayList<StockInfo>();
		if (lo != null) {
			try {
				ResultSet rs = (ResultSet)lo.get(0);
				while (rs.next()) {
	    			stockDetailList.add((StockInfo)DataBaseAccess.getBlobObj(rs.getBinaryStream("stock_bean")));
				}
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				DataBaseAccess.close(lo.get(1));
			}
		}
		return stockDetailList;
	}
	public List<Object> getAllStockDetail() {
		StockCodeDAO scd = StockDAOFactory.getStockCodeDAOInstance();
		List<String> stcList = scd.getAllStockCode();
		return getStockDetailWithStockCode(stcList);
	}

	public List<Object> getStockDetailWithStockCode(List<String> stcList) {
		List<Object> allStockDetail = new ArrayList<Object>();
		for (String st : stcList) {
			List<StockInfo> si = getOneStockDetail(st);
			if (si.size() > 0)
				allStockDetail.add(si);
		}
		return allStockDetail;
	}
}