package GUIElements;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class Text extends JLabel{
    char c;
    Text(char c){
        super(c+"");
        //JLabel l = new JLabel("hi");
        this.c = c;
    }

    public void setC(char c){setText(c+"");}
    public char getC(){return c;}
}