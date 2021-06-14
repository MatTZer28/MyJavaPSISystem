package alertbox.unsaved.stage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Controller {

	private boolean isSaveAnswer = false;

	@FXML
	public void saveButtonClicked(ActionEvent event) {
		isSaveAnswer = true;
		main.Main.getUnsavedAlertBoxStage().close();
	}

	@FXML
	public void leaveButtonClicked(ActionEvent event) {
		isSaveAnswer = false;
		main.Main.getUnsavedAlertBoxStage().close();
	}

	public boolean isSaveAnswer() {
		return isSaveAnswer;
	}
}
