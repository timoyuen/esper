package esperengine;
import java.util.*;
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
    static Log log = LogFactory.getLog(EsperEngine.class);
    private ServletContext context = null;
    private static Map<String, EsperInstance> instance;
    static {
        instance = new HashMap<String, EsperInstance>();
    }
    @Override
    public void contextInitialized(ServletContextEvent event) {
        this.context = event.getServletContext();
		instance.put("Stock", new StockEsperInstance());
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
        for (String key : instance.keySet()) {
            instance.get(key).shutdown();
        }
    }
    public static EsperInstance getInstance(String key) {
        return instance.get(key);
    }
}