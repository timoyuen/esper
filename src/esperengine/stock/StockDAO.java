package esperengine.stock;
import java.util.List;
public interface StockDAO
{
	public List<String> getAllStockCode();
	public void removeStockCode(String stockCode);
	public void addStockCode(String stockCode);
}