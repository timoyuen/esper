package esperengine.stock;
import java.util.List;
import login.person.*;
import com.espertech.esper.client.*;

public interface StockDetailDAO {
	public int insertStockDetail(String stockCode, StockInfo si);
	public List<Object> getAllStockDetail();
	public List<Object> getStockDetailWithStockCode(List<String> stcList);
}