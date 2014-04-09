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
public final class EsperEngine implements ServletContextListener
{
	CrawlerScheduler crawler = null;
    static Log log = LogFactory.getLog(EsperEngine.class);
    private ServletContext context = null;
    CepConfig cep = null;
    @Override
    public void contextInitialized(ServletContextEvent event) {
        this.context = event.getServletContext();
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
			crawler.setTrigger("trigger3", "group1-morning", "0/5 0-59 0-24 ? * MON-FRI");
			// crawler.setTrigger("trigger4", "group2-afternoon", "0/3 0-59 0-12 ? * MON-FRI");
			crawler.start();
		} catch (SchedulerException e) {
			System.out.println(e);
		}
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                log.info(String.format("deregistering jdbc driver: %s", driver));
            } catch (SQLException e) {
                log.fatal(String.format("Error deregistering driver %s", driver), e);
            }
        }
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
        for(Thread t : threadArray) {
            System.out.println(t.getName());
            if (t.getName().contains("com.espertech.esper") ||
               t.getName().contains("Abandoned connection cleanup thread")) {
                synchronized(t) {
                    t.stop(); //don't complain, it works
                }
            }
        }
        try {
            System.out.println("I'm Leaving");
        	crawler.shutdown();
        } catch (SchedulerException e) {
        	System.out.println(e);
        }
    }

    public static ruleInserted(long subId) {
        RuleSubscriptionDAO rsd = StockDAOFactory.getRuleSubscriptionDAOInstance();
        PersonVo pv = new PersonVo();
        String epl = rsd.getEPLWithSubId(subId, pv);
        String eplName = String.valueOf(subId);
        cep.createEPL(epl, eplName, new StockListener(pv));
    }

    private void initRule() {
        RuleSubscriptionDAO rsd = StockDAOFactory.getRuleSubscriptionDAOInstance();
        List<String> epl = rsd.getAllEPLRules();
        for (String str : epl) {
            cep.createEPL(str, eplName, new StockListener(pv));
        }
    }

}