package product.purchase.stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

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
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import product.setting.stage.Controller.ProductDataForTable;

public class Controller implements Initializable{
	
	@FXML
	private TableView<PurchaseDataForTable> TableView_purchaseTable;
	@FXML
	private TableView<?> TableView_productTable;
	@FXML	
	private TableView<?> TableView_warehouseTable;
	@FXML
	private TableColumn<?, String> TableColumn_purchaseId;
	@FXML
	private TableColumn<?, ChoiceBox<String>> TableColumn_vendorId;
	@FXML
	private TableColumn<?, String> TableColumn_vendorName;
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
    
    @FXML
    private ToolBar ToolBar_toolBar;
    
    public double xOffset;
	public double yOffset;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setStageDragable();
		initDataToPurchaseTable();
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
	
	public void initDataToPurchaseTable() {
		setPurchaseTableColumn();
		setPurchaseTableItems();
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
				
			} while(resultsetForPurchaseTable.next());
		} catch (SQLException e) {
			return FXCollections.observableArrayList();
		}
		return purchases;
	}
	
	public static class PurchaseDataForTable {
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

		public String getPurchaseId() { return purchaseId; }
		public void setPurchaseId(String purchaseId) { this.purchaseId = purchaseId; }
		
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
	
	
	@FXML
	public void insertButtonClicked(ActionEvent event) {
		 
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
