package GUI;

import Core.EquationSolver;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class EQSolveGUI extends VBox {
    EquationSolver eqSolver = new EquationSolver();

    TextField preEqual = new TextField();
    Label equalSign = new Label("=");
    TextField postEqual = new TextField();
    Label result = new Label();

    public EQSolveGUI(){
        getChildren().add(preEqual);
        getChildren().add(equalSign);
        getChildren().add(postEqual);
        getChildren().add(result);

        addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if(event.getCode() == KeyCode.ENTER){
                if(preEqual.isFocused()){
                    postEqual.requestFocus();
                }else if(postEqual.isFocused()){
                    if(!(preEqual.getText().isEmpty() || postEqual.getText().isEmpty())){
                        String eq = preEqual.getText() + "=" + postEqual.getText();
                        System.out.println(eq);
                        double res = eqSolver.solve(eq);
                        result.setText("X=" + res);
                    }
                }
            }
        });
    }
}
