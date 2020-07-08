package GUI;

import javafx.beans.value.ChangeListener;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class Menu extends GridPane {
    double currentHeight;
    double currentWidth;

    double ratio = 5.0/24.0;
    double spacingRatio = 27.0/320.0;
    double radiusRatio = 0.1;

    IOMapping io;

    Button mainBtn = new Button();
    Button graphingBtn = new Button();

    Button[] iconBtns = {mainBtn, graphingBtn};
    Parent[] scenes = new Parent[2];

    void setButtonSize(double length){
        for(Button i : iconBtns){
            i.setPrefWidth(length);
            i.setPrefHeight(length);
        }
    }

    void setGaps(double length){
        setHgap(length);
        setVgap(length);
    }

    void setup(){
        mainBtn.setId("mainBtn");
        graphingBtn.setId("graphingBtn");

        mainBtn.setOnAction(click -> io.setRootScene(scenes[0]));
        graphingBtn.setOnAction(click -> io.setRootScene(scenes[1]));

        for(Button i : iconBtns){
            i.getStyleClass().add("iconBtn");
        }

        add(mainBtn, 1, 1);
        add(graphingBtn, 2, 1);

        widthProperty().addListener(widthListener);
        heightProperty().addListener(heightListener);
    }

    void baseListenerActivity(){
        setButtonSize(ratio * Math.min(currentWidth, currentHeight));
        //mainBtn.setStyle("-fx-background-radius: " + radiusRatio*ratio*Math.min(currentWidth, currentHeight) + "px;");
        //TODO make lines appear heavier hard to see
        for(Button i : iconBtns){
            i.setStyle("-fx-background-size: " + i.getPrefWidth() + ";");
        }
        setGaps(spacingRatio*currentWidth);
    }

    ChangeListener<Number> widthListener = (observable, oldValue, newValue) -> {
        currentWidth = (double) newValue;
        baseListenerActivity();
    };

    ChangeListener<Number> heightListener = (observable, oldValue, newValue) -> {
        currentHeight = (double) newValue;
        baseListenerActivity();
    };

    Menu(IOMapping m_io){
        io = m_io;

        scenes[0] = io.getMain();
        scenes[1] = io.getGraph();
        setup();
    }
}
