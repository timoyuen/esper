package esperengine.stock;
import java.util.List;
import login.person.*;
public interface RuleSubscriptionDAO
{
	public RuleSubscriptionVo getEPLWithSubId(int subId);
	public List<RuleSubscriptionVo> getAllEPLRules();
}