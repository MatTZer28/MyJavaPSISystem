package excel;

public class SellDataItems {
	private String sellId;
	private String customerId;
	private String customerName;
	private String combineName;
	private String sellAmount;
	private String sellPrice;
	private String totalSellPrice;

	public SellDataItems(String sellId, String customerId, String customerName, String combineName, String sellAmount, String sellPrice,
			String totalSellPrice) {
		super();
		this.sellId = sellId;
		this.customerId = customerId;
		this.customerName = customerName;
		this.combineName = combineName;
		this.sellAmount = sellAmount;
		this.sellPrice = sellPrice;
		this.totalSellPrice = totalSellPrice;
	}

	public String getSellId() {
		return sellId;
	}

	public void setSellId(String sellId) {
		this.sellId = sellId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCombineName() {
		return combineName;
	}

	public void setCombineName(String combineName) {
		this.combineName = combineName;
	}

	public String getSellAmount() {
		return sellAmount;
	}

	public void setSellAmount(String sellAmount) {
		this.sellAmount = sellAmount;
	}

	public String getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getTotalSellPrice() {
		return totalSellPrice;
	}

	public void setTotalSellPrice(String totalSellPrice) {
		this.totalSellPrice = totalSellPrice;
	}

}
