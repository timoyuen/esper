package esperengine.stock;

import java.util.Date;
import java.util.List;
public class StockRuleVo {
	private int eplId;
	private String eplStr = null;
	private String eplDescription = null;
	private List<String> ruleArgsDescription = null;
	private List<String> ruleArgsExample = null;
	private List<String> eventIdList = null;
	public StockRuleVo(int eplId, String eplStr, String eplDescription,
	                   List<String> ruleArgsDescription, List<String> ruleArgsExample, List<String> eventIdList) {
		this.eplId = eplId;
	 	this.eplStr = eplStr;
	 	this.eplDescription = eplDescription;
		this.ruleArgsDescription = ruleArgsDescription;
		this.ruleArgsExample = ruleArgsExample;
		this.eventIdList = eventIdList;
	}
	public StockRuleVo(int eplId, String eplStr, String eplDescription,
	                   List<String> ruleArgsDescription, List<String> ruleArgsExample) {
		this.eplId = eplId;
	 	this.eplStr = eplStr;
	 	this.eplDescription = eplDescription;
		this.ruleArgsDescription = ruleArgsDescription;
		this.ruleArgsExample = ruleArgsExample;
	}
	public List<String> getEventIdList() { return eventIdList; }
	public void setEventIdList(List<String> eventIdList) { this.eventIdList = eventIdList; }
	public int getEplId() { return eplId; }
	public String getEplStr() { return eplStr; }
	public String getEplDescription() { return eplDescription; }
	public List<String> getRuleArgsDescription() { return ruleArgsDescription; }
	public List<String> getRuleArgsExample() { return ruleArgsExample; }
	public String toString() {
		return eplStr + ", " + eplDescription;
	}
}
