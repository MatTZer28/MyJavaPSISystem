package company.information.stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import alertbox.unsaved.stage.UnsavedAlertBox;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller implements Initializable {
	
	@FXML
    private TextField TextField_chineseName;
	@FXML
    private TextField TextField_englishName;
	@FXML
    private TextField TextField_id;
	@FXML
    private TextField TextField_uniNumber;
	@FXML
    private TextField TextField_manager;
	@FXML
    private TextField TextField_phoneNumber;
	@FXML
    private TextField TextField_faxNumber;
	@FXML
    private TextField TextField_chineseAddress;
    @FXML
    private TextField TextField_englishAddress;
	@FXML
    private TextField TextField_emailAddress;
	@FXML
    private TextField TextField_webSiteAddress;
	@FXML
    private Label Lable_saveState;
	@FXML
    private Button Button_saveButton;
    
	private ArrayList<TextField> textFieldArrayList = new ArrayList<TextField>();
	
	private ResultSet resultset;
	
	private boolean isSaved = true;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		createTextFieldArrayList();
		initDataToTextField();
		addListenerToTextField();
	}
	
    public void createTextFieldArrayList() {
    	addAllTextFieldToTextFieldArrayList();
    }
    
    private void addAllTextFieldToTextFieldArrayList() {
    	textFieldArrayList.add(TextField_id);
    	textFieldArrayList.add(TextField_chineseName);
    	textFieldArrayList.add(TextField_englishName);
    	textFieldArrayList.add(TextField_manager);
    	textFieldArrayList.add(TextField_uniNumber);
    	textFieldArrayList.add(TextField_phoneNumber);
    	textFieldArrayList.add(TextField_faxNumber);
    	textFieldArrayList.add(TextField_chineseAddress);
    	textFieldArrayList.add(TextField_englishAddress);
    	textFieldArrayList.add(TextField_emailAddress);
    	textFieldArrayList.add(TextField_webSiteAddress);
    }
    
    public void initDataToTextField() {
    	retriveDataFromDBWithSQLException();
    	showDataToTextFieldWithSQLException();
    }
    
    public void retriveDataFromDBWithSQLException() {
		try {
			retriveDataFromDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void retriveDataFromDB() throws SQLException {
    	Statement statement = main.Main.getConnection().createStatement();
		resultset = statement.executeQuery("SELECT * FROM javaclassproject2021.companyinformation");
    	resultset.next();
    }
    
    public void showDataToTextFieldWithSQLException() {
		try {
			showDataToTextField();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void showDataToTextField() throws SQLException {
    	for (int i = 0; i < textFieldArrayList.size(); i++) {
			textFieldArrayList.get(i).setText(resultset.getString(i + 2));
		}
    }
    
    public void addListenerToTextField() {
    	for (int i = 0; i < textFieldArrayList.size(); i++) {
			textFieldArrayList.get(i).textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					StringProperty textProperty = (StringProperty) observable;
					try {
						if (newValue.equals(resultset.getString(textFieldArrayList.indexOf(textProperty.getBean()) + 2))) {
							isSaved = true;
							Lable_saveState.setVisible(false);
							Button_saveButton.setDisable(true);
						}
						else {
							isSaved = false;
							Lable_saveState.setText("（*未儲存）");
							Lable_saveState.setVisible(true);
							Button_saveButton.setDisable(false);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			});
    	}
    }
	
    @FXML
	public void leaveButtonClicked(ActionEvent event) throws SQLException {
    	if (isSaved == true) {
    		resultset.close();
    		main.Main.getCompanyInfomationStage().close();
    	}
    	else {
    		showUnsavedAlertBoxWithException();
    		executeUnsavedAlertBoxAnswerWithSQLException();
    	}
	}
    
    public void showUnsavedAlertBoxWithException() {
    	try {
			showUnsavedAlertBox();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void showUnsavedAlertBox() throws Exception {
    	main.Main.setUnsavedAlertBoxStage(new Stage());
    	new UnsavedAlertBox().launchScene(main.Main.getUnsavedAlertBoxStage());
    }
    
    public void executeUnsavedAlertBoxAnswerWithSQLException() {
    	try {
			executeUnsavedAlertBoxAnswer();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void executeUnsavedAlertBoxAnswer() throws SQLException {
    	alertbox.unsaved.stage.Controller UnsavedAlertBoxController = getUnsavedAlertBoxController();
		if (UnsavedAlertBoxController.isSaveAnswer() == true) {
			insertDataToDBWithSQLException();
			resultset.close();
			main.Main.getCompanyInfomationStage().close();
		}
		else {
			resultset.close();
			main.Main.getCompanyInfomationStage().close();
		}
    }
    
    public alertbox.unsaved.stage.Controller getUnsavedAlertBoxController() {
    	 return alertbox.unsaved.stage.UnsavedAlertBox.getFxmlLoader().getController();
    }
	
    @FXML
	public void saveButtonClicked(ActionEvent event) {
		insertDataToDBWithSQLException();
		setSaveStateLableToSaved();
	}
    
    public void insertDataToDBWithSQLException() {
    	try {
			insertDataToDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void insertDataToDB() throws SQLException {
    	PreparedStatement statement = main.Main.getConnection().prepareStatement("UPDATE javaclassproject2021.companyinformation SET CompanyID = ?, ChineseName = ?, EnglishName = ?"
    			+ ", Manager = ?, UniNumber = ?, PhoneNumber = ?, FaxNumber = ?, ChineseAddress = ?, EnglishAddress = ?, EmailAddress = ?, WebSiteAddress = ? WHERE ID = 1");
    	
    	statement.setString(1, TextField_id.getText());
    	statement.setString(2, TextField_chineseName.getText());
    	statement.setString(3, TextField_englishName.getText());
    	statement.setString(4, TextField_manager.getText());
    	statement.setString(5, TextField_uniNumber.getText());
    	statement.setString(6, TextField_phoneNumber.getText());
    	statement.setString(7, TextField_faxNumber.getText());
    	statement.setString(8, TextField_chineseAddress.getText());
    	statement.setString(9, TextField_englishAddress.getText());
    	statement.setString(10, TextField_emailAddress.getText());
    	statement.setString(11, TextField_webSiteAddress.getText());
    	
    	statement.execute();
    	statement.close();
    	
    	retriveDataFromDBWithSQLException();
    }
    
    public void setSaveStateLableToSaved() {
    	isSaved = true;
    	Lable_saveState.setText("（儲存成功）");
    	Lable_saveState.setVisible(true);
    	Button_saveButton.setDisable(true);
    }
}
