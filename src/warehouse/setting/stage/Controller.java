package warehouse.setting.stage;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class Controller implements Initializable {
	
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
    private TableView<?> TableView_table;
    @FXML
    private TableView<?> TableView_productTable;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

    @FXML
    void deleteButtonClicked(ActionEvent event) {

    }

    @FXML
    void editButtonClicked(ActionEvent event) {

    }

    @FXML
    void insertButtonClicked(ActionEvent event) {

    }

    @FXML
    void leaveButtonClicked(ActionEvent event) {

    }

    @FXML
    void quitButtonClicked(ActionEvent event) {

    }

    @FXML
    void saveButtonClicked(ActionEvent event) {

    }
}
