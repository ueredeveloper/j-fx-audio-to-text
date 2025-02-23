package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {

		Scene scene = null;
		try {
			scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/Main.fxml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		stage.setTitle("Speech Recorder");
		stage.setScene(scene);
		stage.show();

	}

}