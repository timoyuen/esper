package esperengine.listener;

import java.util.Date;
import java.util.*;
import com.espertech.esper.client.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import login.person.*;
import helper.*;
import esperengine.stock.*;
public class StockListener implements UpdateListener {

    static Log log = LogFactory.getLog(StockListener.class);
    int subId;
	public StockListener(int subId) {
		this.subId = subId;
	}

	List<String> getSelectItem(String epl) {
		int select = epl.indexOf("SELECT");
		int from = epl.indexOf("FROM");
		String sub = epl.substring(select + "SELECT".length(), from);
		String [] content = sub.split(",");
		for (String s : content)
			System.out.println(s);
		List<String> ret = new ArrayList<String>();
		for (String s : content) {
			ret.add(s.trim());
		}
		log.info(ret);
		return ret;
	}
	@Override
	public void update(EventBean[] newData, EventBean[] oldData) {
		RuleSubscriptionDAO rsd = StockDAOFactory.getRuleSubscriptionDAOInstance();
		RuleSubscriptionVo subscribe = rsd.getEPLWithSubId(subId);
		String epl = subscribe.getEpl();
		List<String> selectItems = getSelectItem(epl);
		int event_id = insertBean(newData, oldData, selectItems);
		if (event_id <= 0)
			return;
		String prio = subscribe.getPriority();
		PersonVo pv = subscribe.getPv();
   		String to = pv.getEmail();
		String title = "INFO " + subscribe.getRuleDescription();
   		String content = "<h1>Subscribtion "+subId+" Triggered</h1>"+
   						 "<div>"+subscribe.getRuleDescription()+"</div>"+
   						 "<div>click the link below to see details</div>"+
   						 "<a href='http://localhost.0x271828.com:8080/esper/management?method=viewGraph&eventid=9'>http://localhost.0x271828.com:8080/esper/management?method=viewGraph&eventid=9</a>";
		/// only send EMail
		if (prio == null)
			return;
		if (prio.equals("0")) {
			// SendMail.send(to, title, content);
		} else {  // BOTH
			title = "IMPORTANT "+subscribe.getRuleDescription();
			// SendMail.send(to, title, content);
			to = pv.getTelephone();
			content = "IMPORTANT! Esper Template Engine has been triggered with id "+subId+", description: "+subscribe.getRuleDescription();
			log.info(content);
			/*if (SendSMS.send(to, content) == -1) {
				log.error("Cannot SendSMS to "+to);
			}*/
		}

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
		// log.info("hey");
		// log.info(newData[0].getUnderlying().toString());
	}

	private List<Object> transEventBean(EventBean[] bean, List<String> select) {
		List<Object> ret = new ArrayList<Object>();
		if (bean == null)
			return null;
		for (EventBean eb: bean) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (String s : select)
				map.put(s, eb.get(s));
			ret.add(map);
		}
		return ret;
	}
	private int insertBean(EventBean[] newData, EventBean[] oldData, List<String> select) {
		EventDAO ed = StockDAOFactory.getEventDAOInstance();
		return ed.insertEvent(transEventBean(newData, select), transEventBean(oldData, select), subId+"");
	}

}
