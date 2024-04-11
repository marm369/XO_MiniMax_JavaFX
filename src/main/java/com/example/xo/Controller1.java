package com.example.xo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import javafx.stage.Modality;
import java.io.IOException;
import java.net.URISyntaxException;
import javafx.scene.layout.Pane;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;

public class Controller1
{
    final private String charactersToRemove = "[: ]"; // Les caractères que vous voulez supprimer
    private Timeline countdownTimer;
    private int elapsedTimeInSeconds = 0;
    @FXML
    private GridPane grid_pane;
    @FXML
    private Text _j1;
    @FXML
    private Text _j2;
    @FXML
    private Text _ox;
    @FXML
    private Text _xo;
    @FXML
    private Text timer;
    @FXML
    private Pane cadre1;
    @FXML
    private Pane cadre2;
    @FXML
    private TextField input1;
    @FXML
    private TextField input2;
    @FXML
    private Text j1;
    @FXML
    private Text j2;
    @FXML
    private Text jr1;
    @FXML
    private Text jr2;
    @FXML
    private RadioButton r1;
    @FXML
    private RadioButton r2;
    @FXML
    private Text xo;
    @FXML
    private Text ox;
    @FXML
    private ToggleGroup joueur;
    private boolean playerX = true; // true for player X, false for player O
    private boolean go=false;
    String[][] board = new String[3][3];
    // les fonctions pour changer le nom des joueurs
    @FXML
    void changename1()
    {
        input1.setVisible(true);
        input1.requestFocus(); // Set focus to the input field
        j1.setVisible(false); // Hide the text while editing
        input1.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                j1.setText(input1.getText());
                jr1.setText(input1.getText());
                r1.setText(input1.getText());
                input1.setVisible(false);
                j1.setVisible(true); // Show the updated text
            }
        });
    }
    @FXML
    void changename2()
    {
        input2.setVisible(true);
        input2.requestFocus(); // Set focus to the input field
        j2.setVisible(false); // Hide the text while editing
        input2.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                j2.setText(input2.getText());
                jr2.setText(input2.getText());
                r2.setText(input2.getText());
                input2.setVisible(false);
                j2.setVisible(true); // Show the updated text
            }
        });
    }
    // fonction pour savoir qu'est le joueur qui va commencer le premier
    public void initialize() {
        joueur.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == r1) {
                playerX = true;
            } else if (newValue == r2) {
                playerX = false;
            } else {
                playerX = true;
            }
        });
    }
    // fonction pour inverser le X et le O
    @FXML
    void reverse(MouseEvent event)
    {
        String tempValue = xo.getText();
        xo.setText(ox.getText());
        ox.setText(tempValue);
    }
    // fonction pou controler le miniteur et ajouter au boute de chaque seconde 1 et appeler
    // la fonction mise_ajour_time pour changer format of miiniteur
    private void lancer_timer() {
        if (countdownTimer == null) {
            countdownTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                elapsedTimeInSeconds++;
                mise_ajour_time();
            }));
            countdownTimer.setCycleCount(Timeline.INDEFINITE);
        }
        countdownTimer.play();
    }
    // la fonction prends le nombre des secondes et donne HH:MM:SS
    private void mise_ajour_time() {
        int hours = elapsedTimeInSeconds / 3600;
        int minutes = (elapsedTimeInSeconds % 3600) / 60;
        int seconds = elapsedTimeInSeconds % 60;

        String timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timer.setText(timeFormatted);
    }
    // la fonction test si le tableau de deux dimensions et remplit pour annoncer le cas d'egalite
    private boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == null || board[row][col].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
    // la fonction determine le vainqueur en se basant sur l'etat de board
    private String determineWinner(String board[][]) {
        // Vérifier les combinaisons horizontales, verticales et diagonales
        for (int i = 0; i < 3; i++) {
            // Vérifier les lignes horizontales
            if (board[i][0] != null && board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2])) {
                return board[i][0]; // Le joueur gagnant (X ou O)
            }

            // Vérifier les colonnes verticales
            if (board[0][i] != null && board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i])) {
                return board[0][i]; // Le joueur gagnant (X ou O)
            }
        }
        // Vérifier les diagonales
        if (board[0][0] != null && board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2])) {
            return board[0][0]; // Le joueur gagnant (X ou O)
        }

        if (board[0][2] != null && board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0])) {
            return board[0][2]; // Le joueur gagnant (X ou O)
        }

        // S'il n'y a pas de gagnant
        return null;
    }
    @FXML
    void play1(String mp , int cycle){
        try {
            String path = getClass().getResource(mp).toURI().toString();
            Media media = new Media(path);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            if(cycle > 0)
               mediaPlayer.setCycleCount(cycle);
            else
                mediaPlayer.setCycleCount(1);
            mediaPlayer.play();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
 // la fonction ajoute le X ou le O dans le grid et en meme temps remplit le tableau de board
    @FXML
    void makeMove(MouseEvent event)
    {
            if (go)
            {
                Button clickedButton = (Button) event.getSource();
                if (clickedButton.getText().isEmpty()) {
                    play1("clicksound.wav",1);
                    if (playerX) {
                        clickedButton.setText(_xo.getText().replaceAll(charactersToRemove, ""));
                        board[GridPane.getRowIndex(clickedButton)][GridPane.getColumnIndex(clickedButton)]=clickedButton.getText();
                    } else {
                        clickedButton.setText(_ox.getText().replaceAll(charactersToRemove, ""));
                        board[GridPane.getRowIndex(clickedButton)][GridPane.getColumnIndex(clickedButton)]=clickedButton.getText();
                    }
                    if ("O".equalsIgnoreCase(clickedButton.getText())) {
                        clickedButton.getStyleClass().add("blue1");
                    } else {
                        clickedButton.getStyleClass().add("blue2");
                    }
                    playerX = !playerX; // Switch players
                }
            }
       // apres chaque mouvement en test si ona un vainqeur ou un match NULL
        String winner = determineWinner(board);
        if (winner != null) {
            showWinnerDialog(winner);
            System.out.println("The winner is: " + winner);
        }
        // tester si le bord est completement remplit si oui afficher un matche null
        else if (isBoardFull()) {
            showWinnerDialog(winner);
        }
    }
    // la fonction affiche la boite de dialogue de vainqueur
    private void showWinnerDialog(String winner) {
        play1("winsound.mp3",0);
        Stage winnerDialogStage = new Stage();
        String name;
        winnerDialogStage.initModality(Modality.APPLICATION_MODAL);
        winnerDialogStage.initStyle(StageStyle.UTILITY);
        winnerDialogStage.setTitle("Vainqueur");
        if (_xo.getText().contains(winner)) {
            name = _j1.getText();

        } else {
            name = _j2.getText();

        }
        Text winnerText = new Text("Le joueur " + name + " a gagné !" + "\n" + " Le temps ecoulé : " + timer.getText());
        winnerText.setStyle("-fx-font-size: 18px;  -fx-font-weight: bold;");
        // Créer un BorderPane et placer l'icône en haut et le texte au centre
        BorderPane dialogContent = new BorderPane();
        dialogContent.setCenter(winnerText);
        Scene dialogScene = new Scene(dialogContent, 300, 200);
        // Ajouter la classe de style à la liste de classes de la scène
        dialogScene.getStylesheets().add(getClass().getResource("/style_jvsj.css").toExternalForm());
        dialogScene.getRoot().getStyleClass().add("dialog1");
        winnerDialogStage.setScene(dialogScene);
        winnerDialogStage.setOnHidden(event -> {
            // Fermer la fenêtre actuelle
            grid_pane.getScene().getWindow().hide();
            // Ouvrir une nouvelle fenêtre
            try {
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("pageprincipale.fxml"));
                stage.setTitle("j_vs_j");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        winnerDialogStage.showAndWait();
    }

    @FXML
    void startgame(MouseEvent event)
    {
        lancer_timer();
        go = true;
        cadre1.setVisible(false);
        cadre2.setVisible(true);
        _j1.setText(j1.getText());
        _j2.setText(j2.getText());
        _xo.setText(xo.getText());
        _ox.setText(ox.getText());
    }
}
