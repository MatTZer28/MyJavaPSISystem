package product.setting.stage;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class Controller implements Initializable {
	
	@FXML
	private TableView<?> TableView_productTable;
	@FXML
	private TableView<?> TableView_warehouseTable;
	@FXML
    private TableColumn<?, String> TableColumn_id;
	@FXML
    private TableColumn<?, String> TableColumn_name;
	@FXML
    private TableColumn<?, String> TableColumn_specification;
	@FXML
    private TableColumn<?, String> TableColumn_type;
	@FXML
    private TableColumn<?, String> TableColumn_unit;
	@FXML
    private TableColumn<?, String> TableColumn_total;
	@FXML
    private TableColumn<?, String> TableColumn_cost;
	@FXML
    private TableColumn<?, String> TableColumn_sellPrice;
	@FXML
    private TableColumn<?, String> TableColumn_safeAmount;
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	public void productTableOnClicked() {
		
	}
	
	@FXML
	public void insertButtonClicked() {
		
	}
	
	@FXML
	public void deleteButtonClicked() {
		
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
