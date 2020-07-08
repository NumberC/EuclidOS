package GUI;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;


public class SpecialMath extends BorderPane {
    int currentIndex = 0;
    int previousTopIndex = 0;
    int topIndex = 0;

    Label[] topLbls;
    Label[] mathLbls;
    Label[] matrixLbls;
    VBox[] vBoxes;

    MainScene main;
    IOMapping io;

    HBox topBox = new HBox();
    VBox normalMath = new VBox();
    VBox matrixMath = new VBox();

    SpecialMath(IOMapping io){
        this.io = io;
        this.main = io.getMain();

        Label factorial = new Label("!");
        Label abs = new Label("ABS");
        Label nd = new Label("n/d");
        Label sigma = new Label("Sigma");

        Label det = new Label("det");

        topLbls = new Label[] {new Label("Normal"), new Label("Matrix")};
        vBoxes = new VBox[] {normalMath, matrixMath};
        mathLbls = new Label[] {factorial, abs, nd, sigma};
        matrixLbls = new Label[] {det};

        topBox.setSpacing(10);
        topLbls[0].getStyleClass().add("highSolid");
        for(Label i : topLbls){
            topBox.getChildren().add(i);
        }
        for(Label i : mathLbls){
            i.getStyleClass().add("highLbl");
            normalMath.getChildren().add(i);
        }
        for(Label i : matrixLbls){
            i.getStyleClass().add("highLbl");
            matrixMath.getChildren().add(i);
        }

        setTop(topBox);
        setCenter(normalMath);
    }

    public EventHandler<KeyEvent> handler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent e) {
            KeyCode key = e.getCode();
            switch(key){
                case RIGHT:
                    indexCheck(false, true);
                    break;
                case LEFT:
                    indexCheck(false, false);
                    break;
                case UP:
                    indexCheck(true, true);
                    break;
                case DOWN:
                    indexCheck(true, false);
                    break;
                case ENTER:
                    String text = ((Label) vBoxes[topIndex].getChildren().get(currentIndex)).getText();
                    main.latestTxt.appendText(text);
                    io.setRootScene(main);
                    break;
            }
        }
    };

    void highlightLabel(){
        vBoxes[topIndex].getChildren().get(currentIndex).requestFocus();

        topLbls[previousTopIndex].getStyleClass().remove("highSolid");
        topLbls[topIndex].getStyleClass().add("highSolid");
        setCenter(vBoxes[topIndex]);
    }

    void indexCheck(boolean isCurrentInd, boolean isRightUp){
        if(isCurrentInd){
            currentIndex = isRightUp ? currentIndex-1:currentIndex+1;
            if(currentIndex < 0){
                currentIndex = vBoxes[topIndex].getChildren().size()-1;
            } else if(currentIndex > vBoxes[topIndex].getChildren().size()-1){
                currentIndex = 0;
            }
        } else{
            previousTopIndex = topIndex;
            topIndex = isRightUp ? topIndex+1:topIndex-1;
            currentIndex = 0;
            if(topIndex < 0){
                topIndex = topLbls.length-1;
            } else if(topIndex > topLbls.length-1){
                topIndex = 0;
            }
        }

        highlightLabel();
    }
}