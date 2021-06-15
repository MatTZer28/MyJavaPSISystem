package product.sell.stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import alertbox.nullid.stage.NullIDAlertBox;
import excel.CreateSellExcelFile;
import excel.SellDataItems;
import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.*;

public class Controller implements Initializable {

	@FXML
	private StackPane parentContainer;

	@FXML
	private AnchorPane anchorRoot;

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
	private Button Button_newCombineSell;
	@FXML
	private Button Button_deleteCombineSell;
	@FXML
	private Button Button_newCombine;
	@FXML
	private Button Button_deleteCombine;
	@FXML
	private Button Button_newProduct;
	@FXML
	private Button Button_deleteProduct;
	@FXML
	private Button Button_sellRanking;
	@FXML
	private Button Button_printAll;
	@FXML
	private Button Button_printSingle;

	@FXML
	private TextField TextField_search;

	@FXML
	private TableView<SellDataForTable> TableView_sellTable;
	@FXML
	private TableView<CombineSellDataForTable> TableView_combineSellTable;
	@FXML
	private TableView<CombineDataForTable> TableView_combineTable;
	@FXML
	private TableView<ProductDataForTable> TableView_productTable;

	@FXML
	private TableColumn<SellDataForTable, String> TableColumn_sellId;
	@FXML
	private TableColumn<SellDataForTable, ChoiceBox<String>> TableColumn_customerId;
	@FXML
	private TableColumn<SellDataForTable, String> TableColumn_customerName;
	@FXML
	private TableColumn<CombineSellDataForTable, ChoiceBox<String>> TableColumn_sellCombineName;
	@FXML
	private TableColumn<CombineSellDataForTable, String> TableColumn_sellCombineAmount;
	@FXML
	private TableColumn<CombineDataForTable, String> TableColumn_combineName;
	@FXML
	private TableColumn<CombineDataForTable, String> TableColumn_conbinePrice;
	@FXML
	private TableColumn<ProductDataForTable, ChoiceBox<String>> TableColumn_warehouseId;
	@FXML
	private TableColumn<ProductDataForTable, ChoiceBox<String>> TableColumn_productId;
	@FXML
	private TableColumn<ProductDataForTable, String> TableColumn_productAmount;

	private ObservableList<SellDataForTable> sellTableItems;

	private ObservableList<SellDataForTable> oldSellTableItem;

	private ObservableList<CombineSellDataForTable> sellCombineItems;

	private ObservableList<CombineDataForTable> combineTableItems;

	private ObservableList<ProductDataForTable> productTableItems;

	private Map<String, ObservableList<CombineSellDataForTable>> sellCombineTableItemMap = new HashMap<>();

	private Map<String, ObservableList<ProductDataForTable>> productTableItemMap = new HashMap<>();

	private ResultSet resultSetForSellTable;

	private ResultSet resultSetForSellCombineTable;

	private ResultSet resultSetForCombineTable;

	private ResultSet resultSetForProductTable;

	private String oldSelectedCombineName;

	private String oldSelectedSellId;

	private boolean isSaved = true;

	@FXML
	private ToolBar ToolBar_toolBar;

	private double xOffset;
	private double yOffset;

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		setStageDragable();
		setSellTableColumn();
		setCombineSellTableColumn();
		setCombineTableColumn();
		setProductTableColumn();
		setSellTableItems();
		setUpSellCombineTableItemMap();
		setCombineTableItems();
		setUpProductTableItemMap();
		addListenerToTextField_search();
		createTextFieldToCombineTableColumn();
		createTextFieldToProductTableColumn();
	}

	public void setStageDragable() {
		ToolBar_toolBar.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = main.Main.getSellStage().getX() - event.getScreenX();
				yOffset = main.Main.getSellStage().getY() - event.getScreenY();
			}
		});

		ToolBar_toolBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				main.Main.getSellStage().setX(event.getScreenX() + xOffset);
				main.Main.getSellStage().setY(event.getScreenY() + yOffset);
			}
		});
	}

	public void setSellTableColumn() {
		TableColumn_sellId.setCellValueFactory(new PropertyValueFactory<>("sellId"));
		TableColumn_customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
		TableColumn_customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
	}

	public void setCombineSellTableColumn() {
		TableColumn_sellCombineName.setCellValueFactory(new PropertyValueFactory<>("sellCombineName"));
		TableColumn_sellCombineAmount.setCellValueFactory(new PropertyValueFactory<>("sellCombineAmount"));
	}

	public void setCombineTableColumn() {
		TableColumn_combineName.setCellValueFactory(new PropertyValueFactory<>("combineName"));
		TableColumn_conbinePrice.setCellValueFactory(new PropertyValueFactory<>("combinePrice"));
	}

	public void setProductTableColumn() {
		TableColumn_warehouseId.setCellValueFactory(new PropertyValueFactory<>("warehouseId"));
		TableColumn_productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
		TableColumn_productAmount.setCellValueFactory(new PropertyValueFactory<>("productAmount"));
	}

	public void setSellTableItems() {
		sellTableItems = getSellData();
		TableView_sellTable.setItems(sellTableItems);
	}

	public ObservableList<SellDataForTable> getSellData() {
		retriveDataFromDBForSellTable();
		ObservableList<SellDataForTable> sells = FXCollections.observableArrayList();
		try {
			while (resultSetForSellTable.next()) {
				sells.add(new SellDataForTable(resultSetForSellTable.getString(1), resultSetForSellTable.getString(2)));
			}
		} catch (SQLException e) {
			return FXCollections.observableArrayList();
		}
		return sells;
	}

	public void retriveDataFromDBForSellTable() {
		try {
			Statement statement = main.Main.getConnection().createStatement();
			resultSetForSellTable = statement.executeQuery("SELECT SellID, CustomerID FROM javaclassproject2021.sell");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setUpSellCombineTableItemMap() {
		try {
			retriveSellIdFromDBForSellCombineTable();
			while (resultSetForSellTable.next()) {
				ObservableList<CombineSellDataForTable> sellCombines = FXCollections.observableArrayList();
				String sellId = resultSetForSellTable.getString(1);
				retriveSellCombineFromDBForSellCombineTable(sellId);
				while (resultSetForSellCombineTable.next()) {
					sellCombines.add(new CombineSellDataForTable(resultSetForSellCombineTable.getString(1),
							resultSetForSellCombineTable.getString(2)));
				}
				sellCombineTableItemMap.put(sellId, sellCombines);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void retriveSellIdFromDBForSellCombineTable() throws SQLException {
		Statement statement = main.Main.getConnection().createStatement();
		resultSetForSellTable = statement.executeQuery("SELECT SellID FROM javaclassproject2021.sell");
	}

	public void retriveSellCombineFromDBForSellCombineTable(String sellId) throws SQLException {
		String sql = "SELECT CombineName, Amount FROM javaclassproject2021.sellcombine WHERE SellID = ?";
		PreparedStatement statement = main.Main.getConnection().prepareStatement(sql);
		statement.setString(1, sellId);
		resultSetForSellCombineTable = statement.executeQuery();
	}

	public class SellDataForTable {
		private String sellId;
		private ChoiceBox<String> customerId;
		private String customerName;

		private ResultSet resultsetForChoiceBox;

		public SellDataForTable(String sellId, String customerId) {
			this.sellId = sellId;

			this.customerId = new ChoiceBox<String>();
			this.customerId.setItems(getVendorData());
			this.customerId.setValue(customerId);
			this.customerId.setDisable(true);
			addChangeListenerToCustomerId();

			try {
				this.customerName = retriveCustomerNameFromDB(this.customerId.getValue());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		public ObservableList<String> getVendorData() {
			ObservableList<String> options = FXCollections.observableArrayList();
			try {
				retriveDataFromDBToChoiceBox();
				while (resultsetForChoiceBox.next()) {
					options.add(resultsetForChoiceBox.getString(1));
				}
				resultsetForChoiceBox.close();
			} catch (SQLException e) {
				return FXCollections.observableArrayList();
			}
			return options;
		}

		public void retriveDataFromDBToChoiceBox() throws SQLException {
			Statement statement = main.Main.getConnection().createStatement();
			resultsetForChoiceBox = statement
					.executeQuery("SELECT CustomerID FROM javaclassproject2021.customerinformation");
		}

		public void addChangeListenerToCustomerId() {
			this.customerId.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					String choiceBoxValue = getCustomerId().getItems().get((int) newValue);
					try {
						setCustomerName(retriveCustomerNameFromDB(choiceBoxValue));
						TableView_sellTable.getColumns().get(2).setVisible(false);
						TableView_sellTable.getColumns().get(2).setVisible(true);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			});
		}

		public String retriveCustomerNameFromDB(String customerId) throws SQLException {
			Statement statement = main.Main.getConnection().createStatement();
			ResultSet name = statement
					.executeQuery("SELECT Name FROM javaclassproject2021.customerinformation WHERE CustomerID = \""
							+ customerId + "\"");
			if (name.next())
				return name.getString(1);
			else
				return "";
		}

		public String getSellId() {
			return sellId;
		}

		public void setSellId(String sellId) {
			this.sellId = sellId;
		}

		public ChoiceBox<String> getCustomerId() {
			return customerId;
		}

		public void setCustomerId(ChoiceBox<String> customerId) {
			this.customerId = customerId;
		}

		public String getCustomerName() {
			return customerName;
		}

		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}
	}

	public class CombineSellDataForTable {
		private ChoiceBox<String> sellCombineName;
		private String sellCombineAmount;

		public CombineSellDataForTable(String sellCombineName, String sellCombineAmount) {
			this.sellCombineName = new ChoiceBox<String>();
			this.sellCombineName.setItems(getCombineData());
			this.sellCombineName.setValue(sellCombineName);

			this.sellCombineAmount = sellCombineAmount;
		}

		public ObservableList<String> getCombineData() {
			ObservableList<String> options = FXCollections.observableArrayList();
			for (int i = 0; i < TableView_combineTable.getItems().size(); i++) {
				options.add(TableView_combineTable.getItems().get(i).getCombineName());
			}
			for (int i = 0; i < TableView_combineSellTable.getItems().size() - 1; i++) {
				ChoiceBox<String> choiceBox = TableColumn_sellCombineName.getCellObservableValue(i).getValue();
				String existedCombineName = choiceBox.getValue();
				if (options.contains(existedCombineName))
					options.remove(existedCombineName);
			}
			return options;
		}

		public void addChangeListenerToCombineName() {
			this.sellCombineName.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					for (int i = 0; i < TableView_combineSellTable.getItems().size() - 1; i++) {
						ChoiceBox<String> choiceBox = TableView_combineSellTable.getItems().get(i).getSellCombineName();
						if (choiceBox.getItems().contains(sellCombineName.getItems().get(newValue.intValue()))) {
							ObservableList<String> observableList = choiceBox.getItems();
							observableList.remove(sellCombineName.getItems().get(newValue.intValue()));
							choiceBox.setItems(observableList);
						}
					}
				}
			});
		}

		public ChoiceBox<String> getSellCombineName() {
			return sellCombineName;
		}

		public void setSellCombineName(ChoiceBox<String> sellCombineName) {
			this.sellCombineName = sellCombineName;
		}

		public String getSellCombineAmount() {
			return sellCombineAmount;
		}

		public void setSellCombineAmount(String sellCombineAmount) {
			this.sellCombineAmount = sellCombineAmount;
		}
	}

	public void setCombineTableItems() {
		combineTableItems = getCombineData();
		TableView_combineTable.setItems(combineTableItems);
	}

	public ObservableList<CombineDataForTable> getCombineData() {
		ObservableList<CombineDataForTable> combines = FXCollections.observableArrayList();
		try {
			retriveDataFromDBForCombineTable();
			while (resultSetForCombineTable.next()) {
				combines.add(new CombineDataForTable(resultSetForCombineTable.getString(1),
						resultSetForCombineTable.getString(2)));
			}
		} catch (SQLException e) {
			return FXCollections.observableArrayList();
		}
		return combines;
	}

	public void retriveDataFromDBForCombineTable() throws SQLException {
		Statement statement = main.Main.getConnection().createStatement();
		resultSetForCombineTable = statement
				.executeQuery("SELECT CombineName, CombinePrice FROM javaclassproject2021.combine");
	}

	public void setUpProductTableItemMap() {
		try {
			retriveCombineNameFromDBForProductTable();
			while (resultSetForCombineTable.next()) {
				ObservableList<ProductDataForTable> products = FXCollections.observableArrayList();
				String combineName = resultSetForCombineTable.getString(1);
				retriveProductFromDBForProductTable(combineName);
				while (resultSetForProductTable.next()) {
					products.add(new ProductDataForTable(resultSetForProductTable.getString(1),
							resultSetForProductTable.getString(2), resultSetForProductTable.getString(3)));
				}
				productTableItemMap.put(combineName, products);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void retriveCombineNameFromDBForProductTable() throws SQLException {
		Statement statement = main.Main.getConnection().createStatement();
		resultSetForCombineTable = statement.executeQuery("SELECT CombineName FROM javaclassproject2021.combine");
	}

	public void retriveProductFromDBForProductTable(String combineName) throws SQLException {
		String sql = "SELECT (SELECT WarehouseID FROM productstoreinwarehouse WHERE ProductID = javaclassproject2021.combineproduct.ProductID), ProductID, Amount "
				+ "FROM javaclassproject2021.combineproduct WHERE CombineName = ?";
		PreparedStatement statement = main.Main.getConnection().prepareStatement(sql);
		statement.setString(1, combineName);
		resultSetForProductTable = statement.executeQuery();
	}

	public void addListenerToTextField_search() {
		TextField_search.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.equals("")) {
					setSellTableItems();
				} else {
					compareIdWithNewValueAndShowReslutToTable(newValue);
				}
			}
		});
	}

	public void compareIdWithNewValueAndShowReslutToTable(String targetValue) {
		TableView_sellTable.setItems(getComparedDataWtihTargetValue(targetValue));
		TableView_combineSellTable.setItems(FXCollections.observableArrayList());
	}

	public ObservableList<SellDataForTable> getComparedDataWtihTargetValue(String targetValue) {
		ObservableList<SellDataForTable> sells = FXCollections.observableArrayList();
		try {
			retriveDataFromDBForSellTable();
			while (resultSetForSellTable.next()) {
				if (resultSetForSellTable.getString(1).contains(targetValue) || resultSetForSellTable.getString(2).contains(targetValue)) {
					SellDataForTable sellData = new SellDataForTable(resultSetForSellTable.getString(1),
							resultSetForSellTable.getString(2));
					sells.add(sellData);
				}
			}
		} catch (SQLException e) {
			return FXCollections.observableArrayList();
		}
		return sells;
	}

	public class CombineDataForTable {
		private String combineName;
		private String combinePrice;

		public CombineDataForTable(String comnineName, String price) {
			this.combineName = comnineName;
			this.combinePrice = price;
		}

		public String getCombineName() {
			return combineName;
		}

		public void setCombineName(String combineName) {
			this.combineName = combineName;
		}

		public String getCombinePrice() {
			return combinePrice;
		}

		public void setCombinePrice(String combinePrice) {
			this.combinePrice = combinePrice;
		}
	}

	public class ProductDataForTable {
		private ChoiceBox<String> warehouseId;
		private ChoiceBox<String> productId;
		private String productAmount;

		private ResultSet resultsetForChoiceBox;

		public ProductDataForTable(String warehouseId, String productId, String amount) {
			this.warehouseId = new ChoiceBox<String>();
			this.warehouseId.setItems(getWarehouseData());
			this.warehouseId.setValue(warehouseId);

			this.productId = new ChoiceBox<String>();
			this.productId.setValue(productId);

			this.productAmount = amount;

			addChangeListenerToWarehouseId();
			addChangeListenerToProductId();
		}

		public ObservableList<String> getWarehouseData() {
			ObservableList<String> options = FXCollections.observableArrayList();
			try {
				retriveDataFromDBToWarehouseChoiceBox();
				while (resultsetForChoiceBox.next()) {
					options.add(resultsetForChoiceBox.getString(1));
				}
				resultsetForChoiceBox.close();
			} catch (SQLException e) {
				return FXCollections.observableArrayList();
			}
			return options;
		}

		public void retriveDataFromDBToWarehouseChoiceBox() throws SQLException {
			Statement statement = main.Main.getConnection().createStatement();
			resultsetForChoiceBox = statement.executeQuery("SELECT WarehouseID FROM javaclassproject2021.warehouse");
		}

		public void addChangeListenerToWarehouseId() {
			this.warehouseId.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					productId.setItems(getProductData(newValue.intValue()));
				}
			});
		}

		public ObservableList<String> getProductData(int newValueIndex) {
			ObservableList<String> options = FXCollections.observableArrayList();
			try {
				retriveDataFromDBToProductChoiceBox(newValueIndex);
				while (resultsetForChoiceBox.next()) {
					options.add(resultsetForChoiceBox.getString(1));
				}
				resultsetForChoiceBox.close();
			} catch (SQLException e) {
				return FXCollections.observableArrayList();
			}
			for (int i = 0; i < TableView_productTable.getItems().size() - 1; i++) {
				ChoiceBox<String> choiceBox = TableColumn_productId.getCellObservableValue(i).getValue();
				String existedProductId = choiceBox.getValue();
				if (options.contains(existedProductId))
					options.remove(existedProductId);
			}
			return options;
		}

		public void retriveDataFromDBToProductChoiceBox(int newValueIndex) throws SQLException {
			String sql = "SELECT ProductID FROM javaclassproject2021.productstoreinwarehouse WHERE WarehouseID = ?";
			PreparedStatement statement = main.Main.getConnection().prepareStatement(sql);
			statement.setString(1, warehouseId.getItems().get(newValueIndex));
			resultsetForChoiceBox = statement.executeQuery();
		}

		public void addChangeListenerToProductId() {
			this.productId.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					for (int i = 0; i < TableView_productTable.getItems().size() - 1; i++) {
						ChoiceBox<String> choiceBox = TableView_productTable.getItems().get(i).getProductId();
						if (choiceBox.getItems().contains(productId.getItems().get(newValue.intValue()))) {
							ObservableList<String> observableList = choiceBox.getItems();
							observableList.remove(productId.getItems().get(newValue.intValue()));
							choiceBox.setItems(observableList);
						}
					}
					updateCombineProductIdToDB(oldValue, newValue);
				}
			});
		}

		public void updateCombineProductIdToDB(Number oldValue, Number newValue) {
			try {
				if (oldValue.intValue() == -1) {
					String insertSql = "INSERT INTO javaclassproject2021.combineproduct VALUES (?, ?, ?)";
					String combineName = TableView_combineTable.getSelectionModel().getSelectedItem().getCombineName();
					String productId = this.productId.getItems().get(newValue.intValue());
					String productAmount = getProductAmount() == "" ? "0" : getProductAmount();
					PreparedStatement insertStatement = main.Main.getConnection().prepareStatement(insertSql);
					insertStatement.setString(1, combineName);
					insertStatement.setString(2, productId);
					insertStatement.setInt(3, Integer.parseInt(productAmount));
					insertStatement.execute();
				} else if (oldValue != newValue) {
					String updateSql = "UPDATE javaclassproject2021.combineproduct SET ProductID = ? WHERE CombineName = ? AND ProductID = ?";
					String newProductId = this.productId.getItems().get(newValue.intValue());
					String combineName = TableView_combineTable.getSelectionModel().getSelectedItem().getCombineName();
					String oldProductId = this.productId.getItems().get(oldValue.intValue());
					PreparedStatement updateStatement = main.Main.getConnection().prepareStatement(updateSql);
					updateStatement.setString(1, newProductId);
					updateStatement.setString(2, combineName);
					updateStatement.setString(3, oldProductId);
					updateStatement.execute();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public ChoiceBox<String> getWarehouseId() {
			return warehouseId;
		}

		public void setWarehouseId(ChoiceBox<String> warehouseId) {
			this.warehouseId = warehouseId;
		}

		public ChoiceBox<String> getProductId() {
			return productId;
		}

		public void setProductId(ChoiceBox<String> productId) {
			this.productId = productId;
		}

		public String getProductAmount() {
			return productAmount;
		}

		public void setProductAmount(String productAmount) {
			this.productAmount = productAmount;
		}
	}

	@FXML
	void insertButtonClicked(ActionEvent event) {
		TableView_sellTable.setEditable(true);

		oldSellTableItem = sellTableItems;

		createNewRowInSellTable();
		createTextFieldToSellTableColumn();
		setOnEditOnNewSellRow();
		setChoiceBoxInSellTableEnable();

		isSaved = false;

		Button_saveButton.setDisable(false);
		Button_quitButton.setDisable(false);
		TableView_combineSellTable.setDisable(false);

		Button_insertButton.setDisable(true);
		Button_deleteButton.setDisable(true);
		Button_editButton.setDisable(true);
		Button_leaveButton.setDisable(true);
		Button_printAll.setDisable(true);
		TextField_search.setDisable(true);
	}

	public void createNewRowInSellTable() {
		sellTableItems.add(new SellDataForTable("", ""));
		TableView_sellTable.setItems(sellTableItems);
	}

	public void createTextFieldToSellTableColumn() {
		TableColumn_sellId.setCellFactory(TextFieldTableCell.forTableColumn());
		TableColumn_sellId.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SellDataForTable, String>>() {
			@Override
			public void handle(CellEditEvent<SellDataForTable, String> e) {
				e.getTableView().getItems().get(e.getTablePosition().getRow()).setSellId(e.getNewValue());
			}
		});
	}

	public void setOnEditOnNewSellRow() {
		TableView_sellTable.getSelectionModel().clearAndSelect(TableView_sellTable.getItems().size() - 1);
		int selectedRow = TableView_sellTable.getSelectionModel().getSelectedIndex();
		TableView_sellTable.edit(selectedRow, TableColumn_sellId);
	}

	public void setChoiceBoxInSellTableEnable() {
		for (int i = 0; i < TableView_sellTable.getItems().size(); i++) {
			TableView_sellTable.getItems().get(i).getCustomerId().setDisable(false);
		}
	}

	@FXML
	void sellTableOnClicked(MouseEvent event) {
		if (TableView_sellTable.getSelectionModel().getSelectedItem() != null) {
			String sellId = TableView_sellTable.getSelectionModel().getSelectedItem().getSellId();
			if (!sellId.equals("")) {
				if (isSaved == false) {
					Button_deleteCombineSell.setDisable(false);
					Button_newCombineSell.setDisable(false);
					TableView_combineSellTable.setEditable(true);
				}
				if (isSaved == true) {
					Button_printSingle.setDisable(false);
					Button_deleteButton.setDisable(false);
				}
				if (sellId != oldSelectedSellId && oldSelectedSellId != null) {
					sellCombineTableItemMap.put(oldSelectedSellId, sellCombineItems);
				}
				if (!sellCombineTableItemMap.containsKey(sellId)) {
					sellCombineItems = FXCollections.observableArrayList();
					sellCombineTableItemMap.put(sellId, sellCombineItems);
					TableView_combineSellTable.setItems(sellCombineItems);
					oldSelectedSellId = sellId;
				} else {
					sellCombineItems = sellCombineTableItemMap.get(sellId);
					TableView_combineSellTable.setItems(sellCombineItems);
					oldSelectedSellId = sellId;
				}
			} else {
				Button_deleteCombineSell.setDisable(false);
				TableView_combineSellTable.setEditable(true);
			}
		}
	}

	@FXML
	void deleteButtonClicked(ActionEvent event) {

		revertProductToProductSetting();
		deleteSellFromDB();

		SellDataForTable item = TableView_sellTable.getSelectionModel().getSelectedItem();
		sellCombineTableItemMap.remove(item.getSellId());
		sellCombineItems = FXCollections.observableArrayList();
		TableView_combineSellTable.setItems(sellCombineItems);
		TableView_combineSellTable.setEditable(false);
		TableView_sellTable.getItems().remove(item);
		TableView_sellTable.setItems(sellTableItems);

		product.setting.stage.Controller controller = mainmenu.stage.Controller.getProductSetting().getContorller();
		controller.getTableView_warehouseTable().setItems(FXCollections.observableArrayList());
		controller.setProductTotalAmountWithSQLException();
		controller.updateProductTableDataToDBWithSQLException();
		controller.removeTextFieldAndUpdateProductTableCell();
		controller.removeTextFieldAndUpdateWarehouseTableCell();
		controller.setProductTableItems();

		Button_deleteButton.setDisable(true);
	}

	public void revertProductToProductSetting() {
		try {
			String sellId = TableView_sellTable.getSelectionModel().getSelectedItem().getSellId();
			ObservableList<CombineSellDataForTable> sellCombines = sellCombineTableItemMap.get(sellId);
			for (int j = 0; j < sellCombines.size(); j++) {
				String combineName = sellCombines.get(j).getSellCombineName().getValue();
				int combineAmount = Integer.parseInt(sellCombines.get(j).getSellCombineAmount());

				ObservableList<ProductDataForTable> products = productTableItemMap.get(combineName);
				for (int k = 0; k < products.size(); k++) {
					int combineProductAmount = Integer.parseInt(products.get(k).getProductAmount());
					String combineProductId = products.get(k).getProductId().getValue();
					String combineProductWarehouseId = products.get(k).getWarehouseId().getValue();
					String updateProductStoreInWarehouseSql = "UPDATE javaclassproject2021.productstoreinwarehouse SET Amount = Amount + ? WHERE ProductID = ? AND WarehouseID = ?";
					PreparedStatement updateProductStoreInWarehouseStatement = main.Main.getConnection()
							.prepareStatement(updateProductStoreInWarehouseSql);
					updateProductStoreInWarehouseStatement.setInt(1, combineProductAmount * combineAmount);
					updateProductStoreInWarehouseStatement.setString(2, combineProductId);
					updateProductStoreInWarehouseStatement.setString(3, combineProductWarehouseId);
					updateProductStoreInWarehouseStatement.execute();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteSellFromDB() {
		try {
			String sql = "DELETE FROM javaclassproject2021.sell WHERE SellID = ?";
			PreparedStatement statement = main.Main.getConnection().prepareStatement(sql);
			statement.setString(1, TableView_sellTable.getSelectionModel().getSelectedItem().getSellId());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void editButtonClicked(ActionEvent event) {
		TableView_sellTable.setEditable(true);

		oldSellTableItem = sellTableItems;

		createTextFieldToSellTableColumn();
		setChoiceBoxInSellTableEnable();

		isSaved = false;

		Button_saveButton.setDisable(false);
		Button_quitButton.setDisable(false);
		TableView_combineSellTable.setDisable(false);

		Button_insertButton.setDisable(true);
		Button_deleteButton.setDisable(true);
		Button_editButton.setDisable(true);
		Button_leaveButton.setDisable(true);
		Button_printAll.setDisable(true);
		Button_printSingle.setDisable(true);
		TextField_search.setDisable(true);
	}

	@FXML
	void saveButtonClicked(ActionEvent event) {
		for (SellDataForTable rowItems : TableView_sellTable.getItems()) {
			if (rowItems.getSellId().equals("") || rowItems.getCustomerId().getValue().equals("")) {
				showNullIDAlertBox();
				return;
			}
		}

		TableView_sellTable.setEditable(false);

		removeProductFromProductSetting();
		updateTableDataToDB();
		setChoiceBoxInSellTableDisable();

		product.setting.stage.Controller controller = mainmenu.stage.Controller.getProductSetting().getContorller();
		controller.getTableView_warehouseTable().setItems(FXCollections.observableArrayList());
		controller.setProductTotalAmountWithSQLException();
		controller.updateProductTableDataToDBWithSQLException();
		controller.removeTextFieldAndUpdateProductTableCell();
		controller.removeTextFieldAndUpdateWarehouseTableCell();
		controller.setProductTableItems();

		isSaved = true;

		Button_insertButton.setDisable(false);
		Button_deleteButton.setDisable(false);
		Button_editButton.setDisable(false);
		Button_leaveButton.setDisable(false);
		Button_printAll.setDisable(false);
		TextField_search.setDisable(false);

		Button_saveButton.setDisable(true);
		Button_quitButton.setDisable(true);
		TableView_combineSellTable.setDisable(true);
	}

	public void showNullIDAlertBox() {
		try {
			main.Main.setNullIdAlertBoxStage(new Stage());
			new NullIDAlertBox().launchScene(main.Main.getNullIdAlertBoxStage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateTableDataToDB() {
		String sellUpdateSql = "UPDATE javaclassproject2021.sell SET SellID = ?, CustomerID = ? WHERE SellID = ?";
		String sellInsertSql = "INSERT INTO javaclassproject2021.sell VALUES (?, ?)";
		String sellCombineInsertSql = "INSERT INTO javaclassproject2021.sellcombine VALUES (?, ?, ?)";
		String sellCombineUpdateSql = "UPDATE javaclassproject2021.sellcombine SET Amount = ? WHERE CombineName = ?";
		for (int i = 0; i < TableView_sellTable.getItems().size(); i++) {
			try {
				String sellId = TableView_sellTable.getItems().get(i).getSellId();
				String customerId = TableView_sellTable.getItems().get(i).getCustomerId().getValue();
				String oldSellId = oldSellTableItem.get(i).getSellId();
				PreparedStatement sellUpdateStatement = main.Main.getConnection().prepareStatement(sellUpdateSql);
				sellUpdateStatement.setString(1, sellId);
				sellUpdateStatement.setString(2, customerId);
				sellUpdateStatement.setString(3, oldSellId);
				int resultCount = sellUpdateStatement.executeUpdate();
				if (resultCount > 0) {
					ObservableList<CombineSellDataForTable> sellCombineList = sellCombineTableItemMap.get(sellId);
					if (sellCombineList != null) {
						for (int j = 0; j < sellCombineList.size(); j++) {
							try {
								String combineName = sellCombineList.get(j).getSellCombineName().getValue();
								String combineAmount = sellCombineList.get(j).getSellCombineAmount() == "" ? "0"
										: sellCombineList.get(j).getSellCombineAmount();
								PreparedStatement sellCombineInsertStatement = main.Main.getConnection()
										.prepareStatement(sellCombineInsertSql);
								sellCombineInsertStatement.setString(1, sellId);
								sellCombineInsertStatement.setString(2, combineName);
								sellCombineInsertStatement.setInt(3, Integer.parseInt(combineAmount));
								sellCombineInsertStatement.execute();
							} catch (SQLException e) {
								String combineName = sellCombineList.get(j).getSellCombineName().getValue();
								String combineAmount = sellCombineList.get(j).getSellCombineAmount() == "" ? "0"
										: sellCombineList.get(j).getSellCombineAmount();
								PreparedStatement sellCombineUpdateStament = main.Main.getConnection()
										.prepareStatement(sellCombineUpdateSql);
								sellCombineUpdateStament.setInt(1, Integer.parseInt(combineAmount));
								sellCombineUpdateStament.setString(2, combineName);
								sellCombineUpdateStament.execute();
							}
						}
					}
				} else {
					PreparedStatement sellInsertStatement = main.Main.getConnection().prepareStatement(sellInsertSql);
					sellInsertStatement.setString(1, sellId);
					sellInsertStatement.setString(2, customerId);
					sellInsertStatement.execute();
					ObservableList<CombineSellDataForTable> sellCombineList = sellCombineTableItemMap.get(sellId);
					if (sellCombineList != null) {
						for (int j = 0; j < sellCombineList.size(); j++) {
							try {
								String combineName = sellCombineList.get(j).getSellCombineName().getValue();
								String combineAmount = sellCombineList.get(j).getSellCombineAmount() == "" ? "0"
										: sellCombineList.get(j).getSellCombineAmount();
								PreparedStatement sellCombineInsertStatement = main.Main.getConnection()
										.prepareStatement(sellCombineInsertSql);
								sellCombineInsertStatement.setString(1, sellId);
								sellCombineInsertStatement.setString(2, combineName);
								sellCombineInsertStatement.setInt(3, Integer.parseInt(combineAmount));
								sellCombineInsertStatement.execute();
							} catch (SQLException e) {
								String combineName = sellCombineList.get(j).getSellCombineName().getValue();
								String combineAmount = sellCombineList.get(j).getSellCombineAmount() == "" ? "0"
										: sellCombineList.get(j).getSellCombineAmount();
								PreparedStatement sellCombineUpdateStament = main.Main.getConnection()
										.prepareStatement(sellCombineUpdateSql);
								sellCombineUpdateStament.setInt(1, Integer.parseInt(combineAmount));
								sellCombineUpdateStament.setString(2, combineName);
								sellCombineUpdateStament.execute();
							}
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void removeProductFromProductSetting() {
		try {
			for (int i = 0; i < TableView_sellTable.getItems().size(); i++) {
				String sellId = TableView_sellTable.getItems().get(i).getSellId();
				ObservableList<CombineSellDataForTable> sellCombines = sellCombineTableItemMap.get(sellId);
				if (sellCombines != null) {
					for (int j = 0; j < sellCombines.size(); j++) {
						String combineName = sellCombines.get(j).getSellCombineName().getValue();
						int newCombineAmount = Integer.parseInt(sellCombines.get(j).getSellCombineAmount());
						int oldCombineAmount = 0;

						String getOldCombineAmountSql = "SELECT Amount FROM javaclassproject2021.sellcombine WHERE SellID = ? AND CombineName = ?";
						PreparedStatement getOldCombineAmountStatement = main.Main.getConnection()
								.prepareStatement(getOldCombineAmountSql);
						getOldCombineAmountStatement.setString(1, sellId);
						getOldCombineAmountStatement.setString(2, combineName);
						ResultSet getOldCombineAmountResultSet = getOldCombineAmountStatement.executeQuery();
						if (getOldCombineAmountResultSet.next()) {
							oldCombineAmount = getOldCombineAmountResultSet.getInt(1);
						}
						getOldCombineAmountResultSet.close();

						int combineAmountDelta = newCombineAmount - oldCombineAmount;

						ObservableList<ProductDataForTable> products = productTableItemMap.get(combineName);
						for (int k = 0; k < products.size(); k++) {
							int combineProductAmount = Integer.parseInt(products.get(k).getProductAmount());
							String combineProductId = products.get(k).getProductId().getValue();
							String combineProductWarehouseId = products.get(k).getWarehouseId().getValue();
							String updateProductStoreInWarehouseSql = "UPDATE productstoreinwarehouse SET Amount = Amount - ? WHERE ProductID = ? AND WarehouseID = ?";
							PreparedStatement updateProductStoreInWarehouseStatement = main.Main.getConnection()
									.prepareStatement(updateProductStoreInWarehouseSql);
							updateProductStoreInWarehouseStatement.setInt(1, combineProductAmount * combineAmountDelta);
							updateProductStoreInWarehouseStatement.setString(2, combineProductId);
							updateProductStoreInWarehouseStatement.setString(3, combineProductWarehouseId);
							updateProductStoreInWarehouseStatement.execute();
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setChoiceBoxInSellTableDisable() {
		for (int i = 0; i < TableView_sellTable.getItems().size(); i++) {
			TableView_sellTable.getItems().get(i).getCustomerId().setDisable(true);
		}
	}

	@FXML
	void quitButtonClicked(ActionEvent event) {
		TableView_sellTable.setEditable(false);

		setSellTableItems();
		setUpSellCombineTableItemMap();

		isSaved = true;

		Button_insertButton.setDisable(false);
		Button_deleteButton.setDisable(false);
		Button_editButton.setDisable(false);
		Button_leaveButton.setDisable(false);
		Button_printAll.setDisable(false);
		TextField_search.setDisable(false);

		Button_saveButton.setDisable(true);
		Button_quitButton.setDisable(true);
		TableView_combineSellTable.setDisable(true);
	}

	@FXML
	void leaveButtonClicked(ActionEvent event) throws SQLException {
		resultSetForSellTable.close();
		resultSetForCombineTable.close();
		if (resultSetForSellCombineTable != null)
			resultSetForSellCombineTable.close();
		if (resultSetForProductTable != null)
			resultSetForProductTable.close();
		main.Main.getSellStage().close();
	}

	@FXML
	void newCombineSellButtonClicked(ActionEvent event) {
		createNewRowInCombineSellTable();
		createTextFieldToCombineSellTableColumn();
	}

	public void createNewRowInCombineSellTable() {
		CombineSellDataForTable combineSellDatForTable = new CombineSellDataForTable("", "0");
		sellCombineItems.add(combineSellDatForTable);
		TableView_combineSellTable.setItems(sellCombineItems);
	}

	public void createTextFieldToCombineSellTableColumn() {
		TableColumn_sellCombineAmount.setCellFactory(TextFieldTableCell.forTableColumn());
		TableColumn_sellCombineAmount
				.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CombineSellDataForTable, String>>() {
					@Override
					public void handle(CellEditEvent<CombineSellDataForTable, String> e) {
						e.getTableView().getItems().get(e.getTablePosition().getRow())
								.setSellCombineAmount(e.getNewValue());
					}
				});
	}

	@FXML
	void deleteCombineSellButtonClicked(ActionEvent event) {
		TableView_combineSellTable.getItems().remove(TableView_combineSellTable.getSelectionModel().getSelectedItem());
		TableView_combineSellTable.setItems(sellCombineItems);
	}

	@FXML
	void newCombineButtonClicked(ActionEvent event) {
		createNewRowInCombineTable();
		createTextFieldToCombineTableColumn();
		setOnEditOnNewCombineRow();
	}

	public void createNewRowInCombineTable() {
		combineTableItems.add(new CombineDataForTable("", "0"));
		TableView_combineTable.setItems(combineTableItems);
	}

	public void createTextFieldToCombineTableColumn() {
		TableColumn_combineName.setCellFactory(TextFieldTableCell.forTableColumn());
		TableColumn_combineName
				.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CombineDataForTable, String>>() {
					@Override
					public void handle(CellEditEvent<CombineDataForTable, String> e) {
						e.getTableView().getItems().get(e.getTablePosition().getRow()).setCombineName(e.getNewValue());

						addNewCombineNameToSellCombineNameChoiceBox(e.getNewValue());
					}
				});

		TableColumn_conbinePrice.setCellFactory(TextFieldTableCell.forTableColumn());
		TableColumn_conbinePrice
				.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CombineDataForTable, String>>() {
					@Override
					public void handle(CellEditEvent<CombineDataForTable, String> e) {
						e.getTableView().getItems().get(e.getTablePosition().getRow()).setCombinePrice(e.getNewValue());
					}
				});
	}

	public void addNewCombineNameToSellCombineNameChoiceBox(String newValue) {
		for (int i = 0; i < TableView_combineSellTable.getItems().size(); i++) {
			ChoiceBox<String> choiceBox = TableView_combineSellTable.getItems().get(i).getSellCombineName();
			ObservableList<String> options = choiceBox.getItems();
			options.add(newValue);
			choiceBox.setItems(options);
		}
	}

	public void setOnEditOnNewCombineRow() {
		TableView_combineTable.getSelectionModel().clearAndSelect(TableView_combineTable.getItems().size() - 1);
		int selectedRow = TableView_combineTable.getSelectionModel().getSelectedIndex();
		TableView_combineTable.edit(selectedRow, TableColumn_combineName);
	}

	@FXML
	void deleteCombineButtonClicked(ActionEvent event) {

		deleteCombineFromDB();

		CombineDataForTable item = TableView_combineTable.getSelectionModel().getSelectedItem();
		productTableItemMap.remove(item.getCombineName());
		productTableItems = FXCollections.observableArrayList();
		TableView_productTable.setItems(productTableItems);
		TableView_productTable.setEditable(false);
		TableView_combineTable.getItems().remove(item);
		TableView_combineTable.setItems(combineTableItems);

		Button_deleteCombine.setDisable(true);
	}

	public void deleteCombineFromDB() {
		try {
			String sql = "DELETE FROM javaclassproject2021.combine WHERE CombineName = ?";
			PreparedStatement statement = main.Main.getConnection().prepareStatement(sql);
			statement.setString(1, TableView_combineTable.getSelectionModel().getSelectedItem().getCombineName());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void combineTableOnClicked(MouseEvent event) {
		if (TableView_combineTable.getSelectionModel().getSelectedItem() != null) {
			String combineName = TableView_combineTable.getSelectionModel().getSelectedItem().getCombineName();
			if (!combineName.equals("")) {
				Button_deleteCombine.setDisable(false);
				Button_newProduct.setDisable(false);
				TableView_productTable.setEditable(true);

				if (combineName != oldSelectedCombineName && oldSelectedCombineName != null) {
					productTableItemMap.put(oldSelectedCombineName, productTableItems);
				}
				if (!productTableItemMap.containsKey(combineName)) {
					productTableItems = FXCollections.observableArrayList();
					productTableItemMap.put(combineName, productTableItems);
					TableView_productTable.setItems(productTableItems);
					oldSelectedCombineName = combineName;
				} else {
					productTableItems = productTableItemMap.get(combineName);
					TableView_productTable.setItems(productTableItems);
					oldSelectedCombineName = combineName;
				}
			}
		} else {
			Button_deleteCombine.setDisable(true);
			TableView_productTable.setEditable(false);
		}

		updateCombineToDB();
	}

	public void updateCombineToDB() {
		for (int i = 0; i < TableView_combineTable.getItems().size(); i++) {
			if (!TableView_combineTable.getItems().get(i).getCombineName().equals("")) {
				try {
					String sql = "INSERT INTO javaclassproject2021.combine VALUES (?, ?)";
					String combineName = TableView_combineTable.getItems().get(i).getCombineName();
					String combinePrice = TableView_combineTable.getItems().get(i).getCombinePrice() == "" ? "0"
							: TableView_combineTable.getItems().get(i).getCombinePrice();
					PreparedStatement statement = main.Main.getConnection().prepareStatement(sql);
					statement.setString(1, combineName);
					statement.setInt(2, Integer.parseInt(combinePrice));
					statement.execute();
				} catch (SQLException e) {
					try {
						String sql = "UPDATE javaclassproject2021.combine SET CombinePrice = ? WHERE CombineName = ?";
						String combineName = TableView_combineTable.getItems().get(i).getCombineName();
						String combinePrice = TableView_combineTable.getItems().get(i).getCombinePrice() == "" ? "0"
								: TableView_combineTable.getItems().get(i).getCombinePrice();
						PreparedStatement statement = main.Main.getConnection().prepareStatement(sql);
						statement.setInt(1, Integer.parseInt(combinePrice));
						statement.setString(2, combineName);
						statement.execute();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}

	@FXML
	void newProductButtonClicked(ActionEvent event) {
		createNewRowInProductTable();
		createTextFieldToProductTableColumn();
	}

	public void createNewRowInProductTable() {
		productTableItems.add(new ProductDataForTable("", "", "0"));
		TableView_productTable.setItems(productTableItems);
	}

	public void createTextFieldToProductTableColumn() {
		TableColumn_productAmount.setCellFactory(TextFieldTableCell.forTableColumn());
		TableColumn_productAmount
				.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductDataForTable, String>>() {
					@Override
					public void handle(CellEditEvent<ProductDataForTable, String> e) {
						e.getTableView().getItems().get(e.getTablePosition().getRow())
								.setProductAmount(e.getNewValue());

						updateProductAmountToDB(e.getNewValue());
					}
				});
	}

	public void updateProductAmountToDB(String amount) {
		try {
			if (!TableView_productTable.getSelectionModel().getSelectedItem().getProductId().getValue().equals("")) {
				String sql = "UPDATE javaclassproject2021.combineproduct SET Amount = ? WHERE CombineName = ? AND ProductID = ?";
				String CombineName = TableView_combineTable.getSelectionModel().getSelectedItem().getCombineName();
				String ProductID = TableView_productTable.getSelectionModel().getSelectedItem().getProductId()
						.getValue();
				PreparedStatement statement = main.Main.getConnection().prepareStatement(sql);
				statement.setString(1, amount);
				statement.setString(2, CombineName);
				statement.setString(3, ProductID);
				statement.execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void deleteProductButtonClicked(ActionEvent event) {

		deleteProductFromDB();

		TableView_productTable.getItems().remove(TableView_productTable.getSelectionModel().getSelectedItem());
		TableView_productTable.setItems(productTableItems);
	}

	public void deleteProductFromDB() {
		try {
			String sql = "DELETE FROM javaclassproject2021.combineproduct WHERE CombineName = ? AND ProductID = ?";
			PreparedStatement statement = main.Main.getConnection().prepareStatement(sql);
			statement.setString(1, TableView_combineTable.getSelectionModel().getSelectedItem().getCombineName());
			statement.setString(2,
					TableView_productTable.getSelectionModel().getSelectedItem().getProductId().getValue());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void productTableOnClicked(MouseEvent event) {
		if (TableView_productTable.getSelectionModel().getSelectedItem() != null) {
			Button_deleteProduct.setDisable(false);
		}
	}

	@FXML
	void sellRankingClicked(ActionEvent event) throws IOException {
		Parent root = (Parent) FXMLLoader.load(getClass().getResource("/fxml/SellRankingUI.fxml"));
		Scene scene = Button_sellRanking.getScene();

		root.translateXProperty().set(scene.getWidth());
		parentContainer.getChildren().add(root);

		Timeline timeline = new Timeline();
		KeyValue keyValue = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(500), keyValue);
		timeline.getKeyFrames().add(keyFrame);
		timeline.setOnFinished(e -> {
			parentContainer.getChildren().remove(anchorRoot);
		});
		timeline.play();
	}

	@FXML
	void printAllOnClick(ActionEvent event) {
		try {
			String sql = "SELECT sell.SellID, sell.CustomerID, "
					+ "(SELECT Name FROM javaclassproject2021.customerinformation WHERE CustomerID = sell.CustomerID) CustomerName, "
					+ "sellcombine.CombineName, sellcombine.Amount, combine.CombinePrice "
					+ "FROM javaclassproject2021.sell, javaclassproject2021.sellcombine, javaclassproject2021.combine "
					+ "WHERE sell.SellID = sellcombine.SellID AND sellcombine.CombineName = combine.CombineName "
					+ "ORDER BY sell.SellID ASC";
			PreparedStatement statement = main.Main.getConnection().prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			ArrayList<SellDataItems> data = new ArrayList<>();
			while (resultSet.next()) {
				String sellId = resultSet.getString(1);
				String customerId = resultSet.getString(2);
				String customerName = resultSet.getString(3);
				String combineName = resultSet.getString(4);
				String sellAmount = String.valueOf(resultSet.getInt(5));
				String sellPrice = String.valueOf(resultSet.getInt(6));
				String totalSellPrice = String.valueOf(resultSet.getInt(5) * resultSet.getInt(6));
				data.add(new SellDataItems(sellId, customerId, customerName, combineName, sellAmount, sellPrice,
						totalSellPrice));
			}
			new CreateSellExcelFile(data);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void printSingleOnClick(ActionEvent event) {
		try {
			String sql = "SELECT sell.SellID, sell.CustomerID, "
					+ "(SELECT Name FROM javaclassproject2021.customerinformation WHERE CustomerID = sell.CustomerID) CustomerName, "
					+ "sellcombine.CombineName, sellcombine.Amount, combine.CombinePrice "
					+ "FROM javaclassproject2021.sell, javaclassproject2021.sellcombine, javaclassproject2021.combine "
					+ "WHERE sell.SellID = sellcombine.SellID AND sellcombine.CombineName = combine.CombineName AND sell.SellID = ?"
					+ "ORDER BY sell.SellID ASC";
			PreparedStatement statement = main.Main.getConnection().prepareStatement(sql);
			statement.setString(1, TableView_sellTable.getSelectionModel().getSelectedItem().getSellId());
			ResultSet resultSet = statement.executeQuery();
			ArrayList<SellDataItems> data = new ArrayList<>();
			while (resultSet.next()) {
				String sellId = resultSet.getString(1);
				String customerId = resultSet.getString(2);
				String customerName = resultSet.getString(3);
				String combineName = resultSet.getString(4);
				String sellAmount = String.valueOf(resultSet.getInt(5));
				String sellPrice = String.valueOf(resultSet.getInt(6));
				String totalSellPrice = String.valueOf(resultSet.getInt(5) * resultSet.getInt(6));
				data.add(new SellDataItems(sellId, customerId, customerName, combineName, sellAmount, sellPrice,
						totalSellPrice));
			}
			new CreateSellExcelFile(data);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
