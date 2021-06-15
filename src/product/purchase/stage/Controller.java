package product.purchase.stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import alertbox.nullid.stage.NullIDAlertBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Controller implements Initializable {

	@FXML
	private TextField TextField_search;

	@FXML
	private TableView<PurchaseDataForTable> TableView_purchaseTable;
	@FXML
	private TableView<ProductDataForTable> TableView_productTable;
	@FXML
	private TableView<WarehouseDataForTable> TableView_warehouseTable;
	@FXML
	private TableColumn<PurchaseDataForTable, String> TableColumn_purchaseId;
	@FXML
	private TableColumn<PurchaseDataForTable, ChoiceBox<String>> TableColumn_vendorId;
	@FXML
	private TableColumn<PurchaseDataForTable, String> TableColumn_vendorName;
	@FXML
	private TableColumn<ProductDataForTable, String> TableColumn_productId;
	@FXML
	private TableColumn<WarehouseDataForTable, String> TableColumn_warehouseId;
	@FXML
	private TableColumn<WarehouseDataForTable, String> TableColumn_costPrice;
	@FXML
	private TableColumn<WarehouseDataForTable, String> TableColumn_amount;
	@FXML
	private TableColumn<WarehouseDataForTable, String> TableColumn_unit;

	private ObservableList<PurchaseDataForTable> purchaseTableItems;

	private ObservableList<ProductDataForTable> productTableItems;

	private ObservableList<WarehouseDataForTable> warehouseTableItems;

	private Map<String, ObservableList<WarehouseDataForTable>> observableListMap = new HashMap<>();

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

	private ResultSet resultsetForPurchaseTable;

	private ResultSet resultsetForProductTable;

	private ResultSet resultsetForWarehouseTable;

	private boolean isSaved = true;

	@FXML
	private ToolBar ToolBar_toolBar;

	private double xOffset;
	private double yOffset;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setStageDragable();
		initTable();
		addListenerToTextField_search();
	}

	public void setStageDragable() {
		ToolBar_toolBar.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = main.Main.getPurchaseStage().getX() - event.getScreenX();
				yOffset = main.Main.getPurchaseStage().getY() - event.getScreenY();
			}
		});

		ToolBar_toolBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				main.Main.getPurchaseStage().setX(event.getScreenX() + xOffset);
				main.Main.getPurchaseStage().setY(event.getScreenY() + yOffset);
			}
		});
	}

	public void initTable() {
		setPurchaseTableColumn();
		setPurchaseTableItems();
		setProductTableColumn();
		setWarehouseTableColumn();
	}

	public void setPurchaseTableColumn() {
		TableColumn_purchaseId.setCellValueFactory(new PropertyValueFactory<>("purchaseId"));
		TableColumn_vendorId.setCellValueFactory(new PropertyValueFactory<>("vendorId"));
		TableColumn_vendorName.setCellValueFactory(new PropertyValueFactory<>("vendorName"));
	}

	public void setPurchaseTableItems() {
		purchaseTableItems = getPurchaseData();
		TableView_purchaseTable.setItems(purchaseTableItems);
	}

	public ObservableList<PurchaseDataForTable> getPurchaseData() {
		retriveDataFromDBForPurchaseTableWithSQLException();
		ObservableList<PurchaseDataForTable> purchases = FXCollections.observableArrayList();
		try {
			do {
				PurchaseDataForTable purcheasData = new PurchaseDataForTable(resultsetForPurchaseTable.getString(1),
						resultsetForPurchaseTable.getString(2));
				purchases.add(purcheasData);
			} while (resultsetForPurchaseTable.next());
		} catch (SQLException e) {
			return FXCollections.observableArrayList();
		}
		return purchases;
	}

	public void retriveDataFromDBForPurchaseTableWithSQLException() {
		try {
			retriveDataFromDBForPurchaseTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void retriveDataFromDBForPurchaseTable() throws SQLException {
		Statement statement = main.Main.getConnection().createStatement();
		resultsetForPurchaseTable = statement
				.executeQuery("SELECT DISTINCT PurchaseID, VendorID FROM javaclassproject2021.purchase");
		resultsetForPurchaseTable.next();
	}

	public void setProductTableColumn() {
		TableColumn_productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
	}

	public void setWarehouseTableColumn() {
		TableColumn_warehouseId.setCellValueFactory(new PropertyValueFactory<>("warehouseId"));
		TableColumn_costPrice.setCellValueFactory(new PropertyValueFactory<>("costPrice"));
		TableColumn_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		TableColumn_unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
	}

	public class PurchaseDataForTable {
		private String purchaseId;
		private ChoiceBox<String> vendorId;
		private String vendorName;

		private ResultSet resultsetForChoiceBox;

		PurchaseDataForTable(String purchaseId, String vendorId) throws SQLException {
			this.purchaseId = purchaseId;

			this.vendorId = new ChoiceBox<String>();
			this.vendorId.setItems(getVendorData());
			this.vendorId.setValue(vendorId);
			this.vendorId.setDisable(true);
			addChangeListenerToVendorId();

			this.vendorName = retriveVendorNameFromDB(this.vendorId.getValue());

			resultsetForChoiceBox.close();
		}

		public ObservableList<String> getVendorData() {
			retriveDataFromDBToChoiceBoxWithSQLException();
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

		public void retriveDataFromDBToChoiceBoxWithSQLException() {
			try {
				retriveDataFromDBToChoiceBox();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		public void retriveDataFromDBToChoiceBox() throws SQLException {
			Statement statement = main.Main.getConnection().createStatement();
			resultsetForChoiceBox = statement
					.executeQuery("SELECT VendorID FROM javaclassproject2021.vendorinformation");
			resultsetForChoiceBox.next();
		}

		public void addChangeListenerToVendorId() {
			this.vendorId.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					String choiceBoxValue = getVendorId().getItems().get((int) newValue);
					try {
						setVendorName(retriveVendorNameFromDB(choiceBoxValue));
						TableView_purchaseTable.getColumns().get(2).setVisible(false);
						TableView_purchaseTable.getColumns().get(2).setVisible(true);
						setProductTableItemsByVendorName(retriveVendorNameFromDB(choiceBoxValue));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			});
		}

		public String retriveVendorNameFromDB(String vendorId) throws SQLException {
			Statement statement = main.Main.getConnection().createStatement();
			ResultSet name = statement.executeQuery(
					"SELECT Name FROM javaclassproject2021.vendorinformation WHERE VendorID = \"" + vendorId + "\"");
			if (name.next())
				return name.getString(1);
			else
				return "";
		}

		public String getPurchaseId() {
			return purchaseId;
		}

		public void setPurchaseId(String purchaseId) {
			this.purchaseId = purchaseId;
		}

		public ChoiceBox<String> getVendorId() {
			return vendorId;
		}

		public void setVendorId(ChoiceBox<String> vendorId) {
			this.vendorId = vendorId;
		}

		public String getVendorName() {
			return vendorName;
		}

		public void setVendorName(String vendorName) {
			this.vendorName = vendorName;
		}

	}

	public void setProductTableItemsByVendorName(String vendorName) {
		productTableItems = getProductDataByVendorName(vendorName);
		TableView_productTable.setItems(productTableItems);
	}

	public ObservableList<ProductDataForTable> getProductDataByVendorName(String vendorName) {
		retriveDataFromDBForProductTableWithSQLExceptionByVendorName(vendorName);
		ObservableList<ProductDataForTable> products = FXCollections.observableArrayList();
		try {
			do {
				ProductDataForTable productData = new ProductDataForTable(resultsetForProductTable.getString(1));
				products.add(productData);
			} while (resultsetForProductTable.next());
		} catch (SQLException e) {
			return FXCollections.observableArrayList();
		}
		return products;
	}

	public void retriveDataFromDBForProductTableWithSQLExceptionByVendorName(String vendorName) {
		try {
			retriveDataFromDBForProductTableByVendorName(vendorName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void retriveDataFromDBForProductTableByVendorName(String vendorName) throws SQLException {
		Statement statement = main.Main.getConnection().createStatement();
		resultsetForProductTable = statement.executeQuery(
				"SELECT ProductID FROM javaclassproject2021.product WHERE VendorName = \"" + vendorName + "\"");
		resultsetForProductTable.next();
	}

	public class ProductDataForTable {
		private String productId;

		ProductDataForTable(String productId) {
			this.productId = productId;
		}

		public String getProductId() {
			return productId;
		}

		public void setProductId(String productId) {
			this.productId = productId;
		}
	}

	public void addListenerToTextField_search() {
		TextField_search.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.equals("")) {
					setPurchaseTableColumn();
				} else {
					compareIdWithNewValueAndShowReslutToTable(newValue);
				}
			}
		});
	}

	public void compareIdWithNewValueAndShowReslutToTable(String targetValue) {
		TableView_purchaseTable.setItems(getComparedDataWtihTargetValue(targetValue));
		TableView_productTable.setItems(FXCollections.observableArrayList());
		TableView_warehouseTable.setItems(FXCollections.observableArrayList());
	}

	public ObservableList<PurchaseDataForTable> getComparedDataWtihTargetValue(String targetValue) {
		retriveDataFromDBForPurchaseTableWithSQLException();
		ObservableList<PurchaseDataForTable> purchases = FXCollections.observableArrayList();
		try {
			do {
				if (resultsetForPurchaseTable.getString(1).contains(targetValue)) {
					PurchaseDataForTable purcheasData = new PurchaseDataForTable(resultsetForPurchaseTable.getString(1),
							resultsetForPurchaseTable.getString(2));
					purchases.add(purcheasData);
				}
			} while (resultsetForPurchaseTable.next());
		} catch (SQLException e) {
			return FXCollections.observableArrayList();
		}
		return purchases;
	}

	@FXML
	public void purchaseTableOnClicked() {
		String vendorName = getPurchaseTableSelectedVendorName();
		if (!vendorName.equals("") && !vendorName.equals("0")) {
			if (isSaved == true)
				Button_deleteButton.setDisable(false);
			setProductTableItemsByVendorName(vendorName);
		}
		TableView_warehouseTable.setItems(FXCollections.observableArrayList());
	}

	public String getPurchaseTableSelectedVendorName() {
		if (TableView_purchaseTable.getSelectionModel().getSelectedItem() != null)
			return TableView_purchaseTable.getSelectionModel().getSelectedItem().getVendorName();
		else
			return "0";
	}

	@FXML
	public void productTableOnClicked() {
		String keyValue = getPurchaseTableSelectedId() + getProductTableSelectedId();
		if (isSaved == false) {
			if (observableListMap.containsKey(keyValue)) {
				TableView_warehouseTable.setDisable(false);
				TableView_warehouseTable.setItems(observableListMap.get(keyValue));
			} else {
				TableView_warehouseTable.setDisable(false);
				setWarehouseTableItemsByProductId(getProductTableSelectedId());
				observableListMap.put(keyValue, warehouseTableItems);
			}
		} else {
			setWarehouseTableItemsByProductId(getProductTableSelectedId());
		}
	}

	public String getPurchaseTableSelectedId() {
		if (TableView_purchaseTable.getSelectionModel().getSelectedItem() != null)
			return TableView_purchaseTable.getSelectionModel().getSelectedItem().getPurchaseId();
		else
			return "0";
	}

	public String getProductTableSelectedId() {
		if (TableView_productTable.getSelectionModel().getSelectedItem() != null)
			return TableView_productTable.getSelectionModel().getSelectedItem().getProductId();
		else
			return "0";
	}

	public void setWarehouseTableItemsByProductId(String productId) {
		warehouseTableItems = getWarehouseDataByProductId(productId);
		TableView_warehouseTable.setItems(warehouseTableItems);
	}

	public ObservableList<WarehouseDataForTable> getWarehouseDataByProductId(String productId) {
		retriveDataFromDBForWarehouseTableWithSQLExceptionByProductId(productId);
		ObservableList<WarehouseDataForTable> warehouses = FXCollections.observableArrayList();
		try {
			do {
				WarehouseDataForTable warehouseData = new WarehouseDataForTable(resultsetForWarehouseTable.getString(1),
						resultsetForWarehouseTable.getString(2));
				warehouses.add(warehouseData);
			} while (resultsetForWarehouseTable.next());
		} catch (SQLException e) {
			return FXCollections.observableArrayList();
		}
		return warehouses;
	}

	public void retriveDataFromDBForWarehouseTableWithSQLExceptionByProductId(String productId) {
		try {
			retriveDataFromDBForWarehouseTableByProductId(productId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void retriveDataFromDBForWarehouseTableByProductId(String productId) throws SQLException {
		Statement statement = main.Main.getConnection().createStatement();
		resultsetForWarehouseTable = statement.executeQuery(
				"SELECT productstoreinwarehouse.WarehouseID, product.Unit FROM javaclassproject2021.productstoreinwarehouse, javaclassproject2021.product "
						+ "WHERE productstoreinwarehouse.ProductID = \"" + productId + "\" AND product.ProductID = \""
						+ productId + "\"");
		resultsetForWarehouseTable.next();
	}

	public class WarehouseDataForTable {
		private String warehouseId;
		private String costPrice;
		private String amount;
		private String unit;

		private ResultSet resultsetForWarehouseData;

		WarehouseDataForTable(String warehouseId, String unit) {
			this.warehouseId = warehouseId;
			this.unit = unit;
			setCostPriceAndAmountWithSQLException();
		}

		public void setCostPriceAndAmountWithSQLException() {
			try {
				setCostPriceAndAmount();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		public void setCostPriceAndAmount() throws SQLException {
			retriveDataFromDBForWarehouseDataWithSQLException();
			if (resultsetForWarehouseData.next()) {
				costPrice = String.valueOf(resultsetForWarehouseData.getInt(1));
				amount = String.valueOf(resultsetForWarehouseData.getInt(2));
			} else {
				costPrice = "0";
				amount = "0";
			}
		}

		public void retriveDataFromDBForWarehouseDataWithSQLException() {
			try {
				retriveDataFromDBForWarehouseData();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		public void retriveDataFromDBForWarehouseData() throws SQLException {
			PreparedStatement statement = main.Main.getConnection().prepareStatement(
					"SELECT CostPrice, Amount FROM javaclassproject2021.purchase WHERE PurchaseID = ? AND ProductID = ? AND WarehouseID = ?");
			statement.setString(1, TableView_purchaseTable.getSelectionModel().getSelectedItem().getPurchaseId());
			statement.setString(2, TableView_productTable.getSelectionModel().getSelectedItem().getProductId());
			statement.setString(3, this.warehouseId);
			resultsetForWarehouseData = statement.executeQuery();
		}

		public String getWarehouseId() {
			return warehouseId;
		}

		public void setWarehouseId(String warehouseId) {
			this.warehouseId = warehouseId;
		}

		public String getCostPrice() {
			return costPrice;
		}

		public void setCostPrice(String costPrice) {
			this.costPrice = costPrice;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public String getUnit() {
			return unit;
		}

		public void setUnit(String unit) {
			this.unit = unit;
		}
	}

	@FXML
	public void insertButtonClicked(ActionEvent event) {
		TableView_purchaseTable.setEditable(true);
		TableView_warehouseTable.setEditable(true);

		isSaved = false;

		createNewRowInPurchaseTableWithSQLExcpetion();
		createTextFieldToPerchaseTableColumn();
		createTextFieldToWarehouseTableColumn();
		setOnEditOnNewPerchaseRow();

		Button_saveButton.setDisable(false);
		Button_quitButton.setDisable(false);
		setChoiceBoxInPurchaseTableEnable();

		Button_insertButton.setDisable(true);
		Button_deleteButton.setDisable(true);
		Button_editButton.setDisable(true);
		Button_leaveButton.setDisable(true);
		TextField_search.setDisable(true);
	}

	public void createNewRowInPurchaseTableWithSQLExcpetion() {
		try {
			createNewRowInPurchaseTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createNewRowInPurchaseTable() throws SQLException {
		purchaseTableItems.add(new PurchaseDataForTable("", ""));
		TableView_purchaseTable.setItems(purchaseTableItems);
	}

	public void createTextFieldToPerchaseTableColumn() {
		TableColumn_purchaseId.setCellFactory(TextFieldTableCell.forTableColumn());
		TableColumn_purchaseId.setOnEditCommit(e -> {
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setPurchaseId(e.getNewValue());
		});
	}

	public void createTextFieldToWarehouseTableColumn() {

		TableColumn_costPrice.setCellFactory(TextFieldTableCell.forTableColumn());
		TableColumn_costPrice.setOnEditCommit(e -> {
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setCostPrice(e.getNewValue());
		});

		TableColumn_amount.setCellFactory(TextFieldTableCell.forTableColumn());
		TableColumn_amount.setOnEditCommit(e -> {
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setAmount(e.getNewValue());
		});
	}

	public void setOnEditOnNewPerchaseRow() {
		TableView_purchaseTable.getSelectionModel().clearAndSelect(TableView_purchaseTable.getItems().size() - 1);
		int selectedRow = TableView_purchaseTable.getSelectionModel().getSelectedIndex();
		TableView_purchaseTable.edit(selectedRow, TableColumn_purchaseId);
	}

	public void setChoiceBoxInPurchaseTableEnable() {
		for (int i = 0; i < TableView_purchaseTable.getItems().size(); i++) {
			TableView_purchaseTable.getItems().get(i).getVendorId().setDisable(false);
		}
	}

	@FXML
	public void deleteButtonClicked() throws SQLException {
		ResultSet amountResultSet;

		retriveDataFromDBForProductTableWithSQLExceptionByVendorName(
				TableView_purchaseTable.getSelectionModel().getSelectedItem().getVendorName());
		do {
			retriveDataFromDBForWarehouseTableWithSQLExceptionByProductId(resultsetForProductTable.getString(1));
			do {
				PreparedStatement retrieveAmountStatement = main.Main.getConnection().prepareStatement(
						"SELECT Amount FROM javaclassproject2021.purchase WHERE PurchaseID = ? AND ProductID = ? AND WarehouseID = ?");
				retrieveAmountStatement.setString(1,
						TableView_purchaseTable.getSelectionModel().getSelectedItem().getPurchaseId());
				retrieveAmountStatement.setString(2, resultsetForProductTable.getString(1));
				retrieveAmountStatement.setString(3, resultsetForWarehouseTable.getString(1));
				amountResultSet = retrieveAmountStatement.executeQuery();
				if (amountResultSet.next()) {
					PreparedStatement updateStatement = main.Main.getConnection().prepareStatement(
							"UPDATE javaclassproject2021.productstoreinwarehouse SET Amount = Amount - ? "
									+ "WHERE ProductID = ? AND WarehouseID = ?");
					updateStatement.setInt(1, amountResultSet.getInt(1));
					updateStatement.setString(2, resultsetForProductTable.getString(1));
					updateStatement.setString(3, resultsetForWarehouseTable.getString(1));
					updateStatement.execute();
					updateStatement.close();
				}
				amountResultSet.close();
			} while (resultsetForWarehouseTable.next());
		} while (resultsetForProductTable.next());

		TableView_warehouseTable.setItems(FXCollections.observableArrayList());
		TableView_productTable.setItems(FXCollections.observableArrayList());
		PreparedStatement delStatement = main.Main.getConnection()
				.prepareStatement("DELETE FROM javaclassproject2021.purchase WHERE  PurchaseID = ?");
		delStatement.setString(1, TableView_purchaseTable.getSelectionModel().getSelectedItem().getPurchaseId());
		delStatement.execute();
		delStatement.close();

		setPurchaseTableItems();
		
		product.setting.stage.Controller controller = mainmenu.stage.Controller.getProductSetting().getContorller();
		controller.getTableView_warehouseTable().setItems(FXCollections.observableArrayList());
		controller.setProductTotalAmountWithSQLException();
		controller.updateProductTableDataToDBWithSQLException();
		controller.setProductTableItems();
	}

	@FXML
	public void editButtonClicked() {
		TableView_purchaseTable.setEditable(true);
		TableView_warehouseTable.setEditable(true);

		isSaved = false;

		createTextFieldToPerchaseTableColumn();
		createTextFieldToWarehouseTableColumn();

		Button_saveButton.setDisable(false);
		Button_quitButton.setDisable(false);
		setChoiceBoxInPurchaseTableEnable();

		Button_insertButton.setDisable(true);
		Button_deleteButton.setDisable(true);
		Button_editButton.setDisable(true);
		Button_leaveButton.setDisable(true);
		TextField_search.setDisable(true);
	}

	@FXML
	public void saveButtonClicked() {
		for (PurchaseDataForTable rowItems : TableView_purchaseTable.getItems()) {
			if (rowItems.getPurchaseId().equals("") || rowItems.getVendorId().getValue().equals("")) {
				showNullIDAlertBoxWithException();
				return;
			}
		}

		TableView_purchaseTable.setEditable(false);
		TableView_warehouseTable.setEditable(false);

		TableView_warehouseTable.setDisable(true);

		isSaved = true;

		updateTableDataToDBWithSQLException();
		setChoiceBoxInPurchaseTableDisable();
		
		product.setting.stage.Controller controller = mainmenu.stage.Controller.getProductSetting().getContorller();
		controller.getTableView_warehouseTable().setItems(FXCollections.observableArrayList());
		controller.setProductTotalAmountWithSQLException();
		controller.updateProductTableDataToDBWithSQLException();
		controller.setProductTableItems();

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

	public void updateTableDataToDBWithSQLException() {
		try {
			updateTableDataToDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateTableDataToDB() throws SQLException {
		boolean isPurchaseReultsetEnd = true;

		int oldAmountValue;
		int newAmountValue;

		retriveDataFromDBForPurchaseTableWithSQLException();

		for (int i = 0; i < TableView_purchaseTable.getItems().size() - 1; i++) {
			if (resultsetForPurchaseTable.getString(2).equals(""))
				continue;
			retriveDataFromDBForProductTableWithSQLExceptionByVendorName(
					TableView_purchaseTable.getItems().get(i).getVendorName());
			do {
				String keyValue = TableView_purchaseTable.getItems().get(i).getPurchaseId()
						+ resultsetForProductTable.getString(1);
				if (observableListMap.containsKey(keyValue) == false) {
					PreparedStatement statement = main.Main.getConnection()
							.prepareStatement("UPDATE javaclassproject2021.purchase SET PurchaseID = ?, VendorID = ? "
									+ "WHERE PurchaseID = ? AND VendorID = ?");
					statement.setString(1, TableView_purchaseTable.getItems().get(i).getPurchaseId());
					statement.setString(2, TableView_purchaseTable.getItems().get(i).getVendorId().getValue());
					statement.setString(3, resultsetForPurchaseTable.getString(1));
					statement.setString(4, TableView_purchaseTable.getItems().get(i).getVendorId().getValue());
					statement.execute();
					statement.close();
				} else {
					for (int j = 0; j < observableListMap.get(keyValue).size(); j++) {
						PreparedStatement statementOldAmountValue = main.Main.getConnection()
								.prepareStatement("SELECT Amount FROM javaclassproject2021.purchase "
										+ "WHERE PurchaseID = ? AND ProductID = ? AND WarehouseID = ?");
						statementOldAmountValue.setString(1, resultsetForPurchaseTable.getString(1));
						statementOldAmountValue.setString(2, resultsetForProductTable.getString(1));
						statementOldAmountValue.setString(3, observableListMap.get(keyValue).get(j).getWarehouseId());
						ResultSet oldAmountValueResultSet;
						oldAmountValueResultSet = statementOldAmountValue.executeQuery();
						oldAmountValueResultSet.next();
						oldAmountValue = oldAmountValueResultSet.getInt(1);
						oldAmountValueResultSet.close();

						PreparedStatement statement = main.Main.getConnection().prepareStatement(
								"UPDATE javaclassproject2021.purchase SET PurchaseID = ?, VendorID = ?, ProductID = ?, WarehouseID = ?, CostPrice = ?, Amount = ? "
										+ "WHERE PurchaseID = ? AND ProductID = ? AND WarehouseID = ?");
						statement.setString(1, TableView_purchaseTable.getItems().get(i).getPurchaseId());
						statement.setString(2, TableView_purchaseTable.getItems().get(i).getVendorId().getValue());
						statement.setString(3, resultsetForProductTable.getString(1));
						statement.setString(4, observableListMap.get(keyValue).get(j).getWarehouseId());
						statement.setInt(5,
								Integer.parseInt(observableListMap.get(keyValue).get(j).getCostPrice() == "" ? "0"
										: observableListMap.get(keyValue).get(j).getCostPrice()));
						statement.setInt(6,
								Integer.parseInt(observableListMap.get(keyValue).get(j).getAmount() == "" ? "0"
										: observableListMap.get(keyValue).get(j).getAmount()));

						newAmountValue = Integer.parseInt(observableListMap.get(keyValue).get(j).getAmount() == "" ? "0"
								: observableListMap.get(keyValue).get(j).getAmount());

						statement.setString(7, resultsetForPurchaseTable.getString(1));
						statement.setString(8, resultsetForProductTable.getString(1));
						statement.setString(9, observableListMap.get(keyValue).get(j).getWarehouseId());
						statement.execute();
						statement.close();

						PreparedStatement statementUpdateAmount = main.Main.getConnection().prepareStatement(
								"UPDATE javaclassproject2021.productstoreinwarehouse SET Amount = Amount - ? + ? "
										+ "WHERE ProductID = ? AND WarehouseID = ?");
						statementUpdateAmount.setInt(1, oldAmountValue);
						statementUpdateAmount.setInt(2, newAmountValue);
						statementUpdateAmount.setString(3, resultsetForProductTable.getString(1));
						statementUpdateAmount.setString(4, observableListMap.get(keyValue).get(j).getWarehouseId());
						statementUpdateAmount.execute();
						statementUpdateAmount.close();
					}
				}
			} while (resultsetForProductTable.next());
			if (i < TableView_purchaseTable.getItems().size() - 2)
				resultsetForPurchaseTable.next();
		}

		if (TableView_purchaseTable.getItems().size() == 1)
			if (resultsetForPurchaseTable.getRow() == 0) {
				isPurchaseReultsetEnd = true;
			} else {
				isPurchaseReultsetEnd = false;
			}
		else if (resultsetForPurchaseTable.next())
			isPurchaseReultsetEnd = false;

		if (isPurchaseReultsetEnd == false) {
			retriveDataFromDBForProductTableWithSQLExceptionByVendorName(TableView_purchaseTable.getItems()
					.get(TableView_purchaseTable.getItems().size() - 1).getVendorName());
			do {
				String keyValue = TableView_purchaseTable.getItems().get(TableView_purchaseTable.getItems().size() - 1)
						.getPurchaseId() + resultsetForProductTable.getString(1);
				if (observableListMap.containsKey(keyValue) == false) {
					PreparedStatement statement = main.Main.getConnection()
							.prepareStatement("UPDATE javaclassproject2021.purchase SET PurchaseID = ?, VendorID = ? "
									+ "WHERE PurchaseID = ? AND VendorID = ?");
					statement.setString(1, TableView_purchaseTable.getItems()
							.get(TableView_purchaseTable.getItems().size() - 1).getPurchaseId());
					statement.setString(2, TableView_purchaseTable.getItems()
							.get(TableView_purchaseTable.getItems().size() - 1).getVendorId().getValue());
					statement.setString(3, resultsetForPurchaseTable.getString(1));
					statement.setString(4, TableView_purchaseTable.getItems()
							.get(TableView_purchaseTable.getItems().size() - 1).getVendorId().getValue());
					statement.execute();
					statement.close();
				} else {
					for (int j = 0; j < observableListMap.get(keyValue).size(); j++) {
						PreparedStatement statementOldAmountValue = main.Main.getConnection()
								.prepareStatement("SELECT Amount FROM javaclassproject2021.purchase "
										+ "WHERE PurchaseID = ? AND ProductID = ? AND WarehouseID = ?");
						statementOldAmountValue.setString(1, resultsetForPurchaseTable.getString(1));
						statementOldAmountValue.setString(2, resultsetForProductTable.getString(1));
						statementOldAmountValue.setString(3, observableListMap.get(keyValue).get(j).getWarehouseId());
						ResultSet oldAmountValueResultSet;
						oldAmountValueResultSet = statementOldAmountValue.executeQuery();
						oldAmountValueResultSet.next();
						oldAmountValue = oldAmountValueResultSet.getInt(1);
						oldAmountValueResultSet.close();

						PreparedStatement statement = main.Main.getConnection().prepareStatement(
								"UPDATE javaclassproject2021.purchase SET PurchaseID = ?, VendorID = ?, ProductID = ?, WarehouseID = ?, CostPrice = ?, Amount = ? "
										+ "WHERE PurchaseID = ? AND ProductID = ?  AND WarehouseID = ?");
						statement.setString(1, TableView_purchaseTable.getItems()
								.get(TableView_purchaseTable.getItems().size() - 1).getPurchaseId());
						statement.setString(2, TableView_purchaseTable.getItems()
								.get(TableView_purchaseTable.getItems().size() - 1).getVendorId().getValue());
						statement.setString(3, resultsetForProductTable.getString(1));
						statement.setString(4, observableListMap.get(keyValue).get(j).getWarehouseId());
						statement.setInt(5,
								Integer.parseInt(observableListMap.get(keyValue).get(j).getCostPrice() == "" ? "0"
										: observableListMap.get(keyValue).get(j).getCostPrice()));
						statement.setInt(6,
								Integer.parseInt(observableListMap.get(keyValue).get(j).getAmount() == "" ? "0"
										: observableListMap.get(keyValue).get(j).getAmount()));

						newAmountValue = Integer.parseInt(observableListMap.get(keyValue).get(j).getAmount() == "" ? "0"
								: observableListMap.get(keyValue).get(j).getAmount());

						statement.setString(7, resultsetForPurchaseTable.getString(1));
						statement.setString(8, resultsetForProductTable.getString(1));
						statement.setString(9, observableListMap.get(keyValue).get(j).getWarehouseId());
						statement.execute();
						statement.close();

						PreparedStatement statementUpdateAmount = main.Main.getConnection().prepareStatement(
								"UPDATE javaclassproject2021.productstoreinwarehouse SET Amount = Amount - ? + ? "
										+ "WHERE ProductID = ? AND WarehouseID = ?");
						statementUpdateAmount.setInt(1, oldAmountValue);
						statementUpdateAmount.setInt(2, newAmountValue);
						statementUpdateAmount.setString(3, resultsetForProductTable.getString(1));
						statementUpdateAmount.setString(4, observableListMap.get(keyValue).get(j).getWarehouseId());
						statementUpdateAmount.execute();
						statementUpdateAmount.close();
					}
				}
			} while (resultsetForProductTable.next());
		} else {
			retriveDataFromDBForProductTableWithSQLExceptionByVendorName(TableView_purchaseTable.getItems()
					.get(TableView_purchaseTable.getItems().size() - 1).getVendorName());
			do {
				String keyValue = TableView_purchaseTable.getItems().get(TableView_purchaseTable.getItems().size() - 1)
						.getPurchaseId() + resultsetForProductTable.getString(1);
				if (observableListMap.containsKey(keyValue) == false) {
					retriveDataFromDBForWarehouseTableWithSQLExceptionByProductId(
							resultsetForProductTable.getString(1));
					do {
						PreparedStatement statement = main.Main.getConnection().prepareStatement(
								"INSERT INTO javaclassproject2021.purchase VALUES (?, ?, ?, ?, ?, ?)");
						statement.setString(1, TableView_purchaseTable.getItems()
								.get(TableView_purchaseTable.getItems().size() - 1).getPurchaseId());
						statement.setString(2, TableView_purchaseTable.getItems()
								.get(TableView_purchaseTable.getItems().size() - 1).getVendorId().getValue());
						statement.setString(3, resultsetForProductTable.getString(1));
						statement.setString(4, resultsetForWarehouseTable.getString(1));
						statement.setInt(5, 0);
						statement.setInt(6, 0);
						statement.execute();
						statement.close();
					} while (resultsetForWarehouseTable.next());
				} else {
					for (int j = 0; j < observableListMap.get(keyValue).size(); j++) {
						PreparedStatement statement = main.Main.getConnection().prepareStatement(
								"INSERT INTO javaclassproject2021.purchase VALUES (?, ?, ?, ?, ?, ?)");
						statement.setString(1, TableView_purchaseTable.getItems()
								.get(TableView_purchaseTable.getItems().size() - 1).getPurchaseId());
						statement.setString(2, TableView_purchaseTable.getItems()
								.get(TableView_purchaseTable.getItems().size() - 1).getVendorId().getValue());
						statement.setString(3, resultsetForProductTable.getString(1));
						statement.setString(4, observableListMap.get(keyValue).get(j).getWarehouseId());
						statement.setInt(5,
								Integer.parseInt(observableListMap.get(keyValue).get(j).getCostPrice() == "" ? "0"
										: observableListMap.get(keyValue).get(j).getCostPrice()));
						statement.setInt(6,
								Integer.parseInt(observableListMap.get(keyValue).get(j).getAmount() == "" ? "0"
										: observableListMap.get(keyValue).get(j).getAmount()));

						newAmountValue = Integer.parseInt(observableListMap.get(keyValue).get(j).getAmount() == "" ? "0"
								: observableListMap.get(keyValue).get(j).getAmount());

						statement.execute();
						statement.close();

						PreparedStatement statementUpdateAmount = main.Main.getConnection().prepareStatement(
								"UPDATE javaclassproject2021.productstoreinwarehouse SET Amount = Amount + ? "
										+ "WHERE ProductID = ? AND WarehouseID = ?");
						statementUpdateAmount.setInt(1, newAmountValue);
						statementUpdateAmount.setString(2, resultsetForProductTable.getString(1));
						statementUpdateAmount.setString(3, observableListMap.get(keyValue).get(j).getWarehouseId());
						statementUpdateAmount.execute();
						statementUpdateAmount.close();
					}
				}
			} while (resultsetForProductTable.next());
		}
	}

	public void setChoiceBoxInPurchaseTableDisable() {
		for (int i = 0; i < TableView_purchaseTable.getItems().size(); i++) {
			TableView_purchaseTable.getItems().get(i).getVendorId().setDisable(true);
		}
	}

	@FXML
	public void quitButtonClicked() {
		TableView_purchaseTable.setEditable(false);
		TableView_warehouseTable.setEditable(false);

		TableView_warehouseTable.setDisable(true);

		isSaved = true;

		TableView_purchaseTable.setEditable(false);

		setPurchaseTableItems();
		TableView_warehouseTable.setItems(FXCollections.observableArrayList());
		TableView_productTable.setItems(FXCollections.observableArrayList());

		isSaved = true;

		Button_insertButton.setDisable(false);
		Button_deleteButton.setDisable(false);
		Button_editButton.setDisable(false);
		Button_leaveButton.setDisable(false);
		TextField_search.setDisable(false);

		Button_saveButton.setDisable(true);
		Button_quitButton.setDisable(true);
	}

	@FXML
	public void leaveButtonClicked() throws SQLException {
		resultsetForPurchaseTable.close();
		if (resultsetForProductTable != null)
			resultsetForProductTable.close();
		if (resultsetForWarehouseTable != null)
			resultsetForWarehouseTable.close();
		main.Main.getPurchaseStage().close();
	}
}
