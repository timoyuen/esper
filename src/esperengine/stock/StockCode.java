package esperengine.stock;
import java.util.List;
import java.util.ArrayList;
public class StockCode
{
	private static boolean isInitialed = false;
	private static List<String> stockCode = new ArrayList<String>();
	public StockCode()
	{
		if (!isInitialed)
			initStockCode();
	}
	public void initStockCode() {
		stockCode = StockDAOFactory.getStockDAOInstance().getAllStockCode();
		isInitialed = true;
	}
	public void addStockCode(String newStockCode) {
		stockCode.add(newStockCode);
	}

	public void delStockCode(String removeStockCode) {
		stockCode.remove(removeStockCode);
	}

	public static final List<String> getStockCode() {
		return stockCode;
	}
}