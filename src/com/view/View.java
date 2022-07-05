package com.view;

import com.model.TileModel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class View {
    private final Scene scene;
    private final Group root;
    private final Rectangle[][] tiles;
    private final Button resetBtn;
    private final ChoiceBox<String> markChoices;

    public View() {
        this.tiles = new Rectangle[3][3];
        this.resetBtn = new Button();
        this.markChoices = new ChoiceBox<>();
        Label choiceLabel = new Label("Choose your mark: ");

        for (int row = 0; row < this.tiles.length; ++row) {
            for (int col = 0; col < this.tiles.length; ++col) {
                this.tiles[row][col] = new Rectangle(200, 200, Color.BLACK);
                this.tiles[row][col].setStrokeType(StrokeType.CENTERED);
                this.tiles[row][col].setStrokeWidth(1);

                String id = Integer.toString(row) + col;

                this.tiles[row][col].setId(id);
                this.tiles[row][col].setStroke(Color.WHITE);
            }
        }
        // 1st row
        this.tiles[0][0].setX(0);
        this.tiles[0][1].setX(200);
        this.tiles[0][2].setX(400);

        // 2nd row
        this.tiles[1][0].setY(200);
        this.tiles[1][1].setX(200);
        this.tiles[1][1].setY(200);
        this.tiles[1][2].setX(400);
        this.tiles[1][2].setY(200);

        // 3rd row
        this.tiles[2][0].setY(400);
        this.tiles[2][1].setX(200);
        this.tiles[2][1].setY(400);
        this.tiles[2][2].setX(400);
        this.tiles[2][2].setY(400);

        choiceLabel.setLayoutX(200);
        choiceLabel.setLayoutY(623);

        this.markChoices.setPrefWidth(90);
        this.markChoices.setLayoutX(305);
        this.markChoices.setLayoutY(620);

        this.markChoices.getItems().addAll("O", "X");
        this.markChoices.getSelectionModel().selectFirst();

        this.resetBtn.setText("Reset");
        this.resetBtn.setPrefWidth(200);
        this.resetBtn.setLayoutX(200);
        this.resetBtn.setLayoutY(660);

        this.root = new Group();
        this.root.getChildren().addAll(this.tiles[0]);
        this.root.getChildren().addAll(this.tiles[1]);
        this.root.getChildren().addAll(this.tiles[2]);
        this.root.getChildren().add(choiceLabel);
        this.root.getChildren().add(this.markChoices);
        this.root.getChildren().add(resetBtn);

        this.scene = new Scene(root, 600, 700);
    }

    public void drawMark(TileModel tileModel) {
        String[] idParts = tileModel.getId().split("");
        int row = Integer.parseInt(idParts[0]);
        int col = Integer.parseInt(idParts[1]);

        if (tileModel.getMark().equals("O")) {
            Circle circle = new Circle(50);
            this.root.getChildren().add(circle);
            circle.setCenterX(tiles[row][col].getX() + 100);
            circle.setCenterY(tiles[row][col].getY() + 100);
            circle.setFill(Color.BLACK);
            circle.setStroke(Color.LAWNGREEN);
            circle.setStrokeWidth(3);
        } else if (tileModel.getMark().equals("X")){
            Line line1 = new Line();
            line1.setStartX(tiles[row][col].getX() + 30);
            line1.setStartY(tiles[row][col].getY() + 30);
            line1.setEndX(tiles[row][col].getX() + 170);
            line1.setEndY(tiles[row][col].getY() + 170);
            line1.setStrokeWidth(3);
            line1.setStroke(Color.RED);

            Line line2 = new Line();
            line2.setStartX(tiles[row][col].getX() + 170);
            line2.setStartY(tiles[row][col].getY() + 30);
            line2.setEndX(tiles[row][col].getX() + 30);
            line2.setEndY(tiles[row][col].getY() + 170);
            line2.setStrokeWidth(3);
            line2.setStroke(Color.RED);

            this.root.getChildren().addAll(line1, line2);
        }
    }

    public void drawWinnerLine(TileModel startTile, TileModel endTile) {
        Line winnerLine = new Line();

        String[] startTileIdPart = startTile.getId().split("");
        int startTileRow = Integer.parseInt(startTileIdPart[0]);
        int startTileCol = Integer.parseInt(startTileIdPart[1]);

        String[] endTileIdPart = endTile.getId().split("");
        int endTileRow = Integer.parseInt(endTileIdPart[0]);
        int endTileCol = Integer.parseInt(endTileIdPart[1]);

        if ((startTileRow == 0 && startTileCol == 0 && endTileRow == 0 && endTileCol == 2) ||
                (startTileRow == 1 && startTileCol == 0 && endTileRow == 1 && endTileCol == 2) ||
                (startTileRow == 2 && startTileCol == 0 && endTileRow == 2 && endTileCol == 2)) { // horizontal win
            winnerLine.setStartX(tiles[startTileRow][startTileCol].getX() + 30);
            winnerLine.setStartY(tiles[startTileRow][startTileCol].getY() + 100);
            winnerLine.setEndX(tiles[endTileRow][endTileCol].getX() + 170);
            winnerLine.setEndY(tiles[endTileRow][endTileCol].getY() + 100);
            winnerLine.setStrokeWidth(3);
            winnerLine.setStroke(Color.WHITESMOKE);
        } else if ((startTileRow == 0 && startTileCol == 0 && endTileRow == 2 && endTileCol == 0) ||
                (startTileRow == 0 && startTileCol == 1 && endTileRow == 2 && endTileCol == 1) ||
                (startTileRow == 0 && startTileCol == 2 && endTileRow == 2 && endTileCol == 2)) { // vertical win
            winnerLine.setStartX(tiles[startTileRow][startTileCol].getX() + 100);
            winnerLine.setStartY(tiles[startTileRow][startTileCol].getY() + 30);
            winnerLine.setEndX(tiles[endTileRow][endTileCol].getX() + 100);
            winnerLine.setEndY(tiles[endTileRow][endTileCol].getY() + 170);
            winnerLine.setStrokeWidth(3);
            winnerLine.setStroke(Color.WHITESMOKE);
        } else if ((startTileRow == 0 && startTileCol == 0 && endTileRow == 2 && endTileCol == 2)) { // slash right
            winnerLine.setStartX(tiles[startTileRow][startTileCol].getX() + 27);
            winnerLine.setStartY(tiles[startTileRow][startTileCol].getY() + 27);
            winnerLine.setEndX(tiles[endTileRow][endTileCol].getX() + 170);
            winnerLine.setEndY(tiles[endTileRow][endTileCol].getY() + 170);
            winnerLine.setStrokeWidth(3);
            winnerLine.setStroke(Color.WHITESMOKE);
        } else { // slash left
            winnerLine.setStartX(tiles[startTileRow][startTileCol].getX() + 170);
            winnerLine.setStartY(tiles[startTileRow][startTileCol].getY() + 30);
            winnerLine.setEndX(tiles[endTileRow][endTileCol].getX() + 30);
            winnerLine.setEndY(tiles[endTileRow][endTileCol].getY() + 170);
            winnerLine.setStrokeWidth(3);
            winnerLine.setStroke(Color.WHITESMOKE);
        }

        root.getChildren().add(winnerLine);
    }

    public Rectangle[][] getTiles() {
        return tiles;
    }

    public Button getResetBtn() {
        return resetBtn;
    }

    public ChoiceBox<String> getMarkChoices() {
        return markChoices;
    }

    public Group getRoot() {
        return root;
    }

    public Scene getScene() {
        return scene;
    }
}