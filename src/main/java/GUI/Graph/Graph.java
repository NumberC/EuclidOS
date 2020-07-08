package GUI.Graph;

import Core.FullEval;
import Core.GraphEquations;
import Core.SystemConfigurations;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.chart.NumberAxis;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph extends StackPane {
    FullEval eval = new FullEval();
    GraphEquations EQs = new GraphEquations();
    SystemConfigurations configs = new SystemConfigurations();

    double xLow;
    double xHi;
    double yLow;
    double yHi;

    boolean reachedMaxHeight = false;

    Circle point;
    Axes axes;
    ArrayList<Plot> plots = new ArrayList<>();
    int plotIndex;
    int currentIndex;

    //TODO set colors
    public Graph(){
        long start;
        long end;
        start = System.currentTimeMillis();

        point = new Circle(0, 0, 5);

        xLow = configs.Xs()[0];
        xHi = configs.Xs()[1];
        yLow = configs.Ys()[0];
        yHi = configs.Ys()[1];
        axes = new Axes(500, 500, xLow, xHi, 1, yLow, yHi, 1);

        EQs.useAllData();

        setPadding(new Insets(20));
        setStyle("-fx-background-color: rgb(35, 39, 50);");

        end = System.currentTimeMillis();
        double diff = end-start;

        System.out.println("Time Difference2: " + diff);
        setOnKeyPressed(keyHandler);
    }

    public boolean getReachedMaxHeight(){return reachedMaxHeight; }

    public void updateGraph(){
        long start = System.currentTimeMillis();
        changePoint(0, 0);
        HashMap<String, String> data = EQs.useAllData();
        plots.clear();
        getChildren().clear();
        getChildren().add(axes);

        for(String i : data.keySet()){
            Plot plot = new Plot(data.get(i).replaceAll("y=", ""), xLow, xHi, 0.1);
            plots.add(plot);
            getChildren().add(plot);
        }

        long end = System.currentTimeMillis();
        System.out.println("Diff2: " + (end-start));
        plotIndex = 0;
        currentIndex = plots.get(plotIndex).midIndex();
        setOnKeyPressed(keyHandler);
    }

    public EventHandler<KeyEvent> keyHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent e) {
            switch (e.getCode()){
                case LEFT:
                    Plot plot = plots.get(plotIndex);
                    if(currentIndex-1 >= 0){
                        currentIndex--;
                        changePoint(plot, currentIndex);
                    }
                    break;
                case RIGHT:
                    plot = plots.get(plotIndex);
                    if(currentIndex+1 <= plot.maxIndex()){
                        currentIndex++;
                        changePoint(plot, currentIndex);
                    }
                    break;
                case UP:
                    if(plotIndex+1 <= plots.size()-1){
                        plotIndex++;
                        changePoint(plots.get(plotIndex), currentIndex);
                    } else{reachedMaxHeight = true; }
                    break;
                case DOWN:
                    reachedMaxHeight = false;
                    if(plotIndex-1 >= 0){
                        plotIndex--;
                        changePoint(plots.get(plotIndex), currentIndex);
                    }
                    break;
            }
        }
    };

    void changePoint(Plot fx, int i){
        double x = fx.getX(i);
        double y = fx.getY(i);
        changePoint(x, y);
    }

    void changePoint(double x, double y){
        point.setCenterX(mapX(x));
        point.setCenterY(mapY(y));
        setPointVisible(true);
    }

    void setPointVisible(boolean bool){ point.setVisible(bool); }

    double mapX(double x){
        double tx = axes.getPrefWidth() / 2;
        double sx = axes.getPrefWidth() / (axes.getXAxis().getUpperBound() - axes.getXAxis().getLowerBound());

        return x * sx + tx;
    }

    double mapY(double y){
        double ty = axes.getPrefHeight() / 2;
        double sy = axes.getPrefHeight() / (axes.getYAxis().getUpperBound() - axes.getYAxis().getLowerBound());

        return -y * sy + ty;
    }

    //TODO check if allowed
    //Axes and Plot class by jewelsea on https://stackoverflow.com/questions/24005247/draw-cartesian-plane-graphi-with-canvas-in-javafx
    class Axes extends Pane {
        private NumberAxis xAxis;
        private NumberAxis yAxis;

        public Axes(int width, int height, double xLow, double xHi, double xTickUnit, double yLow, double yHi, double yTickUnit) {
            setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
            setPrefSize(width, height);
            setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

            xAxis = new NumberAxis(xLow, xHi, xTickUnit);
            xAxis.setSide(Side.BOTTOM);
            xAxis.setMinorTickVisible(false);
            xAxis.setPrefWidth(width);
            xAxis.setLayoutY(height / 2);

            yAxis = new NumberAxis(yLow, yHi, yTickUnit);
            yAxis.setSide(Side.LEFT);
            yAxis.setMinorTickVisible(false);
            yAxis.setPrefHeight(height);
            yAxis.layoutXProperty().bind(Bindings.subtract((width / 2) + 1, yAxis.widthProperty()));

            getChildren().setAll(xAxis, yAxis);
        }

        public NumberAxis getXAxis() {
            return xAxis;
        }
        public NumberAxis getYAxis() {
            return yAxis;
        }
    }

    class Plot extends Pane {
        ArrayList<Double> Xs = new ArrayList<>();
        ArrayList<Double> Ys = new ArrayList<>();
        String eq;
        Path path;

        public Plot(String m_eq, double xMin, double xMax, double xInc) {
            if(m_eq.isEmpty()){
                return;
            }
            eq = m_eq;
            path = new Path();
            path.setStroke(Color.ORANGE.deriveColor(0, 1, 1, 0.6));
            path.setStrokeWidth(2);

            path.setClip(new Rectangle(0, 0, axes.getPrefWidth(), axes.getPrefHeight()));

            double x = xMin;
            double y = eval.graphEval(m_eq, x);
            Xs.add(x);
            Ys.add(y);

            path.getElements().add(new MoveTo(mapX(x), mapY(y)));

            x += xInc;
            while (x < xMax) {
                y = eval.graphEval(m_eq, x);
                Xs.add(x);
                Ys.add(y);
                path.getElements().add(new LineTo(mapX(x), mapY(y)));
                x += xInc;
            }

            setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
            setPrefSize(axes.getPrefWidth(), axes.getPrefHeight());
            setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

            getChildren().setAll(path);
            getChildren().add(point);
        }

        Path getPath(){
            return path;
        }

        String getEQ(){
            return eq;
        }

        int maxIndex(){
            return Xs.size()-1;
        }

        int midIndex(){
            return Xs.size()/2;
        }

        double getX(int x){
            return Xs.get(x);
        }

        double getY(int x){
            return Ys.get(x);
        }

        double getY(double x){
            return eval.graphEval(eq, x);
        }
    }
}