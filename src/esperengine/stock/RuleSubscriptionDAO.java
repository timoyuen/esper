package esperengine.stock;
import java.util.List;
import login.person.*;
public interface RuleSubscriptionDAO
{
	public RuleSubscriptionVo getEPLWithSubId(int subId);
	public List<RuleSubscriptionVo> getAllEPLRules();
	public List<RuleSubscriptionVo> getUserEPLWithUserNameAndPage(String userName, int curPage, int itemPerPage);
	public int getAllUserEPLWithUserName(String userName);
	public int insertUserSubsciption(String eplId, String[] userArgs, List<Object> eventArgs,
	                                 PersonVo pv, String priority);
	public int updateUserSubscription(String subId, String [] userArgs, List<Object> allEventArgs,
	                                  PersonVo pv, String priority);
	public void delete(String subId);
}