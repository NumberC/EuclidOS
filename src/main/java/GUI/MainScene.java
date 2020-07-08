package GUI;

import Core.History;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import Core.FullEval;
import javafx.scene.text.TextAlignment;

public class MainScene extends ScrollPane {
    History history = new History();
    FullEval eval = new FullEval();
    TextField txt = new TextField();
    public TextField latestTxt = txt;
    int txtCount = 1;
    int currentIndex = 0;
    VBox box = new VBox();

    //TODO: fit content for matrix
    // stop highlighting latest in blue
    Background whiteBG = new Background(new BackgroundFill(Color.color(1,1,1), CornerRadii.EMPTY, Insets.EMPTY));


    void basicSetup(){
        setHbarPolicy(ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollBarPolicy.NEVER);

        addEventFilter(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent scrollEvent) {
                if (scrollEvent.getDeltaX() != 0) {
                    scrollEvent.consume();
                }
            }
        });

        setContent(box);
        setFitToHeight(true);
        setFitToWidth(true);
    }

    void setupHistory(){
        for(int i = 0; i < history.getLatestIndex(); i++){
            System.out.println(i);

            String eq = history.getEQ(Integer.toString(i));
            String answer = history.getAnswer(Integer.toString(i));

            Label eqLbl = new Label(eq);
            Label ansLbl = new Label();

            ansLbl.setText(answer);
            ansLbl.setMaxWidth(Double.MAX_VALUE);
            ansLbl.setAlignment(Pos.CENTER_RIGHT);

            box.getChildren().add(eqLbl);
            box.getChildren().add(ansLbl);
        }
        currentIndex = 2*history.getLatestIndex();
    }

    MainScene(){
        basicSetup();
        setupHistory();

        box.setBackground(whiteBG);
        txt.setOnKeyPressed(keyHandler);
        addEventFilter(KeyEvent.KEY_PRESSED, upDownHandler);
        box.getChildren().add(txt);
    }

    EventHandler<KeyEvent> keyHandler = this::handleKeys;
    EventHandler<KeyEvent> upDownHandler = this::handleKeyUpDown;

    void handleKeyUpDown(KeyEvent e){
        if(e.getCode() == KeyCode.UP){
            upPressed();
        } else if(e.getCode() == KeyCode.DOWN){
            downPressed();
        }
    }

    void handleKeys(KeyEvent e){
        if(e.getCode() == KeyCode.ENTER){
            enterPressed();
        }
    }

    public void downPressed(){
        if(currentIndex < box.getChildren().size()-1){
            currentIndex++;
            Node curNode = box.getChildren().get(currentIndex);
            Node prevNode = box.getChildren().get(currentIndex-1);

            setHighlight(curNode);
            setHighlight(prevNode);
            curNode.requestFocus();
        }
    }

    public void upPressed(){
        if(currentIndex > 0){
            currentIndex--;
            Node curNode = box.getChildren().get(currentIndex);
            Node prevNode = box.getChildren().get(currentIndex+1);

            setHighlight(curNode);
            setHighlight(prevNode);
            curNode.requestFocus();
        }
    }

    void setHighlight(Node curNode){
        if(curNode instanceof Label){
            Label lbl = (Label) curNode;
            lbl.requestFocus();
        }
    }

    void resetHighlight(){
        currentIndex = box.getChildren().size()-1;
        setVvalue(1);
    }

    public void enterPressed(){
        String eq = txt.getText();
        String result;
        Label answer = new Label();

        box.getChildren().remove(box.getChildren().size()-1);
        Label eqLbl = new Label(eq);
        eqLbl.getStyleClass().add("highLbl");
        box.getChildren().add(eqLbl);
        eqLbl.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                latestTxt.appendText(eqLbl.getText());
                latestTxt.requestFocus();
                latestTxt.appendText("");
                resetHighlight();
            }
        });

        System.out.println(eq);

        try{
            if(eq.contains("[")){
                result = eval.evaluate(eq) + "";
            } else{
                result = eval.evaluate(eq) + "";
                if(eq.contains("<-U/D")){
                    if(result.contains(".") && !result.contains("E")){
                        result = eval.toFraction(Double.parseDouble(result));
                    }
                }
            }
        } catch (Exception e){
            result = "Error";
        }

        result = result.equals("null") ? "Error":result;

        answer.setText(result);
        answer.setMaxWidth(Double.MAX_VALUE);
        answer.setTextAlignment(TextAlignment.RIGHT);
        answer.setAlignment(Pos.CENTER_RIGHT);
        answer.getStyleClass().add("highLbl");
        answer.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                latestTxt.appendText(answer.getText());
                latestTxt.requestFocus();
                latestTxt.appendText("");
                resetHighlight();
            }
        });

        //history.putEQ(eq, result);

        TextField newTxt = new TextField();
        box.getChildren().add(answer);
        box.getChildren().add(newTxt);
        latestTxt = newTxt;

        applyCss();
        layout();
        setVvalue(1);

        txt.setOnKeyPressed(null);
        txt = newTxt;
        txt.setOnKeyPressed(keyHandler);
        txt.requestFocus();
        txtCount++;
        currentIndex += 2;
    }
}
