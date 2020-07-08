package GUI;

import Core.FullEval;
import Core.SystemConfigurations;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Settings extends GridPane {
    //TODO needs a lot of work
    //TODO help with radians/degree toggle
    int indexTxt = 0;
    SystemConfigurations configs = new SystemConfigurations();
    FullEval eval = new FullEval();
    double[] Xs = configs.Xs();
    double[] Ys = configs.Ys();
    Label[] XYLbl;
    TextField[] XYTxt;
    Label radiansLbl;
    Label degreesLbl;

    Settings(){
        TextField minX = new TextField(Xs[0]+"");
        TextField maxX = new TextField(Xs[1]+"");
        TextField minY = new TextField(Ys[0]+"");
        TextField maxY = new TextField(Ys[1]+"");

        radiansLbl = new Label("Radians");
        degreesLbl = new Label("Degrees");
        Label minXLbl = new Label("Min-X ");
        Label maxXLbl = new Label("Max-X ");
        Label minYLbl = new Label("Min-Y ");
        Label maxYLbl = new Label("Max-Y ");

        TextField[] XYTxt = {minX, maxX, minY, maxY};
        Label[] XYLbl = {radiansLbl, degreesLbl, minXLbl, maxXLbl, minYLbl, maxYLbl};
        this.XYTxt = XYTxt;
        this.XYLbl = XYLbl;

        setRadiansOrDegrees(configs.isRadians());

        setOnKeyPressed(handler);
        addEventFilter(KeyEvent.KEY_PRESSED, handler);

        add(radiansLbl, 0, 0);
        add(degreesLbl, 1, 0);

        for(int i = 0; i < XYTxt.length; i++){
            add(XYLbl[i+2], 0, i+1);
            add(XYTxt[i], 1, i+1);
        }

        minX.requestFocus();
        minX.appendText("");
    }

    EventHandler<KeyEvent> handler = this::basicKeyPress;

    void setRadiansOrDegrees(boolean condition){
        configs.setIsRadians(condition);

        degreesLbl.getStyleClass().remove("highSolid");
        radiansLbl.getStyleClass().remove("highSolid");
        if (condition) {
            radiansLbl.getStyleClass().add("highSolid");
        } else {
            degreesLbl.getStyleClass().add("highSolid");
        }
    }

    void updateConfig(){
        if(indexTxt == 0 || indexTxt == 1){
            Double result = Double.parseDouble(eval.evaluate(XYTxt[indexTxt].getText()));
            configs.setAxis("X", indexTxt == 0, result);
            XYTxt[indexTxt].setText(result + "");
        } else if(indexTxt == 2 || indexTxt == 3){
            Double result = Double.parseDouble(eval.evaluate(XYTxt[indexTxt].getText()));
            configs.setAxis("Y", indexTxt == 2, result);
            XYTxt[indexTxt].setText(result + "");
        }
    }

    void basicKeyPress(KeyEvent e){
        KeyCode key = e.getCode();
        if(key == KeyCode.LEFT){
            setRadiansOrDegrees(true);
        } else if(key == KeyCode.RIGHT){
            setRadiansOrDegrees(false);
        }

        if(key == KeyCode.DOWN){
            updateConfig();
            indexTxt++;
            if(indexTxt > XYTxt.length-1){
                indexTxt = 0;
            }
            XYTxt[indexTxt].requestFocus();
            XYTxt[indexTxt].appendText("");
        } else if(key == KeyCode.ENTER || key == KeyCode.UP){
            updateConfig();
            indexTxt--;
            if(indexTxt < 0){
                indexTxt = XYTxt.length-1;
            }
            XYTxt[indexTxt].requestFocus();
            XYTxt[indexTxt].appendText("");
        }
    }
}
