package main;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class Main {

	public static void main(String[] args) {

		try {
			// Configura o formato do áudio
			AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);

			// Obtém a linha de entrada de áudio (microfone)
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			TargetDataLine line = null;
			try {
				line = (TargetDataLine) AudioSystem.getLine(info);
			} catch (LineUnavailableException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				line.open(format);
			} catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			line.start();

			// Define o arquivo onde o áudio será salvo
			File audioFile = new File("speak.wav");
			AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

			// Inicia a gravação
			AudioSystem.write(new AudioInputStream(line), fileType, audioFile);

			// Espera por 5 segundos (ajuste conforme necessário)
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Para a gravação
			line.stop();
			line.close();

			System.out.println("Gravação concluída.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}