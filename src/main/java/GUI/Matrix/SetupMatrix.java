package GUI.Matrix;

import Core.Matrix;
import GUI.IOMapping;
import GUI.MainScene;
import GUI.Matrix.MatrixContent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.util.HashMap;

public class SetupMatrix extends GridPane {
    Matrix matrixOrg = new Matrix();
    boolean isRef = false;
    MainScene main;
    IOMapping io;

    Label editLbl;
    Label refLbl;

    public SetupMatrix(IOMapping io){
        this.io = io;
        main = io.getMain();

        editLbl = new Label("Edit");
        refLbl = new Label("Reference");

        add(editLbl, 0, 0);
        add(refLbl, 1, 0);

        lblHighlight();
        setupMatrixLabels(true);
        addEventFilter(KeyEvent.KEY_PRESSED, keyHandler);
    }

    EventHandler<KeyEvent> keyHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent e) {
            if(e.getCode() == KeyCode.LEFT){
                isRef = false;
                lblHighlight();
            } else if(e.getCode() == KeyCode.RIGHT){
                isRef = true;
                lblHighlight();
            }
        }
    };

    void lblHighlight(){
        refLbl.getStyleClass().remove("highSolid");
        editLbl.getStyleClass().remove("highSolid");
        if(isRef){
            refLbl.getStyleClass().add("highSolid");
        } else{
            editLbl.getStyleClass().add("highSolid");
        }
    }

    void setupMatrixLabels(boolean isFirst){
        HashMap<String, double[][]> matrices = matrixOrg.getAllMatrices();

        for(int i = 0; i < matrices.keySet().size(); i++) {
            String key = (String) matrices.keySet().toArray()[i];

            if(isFirst){
                Button createNew = new Button("NEW MATRIX");
                createNew.setOnAction(actionEvent -> {
                    matrixOrg.createMatrix(1, 1);
                    setupMatrixLabels(false);
                });
                add(createNew, 0, 1);
            }

            if (!key.equals("Count")) {
                Button matrixBtn = new Button(key);
                matrixBtn.setOnAction(actionEvent -> {
                    if(isRef){
                        main.latestTxt.appendText("[" + key + "]");
                        main.latestTxt.requestFocus();
                        io.setRootScene(main);
                    } else{
                        MatrixContent contentPane = new MatrixContent(key);
                        io.setRootScene(contentPane);
                    }
                });
                add(matrixBtn, 0, i+2);
            }
        }
    }
}
