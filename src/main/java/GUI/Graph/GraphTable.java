package GUI.Graph;

import Core.FullEval;
import Core.GraphEquations;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;

public class GraphTable extends BorderPane {
    //TODO graph table IO working well with F4 (scene with setupGraph, graph, and table)
    GraphEquations objEQ = new GraphEquations();
    String[] EQs = objEQ.getAllEQS();
    FullEval evaluator = new FullEval();

    TableView<double[]> xTable = new TableView<>();
    TableView<ArrayList<Double>> mainTable = new TableView<>();

    ObservableList<ArrayList<Double>> data;
    ObservableList<double[]> Xs;
    int beginningIndex = -2;
    int endingIndex = 10;

    ScrollBar mainBar;
    ScrollBar xBar;

    public GraphTable(){
        Xs = initalSetup();
        data = getData();

        xTable.setItems(Xs);
        xTable.setMaxSize(45, 1000);
        xTable.setEditable(false);

        mainTable.setItems(data);
        mainTable.setEditable(false);
        mainTable.getSelectionModel().setCellSelectionEnabled(true);

        setLeft(xTable);
        setCenter(mainTable);

        addEventFilter(KeyEvent.ANY, e -> {
            if(!mainTable.isFocused()){
                mainFocus();
            }
        });
    }

    ObservableList<double[]> initalSetup(){
        createColumn("X", 0, false);
        double[][] data = new double[1][endingIndex-beginningIndex+1];
        for(int i = 0; i <= endingIndex-beginningIndex; i++){
            data[0][i] = beginningIndex+i;
        }
        return FXCollections.observableArrayList(transpose(data));
    }

    ObservableList<ArrayList<Double>> getData() {
        ArrayList<ArrayList<Double>> allData = new ArrayList<>();

        for(int i = 0; i < EQs.length; i++){
            createColumn(EQs[i], i, true);
            ArrayList<Double> contents = new ArrayList<>();
            for(int x = 0; x <= endingIndex-beginningIndex; x++){
                contents.add(evaluator.graphEval(EQs[i].replaceAll("y=", ""), beginningIndex+x));
            }
            allData.add(contents);
        }

        return FXCollections.observableArrayList(transpose(allData));
    }

    double[][] transpose(double[][] orig){
        int rows = orig.length;
        int columns = orig[0].length;
        double[][] transpose = new double[columns][rows];

        for(int r = 0; r < rows; r++){
            for(int c = 0; c < columns; c++){
                transpose[c][r] = orig[r][c];
            }
        }

        return transpose;
    }

    ArrayList<ArrayList<Double>> transpose(ArrayList<ArrayList<Double>> orig){
        int columns = orig.get(0).size();
        ArrayList<ArrayList<Double>> transpose = new ArrayList<>();

        for(int i = 0; i < columns; i++){
            ArrayList<Double> row = new ArrayList<>();
            for(ArrayList<Double> a : orig){
                row.add(a.get(i));
            }
            transpose.add(row);
        }

        return transpose;
    }

    void createColumn(String txt, int i, boolean isMain){
        if(isMain){
            TableColumn<ArrayList<Double>, Double> eqCol = new TableColumn<>(txt);
            eqCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(i)));
            mainTable.getColumns().add(eqCol);
        } else{
            TableColumn<double[], Double> eqCol = new TableColumn<>(txt);
            eqCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()[i]));
            xTable.getColumns().add(eqCol);
        }
    }

    public void updateEQs(){
        EQs = objEQ.getAllEQS();
        mainTable.getColumns().clear();
        data = getData();
        mainTable.setItems(data);
    }

    void updateValues(boolean isBottom){
        ArrayList<Double> calcData = new ArrayList<>();
        double latestIndex = xTable.getItems().get(xTable.getItems().size()-1)[0]+1;
        double oldestIndex = xTable.getItems().get(0)[0]-1;
        double index = isBottom ? latestIndex:oldestIndex;

        for(TableColumn<ArrayList<Double>, ?> i : mainTable.getColumns()){
            String eq = i.getText();
            calcData.add(evaluator.graphEval(eq, index));
        }

        if(isBottom){
            xTable.getItems().add(new double[]{index});
            mainTable.getItems().add(calcData);
            endingIndex++;
        } else{
            xTable.getItems().add(0, new double[]{index});
            mainTable.getItems().add(0, calcData);
            beginningIndex--;
        }
    }

    public void mainFocus(){
        updateEQs();
        mainTable.requestFocus();

        mainBar = (ScrollBar) mainTable.lookup(".scroll-bar:vertical");
        xBar = (ScrollBar) xTable.lookup(".scroll-bar:vertical");

        mainBar.valueProperty().addListener((obs, oldValue, newValue) -> {
            if(mainTable.getSelectionModel().getFocusedIndex() == 0){
                xBar.setValue(0);
                mainBar.setValue(0);
            } else{
                xBar.setValue(newValue.doubleValue());
            }
        });

        mainTable.setOnKeyPressed(event -> {
            int index = mainTable.getSelectionModel().getFocusedIndex();
            if(index == mainTable.getItems().size()-1 && event.getCode() == KeyCode.DOWN){
                updateValues(true);
            } else if(index == 0 && event.getCode() == KeyCode.UP){
                updateValues(false);
            }
        });
    }

    void print(Object p){
        System.out.println(p);
    }

}
