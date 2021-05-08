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
    private TableView<WarehouseDataForTable> TableView_warehouseTable;
    @FXML
    private TableView<ProductDataForTable> TableView_productTable;
    @FXML
    private TableColumn<WarehouseDataForTable, String> TableColumn_id;
    @FXML
    private TableColumn<WarehouseDataForTable, String> TableColumn_name;
    @FXML
    private TableColumn<WarehouseDataForTable, String> TableColumn_address;
    @FXML
    private TableColumn<WarehouseDataForTable, String> TableColumn_phoneNumber;
    @FXML
    private TableColumn<WarehouseDataForTable, String> TableColumn_faxNumber;
    @FXML
    private TableColumn<ProductDataForTable, String> TableColumn_pId;
    @FXML
    private TableColumn<ProductDataForTable, String> TableColumn_pName;
    @FXML
    private TableColumn<ProductDataForTable, String> TableColumn_pUnit;
    @FXML
    private TableColumn<ProductDataForTable, Integer> TableColumn_pInventory;
    
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
    
    private ResultSet resultsetForWarehouseTable;
    
    private ResultSet resultsetForProductTable;
    
    private boolean isSaved = true;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	initDataToWarehouseTable();
    	setProductTableColumn();
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
    
    public ObservableList<WarehouseDataForTable> getWarehouseData() {
    	retriveDataFromDBForTableWithSQLExceptionByTableName("warehouse");
    	ObservableList<WarehouseDataForTable> warehouses = FXCollections.observableArrayList();
    	try {
			do {
				warehouses.add(new WarehouseDataForTable(resultsetForWarehouseTable.getString(1), resultsetForWarehouseTable.getString(2), resultsetForWarehouseTable.getString(3), resultsetForWarehouseTable.getString(4), resultsetForWarehouseTable.getString(5)));
			} while(resultsetForWarehouseTable.next());
		} catch (SQLException e) {
			return FXCollections.observableArrayList();
		}
		return warehouses;
    }
    
    public static class WarehouseDataForTable {
    	private String warehouseId;
		private String name;
    	private String address;
    	private String phoneNumber;
    	private String faxNumber;
    	
    	WarehouseDataForTable(String warehouseId, String name, String address, String phoneNumber, String faxNumber) {
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
    	resultsetForWarehouseTable = statement.executeQuery("SELECT * FROM javaclassproject2021." + tableName);
    	resultsetForWarehouseTable.next();
    }
    
    public void setProductTableColumn() {
    	TableColumn_pId.setCellValueFactory(new PropertyValueFactory<>("productId"));
    	TableColumn_pName.setCellValueFactory(new PropertyValueFactory<>("name"));
    	TableColumn_pUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
    	TableColumn_pInventory.setCellValueFactory(new PropertyValueFactory<>("inventory"));
    }
    
    public static class ProductDataForTable {
    	private String productId;
    	private String name;
    	private String unit;
    	private String inventory;
    	
    	ProductDataForTable(String productId, String name, String unit, String inventory)  {
    		this.productId = productId;
    		this.name = name;
    		this.unit = unit;
    		this.inventory = inventory;
    	}

		public String getProductId() { return productId; }
		public void setProductId(String productId) { this.productId = productId; }

		public String getName() { return name; }
		public void setName(String name) { this.name = name; }

		public String getUnit() { return unit; }
		public void setUnit(String unit) { this.unit = unit; }

		public String getInventory() { return inventory; }
		public void setInventory(String inventory) { this.inventory = inventory; }
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
    
    public ObservableList<WarehouseDataForTable> getComparedDataWtihTargetValue(String targetValue) {
    	retriveDataFromDBForTableWithSQLExceptionByTableName("warehouse");
    	ObservableList<WarehouseDataForTable> warehouses = FXCollections.observableArrayList();
    	try {
			do {
				if (resultsetForWarehouseTable.getString(1).contains(targetValue) || resultsetForWarehouseTable.getString(2).contains(targetValue) || resultsetForWarehouseTable.getString(3).contains(targetValue) || resultsetForWarehouseTable.getString(4).contains(targetValue) || resultsetForWarehouseTable.getString(5).contains(targetValue)) {
					warehouses.add(new WarehouseDataForTable(resultsetForWarehouseTable.getString(1), resultsetForWarehouseTable.getString(2), resultsetForWarehouseTable.getString(3), resultsetForWarehouseTable.getString(4), resultsetForWarehouseTable.getString(5)));
				}
			} while(resultsetForWarehouseTable.next());
		} catch (SQLException e) {
			return FXCollections.observableArrayList();
		}
		return warehouses;
    }
    
    @FXML
    public void warehouseTableOnClicked() {
    	if (isSaved == true) {
    		retriveDataFromDBForTableWithSQLExceptionByTableName("warehouse");
    		setDeleteAndEditButtonEnableWithSQLException();
    	}
    	setProductTableItems();
    }
    
    public void setDeleteAndEditButtonEnableWithSQLException() {
    	try {
			do {
				if (getTableSelectedWarehouseIdWithNullPointerException().equals(resultsetForWarehouseTable.getString(1))) {
					Button_deleteButton.setDisable(false);
					Button_editButton.setDisable(false);
					break;
				}
			} while(resultsetForWarehouseTable.next());
		} catch (SQLException e) {
			//Do Nothing
		}
    }
    
    public String getTableSelectedWarehouseIdWithNullPointerException() {
    	try {
    		return TableView_warehouseTable.getSelectionModel().getSelectedItem().getWarehouseId();
		} catch (NullPointerException e) {
			return "0";
		}
    }
    
    public void setProductTableItems() {
    	TableView_productTable.setItems(getProductData());
    }
    
    public ObservableList<ProductDataForTable> getProductData() {
    	retriveProductDataFromDBForTableWithSQLExceptionByTableName("product");
    	ObservableList<ProductDataForTable> products = FXCollections.observableArrayList();
    	try {
			do {
				products.add(new ProductDataForTable(resultsetForProductTable.getString(1), resultsetForProductTable.getString(2), resultsetForProductTable.getString(3), String.valueOf(resultsetForProductTable.getInt(4))));
			} while(resultsetForProductTable.next());
		} catch (SQLException e) {
			return FXCollections.observableArrayList();
		}
		return products;
    }
    
    public void retriveProductDataFromDBForTableWithSQLExceptionByTableName(String tableName) {
    	try {
    		retriveProductDataFromDBForTableByTableName(tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void retriveProductDataFromDBForTableByTableName(String tableName) throws SQLException {
    	Statement statement = main.Main.getConnection().createStatement();
    	resultsetForProductTable = statement.executeQuery("SELECT product.ProductID, product.Name, product.Unit, productstoreinwarehouse.Amount FROM javaclassproject2021." + tableName + " INNER JOIN productstoreinwarehouse "
    			+ "ON WarehouseID = " + "\"" + getTableSelectedWarehouseIdWithNullPointerException() + "\"");
		resultsetForProductTable.next();
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
    	TableView_warehouseTable.getItems().add(new WarehouseDataForTable("", "", "", "", ""));
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
    	delStatement.setString(1, getTableSelectedWarehouseIdWithNullPointerException());
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
    	
    	for(WarehouseDataForTable rowItems : TableView_warehouseTable.getItems()) {
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
    
    private class UpdataedTableCell extends TableCell<WarehouseDataForTable, String> {
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
    	resultsetForWarehouseTable.close();
    	resultsetForProductTable.close();
		main.Main.getWarehouseSettingStage().close();
    }
}
