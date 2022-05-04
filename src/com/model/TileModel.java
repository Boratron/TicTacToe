package com.model;

public class TileModel {
    private final String id;
    private String mark;
    private boolean marked;

    public TileModel(String id) {
        this.id = id;
        this.mark = "O";
        this.marked = false;
    }

    public String getId() {
        return id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }
}