package admin;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import login.person.*;
import esperengine.stock.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.espertech.esper.client.*;
import com.espertech.esper.collection.*;
import esperengine.cepConfig.*;
public class EPLChecker
{
    static Log log = LogFactory.getLog(EPLChecker.class);
    static private Map<String, String> syntax = new HashMap<String, String>();
    static {
    	syntax.put("\\$CODE", "code");
    	syntax.put("\\$NAME", "name");
		syntax.put("\\$TOPENPRICE", "todayOpenPrice");
		syntax.put("\\$YOPENPRICE", "yesterdayOpenPrice");
		syntax.put("\\$CPRICE", "curPrice");
		syntax.put("\\$TMAXPRICE", "todayMaxPrice");
		syntax.put("\\$TMINPRICE", "todayMinPrice");
		syntax.put("\\$DEALCOUNT", "dealStockCount");
		syntax.put("\\$DEALMONEY", "dealStockMoney");
		syntax.put("\\$B1PRICE", "buyOnePrice");
		syntax.put("\\$B2PRICE", "buyTwoPrice");
		syntax.put("\\$B3PRICE", "buyThreePrice");
		syntax.put("\\$B4PRICE", "buyFourPrice");
		syntax.put("\\$B5PRICE", "buyFivePrice");
		syntax.put("\\$S1PRICE", "sellOnePrice");
		syntax.put("\\$S2PRICE", "sellTwoPrice");
		syntax.put("\\$S3PRICE", "sellThreePrice");
		syntax.put("\\$S4PRICE", "sellFourPrice");
		syntax.put("\\$S5PRICE", "sellFivePrice");
		syntax.put("\\$B1COUNT", "buyOneCount");
		syntax.put("\\$B2COUNT", "buyTwoCount");
		syntax.put("\\$B3COUNT", "buyThreeCount");
		syntax.put("\\$B4COUNT", "buyFourCount");
		syntax.put("\\$B5COUNT", "buyFiveCount");
		syntax.put("\\$S1COUNT", "sellOneCount");
		syntax.put("\\$S2COUNT", "sellTwoCount");
		syntax.put("\\$S3COUNT", "sellThreeCount");
		syntax.put("\\$S4COUNT", "sellFourCount");
		syntax.put("\\$S5COUNT", "sellFiveCount");
		syntax.put("STOCK", "Stock");
    }

    public static String replaceEPLKey(String epl) {
    	for (String str : syntax.keySet()) {
			epl = epl.replaceAll(str, syntax.get(str));
		}
		return epl;
    }
}