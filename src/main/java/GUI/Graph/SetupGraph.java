package GUI.Graph;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

import Core.GraphEquations;

public class SetupGraph extends GridPane {
    public SetupGraph(){
        GraphEquations EQs = new GraphEquations();
        int currentIndex = 0;
        TextField RED = new TextField();
        TextField BLUE = new TextField();
        TextField PINK = new TextField();
        TextField PURPLE = new TextField();
        TextField BLACK = new TextField();

        TextField[] allTxts = {RED, BLUE, PINK, PURPLE, BLACK};
        String[] colors = {"Red", "Blue", "Pink", "Purple", "Black"};

        for(int i = 0; i < allTxts.length; i++){
            TextField txtField = allTxts[i];
            String curColor = colors[i];
            String storedData = EQs.useData(curColor);
            if(storedData == null){
                txtField.setText("y=");
            } else {
                txtField.setText(storedData);
            }

            int finalI = i;
            txtField.setOnKeyPressed(event->{
                if(event.getCode() == KeyCode.ENTER){
                    EQs.store(curColor, txtField.getText());
                    TextField choosenTxtField;
                    if(finalI+1 < allTxts.length){
                        choosenTxtField = allTxts[finalI+1];
                    } else{
                        choosenTxtField = allTxts[0];
                    }
                    choosenTxtField.requestFocus();
                    choosenTxtField.appendText("");
                }
            });

            add(txtField, 0, (int)(txtField.getHeight()+1)*i);
        }
    }
}
