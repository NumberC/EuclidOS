package GUI.GUIElements;

import java.awt.Graphics;
import java.awt.*;

import javax.swing.JComponent;

public abstract class GUIElementInterface extends JComponent{
    //public GUIElementInterface[] getContents();
    int x;
    int y;

    @Override
    public void setSize(int width, int height){
        setSuperSize(width, height);
        revalidate();
    }

    void setSuperSize(int width, int height){
        super.setSize(width, height);
        super.setPreferredSize(new Dimension(width, height));
        super.setBounds(0, 0, width, height);
    }

    @Override
    public void setLocation(int x, int y){
        super.setLocation(x, y);
        this.x = x;
        this.y = y;
        revalidate();
    }

    @Override
    public Point getLocation(){ return new Point(this.x, this.y); }
    
    public void setX(int x){this.x = x;}
    public void setY(int y){this.y = y;}
    public int getX(){ return x; }
    public int getY(){ return y; }
}