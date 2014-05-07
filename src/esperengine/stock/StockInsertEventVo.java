package esperengine.stock;
import java.util.List;
import login.person.*;
import com.espertech.esper.client.*;

public class StockInsertEventVo {
	String eventId = null;
	String eventName = null;
	String eventStr = null;
	String eventDescription = null;
	List<String> eventArgsDescription = null;
	List<String> eventArgsExample = null;
	public StockInsertEventVo(String eventId, String eventName, String eventStr, String eventDescription,
	                          List<String> eventArgsDescription, List<String> eventArgsExample
	                          ) {
		this.eventId = eventId;
		this.eventName = eventName;
		this.eventStr = eventStr;
		this.eventDescription = eventDescription;
		this.eventArgsDescription = eventArgsDescription;
		this.eventArgsExample = eventArgsExample;
	}

	public String getEventId() { return eventId; }
	public String getEventName() { return eventName; }
	public String getEventStr() { return eventStr; }
	public String getEventDescription() { return eventDescription; }
	public List<String> getEventArgsDescription() { return eventArgsDescription; }
	public List<String> getEventArgsExample() { return eventArgsExample; }

	public void setEventId(String eventId) { this.eventId = eventId; }
	public void setEventName(String eventName) { this.eventName = eventName; }
	public void setEventStr(String eventStr) { this.eventStr = eventStr; }
	public void setEventDescription(String eventDescription) { this.eventDescription = eventDescription; }
	public void setEventArgsDescription(List<String> eventArgsDescription) { this.eventArgsDescription = eventArgsDescription; }
	public void setEventArgsExample(List<String> eventArgsExample) { this.eventArgsExample = eventArgsExample; }
}