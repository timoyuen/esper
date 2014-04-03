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
public class StockCrawlerJob extends StockCode implements Job {
	JobDataMap dataMap = null;
	final static int lenPerRequest = 50;
	public StockCrawlerJob()
	{
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		dataMap = context.getJobDetail().getJobDataMap();
		List<String> stockCode = getStockCode();
		int count = 1;
		String stockCodeStr = "";
		boolean remain = false;
		for (String sc: stockCode) {
			if (count == 1)
				stockCodeStr = sc;
			else
				stockCodeStr += "," + sc;
			if (count == lenPerRequest) {
				sendRequest(stockCodeStr);
				count = 1;
				remain = false;
			}
			count++;
		}
		if (count != lenPerRequest)
			sendRequest(stockCodeStr);
	}

	private void sendRequest(final String stockCode)  {
		if (stockCode == null || stockCode.equals(""))
			return;
		Thread t = new Thread() {
			public void run() {
				try {
					final URL stockUrl = new URL("http://hq.sinajs.cn/?list=" + stockCode);
					System.out.println(stockCode);

					BufferedReader stockURLReader = new BufferedReader(new InputStreamReader(stockUrl.openStream()));
					String stockInputData = null;
					while ((stockInputData = stockURLReader.readLine()) != null) {
						parseStockData(stockInputData);
					}
					stockURLReader.close();
				} catch (MalformedURLException me){
					me.printStackTrace();
				} catch(IOException ie){
					ie.printStackTrace();
				}
			}
		};
		t.start();
	}

	private void parseStockData(String stockInputData) {
		String [] content = stockInputData.split("\"");
		if (content.length == 0)
			return;
		String stockCode = content[0].substring(content[0].length() - 8, content[0].length() - 1); //, content[0].length());

		if(content[1].equals("")) {
			System.out.println("StockCrawlerJob Error");
			System.out.println("Code: "+stockCode);
			return;
		}
		String [] temp = content[1].split(",");
		String stockName = temp[0];
		Double stockTodayOpenPrice = Double.parseDouble(temp[1]);
		Double stockYesterdayOpenPrice = Double.parseDouble(temp[2]);
		Double stockCurPrice = Double.parseDouble(temp[3]);
		Double stockTodayMaxPrice = Double.parseDouble(temp[4]);
		Double stockTodayMinPrice = Double.parseDouble(temp[5]);

		Integer dealStockCount = Integer.parseInt(temp[8]);
		Double dealStockMoney = Double.parseDouble(temp[9]);

		List<Double> buyOnePrice = new ArrayList<Double>();
		List<Integer> buyOneCount = new ArrayList<Integer>();
		buyOneCount.add(Integer.parseInt(temp[10]));
		buyOnePrice.add(Double.parseDouble(temp[11]));
		buyOneCount.add(Integer.parseInt(temp[12]));
		buyOnePrice.add(Double.parseDouble(temp[13]));
		buyOneCount.add(Integer.parseInt(temp[14]));
		buyOnePrice.add(Double.parseDouble(temp[15]));
		buyOneCount.add(Integer.parseInt(temp[16]));
		buyOnePrice.add(Double.parseDouble(temp[17]));
		buyOneCount.add(Integer.parseInt(temp[18]));
		buyOnePrice.add(Double.parseDouble(temp[19]));

		List<Double> sellOnePrice = new ArrayList<Double>();
		List<Integer> sellOneCount = new ArrayList<Integer>();
		sellOneCount.add(Integer.parseInt(temp[20]));
		sellOnePrice.add(Double.parseDouble(temp[21]));
		sellOneCount.add(Integer.parseInt(temp[22]));
		sellOnePrice.add(Double.parseDouble(temp[23]));
		sellOneCount.add(Integer.parseInt(temp[24]));
		sellOnePrice.add(Double.parseDouble(temp[25]));
		sellOneCount.add(Integer.parseInt(temp[26]));
		sellOnePrice.add(Double.parseDouble(temp[27]));
		sellOneCount.add(Integer.parseInt(temp[28]));
		sellOnePrice.add(Double.parseDouble(temp[29]));
		Date curTime;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			curTime = dateFormat.parse(temp[30] + " " + temp[31]); // string to date use format
		} catch (ParseException e) {
			System.out.println("Url Parse Error");
			return;
		}
		StockInfo stock = new StockInfo(stockCode, stockName,
		                                stockTodayOpenPrice, stockYesterdayOpenPrice,
		                                stockCurPrice, stockTodayMaxPrice,
										stockTodayMinPrice, dealStockCount,
										dealStockMoney, buyOneCount,
										buyOnePrice, sellOneCount,
										sellOnePrice, curTime);

		CepConfig.sendEvent(dataMap.get("cepRT"), stock);
		System.out.println("Send Event-> Code: " + stockCode + ", Price: " + stockCurPrice + ", " + curTime);
	}
}