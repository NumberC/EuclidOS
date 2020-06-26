package GUIElements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JComponent;

public class NumberContainer extends JComponent{
    int x = 0;
    int y = 0;
    int length = 50;
    int dashLength = 10;

    NumberContainer(){
        super();
        int totalLength = length+dashLength;
        setSuperSize(totalLength);
    }

    void setSuperSize(int totalLength){
        super.setSize(totalLength, totalLength);
        super.setPreferredSize(new Dimension(totalLength, totalLength)); //length+dashLength
        super.setBounds(0, 0, totalLength, totalLength);
    }

    @Override
    public void setSize(int width, int height){
        this.length = (width > height) ? height:width;
        setSuperSize(length+dashLength);
        revalidate();
    }

    @Override
    public Dimension getSize(){
        return new Dimension(length+dashLength, length+dashLength);
    }

    @Override
    public void setLocation(int x, int y){
        super.setLocation(x, y);
        this.x = x;
        this.y = y;
        revalidate();
    }

    @Override
    public Point getLocation(){
        return new Point(this.x, this.y);
    }

    //TODO: add revalidate to everything?
    public void setLength(int length){this.length = length;}
    public int getLength(){return this.length;}
    public void setDashLength(int dashlength){this.dashLength = dashlength;}
    public int getDashLength(){return this.dashLength;}
    public void setX(int x){this.x = x;}
    public void setY(int y){this.y = y;}
    public int getX(){ return x; }
    public int getY(){ return y; }

    //Code altered, but mainly from http://www.java2s.com/Code/Java/2D-Graphics-GUI/Dashedstroke.htm
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        setBackground(Color.white);

        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, dashLength, new float[]{dashLength}, 0.0f));
        g2.setPaint(Color.black);
        Rectangle r = new Rectangle(0, 0, length, length);

        g2.draw(r);
    }
}
