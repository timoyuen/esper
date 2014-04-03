package esperengine.stock;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;
import db.*;
public class StockDAOImpl implements StockDAO
{
	public List<String> getAllStockCode() {
		DataBaseConnection dbc = new DataBaseConnection();
		List<String> stockCode = new ArrayList<String>();
		try {
			String sql = "SELECT code, name FROM stock_code";
			Statement st = dbc.getConnection().createStatement();
			ResultSet result = st.executeQuery(sql);
			while (result.next()) {
				stockCode.add(result.getString("code"));
			}
			result.close();
		} catch(Exception e) {
			System.out.println(e);
		} finally {
			dbc.close();
		}
		return stockCode;
	}
	public void removeStockCode(String stockCode)
	{
	}
	public void addStockCode(String stockCode){}
}