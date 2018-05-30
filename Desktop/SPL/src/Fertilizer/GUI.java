package Fertilizer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("myPanel.fxml"));
		
		Scene sc = new Scene(root);
	//	stage.setFullScreen(true);
		stage.setMaximized(true);
		sc.getStylesheets().add("/Fertilizer/design.css");
		stage.setScene(sc);
		
		
		//sc.getStylesheets().add("Design.css");
		stage.show();
		
	}

}
