package esperengine.stock;

import java.util.Date;
import java.util.List;
import com.espertech.esper.client.*;
import java.io.*;
public class EventVo implements Serializable {
	private String eventId;
	private List<Object> newEvent;
	private List<Object> oldEvent;
	private Date createTime;
	private String subscriptionId;
	public EventVo(String eventId, List<Object> newEvent, List<Object> oldEvent,
	               Date createTime, String subscriptionId) {
		this.eventId = eventId;
		this.newEvent = newEvent;
		this.oldEvent = oldEvent;
		this.createTime = createTime;
		this.subscriptionId = subscriptionId;
	}

	public String getEventId() { return eventId; }
	public List<Object> getNewEvent() { return newEvent; }
	public List<Object> getOldEvent() { return oldEvent; }
	public Date getCreateTime() { return createTime; }
	public String getSubscriptionId() { return subscriptionId; }

	public void setEventId(String eventId) { this.eventId = eventId; }
	public void setNewEvent(List<Object> newEvent) { this.newEvent = newEvent; }
	public void setOldEvent(List<Object> oldEvent) { this.oldEvent = oldEvent; }
	public void setCreateTime(Date createTime) { this.createTime = createTime; }
	public void setSubscriptionId(String subscriptionId) { this.subscriptionId = subscriptionId; }
	public String toString() {
		return createTime + ", " + oldEvent;
	}
}
