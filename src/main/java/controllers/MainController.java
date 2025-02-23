package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import main.RecordSpeech;

public class MainController implements Initializable {

	@FXML
	private Button btnRecord;

	@FXML
	private TextArea taText;

	@FXML
	private TextArea taJson;

	private boolean isRecording = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnRecord.setOnAction(event -> {
			if (!isRecording) {
				
				System.out.println("gravando false ");
				startRecording();
			} else {
				
				System.out.println("gravando true");
				stopRecording();
			}
		});

	}

	RecordSpeech re = new RecordSpeech();

	private void startRecording() {

		isRecording = re.startRecording();

	}

	private void stopRecording() {

		isRecording = re.startRecording();
	}

	public void stop() {
		// Garante que a gravação seja parada ao fechar a janela
		if (isRecording) {
			stopRecording();
		}
	}

}
