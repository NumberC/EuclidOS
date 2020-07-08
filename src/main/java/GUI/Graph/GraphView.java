package GUI.Graph;

import GUI.IOMapping;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class GraphView extends BorderPane {
    SetupGraph setup;
    Graph graph;
    GraphTable table;
    IOMapping io;

    Label eqLbl = new Label("y=");
    Label graphLbl = new Label("Graph");
    Label tableLbl = new Label("Table");

    Label[] lbls = {eqLbl, graphLbl, tableLbl};
    int labelIndex = 0;

    void labelHighlights(){
        for(Label i : lbls){
            i.getStyleClass().remove("highSolid");
        }
        lbls[labelIndex].getStyleClass().add("highSolid");
    }

    public GraphView(IOMapping io){
        this.io = io;
        setup = io.getSetupGraph();
        graph = io.getGraph();
        table = io.getGraphTable();

        HBox options = new HBox();
        for(int i = 0; i < lbls.length; i++){
            options.getChildren().add(lbls[i]);
        }
        options.setSpacing(10);
        setTop(options);
        setCenter(setup);
        labelHighlights();
    }

    void setupScenes(){
        System.out.println(labelIndex);
        switch (labelIndex){
            case 0:
                setCenter(setup);
                break;
            case 1:
                graph.updateGraph();
                setCenter(graph);
                graph.requestFocus();
                break;
            case 2:
                setCenter(table);
                break;
        }
        labelHighlights();
    }

    public EventHandler<KeyEvent> keyHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent e) {
            if(!(labelIndex == 1) || graph.getReachedMaxHeight()){
                if(e.getCode() == KeyCode.LEFT){
                    labelIndex = (labelIndex <= 0) ? 0:labelIndex-1;
                    setupScenes();
                } else if(e.getCode() == KeyCode.RIGHT){
                    labelIndex = (labelIndex >= lbls.length-1) ? lbls.length-1:labelIndex+1;
                    setupScenes();
                }
            }
        }
    };
}
