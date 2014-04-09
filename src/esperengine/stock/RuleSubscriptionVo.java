package esperengine.stock;
import java.util.*;
import login.person.*;
public class RuleSubscriptionVo {
	private String epl;
	private String subId;
	private List<String> userArgs;
	private PersonVo pv;
	public RuleSubscriptionVo(String subId, String epl, List<String> userArgs, PersonVo pv) {
		this.epl = epl;
		this.subId = subId;
		this.userArgs = userArgs;
		this.pv = pv;
	}
	public String getEpl() { return epl; }
	public String getSubId() { return subId; }
	public void setEpl(String epl) { this.epl = epl; }
	public void setSubId(String subId) { this.subId = subId; }
	public List<String> getUserArgs() { return userArgs; }
	public void setUserArgs(List<String> userArgs) { this.userArgs = userArgs; }
	public PersonVo getPv() { return pv; }
	public void setPv(PersonVo pv) { this.pv = pv; }
}
