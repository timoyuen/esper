package esperengine.stock;
import java.util.*;
import login.person.*;
public interface StockRuleDAO
{
	public boolean insert(String epl, String ruleDescription,
	                             String [] argExample, String [] argDescription,
	                             List<String> eventIdList);
	public List<StockRuleVo> getAllRulesWithPage(int page, int itemPerPage);
	public int getAllRulesCount();
	public StockRuleVo getRuleWithId(String id);
}