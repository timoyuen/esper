package esperengine.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.text.*;
import java.lang.Thread;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import esperengine.cepConfig.*;
import esperengine.stock.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StockCrawlerJob implements Job {
	JobDataMap dataMap = null;
    static Log log = LogFactory.getLog(StockCrawlerJob.class);
	public StockCrawlerJob()
	{
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		dataMap = context.getJobDetail().getJobDataMap();
		StockInfo stock;
		StockCode.startGenStockBean();
		while ((stock = StockCode.nextStockBean()) != null) {
			CepConfig.sendEvent(dataMap.get("cepRT"), stock);

			StockDetailDAO sdd = StockDAOFactory.getStockDetailInstance();
			sdd.insertStockDetail(stock.getCode(), stock);

			// log.info("Send Event-> Code: " + stock.getCode() +
			//                    ", Price: " + stock.getCurPrice() + ", " + stock.getCurTime());
		}
	}
}