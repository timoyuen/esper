package esperengine.stock;
import java.util.List;
import login.person.*;
import com.espertech.esper.client.*;

public interface StockInsertEventDAO {
	public boolean insert(String eventStr, String eventName, String eventDescription,
	                             String [] argExample, String [] argDescription);
	public List<StockInsertEventVo> getAllInsertEvent();
	public StockInsertEventVo getInsertEventWithId(String id);
}