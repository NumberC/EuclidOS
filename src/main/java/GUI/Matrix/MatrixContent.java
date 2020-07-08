package GUI.Matrix;

import Core.FullEval;
import Core.Matrix;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class MatrixContent extends GridPane {
    double[][] matrix;
    ArrayList<TextField> txts = new ArrayList<>();
    int txtIndex = 0;

    FullEval eval = new FullEval();
    Matrix matrixContr = new Matrix();

    String key;
    int rows;
    int columns;

    TextField rowsTxt = new TextField();
    TextField columnsTxt = new TextField();

    //TODO: allow arrow buttons to navigate the contents
    MatrixContent(String givenKey){
        key = givenKey;
        matrix = matrixContr.getMatrix(key);
        rows = matrix.length;
        columns = matrix[0].length;
        setupMatrixContents();
    }

    void rowPress(KeyEvent e){
        if(e.getCode() == KeyCode.TAB || e.getCode() == KeyCode.ENTER){
            int input = Integer.parseInt(rowsTxt.getText());
            if(input-rows != 0){
                matrixContr.addRow(key, input-rows);
                matrix = matrixContr.getMatrix(key);
                rows = matrix.length;
                displayValues(false);
            }
            txtIndex = (txtIndex+1 >= txts.size()) ? 0:txtIndex+1;
            txts.get(txtIndex).requestFocus();
        }
    }

    void columnPress(KeyEvent e){
        if(e.getCode() == KeyCode.TAB || e.getCode() == KeyCode.ENTER){
            int input = Integer.parseInt(columnsTxt.getText());
            if(input-columns != 0){
                matrixContr.addColumn(key, input-columns);
                matrix = matrixContr.getMatrix(key);
                columns = matrix[0].length;
                displayValues(false);
            }
            txtIndex = (txtIndex+1 >= txts.size()) ? 0:txtIndex+1;
            txts.get(txtIndex).requestFocus();
        }
    }

    void valuePress(KeyEvent e){
        if(e.getCode() == KeyCode.TAB || e.getCode() == KeyCode.ENTER){
            TextField node = txts.get(txtIndex);
            String preR = (txtIndex-2)/columns + "";
            int r = Integer.parseInt(preR.split("\\.")[0]);

            int c = (txtIndex-2) - r*columns;

            double result = Double.parseDouble(eval.evaluate(node.getText()));
            matrixContr.editMatrix(key, c, r, result);
            node.setText(result+"");

            txtIndex = (txtIndex+1 >= txts.size()) ? 0:txtIndex+1;
            txts.get(txtIndex).requestFocus();
        }
    }

    void setupMatrixContents(){
        Label rowsLbl = new Label("ROWS: ");
        Label columnsLbl = new Label("COLUMNS: ");
        Label valuesLbl = new Label("VALUES: ");
        rowsTxt.setText(rows + "");
        columnsTxt.setText(columns + "");

        rowsTxt.addEventFilter(KeyEvent.KEY_PRESSED, this::rowPress);
        columnsTxt.addEventFilter(KeyEvent.KEY_PRESSED, this::columnPress);

        add(rowsLbl, 0, 0);
        add(columnsLbl, 1, 0);
        add(rowsTxt, 0, 1);
        add(columnsTxt, 1, 1);
        add(valuesLbl, 0, 2);

        rowsTxt.requestFocus();
        rowsTxt.appendText("");

        txts.add(rowsTxt);
        txts.add(columnsTxt);
        displayValues(true);
    }

    void displayValues(boolean isFirst){
        if(!isFirst){
            for(int i = 0; i < getChildren().size()-5; i++){
                txts.remove(txts.size()-1);
            }
            getChildren().remove(5, getChildren().size());
        }

        for(int r = 0; r < rows; r++){
            for(int c = 0; c < columns; c++){
                double num = matrix[r][c];
                TextField node = new TextField();
                node.setText(num + "");
                node.addEventFilter(KeyEvent.KEY_PRESSED, this::valuePress);
                add(node, c, r+3);
                txts.add(node);
            }
        }
    }
}
