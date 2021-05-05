package product.setting.stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.sun.org.apache.xml.internal.utils.ListingErrorHandler;

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
	private TableView<?> TableView_warehouseTable;
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
    private TableColumn<?, String> TableColumn_wId;
	@FXML
    private TableColumn<?, String> TableColumn_wName;
	@FXML
    private TableColumn<?, String> TableColumn_wAmount;
	
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
	
	private ResultSet resultsetForTable;
	
	private boolean isSaved = true;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initDataToProductTable();
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
    	retriveDataFromDBForTableWithSQLExceptionByTableName("product");
    	ObservableList<ProductDataForTable> products = FXCollections.observableArrayList();
    	try {
			do {
				Integer total = resultsetForTable.getInt(6);
				Integer cost = resultsetForTable.getInt(7);
				Integer sellPrice = resultsetForTable.getInt(8);
				Integer safeAmount = resultsetForTable.getInt(9);
				
				products.add(new ProductDataForTable(resultsetForTable.getString(1), resultsetForTable.getString(2), resultsetForTable.getString(3), resultsetForTable.getString(4), resultsetForTable.getString(5), total.toString(),  cost.toString(),  sellPrice.toString(), safeAmount.toString(), resultsetForTable.getString(10)));
			} while(resultsetForTable.next());
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
    	
    	ProductDataForTable(String productId, String name, String specification, String type, String unit, String total, String cost, String sellPrice, String safeAmount, String vendor) {
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
		}
    	
    	public ObservableList<String> getVendorData() {
    		retriveDataFromDBChoiceBoxWithSQLExceptionByTableName("vendorinformation");
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
    	
    	public void retriveDataFromDBChoiceBoxWithSQLExceptionByTableName(String tableName) {
        	try {
    			retriveDataFromDBChoiceBoxByTableName(tableName);
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
        }
        
        public void retriveDataFromDBChoiceBoxByTableName(String tableName) throws SQLException {
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
    	retriveDataFromDBForTableWithSQLExceptionByTableName("product");
    	ObservableList<ProductDataForTable> products = FXCollections.observableArrayList();
    	try {
			do {
				if (resultsetForTable.getString(1).contains(targetValue) || resultsetForTable.getString(2).contains(targetValue) || resultsetForTable.getString(3).contains(targetValue) || resultsetForTable.getString(4).contains(targetValue) || resultsetForTable.getString(10).contains(targetValue)) {
					Integer total = resultsetForTable.getInt(6);
					Integer cost = resultsetForTable.getInt(7);
					Integer sellPrice = resultsetForTable.getInt(8);
					Integer safeAmount = resultsetForTable.getInt(9);
					
					products.add(new ProductDataForTable(resultsetForTable.getString(1), resultsetForTable.getString(2), resultsetForTable.getString(3), resultsetForTable.getString(4), resultsetForTable.getString(5), total.toString(),  cost.toString(),  sellPrice.toString(), safeAmount.toString(), resultsetForTable.getString(10)));
				}
			} while(resultsetForTable.next());
		} catch (SQLException e) {
			return FXCollections.observableArrayList();
		}
		return products;
    }
	
	@FXML
	public void productTableOnClicked() {
    		retriveDataFromDBForTableWithSQLExceptionByTableName("product");
    		ShowSelectedDataToTableWithSQLException();
	}
	
	public void ShowSelectedDataToTableWithSQLException() {
    	try {
			do {
				if (getTableSelectedIdWithNullPointerException().equals(resultsetForTable.getString(1))) {
					if (isSaved == true) {
						Button_deleteButton.setDisable(false);
						Button_editButton.setDisable(false);
					}
					if (isSaved == false) {
						Button_newSpace.setDisable(false);
					}
					break;
				}
			} while(resultsetForTable.next());
		} catch (SQLException e) {
			//Do Nothing
		}
    }
	
	public String getTableSelectedIdWithNullPointerException() {
    	try {
    		return TableView_productTable.getSelectionModel().getSelectedItem().getProductId();
		} catch (NullPointerException e) {
			return "0";
		}
    }
	
	@FXML
	public void insertButtonClicked() {
		TableView_productTable.setEditable(true);
    	
    	isSaved = false;
    	
    	createNewRowInTable();
    	createTextFieldToTableColumn();
    	setOnEditOnNewRow();
    	
    	Button_saveButton.setDisable(false);
    	Button_quitButton.setDisable(false);
    	setChoiceBoxEnable();
    	
    	Button_insertButton.setDisable(true);
    	Button_deleteButton.setDisable(true);
    	Button_editButton.setDisable(true);
    	Button_leaveButton.setDisable(true);
    	TextField_search.setDisable(true);
	}
	
	public void createNewRowInTable() {
    	TableView_productTable.getItems().add(new ProductDataForTable("點此新增產品編號...", "點此新增產品名稱...", "點此新增產品規格...", "點此新增類型...", "點此新增單位...", "0", "0", "0", "0", "點此選擇供應廠商"));
    	TableView_productTable.setItems(TableView_productTable.getItems());
    }
    
    public void createTextFieldToTableColumn() {
    	
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
    
    public void setOnEditOnNewRow() {
    	TableView_productTable.getSelectionModel().clearAndSelect(TableView_productTable.getItems().size() - 1);
    	int selectedRow = TableView_productTable.getSelectionModel().getSelectedIndex();
    	TableView_productTable.edit(selectedRow, TableColumn_id);
	}
    
    public void setChoiceBoxEnable() {
    	for (int i = 0; i < TableView_productTable.getItems().size(); i++) {
    		TableView_productTable.getItems().get(i).getVendor().setDisable(false);
    	}
    }
	
	@FXML
	public void deleteButtonClicked() throws SQLException {
		PreparedStatement delStatement = main.Main.getConnection().prepareStatement("DELETE FROM javaclassproject2021.product WHERE  ProductID = ?");
    	delStatement.setString(1, getTableSelectedIdWithNullPointerException());
    	delStatement.execute();
    	delStatement.close();
    	
    	setProductTableItems();
	}
	
	@FXML
	public void editButtonClicked() {
		TableView_productTable.setEditable(true);
    	
    	isSaved = false;
    	
    	createTextFieldToTableColumn();
    	
    	Button_saveButton.setDisable(false);
    	Button_quitButton.setDisable(false);
    	setChoiceBoxEnable();
    	
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
    	
    	isSaved = true;

    	updateDataToDBWithSQLException();
    	removeTextFieldAndUpdateTableCell();
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
    
    public void updateDataToDBWithSQLException() {
    	try {
    		updateDataToDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void updateDataToDB() throws SQLException {
    	for (int i = 0; i < TableView_productTable.getItems().size(); i++) {
    		PreparedStatement statement = main.Main.getConnection().prepareStatement("REPLACE INTO javaclassproject2021.product VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
    	}
    }
    
    public void removeTextFieldAndUpdateTableCell() {
    	TableColumn_name.setCellFactory(param -> new UpdataedTableCell());
    	TableColumn_specification.setCellFactory(param -> new UpdataedTableCell());
    	TableColumn_type.setCellFactory(param -> new UpdataedTableCell());
    	TableColumn_unit.setCellFactory(param -> new UpdataedTableCell());
    	TableColumn_cost.setCellFactory(param -> new UpdataedTableCell());
    	TableColumn_sellPrice.setCellFactory(param -> new UpdataedTableCell());
    	TableColumn_safeAmount.setCellFactory(param -> new UpdataedTableCell());
    }
    
    private class UpdataedTableCell extends TableCell<ProductDataForTable, String> {
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
    	
    	isSaved = true;
    	
    	retriveDataFromDBForTableWithSQLExceptionByTableName("product");
    	removeTextFieldAndUpdateTableCell();
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
		resultsetForTable.close();
		main.Main.getProductSettingStage().close();
	}
	
	@FXML
	public void newSpaceButtonClicked() {
		
	}
	
	public static class ProdouctStoreInWarehouseDataForTable {
		private String warehouseID;
		private ChoiceBox<String> warehouseName;
		private String amount;
		
    	private ResultSet resultsetForChoiceBox;
		
		public ProdouctStoreInWarehouseDataForTable(String warehouseId, String warehouseName, String amount) {
			this.warehouseID = warehouseId;
			this.amount = amount;
			
			this.warehouseName = new ChoiceBox<String>();
			this.warehouseName.setItems(getWarehouseData());
			this.warehouseName.setValue(warehouseName);
			this.warehouseName.setDisable(true);
		}
    	
    	public ObservableList<String> getWarehouseData() {
    		retriveDataFromDBChoiceBoxWithSQLExceptionByTableName("warehouse");
    		ObservableList<String> options = FXCollections.observableArrayList();
    		try {
    			do {
    				options.add(resultsetForChoiceBox.getString(2));
    			} while(resultsetForChoiceBox.next());
    		} catch (SQLException e) {
    			return FXCollections.observableArrayList();
    		}
    		return options;
    	}
    	
    	public void retriveDataFromDBChoiceBoxWithSQLExceptionByTableName(String tableName) {
        	try {
    			retriveDataFromDBChoiceBoxByTableName(tableName);
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
        }
        
        public void retriveDataFromDBChoiceBoxByTableName(String tableName) throws SQLException {
        	Statement statement = main.Main.getConnection().createStatement();
        	resultsetForChoiceBox = statement.executeQuery("SELECT * FROM javaclassproject2021." + tableName);
        	resultsetForChoiceBox.next();
        }

		public String getWarehouseID() { return warehouseID; }
		public void setWarehouseID(String warehouseID) { this.warehouseID = warehouseID; }

		public ChoiceBox<String> getWarehouseName() { return warehouseName; }
		public void setWarehouseName(ChoiceBox<String> warehouseName) { this.warehouseName = warehouseName; }

		public String getAmount() { return amount; }
		public void setAmount(String amount) { this.amount = amount; }
	}
	
	@FXML
	public void deleteSpaceButtonClicked() {
		
	}
}
