package esperengine.stock;
import java.util.List;
import java.util.ArrayList;
import java.lang.Thread;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.text.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StockCode
{
	private static boolean isInitialed = false;
	private final static int lenPerRequest = 50;
	private static List<String> stockCode = new ArrayList<String>();  /// notice that it's STATIC
	// private final static String requestURLBase = "http://hq.sinajs.cn/?list=";
	private final static String requestURLBase = "http://localhost/espercode?list=";
	private static List<StockInfo> stockBeanList = null;
	private static Iterator<StockInfo> it;
    static Log log = LogFactory.getLog(StockCode.class);

	public static void initStockCode() {
		stockCode = StockDAOFactory.getStockCodeDAOInstance().getAllStockCode();
		isInitialed = true;
	}

	public static void addStockCode(String newStockCode) {
		stockCode.add(newStockCode);
	}

	public static void delStockCode(String removeStockCode) {
		stockCode.remove(removeStockCode);
	}

	public static final List<String> getStockCode() {
		if (!isInitialed)
			initStockCode();
		return stockCode;
	}
	public synchronized static void startGenStockBean() {
		stockBeanList = new ArrayList<StockInfo>();
		List<Thread> threadList = new ArrayList<Thread>();
		generateStockBean(threadList);
		try {
			for (Thread thread : threadList) {
				synchronized (thread) {
					if (thread != null)
						thread.wait();
				}
			}
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		it = stockBeanList.iterator();

	}
	public synchronized static StockInfo nextStockBean() {
		if (it.hasNext())
			return it.next();
		else
			return null;
	}

	private static void generateStockBean(List<Thread> threadList) {
		int count = 1;
		String stockCodeStr = "";
		boolean remain = false;
		for (String sc: stockCode) {
			if (count == 1)
				stockCodeStr = sc;
			else
				stockCodeStr += "," + sc;
			if (count == lenPerRequest) {
				threadList.add(sendRequest(stockCodeStr));
				count = 1;
				remain = false;
			}
			count++;
		}
		if (count != lenPerRequest)
			threadList.add(sendRequest(stockCodeStr));
	}

	private static Thread sendRequest(final String stockCodeStr)  {
		if (stockCodeStr == null || stockCodeStr.equals(""))
			return null;
		Thread t = new Thread() {
			public void run() {
				synchronized (this) {
					try {
						final URL stockUrl = new URL(requestURLBase + stockCodeStr);
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
					notify();
				}
			}
		};
		t.start();
		return t;
	}

	private static void parseStockData(String stockInputData) {
		String [] content = stockInputData.split("\"");
		if (content.length == 0)
			return;
		String stockCode = content[0].substring(content[0].length() - 9, content[0].length() - 1);
		//, content[0].length());

		if(content[1].equals("")) {
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

		Double buyOnePrice = Double.parseDouble(temp[11]),
			   buyTwoPrice = Double.parseDouble(temp[13]),
			   buyThreePrice = Double.parseDouble(temp[15]),
			   buyFourPrice = Double.parseDouble(temp[17]),
			   buyFivePrice = Double.parseDouble(temp[19]),
			   sellOnePrice = Double.parseDouble(temp[21]),
			   sellTwoPrice = Double.parseDouble(temp[23]),
			   sellThreePrice = Double.parseDouble(temp[25]),
			   sellFourPrice = Double.parseDouble(temp[27]),
			   sellFivePrice = Double.parseDouble(temp[29]);
		Integer buyOneCount = Integer.parseInt(temp[10]),
				buyTwoCount = Integer.parseInt(temp[12]),
				buyThreeCount = Integer.parseInt(temp[14]),
				buyFourCount = Integer.parseInt(temp[16]),
				buyFiveCount = Integer.parseInt(temp[18]),
				sellOneCount = Integer.parseInt(temp[20]),
				sellTwoCount = Integer.parseInt(temp[22]),
				sellThreeCount = Integer.parseInt(temp[24]),
				sellFourCount = Integer.parseInt(temp[26]),
				sellFiveCount = Integer.parseInt(temp[28]);

		/*List<Double> buyOnePrice = new ArrayList<Double>();
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
		sellOnePrice.add(Double.parseDouble(temp[29]));*/
		Date curTime;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			curTime = dateFormat.parse(temp[30] + " " + temp[31]); // string to date use format
		} catch (ParseException e) {
			log.info("Url Parse Error");
			return;
		}
		StockInfo stock = new StockInfo(stockCode, stockName,
		                                stockTodayOpenPrice, stockYesterdayOpenPrice,
		                                stockCurPrice, stockTodayMaxPrice,
										stockTodayMinPrice, dealStockCount,
										dealStockMoney,
										buyOnePrice, buyTwoPrice,
										buyThreePrice, buyFourPrice,
										buyFivePrice, sellOnePrice,
										sellTwoPrice, sellThreePrice,
										sellFourPrice, sellFivePrice,
										buyOneCount, buyTwoCount,
										buyThreeCount, buyFourCount,
										buyFiveCount, sellOneCount,
										sellTwoCount, sellThreeCount,
										sellFourCount, sellFiveCount, curTime);
		synchronized(stockBeanList) {
			stockBeanList.add(stock);
		}
	}

	public static String isStockCodeValid(String sc) {
		String name = null;
		try {
			final URL stockUrl = new URL(requestURLBase + sc);
			BufferedReader stockURLReader = new BufferedReader(new InputStreamReader(stockUrl.openStream(), "GB2312"));
			String stockInputData = stockInputData = stockURLReader.readLine();
			String [] content = stockInputData.split("\"");
			if (!content[1].equals("")) {
				String [] temp = content[1].split(",");
				name = temp[0];
			}
			stockURLReader.close();
		} catch (MalformedURLException me){
			me.printStackTrace();
		} catch(IOException ie){
			ie.printStackTrace();
		}
		return name;
	}
}