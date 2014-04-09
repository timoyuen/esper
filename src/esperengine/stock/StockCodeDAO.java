package esperengine.stock;
import java.util.List;
public interface StockCodeDAO
{
	public List<String> getAllStockCode();
	public boolean deleteStockCode(String stockCode);
	public boolean insertStockCode(String stockCode, String stockName);
	public boolean isStockCodeExist(String stockCode);
}