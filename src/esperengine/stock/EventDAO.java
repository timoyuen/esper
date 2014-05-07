package esperengine.stock;
import java.util.List;
import login.person.*;
import com.espertech.esper.client.*;

public interface EventDAO {
	public int insertEvent(List<Object> newData, List<Object> oldData, String subscriptionId);
	public EventVo getEventWithEventId(String eventId);
	public List<EventVo> getAllEventWithSubId(String subId);
}