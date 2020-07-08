package GUI;

import GUI.Graph.Graph;
import GUI.Graph.GraphTable;
import GUI.Graph.GraphView;
import GUI.Graph.SetupGraph;
import GUI.Matrix.SetupMatrix;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.HashMap;

public class IOMapping {
    MainScene main = new MainScene();

    SetupGraph setupGraph = new SetupGraph();
    Graph graph  = new Graph();
    GraphTable graphTable = new GraphTable();
    GraphView realGraph = new GraphView(this);

    EQSolveGUI eqSolver = new EQSolveGUI();
    SetupMatrix matrixGUI = new SetupMatrix(this);
    Settings settings = new Settings();
    SpecialMath math = new SpecialMath(this);
    Menu menu = new Menu(this);

    Parent currentRoot = main;
    Stage stage;
    HashMap<String, Parent> mappingHash = new HashMap<>();
    HashMap<Parent, EventHandler<KeyEvent>> keyHandlerHash;

    EventHandler<KeyEvent> mainHandler = this::keyHandler;
    EventHandler<KeyEvent> keyHandler = this::keyHandler;

    IOMapping(Stage stage){
        this.stage = stage;
        setupMapping();
    }

    public MainScene getMain(){return main; }
    public GraphView getGraphView(){return realGraph; }
    public SetupGraph getSetupGraph(){return setupGraph; }
    public Graph getGraph(){return graph; }
    public GraphTable getGraphTable(){return graphTable;}

    public void setRootScene(Parent scene){
        currentRoot = scene;
        if(currentRoot == graph){
            graph.updateGraph();
        }

        if(keyHandlerHash.containsKey(currentRoot)){
            setKeyHandler(keyHandlerHash.get(currentRoot));
        } else{
            setKeyHandler(keyEvent -> currentRoot.getOnKeyPressed());
        }

        setRoot();
    }

    void setupMapping(){
        mappingHash.put("F1", main);
        mappingHash.put("F2", settings);
        mappingHash.put("F3", graphTable); //setupGraph
        mappingHash.put("F4", realGraph);
        mappingHash.put("F5", matrixGUI);
        mappingHash.put("F6", math);
        mappingHash.put("F7", menu);
        mappingHash.put("F8", eqSolver);

        keyHandlerHash = new HashMap<Parent, EventHandler<KeyEvent>>(){{
            put(graph, graph.keyHandler);
            put(realGraph, realGraph.keyHandler);
            put(math, math.handler);
        }};
    }

    void sceneSwitch(KeyEvent event){
        String key = event.getCode().toString();
        if(mappingHash.containsKey(key)){
            setRootScene(mappingHash.get(event.getCode().toString()));
        }
    }

    void digits(KeyEvent event){
        //TODO add this to any focused text field
        switch (event.getCode()){
            case F9:
                main.latestTxt.appendText("√(");
                break;
            case F10:
                main.latestTxt.appendText("π");
                break;
            case F11:
                main.latestTxt.appendText("℮");
                break;
        }
    }

    public void keyHandler(KeyEvent event){
        sceneSwitch(event);
        digits(event);
    }

    void setRoot(){
        stage.getScene().setRoot(currentRoot);
    }

    void setRootHandler(){
        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, keyHandler);
    }

    public void resetKeyHandler(){
        stage.getScene().removeEventFilter(KeyEvent.KEY_PRESSED, keyHandler);
        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, mainHandler);
    }

    void setKeyHandler(EventHandler<KeyEvent> handler){
        stage.getScene().removeEventFilter(KeyEvent.KEY_PRESSED, keyHandler);
        keyHandler = handler;
        setRootHandler();
    }

    public Parent getCurrentScene(){
        return currentRoot;
    }

    EventHandler<KeyEvent> getKeyHandler(){
        return keyHandler;
    }

    void print(Object p){
        System.out.println(p);
    }
}
