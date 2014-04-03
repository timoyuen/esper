package esperengine;
import java.util.Map;
import java.util.HashMap;

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

public final class EsperEngine implements ServletContextListener
{
	CrawlerScheduler crawler;
    private ServletContext context = null;
    @Override
    public void contextInitialized(ServletContextEvent event) {
    	return;
        /*this.context = event.getServletContext();
        // log("contextInitialized()");
		CepConfig cep = new CepConfig();
        System.out.println("step0");
		cep.addEventType("Stock", StockInfo.class);
        System.out.println("step1");
		int sec = 5;
		String epl = "select time as fisrtTime, first(time) as lastTime from "
													+ "Stock.win:time(" + sec + " sec)";
        System.out.println("step2");
		cep.createEPL(epl, new StockListener());
        System.out.println("step3");
		try {
			crawler = new CrawlerScheduler("stock.crawler", "stock");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cepRT", cep.getRuntime());
			crawler.setJobDetail(StockCrawlerJob.class, map);
			crawler.setTrigger("trigger1", "group1-morning", "0/5 30-59 9 ? * MON-FRI");
			crawler.setTrigger("trigger2", "group1-morning", "0/5 * 10 ? * MON-FRI");
			crawler.setTrigger("trigger3", "group1-morning", "0/5 0-59 13-23 ? * MON-FRI");
			crawler.setTrigger("trigger4", "group2-afternoon", "0/3 0-59 0-12 ? * MON-FRI");
			crawler.start();
		} catch (SchedulerException e) {
			System.out.println(e);
		}*/
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
    	return;
       /* try {
        	crawler.shutdown();
        } catch (SchedulerException e) {
        	System.out.println(e);
        }
        System.out.println("I'm Leaving");*/
    }
}