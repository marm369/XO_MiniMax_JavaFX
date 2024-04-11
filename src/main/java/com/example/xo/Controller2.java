package com.example.xo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URISyntaxException;

public class Controller2 {

    @FXML
    private GridPane pane;
    private boolean playerX = true; // true for player X, false for player O
    String[][] board = new String[3][3];
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
    private String determineWinner() {
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
    private class Coordonne {
        int row;
        int col;
        Coordonne(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private Coordonne minimax_ordinateur(String[][] board) {
        int bestScore = Integer.MIN_VALUE;
        Coordonne bestMove = null;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == null)
                {
                    board[row][col] = "O"; // Simulate computer's move
                    int score = minimax(board, 0, false);
                    board[row][col] = null; // Undo the move
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new Coordonne(row, col);
                    }
                }
            }
        }

        return bestMove;
    }

    private int minimax(String[][] board, int depth, boolean isMaximizing) {
        String winner = determineWinner();
        if (winner != null) {
            return winner.equals("O") ? 1 : -1;
        }
        if (isBoardFull()) {
            return 0;
        }
        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            System.out.println(bestScore);
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == null) {
                        board[row][col] = "O"; // Simulate computer's move
                        int score = minimax(board, depth + 1, false);
                        board[row][col] = null; // Undo the move
                        bestScore = Math.max(bestScore, score);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            System.out.println(bestScore);
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == null) {
                        board[row][col] = "X"; // Simulate player's move
                        int score = minimax(board, depth + 1, true);
                        board[row][col] = null; // Undo the move
                        bestScore = Math.min(bestScore, score);
                    }
                }
            }
            return bestScore;
        }
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
    private boolean computerTurn = false; // Variable pour indiquer si c'est au tour de l'ordinateur

    @FXML
    void makeMove(MouseEvent event) {
        System.out.println("ana houa ana \n");
        Button clickedButton = (Button) event.getSource();
        Integer rowIndex = GridPane.getRowIndex(clickedButton);
        Integer colIndex = GridPane.getColumnIndex(clickedButton);
        System.out.println("le X est :"+rowIndex +"le Y est : " +colIndex);
        if (rowIndex != null && colIndex != null && clickedButton.getText().isEmpty() && !computerTurn) {
            play1("clicksound.wav", 1);
            clickedButton.setText("X");
            board[GridPane.getRowIndex(clickedButton)][GridPane.getColumnIndex(clickedButton)] = clickedButton.getText();

            if ("O".equalsIgnoreCase(clickedButton.getText())) {
                clickedButton.getStyleClass().add("blue1");
            } else {
                clickedButton.getStyleClass().add("blue2");
            }
            computerTurn = true; // Indiquer que c'est maintenant au tour de l'ordinateur

        }

        // Vérifier s'il y a un gagnant ou un match nul après le mouvement du joueur
        String winner = determineWinner();
        if (winner != null) {
            showWinnerDialog(winner);
            System.out.println("The winner is: " + winner);
        } else if (isBoardFull()) {
            showWinnerDialog(winner);
        }
        else playComputerMove();
    }

    private void playComputerMove() {
        System.out.println("ana houa ordinateur \n");
        Coordonne computerMove = minimax_ordinateur(board);
        int row = computerMove.row;
        int col = computerMove.col;
        System.out.println("le X est :"+row +"le Y est : " +col);
        Button computerButton = (Button) pane.getChildren().get(row * 3 + col);

        computerButton.setText("O");
        board[row][col] = "O";

        if ("O".equalsIgnoreCase(computerButton.getText())) {
            computerButton.getStyleClass().add("blue1");
        } else {
            computerButton.getStyleClass().add("blue2");
        }
        computerTurn = false; // Indiquer que c'est maintenant au tour du joueur
    }

    private void showWinnerDialog(String winner) {
        play1("winsound.mp3",0);
        Stage winnerDialogStage = new Stage();
        winnerDialogStage.initModality(Modality.APPLICATION_MODAL);
        winnerDialogStage.initStyle(StageStyle.UTILITY);
        winnerDialogStage.setTitle("Vainqueur");
        Text winnerText = new Text( winner + " a gagné !" );
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
        pane.getScene().getWindow().hide();
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

}