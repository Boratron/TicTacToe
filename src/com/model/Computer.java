package com.model;

import java.util.Random;

public class Computer {
    private int row;
    private int col;
    private final Random random;

    public Computer() {
        random = new Random();
    }

    public void decide() {
        row = random.nextInt(3);
        col = random.nextInt(3);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}