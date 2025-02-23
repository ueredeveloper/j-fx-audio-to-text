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

public class RecordSpeech {

	private TargetDataLine line;
	private Thread recordingThread;
	private boolean isRecording = false;
	private final File audioFile = new File("speech.wav");

	public boolean startRecording() {
		try {
			// Configura o formato do áudio (mono, 44100 Hz)
			AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 1, 2, 44100, false);
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

			// Verifica se o formato é suportado
			if (!AudioSystem.isLineSupported(info)) {
				System.out.println("Formato de áudio não suportado.");
				return false;
			}

			// Obtém a linha de entrada (microfone)
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format);
			line.start();

			// Inicia a gravação em um thread separado
			recordingThread = new Thread(() -> {
				try {
					AudioSystem.write(new AudioInputStream(line), AudioFileFormat.Type.WAVE, audioFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			recordingThread.start();

			// Atualiza a UI
			isRecording = true;
			return isRecording;
		} catch (LineUnavailableException e) {
			System.out.println("Erro: Microfone indisponível.");
			e.printStackTrace();
		}
		return isRecording;
	}

	public boolean stopRecording() {

		System.out.println("line != null " + line != null);

		if (line != null) {
			line.stop();
			line.close();
		}

		System.out.println("recordingThread != null " + line != null);
		if (recordingThread != null) {
			recordingThread.interrupt();
		}

		// Atualiza a UI
		isRecording = false;
		return isRecording;
	}

}
