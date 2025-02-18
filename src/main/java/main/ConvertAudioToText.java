package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

public class ConvertAudioToText {

    public static void main(String[] args) {

        try {
            // Configuração do CMU Sphinx
            Configuration configuration = new Configuration();

            // Carregar o modelo de linguagem e o dicionário
            configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
            configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
            configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

            // Configurar a entrada de áudio
            configuration.setSampleRate(16000);

            // Inicializar o reconhecedor de fala
            StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);

            // Carregar o arquivo de áudio para reconhecimento
            File audioFile = new File("speak.wav");
            try (InputStream stream = new FileInputStream(audioFile)) {
                recognizer.startRecognition(stream);

                SpeechResult result;
                while ((result = recognizer.getResult()) != null) {
                    System.out.format("Hypothesis: %s\n", result.getHypothesis());
                }

                recognizer.stopRecognition();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}