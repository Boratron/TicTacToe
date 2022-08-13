package com.controller;

import com.model.Computer;
import com.model.GameState;
import com.model.Player;
import com.model.TileModel;
import com.view.View;
import javafx.scene.Scene;

public class Controller {
    private final View view;
    private final TileModel[][] tileModels;
    private final Player player;
    private final Computer computer;
    private final GameState gameState;

    public Controller() {
        this.view = new View();
        this.tileModels = new TileModel[this.view.getTiles().length][this.view.getTiles().length];
        this.player = new Player();
        this.computer = new Computer();
        this.gameState = new GameState();

        for (int row = 0; row < this.tileModels.length; ++row) {
            for (int col = 0; col < this.tileModels.length; ++col) {
                this.tileModels[row][col] = new TileModel(this.view.getTiles()[row][col].getId());

                int tempRow = row;
                int tempCol = col;

                // event listener on tiles
                this.view.getTiles()[row][col].setOnMouseClicked(mouseEvent -> {
                    if (!this.gameState.isGameOver() && !this.tileModels[tempRow][tempCol].isMarked()) {
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
            for (TileModel[] tileModels : this.tileModels) {
                for (int col = 0; col < this.tileModels.length; ++col) {
                    tileModels[col].setMarked(false);
                }
            }
        }
        this.gameState.setGameOver(false);
    }

    private void playerDecide(int row, int col) {
        this.tileModels[row][col].setMark(this.player.getChosenMark());
        this.tileModels[row][col].setMarked(true);
        this.view.drawMark(this.tileModels[row][col]);
    }

    private void computerDecide() {
        computer.decide();
        while (this.tileModels[computer.getRow()][computer.getCol()].isMarked()) computer.decide();
        if (this.player.getChosenMark().equals("O")) {
            this.tileModels[computer.getRow()][computer.getCol()].setMark("X");
        } else {
            this.tileModels[computer.getRow()][computer.getCol()].setMark("O");
        }
        this.tileModels[computer.getRow()][computer.getCol()].setMarked(true);
        this.view.drawMark(this.tileModels[computer.getRow()][computer.getCol()]);
    }

    private void checkWinner() {
        if ((tileModels[0][0].isMarked() && tileModels[0][1].isMarked() && tileModels[0][2].isMarked()) &&
                tileModels[0][0].getMark().equals(tileModels[0][1].getMark()) &&
                tileModels[0][0].getMark().equals(tileModels[0][2].getMark())) { // horizontal 1st row
            this.view.drawWinnerLine(tileModels[0][0], tileModels[0][2]);
            this.gameState.setGameOver(true);
        }
        if ((tileModels[1][0].isMarked() && tileModels[1][1].isMarked() && tileModels[1][2].isMarked()) &&
                tileModels[1][0].getMark().equals(tileModels[1][1].getMark()) &&
                tileModels[1][0].getMark().equals(tileModels[1][2].getMark())
        ) { // horizontal 2nd row
            this.view.drawWinnerLine(tileModels[1][0], tileModels[1][2]);
            if (!this.gameState.isGameOver()) this.gameState.setGameOver(true);
        }
        if ((tileModels[2][0].isMarked() && tileModels[2][1].isMarked() && tileModels[2][2].isMarked()) &&
                tileModels[2][0].getMark().equals(tileModels[2][1].getMark()) &&
                tileModels[2][0].getMark().equals(tileModels[2][2].getMark())
        ) { // horizontal 3rd row
            this.view.drawWinnerLine(tileModels[2][0], tileModels[2][2]);
            if (!this.gameState.isGameOver()) this.gameState.setGameOver(true);
        }
        if ((tileModels[0][0].isMarked() && tileModels[1][1].isMarked() && tileModels[2][2].isMarked()) &&
                tileModels[0][0].getMark().equals(tileModels[1][1].getMark()) &&
                tileModels[0][0].getMark().equals(tileModels[2][2].getMark())
        ) { // slash right
            this.view.drawWinnerLine(tileModels[0][0], tileModels[2][2]);
            if (!this.gameState.isGameOver()) this.gameState.setGameOver(true);
        }
        if ((tileModels[0][2].isMarked() && tileModels[1][1].isMarked() && tileModels[2][0].isMarked()) &&
                tileModels[0][2].getMark().equals(tileModels[1][1].getMark()) &&
                tileModels[0][2].getMark().equals(tileModels[2][0].getMark())
        ) { // slash left
            this.view.drawWinnerLine(tileModels[0][2], tileModels[2][0]);
            if (!this.gameState.isGameOver()) this.gameState.setGameOver(true);
        }
        if ((tileModels[0][0].isMarked() && tileModels[1][0].isMarked() && tileModels[2][0].isMarked()) &&
                tileModels[0][0].getMark().equals(tileModels[1][0].getMark()) &&
                tileModels[0][0].getMark().equals(tileModels[2][0].getMark())
        ) { // vertical 1st col
            this.view.drawWinnerLine(tileModels[0][0], tileModels[2][0]);
            if (!this.gameState.isGameOver()) this.gameState.setGameOver(true);
        }
        if ((tileModels[0][1].isMarked() && tileModels[1][1].isMarked() && tileModels[2][1].isMarked()) &&
                tileModels[0][1].getMark().equals(tileModels[1][1].getMark()) &&
                tileModels[0][1].getMark().equals(tileModels[2][1].getMark())
        ) { // vertical 2nd col
            this.view.drawWinnerLine(tileModels[0][1], tileModels[2][1]);
            if (!this.gameState.isGameOver()) this.gameState.setGameOver(true);
        }
        if ((tileModels[0][2].isMarked() && tileModels[1][2].isMarked() && tileModels[2][2].isMarked()) &&
                tileModels[0][2].getMark().equals(tileModels[1][2].getMark()) &&
                tileModels[0][2].getMark().equals(tileModels[2][2].getMark())
        ) { // vertical 3rd col
            this.view.drawWinnerLine(tileModels[0][2], tileModels[2][2]);
            if (!this.gameState.isGameOver()) this.gameState.setGameOver(true);
        }
    }

    private boolean checkAllTilesMarked() {
        boolean allMarked = true;
        for (TileModel[] tileModels : tileModels) {
            for (int col = 0; col < this.tileModels.length; ++col) {
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