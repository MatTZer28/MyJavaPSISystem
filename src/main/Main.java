package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import mainmenu.stage.MainMenu;

public class Main extends Application {

	private static Stage PrimaryStage;
	private static Stage CompanyInfomationStage;
	private static Stage VendorInformationStage;
	private static Stage EmpolyeeInformationStage;
	private static Stage CustomerInformationStage;
	private static Stage UnsavedAlertBoxStage;
	private static Stage NullIdAlertBoxStage;
	private static Stage WarehouseSettingStage;
	private static Stage ProductSettingStage;
	private static Stage PurchaseStage;
	private static Stage SellStage;

	private static Connection connection;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		setPrimaryStage(primaryStage);
		new MainMenu().launchScene(getPrimaryStage());

		createConnectionToMySQLDataBase();

	}

	public static Stage getPrimaryStage() {
		return PrimaryStage;
	}

	public static void setPrimaryStage(Stage primaryStage) {
		PrimaryStage = primaryStage;
	}

	public static Stage getCompanyInfomationStage() {
		return CompanyInfomationStage;
	}

	public static void setCompanyInfomationStage(Stage companyInfomationStage) {
		CompanyInfomationStage = companyInfomationStage;
	}

	public static Stage getVendorInformationStage() {
		return VendorInformationStage;
	}

	public static void setVendorInformationStage(Stage vendorInfomationStage) {
		VendorInformationStage = vendorInfomationStage;
	}

	public static Stage getEmpolyeeInformationStage() {
		return EmpolyeeInformationStage;
	}

	public static void setEmpolyeeInformationStage(Stage empolyeeInformationStage) {
		EmpolyeeInformationStage = empolyeeInformationStage;
	}

	public static Stage getCustomerInformationStage() {
		return CustomerInformationStage;
	}

	public static void setCustomerInformationStage(Stage customerInformationStage) {
		CustomerInformationStage = customerInformationStage;
	}

	public static Stage getUnsavedAlertBoxStage() {
		return UnsavedAlertBoxStage;
	}

	public static void setUnsavedAlertBoxStage(Stage unsavedAlertBoxStage) {
		UnsavedAlertBoxStage = unsavedAlertBoxStage;
	}

	public static Stage getNullIdAlertBoxStage() {
		return NullIdAlertBoxStage;
	}

	public static void setNullIdAlertBoxStage(Stage nullIdAlertBoxStage) {
		NullIdAlertBoxStage = nullIdAlertBoxStage;
	}

	public static Stage getWarehouseSettingStage() {
		return WarehouseSettingStage;
	}

	public static void setWarehouseSettingStage(Stage warehouseSettingStage) {
		WarehouseSettingStage = warehouseSettingStage;
	}

	public static Stage getProductSettingStage() {
		return ProductSettingStage;
	}

	public static void setProductSettingStage(Stage productSettingStage) {
		Main.ProductSettingStage = productSettingStage;
	}

	public static Stage getPurchaseStage() {
		return PurchaseStage;
	}

	public static void setPurchaseStage(Stage purchaseStage) {
		PurchaseStage = purchaseStage;
	}

	public static Stage getSellStage() {
		return SellStage;
	}

	public static void setSellStage(Stage sellStage) {
		SellStage = sellStage;
	}

	public void createConnectionToMySQLDataBase() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://172.20.10.4:3306/javaclassproject2021", "user",
					"adNhc7jYzw3tvNgp");
		} catch (ClassNotFoundException e) {
			Logger.getLogger(main.Main.class.getName()).log(Level.SEVERE, null, e);
		} catch (SQLException e) {
			Logger.getLogger(main.Main.class.getName()).log(Level.SEVERE, null, e);
		}

	}

	public static Connection getConnection() {
		return connection;
	}

}
