package esperengine.stock;

import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.text.*;
import java.io.*;
public class StockInfo implements Serializable {
	private String code;
	/// order by http://hq.sinajs.cn/list=xxxx
	private String name; 			 /// stock Name
	private Double todayOpenPrice;   /// 今日开盘价；
	private Double yesterdayOpenPrice; /// 昨日收盘价；


	private Double curPrice; 	/// 当前价格
	private Double todayMaxPrice;   /// 今日最低价
	private Double todayMinPrice;   /// 今日最低价
	private Integer dealStockCount; /// 成交的股票数
	private Double dealStockMoney;  /// 成交金额
	////////////////////////////////////////////
	Double buyOnePrice, buyTwoPrice,
		   buyThreePrice, buyFourPrice,
		   buyFivePrice, sellOnePrice,
		   sellTwoPrice, sellThreePrice,
		   sellFourPrice, sellFivePrice;
	Integer buyOneCount, buyTwoCount,
		   buyThreeCount, buyFourCount,
		   buyFiveCount, sellOneCount,
		   sellTwoCount, sellThreeCount,
		   sellFourCount, sellFiveCount;

	Date curTime;
	public StockInfo(String code, String name) {
		this.code = code;
		this.name = name;
	}
	public StockInfo(String code, String name,
	                 Double todayOpenPrice, Double yesterdayOpenPrice,
	                 Double curPrice, Double todayMaxPrice,
	                 Double todayMinPrice, Integer dealStockCount,
	                 Double dealStockMoney,
					 Double buyOnePrice, Double buyTwoPrice,
					 Double buyThreePrice, Double buyFourPrice,
					 Double buyFivePrice, Double sellOnePrice,
					 Double sellTwoPrice, Double sellThreePrice,
					 Double sellFourPrice, Double sellFivePrice,
					 Integer buyOneCount, Integer buyTwoCount,
					 Integer buyThreeCount, Integer buyFourCount,
					 Integer buyFiveCount, Integer sellOneCount,
					 Integer sellTwoCount, Integer sellThreeCount,
					 Integer sellFourCount, Integer sellFiveCount, Date curTime) {
		this.code = code;
		this.name = name;
		this.todayOpenPrice = todayOpenPrice;
		this.yesterdayOpenPrice = yesterdayOpenPrice;
		this.curPrice = curPrice;
		this.todayMaxPrice = todayMaxPrice;
		this.todayMinPrice = todayMinPrice;
		this.dealStockCount = dealStockCount;
		this.dealStockMoney = dealStockMoney;
		this.buyOnePrice = buyOnePrice;
		this.buyThreePrice = buyThreePrice;
		this.buyFivePrice = buyFivePrice;
		this.sellTwoPrice = sellTwoPrice;
		this.sellFourPrice = sellFourPrice;
		this.buyOneCount = buyOneCount;
		this.buyThreeCount = buyThreeCount;
		this.buyFiveCount = buyFiveCount;
		this.sellTwoCount = sellTwoCount;
		this.sellFourCount = sellFourCount;
		this.buyTwoPrice = buyTwoPrice;
		this.buyFourPrice = buyFourPrice;
		this.sellOnePrice = sellOnePrice;
		this.sellThreePrice = sellThreePrice;
		this.sellFivePrice = sellFivePrice;
		this.buyTwoCount = buyTwoCount;
		this.buyFourCount = buyFourCount;
		this.sellOneCount = sellOneCount;
		this.sellThreeCount = sellThreeCount;
		this.sellFiveCount = sellFiveCount;
		this.curTime = curTime;
	}

	public StockInfo(String stockCode, String stockName, Double stockPrice, Date nowTime) {
		this.code = stockCode;
		this.name = stockName;
		this.curPrice = stockPrice;
		this.curTime = nowTime;
	}

	public String getCode() { return code; }
	public String getName() { return name; }
	public Double getTodayOpenPrice() { return todayOpenPrice; }
	public Double getYesterdayOpenPrice() { return yesterdayOpenPrice; }
	public Double getCurPrice() { return curPrice; }
	public Double getTodayMaxPrice() { return todayMaxPrice; }
	public Double geTodayMinPrice() { return todayMinPrice; }
	public Integer getDealStockCount() { return dealStockCount; }
	public Double getDealStockMoney() { return dealStockMoney; }
	public Double getBuyOnePrice() { return buyOnePrice;}
	public Double getBuyTwoPrice() { return buyTwoPrice;}
	public Double getBuyThreePrice() { return buyThreePrice;}
	public Double getBuyFourPrice() { return buyFourPrice;}
	public Double getBuyFivePrice() { return buyFivePrice;}
	public Double getSellOnePrice() { return sellOnePrice; }
	public Double getSellTwoPrice() { return sellTwoPrice; }
	public Double getSellThreePrice() { return sellThreePrice; }
	public Double getSellFourPrice() { return sellFourPrice; }
	public Double getSellFivePrice() { return sellFivePrice; }
	public Integer getBuyOneCount() { return buyOneCount; }
	public Integer getBuyTwoCount() { return buyTwoCount; }
	public Integer getBuyThreeCount() { return buyThreeCount; }
	public Integer getBuyFourCount() { return buyFourCount; }
	public Integer getBuyFiveCount() { return buyFiveCount; }
	public Integer getSellOneCount() { return sellOneCount; }
	public Integer getSellTwoCount() { return sellTwoCount; }
	public Integer getSellThreeCount() { return sellThreeCount; }
	public Integer getSellFourCount() { return sellFourCount; }
	public Integer getSellFiveCount() { return sellFiveCount; }
/*	public List<Integer> getBuyOneStockCount() { return buyOneStockCount; }
	public List<Double> getBuyOnePrice() { return buyOnePrice; }
	public List<Integer> getSellOneStockCount() { return sellOneStockCount; }
	public List<Double> getSellOnePrice() { return sellOnePrice; }*/

	public Date getCurTime() { return curTime; }
	public String getCurTimeString() {
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return time.format(curTime);
	}

	public String toString() {
		return code + ", " + name + ", " + curPrice.toString() + ", " + curTime.toString();
	}
}
