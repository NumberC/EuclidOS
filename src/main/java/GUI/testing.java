package GUI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class testing extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Canvas canvas = new Canvas(200, 200);

        Rectangle rect = new Rectangle();
        rect.setFill(Color.BLACK);
        rect.setWidth(100);
        rect.setHeight(2);

        draw(canvas.getGraphicsContext2D());
        root.getChildren().add(canvas);

        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    double genStartX = 30;
    double genStartY = 10;

    void draw(GraphicsContext gc){
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        //drawFraction(gc);
        drawSqrt(gc);
    }

    void drawFraction(GraphicsContext gc){
        String numEq = "5x+2";
        String denomEq = "5x+22";

        double startY = genStartY+5;
        double txtLength = Math.max(numEq.length(), denomEq.length()) *7;
        double txtHeight = 20;

        gc.fillText(numEq, genStartX, genStartY);
        gc.fillText(denomEq, genStartX, genStartY+txtHeight);
        gc.strokeLine(genStartX, startY, genStartX+txtLength, startY);
    }

    void drawSqrt(GraphicsContext gc){

        double beginningY = 50;
        double belowY = 55;
        double endY = 40;

        double size = 20;
        String content = "25555555555!";
        String index = "2";
        size = content.length()*7;

        gc.strokePolyline(new double[]{genStartX, genStartX+5, genStartX+7, genStartX+10, genStartX+10+size},
                         new double[]{beginningY, beginningY, belowY, endY, endY}, 5);
        gc.fillText(content, genStartX+14, (belowY-endY)/2+endY+5);
    }
}
