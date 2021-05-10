package product.purchase.stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

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

public class Controller implements Initializable{
	
	@FXML
	private TextField TextField_search;
	
	@FXML
	private TableView<PurchaseDataForTable> TableView_purchaseTable;
	@FXML
	private TableView<?> TableView_productTable;
	@FXML	
	private TableView<?> TableView_warehouseTable;
	@FXML
	private TableColumn<PurchaseDataForTable, String> TableColumn_purchaseId;
	@FXML
	private TableColumn<PurchaseDataForTable, ChoiceBox<String>> TableColumn_vendorId;
	@FXML
	private TableColumn<PurchaseDataForTable, String> TableColumn_vendorName;
	@FXML
	private TableColumn<?, String> TableColumn_productId;
	@FXML
	private TableColumn<?, String> TableColumn_warehouseId;
	@FXML
	private TableColumn<?, Integer> TableColumn_costPrice;
	@FXML
	private TableColumn<?, Integer> TableColumn_amount;
	@FXML
	private TableColumn<?, String> TableColumn_unit;
	
	private ObservableList<PurchaseDataForTable> purchaseTableItems;
	
	private ObservableList<?> productTableItems;
	
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
    
    private boolean isSaved = true;
    
    @FXML
    private ToolBar ToolBar_toolBar;
    
    public double xOffset;
	public double yOffset;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setStageDragable();
		initTable();
		setProductTableColumn();
	}
	
	public void setStageDragable() {
		ToolBar_toolBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            		xOffset = product.setting.stage.Controller.getPurchaseStage().getX() - event.getScreenX();
            		yOffset = product.setting.stage.Controller.getPurchaseStage().getY() - event.getScreenY();
            }
        });
    	
    	ToolBar_toolBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            		product.setting.stage.Controller.getPurchaseStage().setX(event.getScreenX() + xOffset);
            		product.setting.stage.Controller.getPurchaseStage().setY(event.getScreenY() + yOffset);
            }
        });
	}
	
	public void initTable() {
		setPurchaseTableColumn();
		setPurchaseTableItems();
		setProductTableColumn();
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
				PurchaseDataForTable purcheasData = new PurchaseDataForTable(resultsetForPurchaseTable.getString(1), resultsetForPurchaseTable.getString(2));
				purchases.add(purcheasData);
			} while(resultsetForPurchaseTable.next());
		} catch (SQLException e) {
			return FXCollections.observableArrayList();
		}
		return purchases;
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
    			} while(resultsetForChoiceBox.next());
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
        	resultsetForChoiceBox = statement.executeQuery("SELECT VendorID FROM javaclassproject2021.vendorinformation");
        	resultsetForChoiceBox.next();
        }
        
        public void addChangeListenerToVendorId() {
        	this.vendorId.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
        		@Override
        		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        			String choiceBoxValue = getVendorId().getItems().get((int )newValue);
        			try {
						setVendorName(retriveVendorNameFromDB(choiceBoxValue));
						TableView_purchaseTable.getColumns().get(2).setVisible(false);
						TableView_purchaseTable.getColumns().get(2).setVisible(true);
						TableView_productTable.setDisable(false);
						//setProduct
					} catch (SQLException e) {
						e.printStackTrace();
					}
        	    }
			});
        }
        
        public String retriveVendorNameFromDB(String vendorId) throws SQLException {
        	Statement statement = main.Main.getConnection().createStatement();
        	ResultSet name = statement.executeQuery("SELECT Name FROM javaclassproject2021.vendorinformation WHERE VendorID = \"" + vendorId + "\"");
        	if (name.next()) return name.getString(1);
        	else return "";
        }

		public String getPurchaseId() { return purchaseId; }
		public void setPurchaseId(String purchaseId) { this.purchaseId = purchaseId; }

		public ChoiceBox<String> getVendorId() { return vendorId; }
		public void setVendorId(ChoiceBox<String> vendorId) { this.vendorId = vendorId; }

		public String getVendorName() { return vendorName; }
		public void setVendorName(String vendorName) { this.vendorName = vendorName; }
		
	}
	
	public ObservableList<PurchaseDataForTable> getProductData() {
		retriveDataFromDBForPurchaseTableWithSQLException();
    	ObservableList<PurchaseDataForTable> purchases = FXCollections.observableArrayList();
    	try {
			do {
				PurchaseDataForTable purcheasData = new PurchaseDataForTable(resultsetForPurchaseTable.getString(1), resultsetForPurchaseTable.getString(2));
				purchases.add(purcheasData);
			} while(resultsetForPurchaseTable.next());
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
    	resultsetForPurchaseTable = statement.executeQuery("SELECT PurchaseID, VendorID FROM javaclassproject2021.purchase");
    	resultsetForPurchaseTable.next();
	}
	
	public void setProductTableColumn() {
		TableColumn_productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
	}
	
	@FXML
	public void insertButtonClicked(ActionEvent event) {
		 TableView_purchaseTable.setEditable(true);
		 
		 isSaved = false;
		 
		 createNewRowInPurchaseTableWithSQLExcpetion();
		 createTextFieldToProductTableColumn();
		 setOnEditOnNewProductRow();
		 
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
	
	public void createTextFieldToProductTableColumn() {
		TableColumn_purchaseId.setCellFactory(TextFieldTableCell.forTableColumn());
		TableColumn_purchaseId.setOnEditCommit(e -> {
        		e.getTableView().getItems().get(e.getTablePosition().getRow()).setPurchaseId(e.getNewValue());
    	});
	}
	
	public void setOnEditOnNewProductRow() {
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
	public void deleteButtonClicked(ActionEvent event) throws SQLException {
		
	}
	 
	@FXML
	public void editButtonClicked() {
		 
	}
	
	@FXML
	public void saveButtonClicked() {
		
	}
	
	@FXML
	public void quitButtonClicked() {
		
	}
	
	@FXML
	public void leaveButtonClicked() {
		
	}
}
