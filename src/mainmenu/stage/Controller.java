package mainmenu.stage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import company.information.stage.CompanyInfomation;
import customer.inforamtion.stage.CustomerInfomation;
import empolyee.information.stage.EmpolyeeInfomation;
import vendor.information.stage.VendorInfomation;

public class Controller {
	
	@FXML
	public void companyInfoSettingButtonClicked(ActionEvent event) throws Exception {
		main.Main.setCompanyInfomationStage(new Stage());
		new CompanyInfomation().launchScene(main.Main.getCompanyInfomationStage());
	}
	
	@FXML
	public void vendorInfoSettingButtonClicked(ActionEvent event) throws Exception {
		main.Main.setVendorInformationStage(new Stage());
		new VendorInfomation().launchScene(main.Main.getVendorInformationStage());
	}
	
	@FXML
	public void empolyeeInfoSettingButtonClicked(ActionEvent event) throws Exception {
		main.Main.setEmpolyeeInformationStage(new Stage());
		new EmpolyeeInfomation().launchScene(main.Main.getEmpolyeeInformationStage());
	}
	
	@FXML
	public void customerInfoSettingButtonClicked(ActionEvent event) throws Exception {
		main.Main.setCustomerInformationStage(new Stage());
		new CustomerInfomation().launchScene(main.Main.getCustomerInformationStage());
	}
}
