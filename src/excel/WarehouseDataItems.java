package excel;

public class WarehouseDataItems {
	private String warehouseId;
	private String warehouseName;
	private String productId;
	private String productName;
	private String productSpecification;
	private String productType;
	private String productUnit;
	private String productTotal;
	private String productSafeAmount;
	private String productVendorName;

	public WarehouseDataItems(String warehouseId, String warehouseName, String productId, String productName,
			String productSpecification, String productType, String productUnit, String productTotal,
			String productSafeAmount, String productVendorName) {
		super();
		this.warehouseId = warehouseId;
		this.warehouseName = warehouseName;
		this.productId = productId;
		this.productName = productName;
		this.productSpecification = productSpecification;
		this.productType = productType;
		this.productUnit = productUnit;
		this.productTotal = productTotal;
		this.productSafeAmount = productSafeAmount;
		this.productVendorName = productVendorName;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductSpecification() {
		return productSpecification;
	}

	public void setProductSpecification(String productSpecification) {
		this.productSpecification = productSpecification;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}

	public String getProductTotal() {
		return productTotal;
	}

	public void setProductTotal(String productTotal) {
		this.productTotal = productTotal;
	}

	public String getProductSafeAmount() {
		return productSafeAmount;
	}

	public void setProductSafeAmount(String productSafeAmount) {
		this.productSafeAmount = productSafeAmount;
	}

	public String getProductVendorName() {
		return productVendorName;
	}

	public void setProductVendorName(String productVendorName) {
		this.productVendorName = productVendorName;
	}

}
