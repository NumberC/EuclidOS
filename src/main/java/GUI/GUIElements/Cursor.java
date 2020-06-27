package GUI.GUIElements;

import java.awt.Graphics;

import javax.swing.JComponent;

public class Cursor extends JComponent{

    int x = 5;
    int y = 5;
    int width = 5;
    int height = 30;

    public void setX(int x) { this.x = x;}
    public int getX(){ return x; }
    public void setY(int y) { this.y = y; }
    public int getY(){ return y; }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawRect(x, y, width, height);
    }
}