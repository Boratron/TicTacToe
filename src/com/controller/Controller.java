package com.controller;

import com.model.Computer;
import com.model.GameState;
import com.model.Player;
import com.model.TileModel;
import com.view.View;
import javafx.scene.Scene;

public class Controller {
    private final View view;
    private final TileModel[][] tilesModel;
    private final Player player;
    private final Computer computer;
    private final GameState gameState;

    public Controller() {
        this.view = new View();
        this.tilesModel = new TileModel[this.view.getTiles().length][this.view.getTiles().length];
        this.player = new Player();
        this.computer = new Computer();
        this.gameState = new GameState();

        for (int row = 0; row < this.tilesModel.length; ++row) {
            for (int col = 0; col < this.tilesModel.length; ++col) {
                this.tilesModel[row][col] = new TileModel(this.view.getTiles()[row][col].getId());

                int tempRow = row;
                int tempCol = col;

                // event listener on tiles
                this.view.getTiles()[row][col].setOnMouseClicked(mouseEvent -> {
                    if (!this.gameState.isGameOver() && !this.tilesModel[tempRow][tempCol].isMarked()) {
                        playerDecide(tempRow, tempCol);
                        if (!checkAllTilesMarked()) {
                            computerDecide();
                        }
                        checkWinner();
                    }
                });
            }
        }

        // event listener on choice box
        this.view.getMarkChoices().setOnAction(actionEvent -> {
            this.player.setChosenMark(this.view.getMarkChoices().getSelectionModel().getSelectedItem());
            reset();
        });

        // event listener on reset button
        this.view.getResetBtn().setOnMouseClicked(mouseEvent -> {
            reset();
        });
    }

    private void reset() {
        if (this.view.getRoot().getChildren().size() > 12) {
            this.view.getRoot().getChildren().remove(12, this.view.getRoot().getChildren().size());
            for (TileModel[] tileModels : this.tilesModel) {
                for (int col = 0; col < this.tilesModel.length; ++col) {
                    tileModels[col].setMarked(false);
                }
            }
        }
        this.gameState.setGameOver(false);
    }

    private void playerDecide(int row, int col) {
        this.tilesModel[row][col].setMark(this.player.getChosenMark());
        this.tilesModel[row][col].setMarked(true);
        this.view.drawMark(this.tilesModel[row][col]);
    }

    private void computerDecide() {
        computer.decide();
        while (this.tilesModel[computer.getRow()][computer.getCol()].isMarked()) computer.decide();
        if (this.player.getChosenMark().equals("O")) {
            this.tilesModel[computer.getRow()][computer.getCol()].setMark("X");
        } else {
            this.tilesModel[computer.getRow()][computer.getCol()].setMark("O");
        }
        this.tilesModel[computer.getRow()][computer.getCol()].setMarked(true);
        this.view.drawMark(this.tilesModel[computer.getRow()][computer.getCol()]);
    }

    private void checkWinner() {
        if ((tilesModel[0][0].isMarked() && tilesModel[0][1].isMarked() && tilesModel[0][2].isMarked()) &&
                tilesModel[0][0].getMark().equals(tilesModel[0][1].getMark()) &&
                tilesModel[0][0].getMark().equals(tilesModel[0][2].getMark())) { // horizontal 1st row
            this.view.drawWinnerLine(tilesModel[0][0], tilesModel[0][2]);
            this.gameState.setGameOver(true);
        }
        if ((tilesModel[1][0].isMarked() && tilesModel[1][1].isMarked() && tilesModel[1][2].isMarked()) &&
                tilesModel[1][0].getMark().equals(tilesModel[1][1].getMark()) &&
                tilesModel[1][0].getMark().equals(tilesModel[1][2].getMark())
        ) { // horizontal 2nd row
            this.view.drawWinnerLine(tilesModel[1][0], tilesModel[1][2]);
            this.gameState.setGameOver(true);
        }
        if ((tilesModel[2][0].isMarked() && tilesModel[2][1].isMarked() && tilesModel[2][2].isMarked()) &&
                tilesModel[2][0].getMark().equals(tilesModel[2][1].getMark()) &&
                tilesModel[2][0].getMark().equals(tilesModel[2][2].getMark())
        ) { // horizontal 3rd row
            this.view.drawWinnerLine(tilesModel[2][0], tilesModel[2][2]);
            this.gameState.setGameOver(true);
        }
        if ((tilesModel[0][0].isMarked() && tilesModel[1][1].isMarked() && tilesModel[2][2].isMarked()) &&
                tilesModel[0][0].getMark().equals(tilesModel[1][1].getMark()) &&
                tilesModel[0][0].getMark().equals(tilesModel[2][2].getMark())
        ) { // slash right
            this.view.drawWinnerLine(tilesModel[0][0], tilesModel[2][2]);
            this.gameState.setGameOver(true);
        }
        if ((tilesModel[0][2].isMarked() && tilesModel[1][1].isMarked() && tilesModel[2][0].isMarked()) &&
                tilesModel[0][2].getMark().equals(tilesModel[1][1].getMark()) &&
                tilesModel[0][2].getMark().equals(tilesModel[2][0].getMark())
        ) { // slash left
            this.view.drawWinnerLine(tilesModel[0][2], tilesModel[2][0]);
            this.gameState.setGameOver(true);
        }
        if ((tilesModel[0][0].isMarked() && tilesModel[1][0].isMarked() && tilesModel[2][0].isMarked()) &&
                tilesModel[0][0].getMark().equals(tilesModel[1][0].getMark()) &&
                tilesModel[0][0].getMark().equals(tilesModel[2][0].getMark())
        ) { // vertical 1st col
            this.view.drawWinnerLine(tilesModel[0][0], tilesModel[2][0]);
            this.gameState.setGameOver(true);
        }
        if ((tilesModel[0][1].isMarked() && tilesModel[1][1].isMarked() && tilesModel[2][1].isMarked()) &&
                tilesModel[0][1].getMark().equals(tilesModel[1][1].getMark()) &&
                tilesModel[0][1].getMark().equals(tilesModel[2][1].getMark())
        ) { // vertical 2nd col
            this.view.drawWinnerLine(tilesModel[0][1], tilesModel[2][1]);
            this.gameState.setGameOver(true);
        }
        if ((tilesModel[0][2].isMarked() && tilesModel[1][2].isMarked() && tilesModel[2][2].isMarked()) &&
                tilesModel[0][2].getMark().equals(tilesModel[1][2].getMark()) &&
                tilesModel[0][2].getMark().equals(tilesModel[2][2].getMark())
        ) { // vertical 3rd col
            this.view.drawWinnerLine(tilesModel[0][2], tilesModel[2][2]);
            this.gameState.setGameOver(true);
        }
    }

    private boolean checkAllTilesMarked() {
        boolean allMarked = true;
        for (TileModel[] tileModels : tilesModel) {
            for (int col = 0; col < tilesModel.length; ++col) {
                if (!tileModels[col].isMarked()) {
                    allMarked = false;
                    break;
                }
            }
            if (!allMarked) break;
        }
        return allMarked;
    }

    public Scene getScene() {
        return view.getScene();
    }
}