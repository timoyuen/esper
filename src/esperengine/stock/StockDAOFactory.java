package esperengine.stock;

public class StockDAOFactory
{
	public static StockDAO getStockDAOInstance()
	{
		return new StockDAOImpl();
	}
}
