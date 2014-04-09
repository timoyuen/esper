package esperengine.listener;

import java.util.Date;

import com.espertech.esper.client.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
public class StockListener implements UpdateListener {

	private int pct = 3;
    static Log log = LogFactory.getLog(StockListener.class);

	public StockListener() {}
	public StockListener(int percentage) {
		pct = percentage;
	}

	@Override
	public void update(EventBean[] newData, EventBean[] oldData) {
//		Double prePrice = (Double) newData[0].get("curPrice");
//		Double postPrice = (Double) newData[newData.length - 1].get("curPrice");
//		Double portion = Math.abs((prePrice - postPrice) / postPrice);
//		if (portion >= (double) pct / 100)
//			System.out.println("涨幅大于" + pct + "%");
//		else
//			System.out.println("涨幅小于" + pct + "%");

//		Date preTime = null;
//		Date postTime = null;
//		if (newData != null)
//			preTime = (Date) newData[0].get("time");
//		if (oldData != null)
//			postTime = (Date) oldData[0].get("time");
//		System.out.println();
//		System.out.println("preTime : " + preTime);
//		System.out.println("postTime: " + postTime);
//		System.out.println();
		log.info("hey");
		// log.info(newData[0].getUnderlying().toString());
	}

}
