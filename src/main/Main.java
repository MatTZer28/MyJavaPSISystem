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
	
	private static Stage CompanyInfomationStage;
	private static Stage VendorInformationStage;
	private static Stage EmpolyeeInformationStage;
	private static Stage CustomerInformationStage;
	private static Stage UnsavedAlertBoxStage;
	private static Stage NullIdAlertBoxStage;
	private static Stage WarehouseSettingStage;

	private static Connection connection;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		new MainMenu().launchScene(primaryStage);
		
		
		createConnectionToMySQLDataBase();
		
		
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

	public void createConnectionToMySQLDataBase() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaclassproject2021", "root", "kevin*3700924");
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
