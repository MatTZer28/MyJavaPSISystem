package empolyee.information.stage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import alertbox.nullid.stage.NullIDAlertBox;
import alertbox.unsaved.stage.UnsavedAlertBox;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;

public class Controller implements Initializable {
	
	@FXML
    private TextField TextField_id;
    @FXML
    private TextField TextField_webSiteAddress;
    @FXML
    private TextField TextField_bankAccount;
    @FXML
    private TextField TextField_mailingAddress;
    @FXML
    private TextField TextField_firstDayOnDuty;
    @FXML
    private TextField TextField_name;
    @FXML
    private TextField TextField_education;
    @FXML
    private TextField TextField_resignDay;
    @FXML
    private TextField TextField_bankName;
    @FXML
    private TextField TextField_search;
    @FXML
    private TextField TextField_cellPhoneNumber;
    @FXML
    private TextField TextField_workPlace;
    @FXML
    private TextField TextField_phoneNumber;
    @FXML
    private TextField TextField_emailAddress;
    @FXML
    private TextField TextField_residenceAddress;
    @FXML
    private TextField TextField_emerRelation;
    @FXML
    private TextField TextField_emerPhone;
    @FXML
    private TextField TextField_emerContactPerson;
    @FXML
    private TextField TextField_comment;
    @FXML
    private TextField TextField_jobTitle;
    @FXML
    private TextField TextField_birthday;
    @FXML
    private TextField TextField_identityNumber;

    @FXML
    private TableView<table.information.IdAndNameForTable> TableView_table;
    @FXML
    private TableColumn<table.information.IdAndNameForTable, String> TableColumm_id;
    @FXML
    private TableColumn<table.information.IdAndNameForTable, String> TableColumm_name;
    
    @FXML
    private Button Button_insertButton;
    @FXML
    private Button Button_deleteButton;
    @FXML
    private Button Button_editButton;
    @FXML
    private Button Button_saveButton;
    @FXML
    private Button Button_quitButton;
    @FXML
    private Button Button_leaveButton;
    @FXML
    private Button Button_uplaodAvatar;
    @FXML
    private Button Button_delAvatar;
    
    @FXML
    private Label Lable_saveState;
    
    @FXML
    private ImageView ImageView_avatar;
    
    private ResultSet resultsetForTable;

    private ArrayList<TextField> textFieldArrayList = new ArrayList<TextField>();
    
    private boolean isSaved = true;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	createTextFieldArrayList();
    	initDataToTable();
    	addListenerToTextField();
    	addListenerToTextField_search();
	}
    
    public void createTextFieldArrayList() {
    	addAllTextFieldToTextFieldArrayList();
    }
    
    private void addAllTextFieldToTextFieldArrayList() {
    	textFieldArrayList.add(TextField_id);
    	textFieldArrayList.add(TextField_name);
    	textFieldArrayList.add(TextField_jobTitle);
    	textFieldArrayList.add(TextField_identityNumber);
    	textFieldArrayList.add(TextField_birthday);
    	textFieldArrayList.add(TextField_phoneNumber);
    	textFieldArrayList.add(TextField_cellPhoneNumber);
    	textFieldArrayList.add(TextField_emailAddress);
    	textFieldArrayList.add(TextField_webSiteAddress);
    	textFieldArrayList.add(TextField_education);
    	textFieldArrayList.add(TextField_firstDayOnDuty);
    	textFieldArrayList.add(TextField_resignDay);
    	textFieldArrayList.add(TextField_workPlace);
    	textFieldArrayList.add(TextField_residenceAddress);
    	textFieldArrayList.add(TextField_mailingAddress);
    	textFieldArrayList.add(TextField_bankAccount);
    	textFieldArrayList.add(TextField_bankName);
    	textFieldArrayList.add(TextField_emerContactPerson);
    	textFieldArrayList.add(TextField_emerRelation);
    	textFieldArrayList.add(TextField_emerPhone);
    	textFieldArrayList.add(TextField_comment);
    }
    
    public void initDataToTable() {
    	setTableColumm();
		setTableItems();
    }
    
    public void setTableColumm() {
    	TableColumm_id.setCellValueFactory(new PropertyValueFactory<>("id"));
    	TableColumm_name.setCellValueFactory(new PropertyValueFactory<>("name"));
    }
    
    public void setTableItems() {
    	TableView_table.setItems(getData());
    }
    
    public ObservableList<table.information.IdAndNameForTable> getData() {
    	retriveDataFromDBForTableWithSQLException();
    	ObservableList<table.information.IdAndNameForTable> companys = FXCollections.observableArrayList();
    	try {
			do {
				companys.add(new table.information.IdAndNameForTable(resultsetForTable.getString(1), resultsetForTable.getString(2)));
			} while(resultsetForTable.next());
		} catch (SQLException e) {
			return FXCollections.observableArrayList();
		}
		return companys;
    }
    
    public void retriveDataFromDBForTableWithSQLException() {
    	try {
			retriveDataFromDBForTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void retriveDataFromDBForTable() throws SQLException {
    	Statement statement = main.Main.getConnection().createStatement();
    	resultsetForTable = statement.executeQuery("SELECT * FROM javaclassproject2021.empolyeeinformation");
    	resultsetForTable.next();
    }
    
    public void addListenerToTextField() {
    	for (int i = 0; i < textFieldArrayList.size(); i++) {
			textFieldArrayList.get(i).textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					StringProperty textProperty = (StringProperty) observable;
					try {
						if (newValue.equals(resultsetForTable.getString(textFieldArrayList.indexOf(textProperty.getBean()) + 1))) {
							isSaved = true;
							Lable_saveState.setVisible(false);
						}
						else {
							isSaved = false;
							Lable_saveState.setText("（*未儲存）");
							Lable_saveState.setVisible(true);
						}
					} catch (SQLException e) {
						//Empty ResultSet Do Nothing
					}
				}
			});
    	}
    }
    
    public void addListenerToTextField_search() {
    	TextField_search.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.equals("")) {
					setTableItems();
				}
				else {
					compareIdWithNewValueAndShowReslutToTable(newValue);
				}
			}
		});
    }
    
    public void compareIdWithNewValueAndShowReslutToTable(String targetValue) {
    	TableView_table.setItems(getComparedDataWtihTargetValue(targetValue));
    }
    
    public ObservableList<table.information.IdAndNameForTable> getComparedDataWtihTargetValue(String targetValue) {
    	retriveDataFromDBForTableWithSQLException();
    	ObservableList<table.information.IdAndNameForTable> companys = FXCollections.observableArrayList();
    	try {
			do {
				if (resultsetForTable.getString(1).contains(targetValue) || resultsetForTable.getString(3).contains(targetValue)) {
					companys.add(new table.information.IdAndNameForTable(resultsetForTable.getString(1), resultsetForTable.getString(3)));
				}
			} while(resultsetForTable.next());
		} catch (SQLException e) {
			return FXCollections.observableArrayList();
		}
		return companys;
    }
    
    @FXML
    public void tableOnClicked() {
    	
    	retriveDataFromDBForTableWithSQLException();
    	ShowSelectedDataToTableWithSQLException();
    	
    }
    
    public void ShowSelectedDataToTableWithSQLException() {
    		try {
    			do {
    				if (getTableSelectedIdWithNullPointerException().equals(resultsetForTable.getString(1))) {
    					showDataSelectedInTableToTextFieldWithSQLException();
    					Button_deleteButton.setDisable(false);
    					Button_editButton.setDisable(false);
    					break;
    				}
    			} while(resultsetForTable.next());
			} catch (SQLException e) {
				//Do Nothing
			}
    }
    
    public String getTableSelectedIdWithNullPointerException() {
    	try {
    		return TableView_table.getSelectionModel().getSelectedItem().getId();
		} catch (NullPointerException e) {
			return "0";
		}
    }
    
    public void showDataSelectedInTableToTextFieldWithSQLException() {
    	try {
			showDataSelectedInTableToTextField();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void showDataSelectedInTableToTextField() throws SQLException, IOException {
    	for (int i = 0; i < textFieldArrayList.size(); i++) {
			textFieldArrayList.get(i).setText(resultsetForTable.getString(i + 1));
		}
    	
    	InputStream inputStream = resultsetForTable.getBlob(22).getBinaryStream();
    	ImageView_avatar.setImage(new Image(inputStream));
    	inputStream.close();
    }
    
    @FXML
	public void deleteButtonClicked(ActionEvent event) throws SQLException {
    	
    	PreparedStatement delStatement = main.Main.getConnection().prepareStatement("DELETE FROM javaclassproject2021.empolyeeinformation WHERE EmpolyeeID = ?");
    	
    	delStatement.setString(1, getTableSelectedIdWithNullPointerException());
    	
    	delStatement.execute();
    	delStatement.close();
    	
    	Image avatarImage = new Image("/resources/avatar.png");
    	ImageView_avatar.setImage(avatarImage);
    	
    	clearAllTextField();
    	setTableItems();
    	
    	Button_deleteButton.setDisable(true);
    	Button_editButton.setDisable(true);
    	
    	Lable_saveState.setVisible(false);
    	
    	isSaved = true;
	}
    
    public void clearAllTextField() {
    	for (int i = 0; i < textFieldArrayList.size(); i++) {
			textFieldArrayList.get(i).setText("");
		}
    }
    
    @FXML
	public void editButtonClicked(ActionEvent event)  {
    	
    	setAllTextFieldEditable();
    	
    	Button_insertButton.setDisable(true);
    	Button_deleteButton.setDisable(true);
    	Button_editButton.setDisable(true);
    	
    	Button_saveButton.setDisable(false);
    	Button_quitButton.setDisable(false);
    	Button_uplaodAvatar.setDisable(false);
    	Button_delAvatar.setDisable(false);
    	
    	TableView_table.setDisable(true);
    	
    	Lable_saveState.setVisible(true);
    	Lable_saveState.setText("（請填入欲修改的資料）");
    }
    
    public void setAllTextFieldEditable() {
    	for (int i = 0; i < textFieldArrayList.size(); i++) {
			textFieldArrayList.get(i).setEditable(true);
		}
    }
    
    @FXML
	public void insertButtonClicked(ActionEvent event)  {
    	
    	clearAllTextField();
    	setAllTextFieldEditable();
    	
    	Image avatarImage = new Image("/resources/avatar.png");
    	ImageView_avatar.setImage(avatarImage);
    	
    	Button_insertButton.setDisable(true);
    	Button_deleteButton.setDisable(true);
    	Button_editButton.setDisable(true);
    	Button_leaveButton.setDisable(true);
    	
    	Button_saveButton.setDisable(false);
    	Button_quitButton.setDisable(false);
    	Button_uplaodAvatar.setDisable(false);
    	Button_delAvatar.setDisable(false);
    	
    	TableView_table.setDisable(true);
    	
    	Lable_saveState.setVisible(true);
    	Lable_saveState.setText("（請填入欲新增的資料）");
    }
    
    @FXML
	public void saveButtonClicked(ActionEvent event) {
    	
    	if (TextField_id.getText().equals("")) {
    		showNullIDAlertBoxWithException();
    	}
    	else {
    		replaceDataToDBWithSQLException();
        	setAllTextFieldUneditable();
        	
        	Button_insertButton.setDisable(false);
        	Button_leaveButton.setDisable(false);
        	
        	Button_deleteButton.setDisable(true);
        	Button_editButton.setDisable(true);
        	Button_saveButton.setDisable(true);
        	Button_quitButton.setDisable(true);
        	Button_uplaodAvatar.setDisable(true);
        	Button_delAvatar.setDisable(true);
        	
        	Lable_saveState.setText("（儲存成功）");;
        	
        	TableView_table.setDisable(false);
        	
        	isSaved = true;
    	}
    }
    
    public void showNullIDAlertBoxWithException() {
    	try {
    		showNullIDAlertBox();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void showNullIDAlertBox() throws Exception {
    	main.Main.setNullIdAlertBoxStage(new Stage());
    	new NullIDAlertBox().launchScene(main.Main.getNullIdAlertBoxStage());
    }
    
    public void setAllTextFieldUneditable() {
    	for (int i = 0; i < textFieldArrayList.size(); i++) {
			textFieldArrayList.get(i).setEditable(false);
		}
    }
    
    public void replaceDataToDBWithSQLException() {
    	try {
			replaceDataToDB();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void replaceDataToDB() throws SQLException, IOException {
    	PreparedStatement statement = main.Main.getConnection().prepareStatement("REPLACE INTO javaclassproject2021.empolyeeinformation "
    			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    	
    	for (int i = 0; i < textFieldArrayList.size(); i++) {
    		statement.setString(i + 1, textFieldArrayList.get(i).getText());
    	}
    	
    	InputStream inputStream = getAvatarImage();
    	statement.setBlob(22, inputStream);
    	
    	statement.execute();
    	statement.close();
    	
    	inputStream.close();
    	
    	setTableItems();
    }
    
    public InputStream getAvatarImage() {
    	BufferedImage bufferedImage = SwingFXUtils.fromFXImage(ImageView_avatar.getImage(), null);
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    	
    	try {
			ImageIO.write(bufferedImage, "png", outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}   
    	
    	byte[] res  = outputStream.toByteArray();
    	
    	try {
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
        return new ByteArrayInputStream(res);
    }
    
    @FXML
    public void quitButtonClicked() {
    	
    	retriveDataFromDBForTableWithSQLException();
    	ShowSelectedDataToTableWithSQLException();
    	
    	clearAllTextField();
    	setAllTextFieldUneditable();
    	
    	Image avatarImage = new Image("/resources/avatar.png");
    	ImageView_avatar.setImage(avatarImage);
    	
    	Button_insertButton.setDisable(false);
    	Button_leaveButton.setDisable(false);
    	
    	Button_deleteButton.setDisable(true);
    	Button_editButton.setDisable(true);
    	Button_saveButton.setDisable(true);
    	Button_quitButton.setDisable(true);
    	Button_uplaodAvatar.setDisable(true);
    	Button_delAvatar.setDisable(true);
    	
    	TableView_table.setDisable(false);
    	
    	Lable_saveState.setVisible(false);
    	
    	isSaved = true;
    }
    
    @FXML
	public void leaveButtonClicked(ActionEvent event) throws SQLException {
    	if (isSaved == true) {
    		resultsetForTable.close();
    		main.Main.getEmpolyeeInformationStage().close();
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
			if (TextField_id.getText().equals("")) {
	    		showNullIDAlertBoxWithException();
	    	}
			else {
				saveButtonClicked(new ActionEvent());
				resultsetForTable.close();
				main.Main.getEmpolyeeInformationStage().close();
			}
		}
		else {
			resultsetForTable.close();
			main.Main.getEmpolyeeInformationStage().close();
		}
    }
    
    public alertbox.unsaved.stage.Controller getUnsavedAlertBoxController() {
   	 return alertbox.unsaved.stage.UnsavedAlertBox.getFxmlLoader().getController();
   }
    
    @FXML
    public void uplaodAvatarButtonClicked(ActionEvent event) {
    	FileInputStream avatarImage;
		try {
			avatarImage = new FileInputStream(FileSelected());
			ImageView_avatar.setImage(new Image(avatarImage));
	    	avatarImage.close();
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		} catch (NullPointerException e) {
			//Select None Do Nothing
		}
    }
    
    public File FileSelected() {
    	FileChooser fileChooser = new FileChooser();
    	
    	FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
    	FileChooser.ExtensionFilter extFilterjpg = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
    	FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
    	FileChooser.ExtensionFilter extFilterpng = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
    	
    	fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);
    	
    	return fileChooser.showOpenDialog(null);
    }
    
    @FXML
    public void delAvatarButtonClicked(ActionEvent event) throws FileNotFoundException {
    	Image avatarImage = new Image("/resources/avatar.png");
    	ImageView_avatar.setImage(avatarImage);
    }
}
