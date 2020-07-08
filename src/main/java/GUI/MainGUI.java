package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGUI extends Application {
    //Settings, Math(!, fraction, etc.), Apps?, Trace/Table,
    //Exam Mode
    //TODO fractions placeholder

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        IOMapping io = new IOMapping(primaryStage);
        primaryStage.setScene(new Scene(io.getCurrentScene(), 300, 250));
        primaryStage.getScene().getStylesheets().add("GUI/mainStyle.css");
        io.resetKeyHandler();
        primaryStage.setFullScreen(false);
        primaryStage.show();
    }

    static void print(Object p){
        System.out.println(p);
    }
}
