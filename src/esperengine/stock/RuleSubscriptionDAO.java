package esperengine.stock;
import java.util.List;
public interface RuleSubscriptionDAO
{
	public String getEPLWithSubId(long subId, PersonVo pv);
	public List<String> getAllEPLRules();
}