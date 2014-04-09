package esperengine.stock;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;
import db.DataBaseAccess;
public class StockCodeDAOImpl implements StockCodeDAO
{
	List<Object> args = null;
	public List<String> getAllStockCode() {
		List<String> stockCode = new ArrayList<String>();
		String sql = "SELECT code, name FROM stock_code";
		List<Object> lo = DataBaseAccess.executeQuery(sql, null);
		ResultSet result = (ResultSet)(lo.get(0));
		try {
			while (result.next()) {
				stockCode.add(result.getString("code"));
			}
			DataBaseAccess.close(lo.get(1));
		} catch (SQLException e) {
			System.out.println(e);
		}
		return stockCode;
	}

	public boolean deleteStockCode(String stockCode) {
		String sql = "DELETE FROM stock_code WHERE code=?";
		args = new ArrayList<Object>();
		args.add(stockCode);
		int rs = DataBaseAccess.executeUpdate(sql, args);
		return rs > 0 ? true: false;
	}
	public boolean insertStockCode(String stockCode, String stockName) {
		String sql = "INSERT INTO stock_code(code, name) VALUES(?, ?)";
		args = new ArrayList<Object>();
		args.add(stockCode);
		args.add(stockName);
		int rs = DataBaseAccess.executeUpdate(sql, args);
		return rs > 0 ? true: false;
	}

	public boolean isStockCodeExist(String stockCode) {
		String sql = "SELECT code FROM stock_code WHERE code=?";
		args = new ArrayList<Object>();
		args.add(stockCode);
		return DataBaseAccess.isExist(sql, args);
	}
}