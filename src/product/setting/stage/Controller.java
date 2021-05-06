package product.setting.stage;

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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
	private TableView<ProductDataForTable> TableView_productTable;
	@FXML
	private TableView<ProdouctStoreInWarehouseDataForTable> TableView_warehouseTable;
	@FXML
    private TableColumn<ProductDataForTable, String> TableColumn_id;
	@FXML
    private TableColumn<ProductDataForTable, String> TableColumn_name;
	@FXML
    private TableColumn<ProductDataForTable, String> TableColumn_specification;
	@FXML
    private TableColumn<ProductDataForTable, String> TableColumn_type;
	@FXML
    private TableColumn<ProductDataForTable, String> TableColumn_unit;
	@FXML
    private TableColumn<ProductDataForTable, String> TableColumn_total;
	@FXML
    private TableColumn<ProductDataForTable, String> TableColumn_cost;
	@FXML
    private TableColumn<ProductDataForTable, String> TableColumn_sellPrice;
	@FXML
    private TableColumn<ProductDataForTable, String> TableColumn_safeAmount;
	@FXML
    private TableColumn<ProductDataForTable, ChoiceBox<String>> TableColumn_vendor;
	@FXML
    private TableColumn<ProdouctStoreInWarehouseDataForTable, String> TableColumn_wId;
	@FXML
    private TableColumn<ProdouctStoreInWarehouseDataForTable, String> TableColumn_wName;
	@FXML
    private TableColumn<ProdouctStoreInWarehouseDataForTable, String> TableColumn_wAmount;
	
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
	private Button Button_newSpace;
	@FXML
	private Button Button_deleteSpace;
	
	private ResultSet resultsetForProductTable;
	
	private ResultSet resultsetForWarehouseTable;
	
	private boolean isSaved = true;
	
	private boolean isOnInsertionState = false;
	
	private String oldProductId;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initDataToProductTable();
		setWarehouseTableColumn();
		addListenerToTextField_search();
	}
	
	public void initDataToProductTable() {
    	setProductTableColumn();
    	setProductTableItems();
    }
	
	public void setProductTableColumn() {
    	TableColumn_id.setCellValueFactory(new PropertyValueFactory<>("productId"));
    	TableColumn_name.setCellValueFactory(new PropertyValueFactory<>("name"));
    	TableColumn_specification.setCellValueFactory(new PropertyValueFactory<>("specification"));
    	TableColumn_type.setCellValueFactory(new PropertyValueFactory<>("type"));
    	TableColumn_unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
    	TableColumn_total.setCellValueFactory(new PropertyValueFactory<>("total"));
    	TableColumn_cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
    	TableColumn_sellPrice.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
    	TableColumn_safeAmount.setCellValueFactory(new PropertyValueFactory<>("safeAmount"));
    	TableColumn_vendor.setCellValueFactory(new PropertyValueFactory<>("vendor"));
    }
	
	public void setProductTableItems() {
		TableView_productTable.setItems(getProductData());
    }
    
    public ObservableList<ProductDataForTable> getProductData() {
    	retriveDataFromDBForProductTableWithSQLExceptionByTableName("product");
    	ObservableList<ProductDataForTable> products = FXCollections.observableArrayList();
    	try {
			do {
				Integer total = resultsetForProductTable.getInt(6);
				Integer cost = resultsetForProductTable.getInt(7);
				Integer sellPrice = resultsetForProductTable.getInt(8);
				Integer safeAmount = resultsetForProductTable.getInt(9);
				
				products.add(new ProductDataForTable(resultsetForProductTable.getString(1), resultsetForProductTable.getString(2), resultsetForProductTable.getString(3), resultsetForProductTable.getString(4), resultsetForProductTable.getString(5), total.toString(),  cost.toString(),  sellPrice.toString(), safeAmount.toString(), resultsetForProductTable.getString(10)));
			} while(resultsetForProductTable.next());
		} catch (SQLException e) {
			return FXCollections.observableArrayList();
		}
		return products;
    }
    
    public static class ProductDataForTable {
    	private String productId;
		private String name;
    	private String specification;
    	private String type;
    	private String unit;
    	private String total;
    	private String cost;
    	private String sellPrice;
    	private String safeAmount;
    	private ChoiceBox<String> vendor;
    	
    	private ResultSet resultsetForChoiceBox;
    	
    	ProductDataForTable(String productId, String name, String specification, String type, String unit, String total, String cost, String sellPrice, String safeAmount, String vendor) throws SQLException {
			this.productId = productId;
			this.name = name;
			this.specification = specification;
			this.type = type;
			this.unit = unit;
			this.total = total;
			this.cost = cost;
			this.sellPrice = sellPrice;
			this.safeAmount = safeAmount;
			
			this.vendor = new ChoiceBox<String>();
			this.vendor.setItems(getVendorData());
			this.vendor.setValue(vendor);
			this.vendor.setDisable(true);
			
			resultsetForChoiceBox.close();
		}
    	
    	public ObservableList<String> getVendorData() {
    		retriveDataFromDBToChoiceBoxWithSQLExceptionByTableName("vendorinformation");
    		ObservableList<String> options = FXCollections.observableArrayList();
    		try {
    			do {
    				options.add(resultsetForChoiceBox.getString(3));
    			} while(resultsetForChoiceBox.next());
    		} catch (SQLException e) {
    			return FXCollections.observableArrayList();
    		}
    		return options;
    	}
    	
    	public void retriveDataFromDBToChoiceBoxWithSQLExceptionByTableName(String tableName) {
        	try {
    			retriveDataFromDBToChoiceBoxByTableName(tableName);
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
        }
        
        public void retriveDataFromDBToChoiceBoxByTableName(String tableName) throws SQLException {
        	Statement statement = main.Main.getConnection().createStatement();
        	resultsetForChoiceBox = statement.executeQuery("SELECT * FROM javaclassproject2021." + tableName);
        	resultsetForChoiceBox.next();
        }

		public String getProductId() { return productId; }
		public void setProductId(String productId) { this.productId = productId; }

		public String getName() { return name; }
		public void setName(String name) { this.name = name; }

		public String getSpecification() { return specification; }
		public void setSpecification(String specification) { this.specification = specification; }

		public String getType() { return type; }
		public void setType(String type) { this.type = type; }

		public String getUnit() { return unit; }
		public void setUnit(String unit) { this.unit = unit; }

		public String getTotal() { return total; }
		public void setTotal(String total) { this.total = total; }

		public String getCost() { return cost; }
		public void setCost(String cost) { this.cost = cost; }

		public String getSellPrice() { return sellPrice; }
		public void setSellPrice(String sellPrice) { this.sellPrice = sellPrice; }

		public String getSafeAmount() { return safeAmount; }
		public void setSafeAmount(String safeAmount) { this.safeAmount = safeAmount; }

		public ChoiceBox<String> getVendor() { return vendor; }
		public void setVendor(ChoiceBox<String> vendor) { this.vendor = vendor; }
    }
    
    public void retriveDataFromDBForProductTableWithSQLExceptionByTableName(String tableName) {
    	try {
			retriveDataFromDBForProductTableByTableName(tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void retriveDataFromDBForProductTableByTableName(String tableName) throws SQLException {
    	Statement statement = main.Main.getConnection().createStatement();
    	resultsetForProductTable = statement.executeQuery("SELECT * FROM javaclassproject2021." + tableName);
    	resultsetForProductTable.next();
    }
    
    public void setWarehouseTableColumn() {
    	TableColumn_wId.setCellValueFactory(new PropertyValueFactory<>("warehouseId"));
    	TableColumn_wName.setCellValueFactory(new PropertyValueFactory<>("warehouseName"));
    	TableColumn_wAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }
    
    public static class ProdouctStoreInWarehouseDataForTable {
    	private ChoiceBox<String> warehouseId;
		private String warehouseName;
		private String amount;
		
    	private ResultSet resultsetForChoiceBox;
		
		public ProdouctStoreInWarehouseDataForTable(String warehouseId, String amount) throws SQLException {
			this.warehouseId = new ChoiceBox<String>();
			this.warehouseId.setItems(getWarehouseData());
			this.warehouseId.setValue(warehouseId);
			this.warehouseId.setDisable(true);
			
			this.warehouseName = retriveWarehouseNameFromDB();
			this.amount = amount;
			
			addListenerToChoiceBox();
			
			resultsetForChoiceBox.close();
		}
    	
    	public ObservableList<String> getWarehouseData() {
    		retriveDataFromDBToChoiceBoxWithSQLExceptionByTableName("warehouse");
    		ObservableList<String> options = FXCollections.observableArrayList();
    		try {
    			do {
    				options.add(resultsetForChoiceBox.getString(1));
    			} while(resultsetForChoiceBox.next());
    		} catch (SQLException e) {
    			return FXCollections.observableArrayList();
    		}
    		return options;
    	}
    	
    	public void retriveDataFromDBToChoiceBoxWithSQLExceptionByTableName(String tableName) {
        	try {
    			retriveDataFromToDBChoiceBoxByTableName(tableName);
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
        }
        
        public void retriveDataFromToDBChoiceBoxByTableName(String tableName) throws SQLException {
        	Statement statement = main.Main.getConnection().createStatement();
        	resultsetForChoiceBox = statement.executeQuery("SELECT * FROM javaclassproject2021." + tableName);
        	resultsetForChoiceBox.next();
        }
        
        public String retriveWarehouseNameFromDB() throws SQLException {
        	Statement statement = main.Main.getConnection().createStatement();
        	ResultSet name = statement.executeQuery("SELECT Name FROM javaclassproject2021.warehouse WHERE WarehouseID = \"" + this.warehouseId + "\"");
        	if (name.next()) return name.getString(1);
        	else return "";
        }
        
        public void addListenerToChoiceBox() {
        	this.warehouseId.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
        		@Override
        		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        			try {
        				System.out.println("Hi");
        				warehouseName = retriveWarehouseNameFromDB();
        			} catch (SQLException e) {
						e.printStackTrace();
					}
        	      }
        	    });
        }

        public ChoiceBox<String> getWarehouseId() { return warehouseId; }
		public void setWarehouseId(ChoiceBox<String> warehouseId) { this.warehouseId = warehouseId; }
        
		public String getWarehouseName() { return warehouseName; }
		public void setWarehouseName(String warehouseName) { this.warehouseName = warehouseName; }

		public String getAmount() { return amount; }
		public void setAmount(String amount) { this.amount = amount; }
	}
    
    public void addListenerToTextField_search() {
    	TextField_search.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.equals("")) {
					setProductTableItems();
				}
				else {
					compareIdWithNewValueAndShowReslutToTable(newValue);
				}
			}
		});
    }
    
    public void compareIdWithNewValueAndShowReslutToTable(String targetValue) {
    	TableView_productTable.setItems(getComparedDataWtihTargetValue(targetValue));
    }
    
    public ObservableList<ProductDataForTable> getComparedDataWtihTargetValue(String targetValue) {
    	retriveDataFromDBForProductTableWithSQLExceptionByTableName("product");
    	ObservableList<ProductDataForTable> products = FXCollections.observableArrayList();
    	try {
			do {
				if (resultsetForProductTable.getString(1).contains(targetValue) || resultsetForProductTable.getString(2).contains(targetValue) || resultsetForProductTable.getString(3).contains(targetValue) || resultsetForProductTable.getString(4).contains(targetValue) || resultsetForProductTable.getString(10).contains(targetValue)) {
					Integer total = resultsetForProductTable.getInt(6);
					Integer cost = resultsetForProductTable.getInt(7);
					Integer sellPrice = resultsetForProductTable.getInt(8);
					Integer safeAmount = resultsetForProductTable.getInt(9);
					
					products.add(new ProductDataForTable(resultsetForProductTable.getString(1), resultsetForProductTable.getString(2), resultsetForProductTable.getString(3), resultsetForProductTable.getString(4), resultsetForProductTable.getString(5), total.toString(),  cost.toString(),  sellPrice.toString(), safeAmount.toString(), resultsetForProductTable.getString(10)));
				}
			} while(resultsetForProductTable.next());
		} catch (SQLException e) {
			return FXCollections.observableArrayList();
		}
		return products;
    }
	
	@FXML
	public void productTableOnClicked() {
		if (isOnInsertionState == false) {
			retriveDataFromDBForProductTableWithSQLExceptionByTableName("product");
    		ShowSelectedDataToTableWithSQLException();
		}
	}
	
	public void ShowSelectedDataToTableWithSQLException() {
    	try {
			do {
				if (getTableSelectedIdWithNullPointerException().equals(resultsetForProductTable.getString(1))) {
					if (isSaved == true) {
						Button_deleteButton.setDisable(false);
						Button_editButton.setDisable(false);
						setWarehouseTableItems();
					}
					if (isSaved == false) {	
						Button_newSpace.setDisable(false);
						updateWarehouseTableDataToDBWithSQLException();
						setWarehouseTableItems();
						setChoiceBoxInWarehouseTableEnable();
						createTextFieldToWarehouseTableColumn();
					}
					oldProductId = getTableSelectedIdWithNullPointerException();
					break;
				}
			} while(resultsetForProductTable.next());
		} catch (SQLException e) {
			//Do Nothing
		}
    }
	
	public void updateWarehouseTableDataToDBWithSQLException() {
    	try {
    		updateWarehouseTableDataToDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	
	public void updateWarehouseTableDataToDB() throws SQLException {		
	    for (int i = 0; i < TableView_warehouseTable.getItems().size(); i++) {
	    	PreparedStatement statement = main.Main.getConnection().prepareStatement("REPLACE INTO javaclassproject2021.productstoreinwarehouse VALUES (?, ?, ?)");
	    	statement.setString(1, oldProductId);
	    	statement.setString(2, TableView_warehouseTable.getItems().get(i).getWarehouseId().getValue());
	    	statement.setString(3, TableView_warehouseTable.getItems().get(i).getAmount());
	    	statement.execute();
	    	statement.close();
	    }
	}
	
	public String getTableSelectedIdWithNullPointerException() {
    	try {
    		return TableView_productTable.getSelectionModel().getSelectedItem().getProductId();
		} catch (NullPointerException e) {
			return "0";
		}
    }
	
	public void setWarehouseTableItems() {
		TableView_warehouseTable.setItems(getProductStoreInWarehouseData());
	}
	
	public ObservableList<ProdouctStoreInWarehouseDataForTable>  getProductStoreInWarehouseData() {
		retriveDataFromDBForWarehouseTableWithSQLExceptionByTableName("productstoreinwarehouse");
    	ObservableList<ProdouctStoreInWarehouseDataForTable> warehouses = FXCollections.observableArrayList();
    	try {
			do {
				warehouses.add(new ProdouctStoreInWarehouseDataForTable(resultsetForWarehouseTable.getString(2), resultsetForWarehouseTable.getString(3)));
			} while(resultsetForWarehouseTable.next());
		} catch (SQLException e) {
			return FXCollections.observableArrayList();
		}
		return warehouses;
	}
	
	public void retriveDataFromDBForWarehouseTableWithSQLExceptionByTableName(String tableName) {
		try {
			retriveDataFromDBForWarehouseTableByTableName(tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void retriveDataFromDBForWarehouseTableByTableName(String tableName) throws SQLException {
		Statement statement = main.Main.getConnection().createStatement();
    	resultsetForWarehouseTable = statement.executeQuery("SELECT * FROM javaclassproject2021." + tableName + " WHERE ProductID = \"" + getTableSelectedIdWithNullPointerException() + "\"");
    	resultsetForWarehouseTable.next();
	}
	
	@FXML
	public void insertButtonClicked() {
		TableView_productTable.setEditable(true);
		
		TableView_warehouseTable.setDisable(true);
    	
    	isSaved = false;
    	isOnInsertionState = true;
    	
    	createNewRowInProductTableWithSQLExcpetion();
    	createTextFieldToProductTableColumn();
    	setOnEditOnNewProductRow();
    	
    	TableView_warehouseTable.setItems(FXCollections.observableArrayList());
    	    	
    	Button_saveButton.setDisable(false);
    	Button_quitButton.setDisable(false);
    	setChoiceBoxInProductTableEnable();
    	
    	Button_insertButton.setDisable(true);
    	Button_deleteButton.setDisable(true);
    	Button_editButton.setDisable(true);
    	Button_leaveButton.setDisable(true);
    	TextField_search.setDisable(true);
	}
	
	public void createNewRowInProductTableWithSQLExcpetion() {
		try {
			createNewRowInProductTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createNewRowInProductTable() throws SQLException {
    	TableView_productTable.getItems().add(new ProductDataForTable("", "", "", "", "", "0", "0", "0", "0", ""));
    	TableView_productTable.setItems(TableView_productTable.getItems());
    }
    
    public void createTextFieldToProductTableColumn() {
    	
    	TableColumn_id.setCellFactory(TextFieldTableCell.forTableColumn());
    	TableColumn_id.setOnEditCommit(e -> {
        		e.getTableView().getItems().get(e.getTablePosition().getRow()).setProductId(e.getNewValue());
    	});
    	
    	TableColumn_name.setCellFactory(TextFieldTableCell.forTableColumn());
    	TableColumn_name.setOnEditCommit(e -> {
        		e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue());
    	});
    	
    	TableColumn_specification.setCellFactory(TextFieldTableCell.forTableColumn());
    	TableColumn_specification.setOnEditCommit(e -> {
        		e.getTableView().getItems().get(e.getTablePosition().getRow()).setSpecification(e.getNewValue());
    	});
    	
    	TableColumn_type.setCellFactory(TextFieldTableCell.forTableColumn());
    	TableColumn_type.setOnEditCommit(e -> {
        		e.getTableView().getItems().get(e.getTablePosition().getRow()).setType(e.getNewValue());
    	});
    	
    	TableColumn_unit.setCellFactory(TextFieldTableCell.forTableColumn());
    	TableColumn_unit.setOnEditCommit(e -> {
        		e.getTableView().getItems().get(e.getTablePosition().getRow()).setUnit(e.getNewValue());
    	});
    	
    	TableColumn_cost.setCellFactory(TextFieldTableCell.forTableColumn());
    	TableColumn_cost.setOnEditCommit(e -> {
        		e.getTableView().getItems().get(e.getTablePosition().getRow()).setCost(e.getNewValue());
    	});
    	
    	TableColumn_sellPrice.setCellFactory(TextFieldTableCell.forTableColumn());
    	TableColumn_sellPrice.setOnEditCommit(e -> {
        		e.getTableView().getItems().get(e.getTablePosition().getRow()).setSellPrice(e.getNewValue());
    	});
    	
    	TableColumn_safeAmount.setCellFactory(TextFieldTableCell.forTableColumn());
    	TableColumn_safeAmount.setOnEditCommit(e -> {
        		e.getTableView().getItems().get(e.getTablePosition().getRow()).setSafeAmount(e.getNewValue());
    	});
    }
    
    public void setOnEditOnNewProductRow() {
    	TableView_productTable.getSelectionModel().clearAndSelect(TableView_productTable.getItems().size() - 1);
    	int selectedRow = TableView_productTable.getSelectionModel().getSelectedIndex();
    	TableView_productTable.edit(selectedRow, TableColumn_id);
	}
    
    public void setChoiceBoxInProductTableEnable() {
    	for (int i = 0; i < TableView_productTable.getItems().size(); i++) {
    		TableView_productTable.getItems().get(i).getVendor().setDisable(false);
    	}
    }
	
	@FXML
	public void deleteButtonClicked() throws SQLException {
		TableView_warehouseTable.setItems(FXCollections.observableArrayList());
		PreparedStatement delStatement = main.Main.getConnection().prepareStatement("DELETE FROM javaclassproject2021.product WHERE  ProductID = ?");
    	delStatement.setString(1, getTableSelectedIdWithNullPointerException());
    	delStatement.execute();
    	delStatement.close();
    	
    	setProductTableItems();
	}
	
	@FXML
	public void editButtonClicked() {
		TableView_productTable.setEditable(true);
		TableView_warehouseTable.setEditable(true);

    	
    	isSaved = false;
    	
    	createTextFieldToProductTableColumn();
    	createTextFieldToWarehouseTableColumn();
    	
    	Button_saveButton.setDisable(false);
    	Button_quitButton.setDisable(false);
    	setChoiceBoxInProductTableEnable();
    	setChoiceBoxInWarehouseTableEnable();
    	
    	Button_insertButton.setDisable(true);
    	Button_deleteButton.setDisable(true);
    	Button_editButton.setDisable(true);
    	Button_leaveButton.setDisable(true);
    	TextField_search.setDisable(true);
	}

	@FXML
	public void saveButtonClicked() {
		for(ProductDataForTable rowItems : TableView_productTable.getItems()) {
    		if (rowItems.getProductId() == "") {
    			showNullIDAlertBoxWithException();
    			return;
    		}
    	}

    	TableView_productTable.setEditable(false);
    	TableView_warehouseTable.setEditable(false);
    	
    	TableView_warehouseTable.setDisable(false);
    	updateWarehouseTableDataToDBWithSQLException();
    	TableView_warehouseTable.setItems(FXCollections.observableArrayList());
    	
    	isSaved = true;
    	isOnInsertionState = false;

    	updateProductTableDataToDBWithSQLException();
    	removeTextFieldAndUpdateProductTableCell();
    	removeTextFieldAndUpdateWarehouseTableCell();
    	setProductTableItems();
    	
    	Button_insertButton.setDisable(false);
    	Button_deleteButton.setDisable(false);
    	Button_editButton.setDisable(false);
    	Button_leaveButton.setDisable(false);
    	TextField_search.setDisable(false);
    	
    	Button_saveButton.setDisable(true);
    	Button_quitButton.setDisable(true);
    	Button_newSpace.setDisable(true);
    	Button_deleteSpace.setDisable(true);
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
    
    public void updateProductTableDataToDBWithSQLException() {
    	try {
    		updateProductTableDataToDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void updateProductTableDataToDB() throws SQLException {
    	
    	retriveDataFromDBForProductTableByTableName("product");
    	
    	try {
    		ifNoNewInsertion();
    	} catch (SQLException e) {
    		ifHasNewInsertion();
		}
    }
    
    public void ifNoNewInsertion() throws SQLException {
    	for (int i = 0; i < TableView_productTable.getItems().size(); i++) {
			PreparedStatement statement = main.Main.getConnection().prepareStatement("UPDATE javaclassproject2021.product SET ProductID = ?, Name = ?, Specification = ?"
    				+ ", Type = ?, Unit = ?, Total = ?, Cost = ?, SellPrice = ?, SafeAmount = ?, VendorName = ? WHERE ProductID = \"" + resultsetForProductTable.getString(1) + "\"");
        	statement.setString(1, TableView_productTable.getItems().get(i).getProductId());
        	statement.setString(2, TableView_productTable.getItems().get(i).getName());
        	statement.setString(3, TableView_productTable.getItems().get(i).getSpecification());
        	statement.setString(4, TableView_productTable.getItems().get(i).getType());
        	statement.setString(5, TableView_productTable.getItems().get(i).getUnit());
        	statement.setInt(6, Integer.parseInt(TableView_productTable.getItems().get(i).getTotal()));
        	statement.setInt(7, Integer.parseInt(TableView_productTable.getItems().get(i).getCost()));
        	statement.setInt(8, Integer.parseInt(TableView_productTable.getItems().get(i).getSellPrice()));
        	statement.setInt(9, Integer.parseInt(TableView_productTable.getItems().get(i).getSafeAmount()));
        	statement.setString(10, TableView_productTable.getItems().get(i).getVendor().getValue());
        	statement.execute();
        	statement.close();
        	resultsetForProductTable.next();
		}
    }
    
    public void ifHasNewInsertion() throws SQLException {
    	PreparedStatement statement = main.Main.getConnection().prepareStatement("INSERT INTO javaclassproject2021.product VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    	statement.setString(1, TableView_productTable.getItems().get(TableView_productTable.getItems().size() - 1).getProductId());
    	statement.setString(2, TableView_productTable.getItems().get(TableView_productTable.getItems().size() - 1).getName());
    	statement.setString(3, TableView_productTable.getItems().get(TableView_productTable.getItems().size() - 1).getSpecification());
    	statement.setString(4, TableView_productTable.getItems().get(TableView_productTable.getItems().size() - 1).getType());
    	statement.setString(5, TableView_productTable.getItems().get(TableView_productTable.getItems().size() - 1).getUnit());
    	statement.setInt(6, Integer.parseInt(TableView_productTable.getItems().get(TableView_productTable.getItems().size() - 1).getTotal()));
    	statement.setInt(7, Integer.parseInt(TableView_productTable.getItems().get(TableView_productTable.getItems().size() - 1).getCost()));
    	statement.setInt(8, Integer.parseInt(TableView_productTable.getItems().get(TableView_productTable.getItems().size() - 1).getSellPrice()));
    	statement.setInt(9, Integer.parseInt(TableView_productTable.getItems().get(TableView_productTable.getItems().size() - 1).getSafeAmount()));
    	statement.setString(10, TableView_productTable.getItems().get(TableView_productTable.getItems().size() - 1).getVendor().getValue());
    	statement.execute();
    	statement.close();
    }
    
    public void removeTextFieldAndUpdateProductTableCell() {
    	TableColumn_name.setCellFactory(param -> new UpdataedProductTableCell());
    	TableColumn_specification.setCellFactory(param -> new UpdataedProductTableCell());
    	TableColumn_type.setCellFactory(param -> new UpdataedProductTableCell());
    	TableColumn_unit.setCellFactory(param -> new UpdataedProductTableCell());
    	TableColumn_cost.setCellFactory(param -> new UpdataedProductTableCell());
    	TableColumn_sellPrice.setCellFactory(param -> new UpdataedProductTableCell());
    	TableColumn_safeAmount.setCellFactory(param -> new UpdataedProductTableCell());
    }
    
    private class UpdataedProductTableCell extends TableCell<ProductDataForTable, String> {
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
    
    public void removeTextFieldAndUpdateWarehouseTableCell() {
    	TableColumn_wAmount.setCellFactory(param -> new UpdataedWarehouseTableCell());
    }
    
    private class UpdataedWarehouseTableCell extends TableCell<ProdouctStoreInWarehouseDataForTable, String> {
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
	public void quitButtonClicked() {
		
		TableView_productTable.setEditable(false);
		TableView_warehouseTable.setEditable(false);
		
		TableView_warehouseTable.setDisable(false);
		TableView_warehouseTable.setItems(FXCollections.observableArrayList());
    	
    	isSaved = true;
    	isOnInsertionState = false;
    	
    	removeTextFieldAndUpdateProductTableCell();
    	removeTextFieldAndUpdateWarehouseTableCell();
    	retriveDataFromDBForProductTableWithSQLExceptionByTableName("product");
    	setProductTableItems();
    	
    	Button_insertButton.setDisable(false);
    	Button_deleteButton.setDisable(false);
    	Button_editButton.setDisable(false);
    	Button_leaveButton.setDisable(false);
    	TextField_search.setDisable(false);
    	
    	Button_saveButton.setDisable(true);
    	Button_quitButton.setDisable(true);
    	Button_newSpace.setDisable(true);
    	Button_deleteSpace.setDisable(true);
	}
    
	@FXML
	public void leaveButtonClicked() throws SQLException {
		resultsetForProductTable.close();
		if (resultsetForWarehouseTable != null) resultsetForWarehouseTable.close();
		main.Main.getProductSettingStage().close();
	}
	
	@FXML
	public void newSpaceButtonClicked() {
		
		createNewRowInWarehouseTableWithSQLException();
    	createTextFieldToWarehouseTableColumn();
    	setOnEditOnNewWarehouseRow();
    	setChoiceBoxInWarehouseTableEnable();
	}
	
	public void createNewRowInWarehouseTableWithSQLException() {
		try {
			createNewRowInWarehouseTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createNewRowInWarehouseTable() throws SQLException {
    	TableView_warehouseTable.getItems().add(new ProdouctStoreInWarehouseDataForTable("", "0"));
    	TableView_warehouseTable.setItems(TableView_warehouseTable.getItems());
    }
    
    public void createTextFieldToWarehouseTableColumn() {
    	TableColumn_wAmount.setCellFactory(TextFieldTableCell.forTableColumn());
    	TableColumn_wAmount.setOnEditCommit(e -> {
        		e.getTableView().getItems().get(e.getTablePosition().getRow()).setAmount(e.getNewValue());
    	});
    }
    
    public void setOnEditOnNewWarehouseRow() {
    	TableView_warehouseTable.getSelectionModel().clearAndSelect(TableView_warehouseTable.getItems().size() - 1);
    	int selectedRow = TableView_warehouseTable.getSelectionModel().getSelectedIndex();
    	TableView_warehouseTable.edit(selectedRow, TableColumn_wName);
	}
    
    public void setChoiceBoxInWarehouseTableEnable() {
    	for (int i = 0; i < TableView_warehouseTable.getItems().size(); i++) {
    		TableView_warehouseTable.getItems().get(i).getWarehouseId().setDisable(false);
    	}
    }
	
	@FXML
	public void deleteSpaceButtonClicked() {
		
	}
	
}
