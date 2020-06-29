package GUI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel {
    Menu() {
        int gap = 10;
        GridLayout grid = new GridLayout(0, 3);
        grid.setVgap(gap);
        grid.setHgap(gap);

        setLayout(grid);
    }

    void addScene(MainGUI gui, JPanel panel, String name){
        JButton mainBtn = new JButton(name);
        mainBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.setScene(panel);
            }
        });
        add(mainBtn);
    }

    public static void main(String[] args){
        Menu main = new Menu();
        JFrame frame = new JFrame();

        frame.add(main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300); 
        frame.setVisible(true);
    }
}