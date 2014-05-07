package esperengine;
import java.util.*;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.SchedulerException;

import esperengine.cepConfig.CepConfig;
import esperengine.crawler.*;
import esperengine.stock.StockInfo;
import esperengine.listener.*;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.sql.*;
import java.lang.Thread;
import esperengine.stock.*;
import login.person.*;
import helper.*;
public class StockEsperInstance implements EsperInstance {
    static Log log = LogFactory.getLog(StockEsperInstance.class);
	CrawlerScheduler crawler = null;
    CepConfig cep = null;
	public StockEsperInstance() {
		cep = new CepConfig();
		cep.addEventType("Stock", StockInfo.class);
		initRule();
		StockCode.initStockCode();
		try {
			crawler = new CrawlerScheduler("stock.crawler", "stock");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cepRT", cep.getRuntime());
			crawler.setJobDetail(StockCrawlerJob.class, map);
			// crawler.setTrigger("trigger1", "group1-morning", "0/5 30-59 9 ? * MON-FRI");
			// crawler.setTrigger("trigger2", "group1-morning", "0/5 * 10 ? * MON-FRI");
			crawler.setTrigger("trigger3", "group1-morning", "0/5 0-59 0-23 ? * *");
			// crawler.setTrigger("trigger4", "group2-afternoon", "0/3 0-59 0-12 ? * MON-FRI");
			log.info("crawler starting");
			crawler.start();
			log.info("crawler started");
		} catch (SchedulerException e) {
			System.out.println(e);
		}
	}
	public void insertNewSub(int subId) {

        RuleSubscriptionDAO rsd = StockDAOFactory.getRuleSubscriptionDAOInstance();
        RuleSubscriptionVo rule = rsd.getEPLWithSubId(subId);
        String eplStr = rule.getEpl();
        List<Object> lo = rule.getEventArgs();
        List<String> ls = rule.getEventIdList();
        StockInsertEventDAO ed = StockDAOFactory.getStockInsertEventDAOInstance();
        int i = 0;
        StockEsperInstance sei = (StockEsperInstance)(EsperEngine.getInstance("Stock"));
        if (lo != null) {
            for (Object o : lo) {
                List<String> oo = (List<String>) o;
                String eventId = ls.get(i);
                StockInsertEventVo s = ed.getInsertEventWithId(eventId);
                String rep = s.getEventName() + subId + System.currentTimeMillis();
                String eventStr = s.getEventStr();

                eventStr = eventStr.replaceAll(s.getEventName(), rep);
                eplStr = eplStr.replaceAll(s.getEventName(), rep);
                insertNewEvent(eventStr, oo, ""+subId+i);
                i++;
            }
        }
        insertNewSubWithEvent(eplStr, subId);
        // List<StockInsertEventVo> lsiv = sied.getAllInsertEvent();
        // for (StockInsertEventVo stockInsert : lsiv) {
        //     if (eplStr.indexOf(stockInsert.getEventName()) >= 0) {
        //         String eventStr = stockInsert.getEventStr();
        //         String rep = stockInsert.getEventName() + id + System.currentTimeMillis();
        //         eventStr.replaceAll(stockInsert.getEventName(), rep);
        //         eplStr = eplStr.replaceAll(stockInsert.getEventName(), rep);

        //         sei.insertNewEvent(eventStr, eventArgs, rs + i);
        //     }
        // }
        if (CepConfig.isEPLValid(eplStr, rule.getUserArgs())) {
            cep.createEPL(eplStr, rule.getUserArgs(), rule.getSubId(), new StockListener(subId));
        }
    }

    public void insertNewSubWithEvent(String eplStr, int subId) {
        RuleSubscriptionDAO rsd = StockDAOFactory.getRuleSubscriptionDAOInstance();
        RuleSubscriptionVo rule = rsd.getEPLWithSubId(subId);
        if (CepConfig.isEPLValid(eplStr, rule.getUserArgs())) {
            cep.createEPL(eplStr, rule.getUserArgs(), rule.getSubId(), new StockListener(subId));
        }
    }

    public void insertNewEvent(String eventStr, List<String> eventArgs, String eventName) {
        if (CepConfig.isEPLValid(eventStr, eventArgs)) {
            cep.createEventEPL(eventStr, eventArgs, eventName);
        }
    }

    public void updateOldSub(int subId, int eventArgsCount) {
        cep.destroyEPL(subId, eventArgsCount);
        insertNewSub(subId);
    }

    private void initRule() {
        RuleSubscriptionDAO rsd = StockDAOFactory.getRuleSubscriptionDAOInstance();
        List<RuleSubscriptionVo> rule = rsd.getAllEPLRules();
        for (RuleSubscriptionVo str : rule) {
            insertNewSub(Integer.parseInt(str.getSubId()));
            // if (CepConfig.isEPLValid(str.getEpl(), str.getUserArgs())) {
            //     cep.createEPL(str.getEpl(), str.getUserArgs(), str.getSubId(),
            //                   new StockListener(Integer.parseInt(str.getSubId())));
            // }
        }
    }
    public void shutdown() {
        try {
            System.out.println("I'm Leaving");
        	crawler.shutdown();
        } catch (SchedulerException e) {
        	System.out.println(e);
        }
    }
}