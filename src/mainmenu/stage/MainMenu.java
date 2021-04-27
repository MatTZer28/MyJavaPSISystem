package mainmenu.stage;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class MainMenu {
	
	private Stage stage;
    private Scene scene;
    private FXMLLoader fxmlLoader;
    
    public MainMenu() throws Exception {
    	fxmlLoader = loadFxmlFileFrom("/fxml/MainMenuUI.fxml");
    	loadFxmlToTargetSceneFromFxmlLoaderIOException();
    }
    
    private static FXMLLoader loadFxmlFileFrom(String filePath) throws Exception {
		return new FXMLLoader(MainMenu.class.getResource(filePath));
	}
    
    private void loadFxmlToTargetSceneFromFxmlLoaderIOException() {
    	try {
    		setSceneToScene();
    	} catch(IOException e) {
    		System.out.println("Error displaying MainMenu window");
            throw new RuntimeException(e);
    	}
    }
    
    private void setSceneToScene() throws IOException {
    	scene = new Scene(( Parent) fxmlLoader.load());
    }
    
    public void launchScene(Stage stage) {
    	this.stage = stage;
    	
    	setJMetroScene();
    	setStageToMaximizedSize();
    	setStageMinimizeSize();
    	setTitleToStage("進銷存系統");
    	setSceneToStage();
    	showStage();
    }
    
    private void setJMetroScene() {
    	JMetro jMetro = new JMetro(Style.DARK);
    	jMetro.setScene(scene);
    }
    
    private void setStageToMaximizedSize() {
		stage.setMaximized(true);
	}
    
    private void setStageMinimizeSize() {
    	stage.setMinWidth(1280);
		stage.setMinHeight(720);
	}
    
    private void setTitleToStage(String title) {
		stage.setTitle(title);
	}
    
    private void setSceneToStage() {
    	stage.setScene(scene);
    }
    
    private void showStage() {
    	stage.show();
    }
}
