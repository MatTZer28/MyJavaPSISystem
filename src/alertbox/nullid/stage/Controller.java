package alertbox.nullid.stage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Controller {

	@FXML
	public void confirmButtonClicked(ActionEvent event) {
		main.Main.getNullIdAlertBoxStage().close();
	}
	
}
