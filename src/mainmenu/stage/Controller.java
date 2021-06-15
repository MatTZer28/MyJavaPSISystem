package mainmenu.stage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import product.setting.stage.ProductSetting;

import java.net.URL;
import java.util.ResourceBundle;

import company.information.stage.CompanyInfomation;
import customer.inforamtion.stage.CustomerInfomation;
import empolyee.information.stage.EmpolyeeInfomation;
import vendor.information.stage.VendorInfomation;
import warehouse.setting.stage.WarehouseSetting;

public class Controller implements Initializable {

	@FXML
	private ImageView Button_closeButton;

	@FXML
	private ImageView Button_maximizeButton;

	@FXML
	private ImageView Button_minimizeButton;

	@FXML
	private ToolBar ToolBar_toolBar;
	
	private static ProductSetting productSetting;

	private boolean isMaximize = true;

	public double xOffset;
	public double yOffset;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ToolBar_toolBar.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = main.Main.getPrimaryStage().getX() - event.getScreenX();
				yOffset = main.Main.getPrimaryStage().getY() - event.getScreenY();
			}
		});

		ToolBar_toolBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				main.Main.getPrimaryStage().setX(event.getScreenX() + xOffset);
				main.Main.getPrimaryStage().setY(event.getScreenY() + yOffset);
			}
		});

		ToolBar_toolBar.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {
						if (isMaximize == true) {
							main.Main.getPrimaryStage().setMaximized(false);
							isMaximize = false;
							Button_maximizeButton.setImage(new Image("/resources/windowsIcon/Maximize.png"));
						} else {
							main.Main.getPrimaryStage().setMaximized(true);
							isMaximize = true;
							Button_maximizeButton.setImage(new Image("/resources/windowsIcon/Restore.png"));
						}
					}
				}
			}
		});
	}

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

	@FXML
	public void warehouseSettingButtonClicked(ActionEvent event) throws Exception {
		main.Main.setWarehouseSettingStage(new Stage());
		new WarehouseSetting().launchScene(main.Main.getWarehouseSettingStage());
	}

	@FXML
	public void productSettingButtonClicked(ActionEvent event) throws Exception {
		main.Main.setProductSettingStage(new Stage());
		productSetting = new ProductSetting();
		productSetting.launchScene(main.Main.getProductSettingStage());
	}

	@FXML
	public void closeButtonClicked() {
		main.Main.getPrimaryStage().close();
	}

	@FXML
	public void closeButtonMouseEntered() {
		Button_closeButton.setImage(new Image("/resources/windowsIcon/Close-Hover.png"));
	}

	@FXML
	public void closeButtonMouseExited() {
		Button_closeButton.setImage(new Image("/resources/windowsIcon/Close.png"));
	}

	@FXML
	public void closeButtonMousePressed() {
		Button_closeButton.setImage(new Image("/resources/windowsIcon/Close-Pressed.png"));
	}

	@FXML
	public void maximizeButtonClicked() {
		if (isMaximize == true) {
			main.Main.getPrimaryStage().setMaximized(false);
			isMaximize = false;
			Button_maximizeButton.setImage(new Image("/resources/windowsIcon/Maximize.png"));
		} else {
			main.Main.getPrimaryStage().setMaximized(true);
			isMaximize = true;
			Button_maximizeButton.setImage(new Image("/resources/windowsIcon/Restore.png"));
		}
	}

	@FXML
	public void maximizeButtonMouseEntered() {
		if (isMaximize == true) {
			Button_maximizeButton.setImage(new Image("/resources/windowsIcon/Restore-Hover.png"));
		} else {
			Button_maximizeButton.setImage(new Image("/resources/windowsIcon/Maximize-Hover.png"));
		}
	}

	@FXML
	public void maximizeButtonMouseExited() {
		if (isMaximize == true) {
			Button_maximizeButton.setImage(new Image("/resources/windowsIcon/Restore.png"));
		} else {
			Button_maximizeButton.setImage(new Image("/resources/windowsIcon/Maximize.png"));
		}
	}

	@FXML
	public void maximizeButtonMousePressed() {
		Button_maximizeButton.setImage(new Image("/resources/windowsIcon/Restore-Pressed.png"));
		if (isMaximize == true) {
			Button_maximizeButton.setImage(new Image("/resources/windowsIcon/Restore-Pressed.png"));
		} else {
			Button_maximizeButton.setImage(new Image("/resources/windowsIcon/Maximize-Pressed.png"));
		}
	}

	@FXML
	public void minimizeButtonClicked() {
		main.Main.getPrimaryStage().setIconified(true);
	}

	@FXML
	public void minimizeButtonMouseEntered() {
		Button_minimizeButton.setImage(new Image("/resources/windowsIcon/Minimize-Hover.png"));
	}

	@FXML
	public void minimizeButtonMouseExited() {
		Button_minimizeButton.setImage(new Image("/resources/windowsIcon/Minimize.png"));
	}

	@FXML
	public void minimizeButtonMousePressed() {
		Button_minimizeButton.setImage(new Image("/resources/windowsIcon/Minimize-Pressed.png"));
	}

	public static ProductSetting getProductSetting() {
		return productSetting;
	}
}
