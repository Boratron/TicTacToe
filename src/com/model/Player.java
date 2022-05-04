package com.model;

public class Player {
    private String chosenMark;

    public Player() {
        chosenMark = "O";
    }

    public String getChosenMark() {
        return chosenMark;
    }

    public void setChosenMark(String chosenMark) {
        this.chosenMark = chosenMark;
    }
}