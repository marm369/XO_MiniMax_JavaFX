package com.example.xo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.IOException;

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("pageprincipale.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        // Chargement de l'icône à partir des ressources
        Image icon = new Image(getClass().getResourceAsStream("/images/xo.png"));
        stage.setTitle("X vs O");
        // Ajout de l'icône à la fenêtre
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}


