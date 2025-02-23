package main;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SpeechToText {

    public static void main(String[] args) {
        try {
            // Carrega as credenciais do classpath
            InputStream credentialsStream = SpeechToText.class.getClassLoader()
                    .getResourceAsStream("credentials/speak-to-text-451701-f722dabeb309.json");
            if (credentialsStream == null) {
                System.err.println("Arquivo de credenciais não encontrado no classpath.");
                return;
            }

            // Cria as credenciais a partir do stream
            GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);

            // Configura o SpeechClient com as credenciais
            SpeechSettings settings = SpeechSettings.newBuilder()
                    .setCredentialsProvider(() -> credentials)
                    
                    .build();
            
           

            String audioFilePath = "speak.wav";
            transcribeAudio(audioFilePath, settings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void transcribeAudio(String filePath, SpeechSettings settings) throws IOException {
        try (SpeechClient speechClient = SpeechClient.create(settings)) {
            byte[] data = Files.readAllBytes(Paths.get(filePath));
            ByteString audioBytes = ByteString.copyFrom(data);

            RecognitionAudio audio = RecognitionAudio.newBuilder()
                    .setContent(audioBytes)
                    .build();

            RecognitionConfig config = RecognitionConfig.newBuilder()
            	    .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
            	    .setSampleRateHertz(44100) // Alterado de 16000 para 44100
            	    .setAudioChannelCount(2) // Define explicitamente 2 canais
            	    .setLanguageCode("pt-BR")
            	    .build();

            RecognizeResponse response = speechClient.recognize(config, audio);
            List<SpeechRecognitionResult> results = response.getResultsList();
            for (SpeechRecognitionResult result : results) {
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                System.out.printf("Transcrição: %s%n", alternative.getTranscript());
            }
        }
    }
}