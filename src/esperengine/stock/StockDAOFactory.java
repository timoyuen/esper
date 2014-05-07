package esperengine.stock;
public class StockDAOFactory
{
	public static StockCodeDAO getStockCodeDAOInstance()
	{
		return new StockCodeDAOImpl();
	}
	public static StockRuleDAO getStockRuleDAOInstance() {
		return new StockRuleDAOImpl();
	}
	public static RuleSubscriptionDAO getRuleSubscriptionDAOInstance() {
		return new RuleSubscriptionDAOImpl();
	}
	public static EventDAO getEventDAOInstance() {
		return new EventDAOImpl();
	}
	public static StockDetailDAO getStockDetailInstance() {
		return new StockDetailDAOImpl();
	}
	public static StockInsertEventDAO getStockInsertEventDAOInstance() {
		return new StockInsertEventDAOImpl();
	}
}
