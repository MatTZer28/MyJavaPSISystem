package alertbox.nullid.stage;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import mainmenu.stage.MainMenu;

public class NullIDAlertBox {

	private Stage stage;
	private Scene scene;
	private static FXMLLoader fxmlLoader;

	public NullIDAlertBox() throws Exception {
		fxmlLoader = loadFxmlFileFrom("/fxml/NULLIDAlertBoxUI.fxml");
		loadFxmlToTargetSceneFromFxmlLoaderIOException();
	}

	private static FXMLLoader loadFxmlFileFrom(String filePath) throws Exception {
		return new FXMLLoader(MainMenu.class.getResource(filePath));
	}

	private void loadFxmlToTargetSceneFromFxmlLoaderIOException() {
		try {
			setSceneToScene();
		} catch (IOException e) {
			System.out.println("Error displaying CompanyInformation window");
			throw new RuntimeException(e);
		}
	}

	private void setSceneToScene() throws IOException {
		scene = new Scene((Parent) fxmlLoader.load());
	}

	public static FXMLLoader getFxmlLoader() {
		return fxmlLoader;
	}

	public void launchScene(Stage stage) {
		this.stage = stage;

		setJMetroScene();
		setStageCenterOnScreen();
		initStyleOnStage();
		initModalityOnStage();
		setSceneToStage();
		showStageAndWait();
	}

	private void setJMetroScene() {
		JMetro jMetro = new JMetro(Style.DARK);
		jMetro.setScene(scene);
	}

	private void setStageCenterOnScreen() {
		Rectangle2D ScreenBounds = getScreenVisualBounds();
		setStageXYPosition(ScreenBounds);
	}

	private Rectangle2D getScreenVisualBounds() {
		return Screen.getPrimary().getVisualBounds();
	}

	private void setStageXYPosition(Rectangle2D ScreenBounds) {
		stage.setX((ScreenBounds.getWidth() - 300) / 2);
		stage.setY((ScreenBounds.getHeight() - 150) / 2);
	}

	private void initStyleOnStage() {
		stage.initStyle(StageStyle.UNDECORATED);
	}

	private void initModalityOnStage() {
		stage.initModality(Modality.APPLICATION_MODAL);
	}

	private void setSceneToStage() {
		stage.setScene(scene);
	}

	private void showStageAndWait() {
		stage.showAndWait();
	}
}
