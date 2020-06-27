package GUI.GUIElements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;

public class testing extends GUIElementInterface {
    public static void main(String[] args) {
        // setSize(50, 50);
        System.out.println();
        JPanel panel = new JPanel();
        panel.setLayout(null);
        testing t = new testing();
        panel.add(t);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    testing(){
        super();
        setBounds(0,0, 60, 60);
        setSuperSize(60,60);
    }
    

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.white);

        Graphics2D g2 = (Graphics2D) g;

        //g2.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, dashLength, new float[]{dashLength}, 0.0f));
        g2.setPaint(Color.black);
        Rectangle r = new Rectangle(0, 0, 50, 50);

        g2.draw(r);
    }
}