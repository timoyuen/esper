package esperengine.stock;
public class StockDAOFactory
{
	public static StockCodeDAO getStockDAOInstance()
	{
		return new StockCodeDAOImpl();
	}
	public static StockRuleDAO getStockRuleDAOInstance() {
		return new StockRuleDAOImpl();
	}
	public static RuleSubscriptionDAO getRuleSubscriptionDAOInstance() {
		return new RuleSubscriptionDAOImpl();
	}
}
