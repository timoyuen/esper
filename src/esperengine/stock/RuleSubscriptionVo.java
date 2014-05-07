package esperengine.stock;
import java.util.*;
import login.person.*;
public class RuleSubscriptionVo {
	private String epl = null;
	private String subId = null;
	private String eplFullStr = null;
	private List<String> userArgs = null;
	private List<Object> eventArgs = null;
	private List<String> ruleArgsDescription = null;
	private List<String> ruleArgsExample = null;
	private PersonVo pv = null;
	private String ruleDescription = null;
	private String createTime = null;
	private String priority = null;
	private List<String> eventIdList = null;
	public RuleSubscriptionVo(String subId, String epl, List<String> userArgs, PersonVo pv,
	                          List<String> ruleArgsDescription, List<String> ruleArgsExample,
	                          String ruleDescription, String priority, String eplFullStr,
	                          List<Object> eventArgs, List<String> eventIdList) {
		this.epl = epl;
		this.subId = subId;
		this.userArgs = userArgs;
		this.pv = pv;
		this.ruleArgsDescription = ruleArgsDescription;
		this.ruleArgsExample = ruleArgsExample;
		this.ruleDescription = ruleDescription;
		this.priority = priority;
		this.eplFullStr = eplFullStr;
		this.eventArgs = eventArgs;
		this.eventIdList = eventIdList;
	}

	public RuleSubscriptionVo(String subId, String epl, List<String> userArgs, PersonVo pv,
	                          List<String> ruleArgsDescription, List<String> ruleArgsExample,
	                          String ruleDescription, String priority, String eplFullStr) {
		this.epl = epl;
		this.subId = subId;
		this.userArgs = userArgs;
		this.pv = pv;
		this.ruleArgsDescription = ruleArgsDescription;
		this.ruleArgsExample = ruleArgsExample;
		this.ruleDescription = ruleDescription;
		this.priority = priority;
		this.eplFullStr = eplFullStr;
	}

	public RuleSubscriptionVo(String subId, String epl, List<String> userArgs, PersonVo pv) {
		this.subId = subId;
		this.epl = epl;
		this.userArgs = userArgs;
		this.pv = pv;
	}
	public RuleSubscriptionVo(String subId, String epl, List<String> userArgs,
	                          String ruleDescription, String createTime, String eplFullStr) {
		this.epl = epl;
		this.subId = subId;
		this.userArgs = userArgs;
		this.ruleDescription = ruleDescription;
		this.createTime = createTime;
		this.eplFullStr = eplFullStr;
	}
	public String 		getEpl() { return epl; }
	public String 		getSubId() { return subId; }
	public String 		getRuleDescription() { return ruleDescription; }
	public List<String> getUserArgs() { return userArgs; }
	public PersonVo 	getPv() { return pv; }
	public String 		getCreateTime() { return createTime; }
	public List<String> getRuleArgsDescription() { return ruleArgsDescription; }
	public List<String> getRuleArgsExample() { return ruleArgsExample; }
	public String 		getPriority() { return priority; }
	public List<String> getEventIdList() { return eventIdList; }
	public List<Object> getEventArgs() { return eventArgs; }
	public void setEpl(String epl) { this.epl = epl; }
	public void setSubId(String subId) { this.subId = subId; }
	public void setUserArgs(List<String> userArgs) { this.userArgs = userArgs; }
	public void setPv(PersonVo pv) { this.pv = pv; }
	public void setRuleDescription(String ruleDescription) { this.ruleDescription = ruleDescription; }
	public void setRuleArgsDescription(List<String> ruleArgsDescription) { this.ruleArgsDescription = ruleArgsDescription; }
	public void setRuleArgsExample(List<String> ruleArgsExample) { this.ruleArgsExample = ruleArgsExample; }
	public void setPriority(String priority) { this.priority = priority; }
	public void setEventIdList(List<String> eventIdList) { this.eventIdList = eventIdList; }
	public void setEventArgs(List<Object> eventArgs) { this.eventArgs = eventArgs; }
}
