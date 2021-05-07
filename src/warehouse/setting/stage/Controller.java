package warehouse.setting.stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import alertbox.nullid.stage.NullIDAlertBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;


public class Controller implements Initializable {
    
	@FXML
	private TextField TextField_search;
	
    @FXML
    private TableView<warehouseDataForTable> TableView_warehouseTable;
    @FXML
    private TableView<?> TableView_productTable;
    @FXML
    private TableColumn<warehouseDataForTable, String> TableColumn_id;
    @FXML
    private TableColumn<warehouseDataForTable, String> TableColumn_name;
    @FXML
    private TableColumn<warehouseDataForTable, String> TableColumn_address;
    @FXML
    private TableColumn<warehouseDataForTable, String> TableColumn_phoneNumber;
    @FXML
    private TableColumn<warehouseDataForTable, String> TableColumn_faxNumber;
    @FXML
    private TableColumn<warehouseDataForTable, String> TableColumn_pId;
    @FXML
    private TableColumn<?, String> TableColumn_pName;
    @FXML
    private TableColumn<?, String> TableColumn_pUnit;
    @FXML
    private TableColumn<?, Integer> TableColumn_pInventory;
    
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
    
    private ResultSet resultsetForTable;
    
    private boolean isSaved = true;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	initDataToWarehouseTable();
    	addListenerToTextField_search();
	}
    
    public void initDataToWarehouseTable() {
    	setWarehouseTableColumn();
    	setWarehouseTableItems();
    }
    
    public void setWarehouseTableColumn() {
    	TableColumn_id.setCellValueFactory(new PropertyValueFactory<>("warehouseId"));
    	TableColumn_name.setCellValueFactory(new PropertyValueFactory<>("name"));
    	TableColumn_address.setCellValueFactory(new PropertyValueFactory<>("address"));
    	TableColumn_phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    	TableColumn_faxNumber.setCellValueFactory(new PropertyValueFactory<>("faxNumber"));
    }
    
    public void setWarehouseTableItems() {
    	TableView_warehouseTable.setItems(getWarehouseData());
    }
    
    public ObservableList<warehouseDataForTable> getWarehouseData() {
    	retriveDataFromDBForTableWithSQLExceptionByTableName("warehouse");
    	ObservableList<warehouseDataForTable> warehouses = FXCollections.observableArrayList();
    	try {
			do {
				warehouses.add(new warehouseDataForTable(resultsetForTable.getString(1), resultsetForTable.getString(2), resultsetForTable.getString(3), resultsetForTable.getString(4), resultsetForTable.getString(5)));
			} while(resultsetForTable.next());
		} catch (SQLException e) {
			return FXCollections.observableArrayList();
		}
		return warehouses;
    }
    
    public static class warehouseDataForTable {
    	private String warehouseId;
		private String name;
    	private String address;
    	private String phoneNumber;
    	private String faxNumber;
    	
    	warehouseDataForTable(String warehouseId, String name, String address, String phoneNumber, String faxNumber) {
			this.warehouseId = warehouseId;
			this.name = name;
			this.address = address;
			this.phoneNumber = phoneNumber;
			this.faxNumber = faxNumber;
		}

		public String getWarehouseId() { return warehouseId; }
		public void setWarehouseId(String warehouseId) { this.warehouseId = warehouseId; }
		
		public String getName() { return name; }
		public void setName(String name) { this.name = name; }
		
		public String getAddress() { return address; }
		public void setAddress(String address) { this.address = address; }
		
		public String getPhoneNumber() { return phoneNumber; }
		public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
		
		public String getFaxNumber() { return faxNumber; }
		public void setFaxNumber(String faxNumber) { this.faxNumber = faxNumber; }
    }
    
    public void retriveDataFromDBForTableWithSQLExceptionByTableName(String tableName) {
    	try {
			retriveDataFromDBForTableByTableName(tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void retriveDataFromDBForTableByTableName(String tableName) throws SQLException {
    	Statement statement = main.Main.getConnection().createStatement();
    	resultsetForTable = statement.executeQuery("SELECT * FROM javaclassproject2021." + tableName);
    	resultsetForTable.next();
    }
    
    public void addListenerToTextField_search() {
    	TextField_search.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.equals("")) {
					setWarehouseTableItems();
				}
				else {
					compareIdWithNewValueAndShowReslutToTable(newValue);
				}
			}
		});
    }
    
    public void compareIdWithNewValueAndShowReslutToTable(String targetValue) {
    	TableView_warehouseTable.setItems(getComparedDataWtihTargetValue(targetValue));
    }
    
    public ObservableList<warehouseDataForTable> getComparedDataWtihTargetValue(String targetValue) {
    	retriveDataFromDBForTableWithSQLExceptionByTableName("warehouse");
    	ObservableList<warehouseDataForTable> warehouses = FXCollections.observableArrayList();
    	try {
			do {
				if (resultsetForTable.getString(1).contains(targetValue) || resultsetForTable.getString(2).contains(targetValue) || resultsetForTable.getString(3).contains(targetValue) || resultsetForTable.getString(4).contains(targetValue) || resultsetForTable.getString(5).contains(targetValue)) {
					warehouses.add(new warehouseDataForTable(resultsetForTable.getString(1), resultsetForTable.getString(2), resultsetForTable.getString(3), resultsetForTable.getString(4), resultsetForTable.getString(5)));
				}
			} while(resultsetForTable.next());
		} catch (SQLException e) {
			return FXCollections.observableArrayList();
		}
		return warehouses;
    }
    
    @FXML
    public void warehouseTableOnClicked() {
    	
    	if (isSaved == true) {
    		retriveDataFromDBForTableWithSQLExceptionByTableName("warehouse");
    		ShowSelectedDataToTableWithSQLException();
    	}
    }
    
    public void ShowSelectedDataToTableWithSQLException() {
    	try {
			do {
				if (getTableSelectedIdWithNullPointerException().equals(resultsetForTable.getString(1))) {
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
    		return TableView_warehouseTable.getSelectionModel().getSelectedItem().getWarehouseId();
		} catch (NullPointerException e) {
			return "0";
		}
    }
    
    @FXML
    void insertButtonClicked(ActionEvent event) {
    	TableView_warehouseTable.setEditable(true);
    	
    	isSaved = false;
    	
    	createNewRowInTable();
    	createTextFieldToTableColumn();
    	setOnEditOnNewRow();
    	
    	Button_saveButton.setDisable(false);
    	Button_quitButton.setDisable(false);
    	
    	Button_insertButton.setDisable(true);
    	Button_deleteButton.setDisable(true);
    	Button_editButton.setDisable(true);
    	Button_leaveButton.setDisable(true);
    	TextField_search.setDisable(true);
    }
    
    public void createNewRowInTable() {
    	TableView_warehouseTable.getItems().add(new warehouseDataForTable("", "", "", "", ""));
    	TableView_warehouseTable.setItems(TableView_warehouseTable.getItems());
    }
    
    public void createTextFieldToTableColumn() {
    	
    	TableColumn_id.setCellFactory(TextFieldTableCell.forTableColumn());
    	TableColumn_id.setOnEditCommit(e -> {
        		e.getTableView().getItems().get(e.getTablePosition().getRow()).setWarehouseId(e.getNewValue());
    	});
    	
    	TableColumn_name.setCellFactory(TextFieldTableCell.forTableColumn());
    	TableColumn_name.setOnEditCommit(e -> {
        		e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue());
    	});
    	
    	TableColumn_address.setCellFactory(TextFieldTableCell.forTableColumn());
    	TableColumn_address.setOnEditCommit(e -> {
        		e.getTableView().getItems().get(e.getTablePosition().getRow()).setAddress(e.getNewValue());
    	});
    	TableColumn_phoneNumber.setCellFactory(TextFieldTableCell.forTableColumn());
    	TableColumn_phoneNumber.setOnEditCommit(e -> {
        		e.getTableView().getItems().get(e.getTablePosition().getRow()).setPhoneNumber(e.getNewValue());
    	});
    	TableColumn_faxNumber.setCellFactory(TextFieldTableCell.forTableColumn());
    	TableColumn_faxNumber.setOnEditCommit(e -> {
        		e.getTableView().getItems().get(e.getTablePosition().getRow()).setFaxNumber(e.getNewValue());
    	});
    
    }
    
    public void setOnEditOnNewRow() {
    	TableView_warehouseTable.getSelectionModel().clearAndSelect(TableView_warehouseTable.getItems().size() - 1);
    	int selectedRow = TableView_warehouseTable.getSelectionModel().getSelectedIndex();
    	TableView_warehouseTable.edit(selectedRow, TableColumn_id);
	}
    

    @FXML
    void deleteButtonClicked(ActionEvent event) throws SQLException {
    	PreparedStatement delStatement = main.Main.getConnection().prepareStatement("DELETE FROM javaclassproject2021.warehouse WHERE  WarehouseID = ?");
    	delStatement.setString(1, getTableSelectedIdWithNullPointerException());
    	delStatement.execute();
    	delStatement.close();
    	
    	setWarehouseTableItems();
    }

    @FXML
    void editButtonClicked(ActionEvent event) {
    	
    	TableView_warehouseTable.setEditable(true);
    	
    	isSaved = false;
    	
    	createTextFieldToTableColumn();
    	
    	Button_saveButton.setDisable(false);
    	Button_quitButton.setDisable(false);
    	
    	Button_insertButton.setDisable(true);
    	Button_deleteButton.setDisable(true);
    	Button_editButton.setDisable(true);
    	Button_leaveButton.setDisable(true);
    	TextField_search.setDisable(true);
    	
    }
    
    @FXML
    void saveButtonClicked(ActionEvent event) {
    	
    	for(warehouseDataForTable rowItems : TableView_warehouseTable.getItems()) {
    		if (rowItems.getWarehouseId() == "") {
    			showNullIDAlertBoxWithException();
    			return;
    		}
    	}

    	TableView_warehouseTable.setEditable(false);
    	
    	isSaved = true;

    	updateDataToDBWithSQLException();
    	removeTextFieldAndUpdateTableCell();
    	setWarehouseTableItems();
    	
    	Button_insertButton.setDisable(false);
    	Button_deleteButton.setDisable(false);
    	Button_editButton.setDisable(false);
    	Button_leaveButton.setDisable(false);
    	TextField_search.setDisable(false);
    	
    	Button_saveButton.setDisable(true);
    	Button_quitButton.setDisable(true);
    	
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
    
    public void updateDataToDBWithSQLException() {
    	try {
    		updateDataToDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void updateDataToDB() throws SQLException {
    	for (int i = 0; i < TableView_warehouseTable.getItems().size(); i++) {
    		PreparedStatement statement = main.Main.getConnection().prepareStatement("INSERT INTO javaclassproject2021.warehouse VALUES (?, ?, ?, ?, ?) "
    				+ "ON DUPLICATE KEY UPDATE WarehouseID = ?, Name = ?, Address = ?, PhoneNumber = ?, FaxNumber= ?");
    		statement.setString(1, TableView_warehouseTable.getItems().get(i).getWarehouseId());
    		statement.setString(2, TableView_warehouseTable.getItems().get(i).getName());
    		statement.setString(3, TableView_warehouseTable.getItems().get(i).getAddress());
    		statement.setString(4, TableView_warehouseTable.getItems().get(i).getPhoneNumber());
    		statement.setString(5, TableView_warehouseTable.getItems().get(i).getFaxNumber());
    		statement.setString(6, TableView_warehouseTable.getItems().get(i).getWarehouseId());
    		statement.setString(7, TableView_warehouseTable.getItems().get(i).getName());
    		statement.setString(8, TableView_warehouseTable.getItems().get(i).getAddress());
    		statement.setString(9, TableView_warehouseTable.getItems().get(i).getPhoneNumber());
    		statement.setString(10, TableView_warehouseTable.getItems().get(i).getFaxNumber());
    		statement.execute();
    		statement.close();
    	}
    }
    
    public void removeTextFieldAndUpdateTableCell() {
    	TableColumn_name.setCellFactory(param -> new UpdataedTableCell());
    	TableColumn_address.setCellFactory(param -> new UpdataedTableCell());
    	TableColumn_phoneNumber.setCellFactory(param -> new UpdataedTableCell());
    	TableColumn_faxNumber.setCellFactory(param -> new UpdataedTableCell());
    }
    
    private class UpdataedTableCell extends TableCell<warehouseDataForTable, String> {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if(empty){
            	super.setText(null);
            }else {
                setText(item);
            }
        }
    }

    @FXML
    void quitButtonClicked(ActionEvent event) {
    	
    	TableView_warehouseTable.setEditable(false);
    	
    	isSaved = true;
    	
    	retriveDataFromDBForTableWithSQLExceptionByTableName("warehouse");
    	removeTextFieldAndUpdateTableCell();
    	setWarehouseTableItems();
    	
    	Button_insertButton.setDisable(false);
    	Button_deleteButton.setDisable(false);
    	Button_editButton.setDisable(false);
    	Button_leaveButton.setDisable(false);
    	TextField_search.setDisable(false);
    	
    	Button_saveButton.setDisable(true);
    	Button_quitButton.setDisable(true);
    }
    
    @FXML
    void leaveButtonClicked(ActionEvent event) throws SQLException {
    	resultsetForTable.close();
		main.Main.getWarehouseSettingStage().close();
    }
}
