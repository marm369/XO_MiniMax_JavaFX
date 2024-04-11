package com.example.xo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URISyntaxException;

public class Controller {
    private MediaPlayer mediaPlayer;
    @FXML
    private Button bttn1;
    @FXML
    private Button bttn2;
    @FXML
    void play( ){
        try {
            String mp = "button1.mp3";
            String path = getClass().getResource(mp).toURI().toString();
            Media media = new Media(path);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(1);
            mediaPlayer.play();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void jvsj(MouseEvent event) throws IOException {
        play();
        Stage stage = (Stage) bttn1.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("jvsj.fxml"));
        stage.setTitle("j_vs_j");
        stage.setScene(new Scene(root));
    }
    @FXML
    void jvso(MouseEvent event) throws IOException
    {
        play();
        Stage stage = (Stage) bttn2.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("jvso.fxml"));
        stage.setTitle("j_vs_o");
        stage.setScene(new Scene(root));
    }
}
