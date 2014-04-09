package esperengine.stock;

import java.util.Date;
import java.util.List;
public class StockRuleVo {
	private int eplId;
	private String eplStr = null;
	private String eplDescription = null;
	private List<String> ruleArgsDescription = null;
	private List<String> ruleArgsExample = null;
	public StockRuleVo(int eplId, String eplStr, String eplDescription,
	                   List<String> ruleArgsDescription, List<String> ruleArgsExample) {
		this.eplId = eplId;
	 	this.eplStr = eplStr;
	 	this.eplDescription = eplDescription;
		this.ruleArgsDescription = ruleArgsDescription;
		this.ruleArgsExample = ruleArgsExample;
	}
	public int getEplId() { return eplId; }
	public String getEplStr() { return eplStr; }
	public String getEplDescription() { return eplDescription; }
	public List<String> getRuleArgsDescription() { return ruleArgsDescription; }
	public List<String> getRuleArgsExample() { return ruleArgsExample; }
	public String toString() {
		return eplStr + ", " + eplDescription;
	}
}
