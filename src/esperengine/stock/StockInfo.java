package esperengine.stock;

import java.util.Date;
import java.util.List;
public class StockInfo {
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
	private List<Integer> buyOneStockCount; // 1-5 "买一"报价
	private List<Double> buyOnePrice;

	private List<Integer> sellOneStockCount; // 1-5
	private List<Double> sellOnePrice;

	Date time;

	public StockInfo(String code, String name,
	                 Double todayOpenPrice, Double yesterdayOpenPrice,
	                 Double curPrice, Double todayMaxPrice,
	                 Double todayMinPrice, Integer dealStockCount,
	                 Double dealStockMoney, List<Integer> buyOneStockCount,
	                 List<Double> buyOnePrice, List<Integer> sellOneStockCount,
	                 List<Double> sellOnePrice, Date time) {
		this.code = code;
		this.name = name;
		this.todayOpenPrice = todayOpenPrice;
		this.yesterdayOpenPrice = yesterdayOpenPrice;
		this.curPrice = curPrice;
		this.todayMaxPrice = todayMaxPrice;
		this.todayMinPrice = todayMinPrice;
		this.dealStockCount = dealStockCount;
		this.dealStockMoney = dealStockMoney;
		this.buyOneStockCount = buyOneStockCount;
		this.buyOnePrice = buyOnePrice;
		this.sellOneStockCount = sellOneStockCount;
		this.sellOnePrice = sellOnePrice;
		this.time = time;
	}

	public StockInfo(String stockCode, String stockName, Double stockPrice, Date nowTime) {
		this.code = stockCode;
		this.name = stockName;
		this.curPrice = stockPrice;
		this.time = nowTime;
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
	public List<Integer> getBuyOneStockCount() { return buyOneStockCount; }
	public List<Double> getBuyOnePrice() { return buyOnePrice; }
	public List<Integer> getSellOneStockCount() { return sellOneStockCount; }
	public List<Double> getSellOnePrice() { return sellOnePrice; }

	public Date getTime() { return time; }

	public String toString() {
		return code + ", " + name + ", " + curPrice.toString() + ", " + time.toString();
	}
}
