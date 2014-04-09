package esperengine.stock;
import java.util.*;
import login.person.*;
public interface StockRuleDAO
{
	public boolean insert(String epl, String ruleDescription,
	                             String [] argExample, String [] argDescription);
	public List<StockRuleVo> getAllRules(int page);
	public int getRulePageCount();
	public StockRuleVo getRuleWithId(String id);
	public int insertUserRule(String eplId, String[] args, PersonVo pv);
}