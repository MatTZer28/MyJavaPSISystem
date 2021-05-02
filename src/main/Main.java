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
	private static Stage productSettingStage;

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
		return productSettingStage;
	}

	public static void setProductSettingStage(Stage productSettingStage) {
		Main.productSettingStage = productSettingStage;
	}

	public void createConnectionToMySQLDataBase() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaclassproject2021", "root", "MatTZer9020");
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
