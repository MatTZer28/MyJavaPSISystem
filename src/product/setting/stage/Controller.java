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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import product.purchase.stage.Purchase;
import product.sell.stage.Sell;

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
	@FXML
	private Button Button_purchaseButton;
	@FXML
	private Button Button_sellButton;

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
		TableColumn_total.setCellFactory(new Callback<TableColumn<ProductDataForTable,String>, TableCell<ProductDataForTable,String>>() {
			@Override
			public TableCell<ProductDataForTable, String> call(TableColumn<ProductDataForTable, String> param) {
				return new TableCell<ProductDataForTable, String>() {
					@Override
	                public void updateItem(String item, boolean empty) {
	                    super.updateItem(item, empty);
	                    if (!isEmpty()) {
	                        if(Integer.parseInt(item) < Integer.parseInt(TableView_productTable.getItems().get(super.getIndex()).getSafeAmount())) {
	                        	this.setStyle("-fx-background-color: " + "red" + ";");
	                        }
	                        setText(item);
	                    }	
	                }
				};
			}
		});
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
				
				if (total < safeAmount) {
					
				}

				products.add(new ProductDataForTable(resultsetForProductTable.getString(1),
						resultsetForProductTable.getString(2), resultsetForProductTable.getString(3),
						resultsetForProductTable.getString(4), resultsetForProductTable.getString(5), total.toString(),
						cost.toString(), sellPrice.toString(), safeAmount.toString(),
						resultsetForProductTable.getString(10)));
			} while (resultsetForProductTable.next());
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

		ProductDataForTable(String productId, String name, String specification, String type, String unit, String total,
				String cost, String sellPrice, String safeAmount, String vendor) throws SQLException {
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
				} while (resultsetForChoiceBox.next());
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

		public String getProductId() {
			return productId;
		}

		public void setProductId(String productId) {
			this.productId = productId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSpecification() {
			return specification;
		}

		public void setSpecification(String specification) {
			this.specification = specification;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getUnit() {
			return unit;
		}

		public void setUnit(String unit) {
			this.unit = unit;
		}

		public String getTotal() {
			return total;
		}

		public void setTotal(String total) {
			this.total = total;
		}

		public String getCost() {
			return cost;
		}

		public void setCost(String cost) {
			this.cost = cost;
		}

		public String getSellPrice() {
			return sellPrice;
		}

		public void setSellPrice(String sellPrice) {
			this.sellPrice = sellPrice;
		}

		public String getSafeAmount() {
			return safeAmount;
		}

		public void setSafeAmount(String safeAmount) {
			this.safeAmount = safeAmount;
		}

		public ChoiceBox<String> getVendor() {
			return vendor;
		}

		public void setVendor(ChoiceBox<String> vendor) {
			this.vendor = vendor;
		}
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

			this.warehouseName = retriveWarehouseNameFromDB(this.warehouseId.getValue());
			this.amount = amount;

			resultsetForChoiceBox.close();
		}

		public ObservableList<String> getWarehouseData() {
			retriveDataFromDBToChoiceBoxWithSQLExceptionByTableName("warehouse");
			ObservableList<String> options = FXCollections.observableArrayList();
			try {
				do {
					options.add(resultsetForChoiceBox.getString(1));
				} while (resultsetForChoiceBox.next());
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

		public static String retriveWarehouseNameFromDB(String warehouseId) throws SQLException {
			Statement statement = main.Main.getConnection().createStatement();
			ResultSet name = statement.executeQuery(
					"SELECT Name FROM javaclassproject2021.warehouse WHERE WarehouseID = \"" + warehouseId + "\"");
			if (name.next())
				return name.getString(1);
			else
				return "";
		}

		public ChoiceBox<String> getWarehouseId() {
			return warehouseId;
		}

		public void setWarehouseId(ChoiceBox<String> warehouseId) {
			this.warehouseId = warehouseId;
		}

		public String getWarehouseName() {
			return warehouseName;
		}

		public void setWarehouseName(String warehouseName) {
			this.warehouseName = warehouseName;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}
	}

	public void addListenerToTextField_search() {
		TextField_search.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.equals("")) {
					setProductTableItems();
				} else {
					compareIdWithNewValueAndShowReslutToTable(newValue);
				}
			}
		});
	}

	public void compareIdWithNewValueAndShowReslutToTable(String targetValue) {
		TableView_productTable.setItems(getComparedDataWtihTargetValue(targetValue));
		TableView_warehouseTable.setItems(FXCollections.observableArrayList());
	}

	public ObservableList<ProductDataForTable> getComparedDataWtihTargetValue(String targetValue) {
		retriveDataFromDBForProductTableWithSQLExceptionByTableName("product");
		ObservableList<ProductDataForTable> products = FXCollections.observableArrayList();
		try {
			do {
				if (resultsetForProductTable.getString(1).contains(targetValue)
						|| resultsetForProductTable.getString(2).contains(targetValue)
						|| resultsetForProductTable.getString(3).contains(targetValue)
						|| resultsetForProductTable.getString(4).contains(targetValue)
						|| resultsetForProductTable.getString(10).contains(targetValue)) {
					Integer total = resultsetForProductTable.getInt(6);
					Integer cost = resultsetForProductTable.getInt(7);
					Integer sellPrice = resultsetForProductTable.getInt(8);
					Integer safeAmount = resultsetForProductTable.getInt(9);

					products.add(new ProductDataForTable(resultsetForProductTable.getString(1),
							resultsetForProductTable.getString(2), resultsetForProductTable.getString(3),
							resultsetForProductTable.getString(4), resultsetForProductTable.getString(5),
							total.toString(), cost.toString(), sellPrice.toString(), safeAmount.toString(),
							resultsetForProductTable.getString(10)));
				}
			} while (resultsetForProductTable.next());
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
				if (getProductTableSelectedIdWithNullPointerException().equals(resultsetForProductTable.getString(1))) {
					if (isSaved == true) {
						Button_deleteButton.setDisable(false);
						Button_editButton.setDisable(false);
						setWarehouseTableItems();
					}
					if (isSaved == false) {
						Button_newSpace.setDisable(false);
						Button_deleteSpace.setDisable(false);
						TableView_warehouseTable.setDisable(false);
						updateWarehouseTableDataToDBWithSQLException();
						setWarehouseTableItems();
						setChoiceBoxInWarehouseTableEnable();
						createTextFieldToWarehouseTableColumn();
					}
					oldProductId = getProductTableSelectedIdWithNullPointerException();
					break;
				}
			} while (resultsetForProductTable.next());
		} catch (SQLException e) {
			// Do Nothing
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
			if (TableView_warehouseTable.getItems().get(i).getWarehouseId().getValue() == "")
				continue;
			PreparedStatement statement = main.Main.getConnection().prepareStatement(
					"INSERT INTO javaclassproject2021.productstoreinwarehouse VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE ProductID = ?, WarehouseID = ?, Amount = ?");
			statement.setString(1, oldProductId);
			statement.setString(2, TableView_warehouseTable.getItems().get(i).getWarehouseId().getValue());
			statement.setString(3, TableView_warehouseTable.getItems().get(i).getAmount());
			statement.setString(4, oldProductId);
			statement.setString(5, TableView_warehouseTable.getItems().get(i).getWarehouseId().getValue());
			statement.setString(6, TableView_warehouseTable.getItems().get(i).getAmount());
			statement.execute();
			statement.close();
		}
	}

	public String getProductTableSelectedIdWithNullPointerException() {
		try {
			return TableView_productTable.getSelectionModel().getSelectedItem().getProductId();
		} catch (NullPointerException e) {
			return "0";
		}
	}

	public void setWarehouseTableItems() {
		TableView_warehouseTable.setItems(getProductStoreInWarehouseData());
	}

	public ObservableList<ProdouctStoreInWarehouseDataForTable> getProductStoreInWarehouseData() {
		retriveDataFromDBForWarehouseTableWithSQLExceptionByTableName("productstoreinwarehouse");
		ObservableList<ProdouctStoreInWarehouseDataForTable> warehouses = FXCollections.observableArrayList();
		try {
			do {
				ProdouctStoreInWarehouseDataForTable productData = new ProdouctStoreInWarehouseDataForTable(
						resultsetForWarehouseTable.getString(2), resultsetForWarehouseTable.getString(3));
				productData.getWarehouseId().getSelectionModel().selectedIndexProperty()
						.addListener(new ChangeListener<Number>() {
							@Override
							public void changed(ObservableValue<? extends Number> observable, Number oldValue,
									Number newValue) {
								String choiceBoxValue = productData.getWarehouseId().getItems().get((int) newValue);
								setWarehouseNameWithSQLException(productData, choiceBoxValue);
								updateWarehouseIdWithSQLException(productData, choiceBoxValue);
								setWarehouseTableItems();
								setChoiceBoxInWarehouseTableEnable();
								createTextFieldToWarehouseTableColumn();
							}
						});
				warehouses.add(productData);
			} while (resultsetForWarehouseTable.next());
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
		resultsetForWarehouseTable = statement.executeQuery("SELECT * FROM javaclassproject2021." + tableName
				+ " WHERE ProductID = \"" + getProductTableSelectedIdWithNullPointerException() + "\"");
		resultsetForWarehouseTable.next();
	}

	@FXML
	public void insertButtonClicked() {
		TableView_productTable.setEditable(true);

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
		Button_purchaseButton.setDisable(true);
		Button_sellButton.setDisable(true);
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
		PreparedStatement delStatement = main.Main.getConnection()
				.prepareStatement("DELETE FROM javaclassproject2021.product WHERE  ProductID = ?");
		delStatement.setString(1, getProductTableSelectedIdWithNullPointerException());
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
		Button_purchaseButton.setDisable(true);
		Button_sellButton.setDisable(true);
		TextField_search.setDisable(true);
	}

	@FXML
	public void saveButtonClicked() {
		for (ProductDataForTable rowItems : TableView_productTable.getItems()) {
			if (rowItems.getProductId().equals("")) {
				showNullIDAlertBoxWithException();
				return;
			}
		}

		TableView_productTable.setEditable(false);
		TableView_warehouseTable.setEditable(false);

		TableView_warehouseTable.setDisable(true);
		updateWarehouseTableDataToDBWithSQLException();
		TableView_warehouseTable.setItems(FXCollections.observableArrayList());

		isSaved = true;
		isOnInsertionState = false;

		setProductTotalAmountWithSQLException();
		updateProductTableDataToDBWithSQLException();
		removeTextFieldAndUpdateProductTableCell();
		removeTextFieldAndUpdateWarehouseTableCell();
		setProductTableItems();

		Button_insertButton.setDisable(false);
		Button_deleteButton.setDisable(false);
		Button_editButton.setDisable(false);
		Button_leaveButton.setDisable(false);
		Button_purchaseButton.setDisable(false);
		Button_sellButton.setDisable(false);
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

	public void setProductTotalAmountWithSQLException() {
		try {
			setProductTotalAmount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setProductTotalAmount() throws SQLException {
		for (int i = 0; i < TableView_productTable.getItems().size(); i++) {

			int totalAmount = 0;

			PreparedStatement statement = main.Main.getConnection().prepareStatement(
					"SELECT Amount FROM javaclassproject2021.productstoreinwarehouse WHERE ProductID = ?");
			statement.setString(1, TableView_productTable.getItems().get(i).getProductId());
			resultsetForProductTable = statement.executeQuery();
			while (resultsetForProductTable.next()) {
				totalAmount += resultsetForProductTable.getInt(1);
			}

			TableView_productTable.getItems().get(i).setTotal(String.valueOf(totalAmount));
		}
	}

	public void updateProductTableDataToDBWithSQLException() {
		try {
			updateProductTableDataToDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateProductTableDataToDB() throws SQLException {
		for (int i = 0; i < TableView_productTable.getItems().size(); i++) {
			PreparedStatement statement = main.Main.getConnection()
					.prepareStatement("INSERT INTO javaclassproject2021.product VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
							+ "ON DUPLICATE KEY UPDATE ProductID = ?, Name = ?, Specification = ?, Type = ?, Unit = ?, Total = ?, Cost = ?"
							+ ", SellPrice = ?, SafeAmount = ?, VendorName = ?");
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
			statement.setString(11, TableView_productTable.getItems().get(i).getProductId());
			statement.setString(12, TableView_productTable.getItems().get(i).getName());
			statement.setString(13, TableView_productTable.getItems().get(i).getSpecification());
			statement.setString(14, TableView_productTable.getItems().get(i).getType());
			statement.setString(15, TableView_productTable.getItems().get(i).getUnit());
			statement.setInt(16, Integer.parseInt(TableView_productTable.getItems().get(i).getTotal()));
			statement.setInt(17, Integer.parseInt(TableView_productTable.getItems().get(i).getCost()));
			statement.setInt(18, Integer.parseInt(TableView_productTable.getItems().get(i).getSellPrice()));
			statement.setInt(19, Integer.parseInt(TableView_productTable.getItems().get(i).getSafeAmount()));
			statement.setString(20, TableView_productTable.getItems().get(i).getVendor().getValue());
			statement.execute();
			statement.close();
		}
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
			if (empty) {
				super.setText(null);
			} else {
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
			if (empty) {
				super.setText(null);
			} else {
				setText(item);
			}
		}
	}

	@FXML
	public void quitButtonClicked() {

		TableView_productTable.setEditable(false);
		TableView_warehouseTable.setEditable(false);

		TableView_warehouseTable.setDisable(true);
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
		Button_purchaseButton.setDisable(false);
		Button_sellButton.setDisable(false);
		TextField_search.setDisable(false);

		Button_saveButton.setDisable(true);
		Button_quitButton.setDisable(true);
		Button_newSpace.setDisable(true);
		Button_deleteSpace.setDisable(true);
	}

	@FXML
	public void leaveButtonClicked() throws SQLException {
		resultsetForProductTable.close();
		if (resultsetForWarehouseTable != null)
			resultsetForWarehouseTable.close();
		main.Main.getProductSettingStage().close();
	}

	@FXML
	public void newSpaceButtonClicked() {
		updateWarehouseTableDataToDBWithSQLException();
		createNewRowInWarehouseTableWithSQLException();
		setChoiceBoxInWarehouseTableEnable();
		createTextFieldToWarehouseTableColumn();
		setOnEditOnNewWarehouseRow();
	}

	public void createNewRowInWarehouseTableWithSQLException() {
		try {
			createNewRowInWarehouseTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createNewRowInWarehouseTable() throws SQLException {
		ProdouctStoreInWarehouseDataForTable productData = new ProdouctStoreInWarehouseDataForTable("", "0");
		TableView_warehouseTable.getItems().add(productData);
		TableView_warehouseTable.getItems().get(TableView_warehouseTable.getItems().size() - 1).getWarehouseId()
				.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue,
							Number newValue) {
						String choiceBoxValue = productData.getWarehouseId().getItems().get((int) newValue);
						setWarehouseNameWithSQLException(productData, choiceBoxValue);
						updateWarehouseIdWithSQLException(productData, choiceBoxValue);
						setWarehouseTableItems();
						setChoiceBoxInWarehouseTableEnable();
						createTextFieldToWarehouseTableColumn();
					}
				});
		TableView_warehouseTable.setItems(TableView_warehouseTable.getItems());
	}

	public void setWarehouseNameWithSQLException(ProdouctStoreInWarehouseDataForTable productData, String warehouseId) {
		try {
			setWarehouseName(productData, warehouseId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setWarehouseName(ProdouctStoreInWarehouseDataForTable productData, String warehouseId)
			throws SQLException {
		productData.setWarehouseName(ProdouctStoreInWarehouseDataForTable.retriveWarehouseNameFromDB(warehouseId));
	}

	public void updateWarehouseIdWithSQLException(ProdouctStoreInWarehouseDataForTable productData,
			String warehouseId) {
		try {
			updateWarehouseId(productData, warehouseId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateWarehouseId(ProdouctStoreInWarehouseDataForTable productData, String warehouseId)
			throws SQLException {
		PreparedStatement statement = main.Main.getConnection().prepareStatement(
				"INSERT INTO javaclassproject2021.productstoreinwarehouse VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE ProductID = ?, WarehouseID = ?, Amount = ?");
		statement.setString(1, oldProductId);
		statement.setString(2, warehouseId);
		statement.setString(3, productData.getAmount());
		statement.setString(4, oldProductId);
		statement.setString(5, warehouseId);
		statement.setString(6, productData.getAmount());
		statement.execute();
		statement.close();
	}

	public void setChoiceBoxInWarehouseTableEnable() {
		for (int i = 0; i < TableView_warehouseTable.getItems().size(); i++) {
			TableView_warehouseTable.getItems().get(i).getWarehouseId().setDisable(false);
		}
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

	@FXML
	public void deleteSpaceButtonClicked() throws SQLException {
		PreparedStatement delStatement = main.Main.getConnection()
				.prepareStatement("DELETE FROM javaclassproject2021.productstoreinwarehouse WHERE ProductID = ?");
		delStatement.setString(1, getProductTableSelectedIdWithNullPointerException());
		delStatement.execute();
		delStatement.close();

		setWarehouseTableItems();
		setChoiceBoxInWarehouseTableEnable();
		createTextFieldToWarehouseTableColumn();
	}

	@FXML
	public void purchaseButtonClicked() throws Exception {
		main.Main.setPurchaseStage(new Stage());
		new Purchase().launchScene(main.Main.getPurchaseStage());
	}

	@FXML
	public void sellButtonClicked() throws Exception {
		main.Main.setSellStage(new Stage());
		new Sell().launchScene(main.Main.getSellStage());
	}

	public TableView<ProdouctStoreInWarehouseDataForTable> getTableView_warehouseTable() {
		return TableView_warehouseTable;
	}
}
