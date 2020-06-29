package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainGUI extends JFrame{
    JPanel header = new JPanel();
    Menu menu = new Menu();

    void setScene(JPanel panel){
        menu.setVisible(false);

        add(header, BorderLayout.PAGE_START);
        add(panel, BorderLayout.CENTER);
    }

    public static void main(String[] args){
        MainGUI frame = new MainGUI();
    }

    MainGUI(){        
        Color headerColor = new Color(165, 28, 48);
        header.setPreferredSize(new Dimension(0, 30));
        header.setBackground(headerColor);

        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(header, BorderLayout.PAGE_START);
        add(menu, BorderLayout.CENTER);
        setupMenu();

        setSize(500, 300);
        setVisible(true);
    }

    void setupMenu(){
        MainPanel main = new MainPanel();

        menu.addScene(this, menu, "Menu");
        menu.addScene(this, main, "Main");
    }

    static void print(Object p){
        System.out.println(p);
    }
}