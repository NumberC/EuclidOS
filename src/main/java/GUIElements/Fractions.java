package GUIElements;

import javax.swing.JComponent;
import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Dimension;

public class Fractions extends JComponent{
    int x = 100;
    int y = 100;
    int margin = 20;
    int height = 3;
    int width = 50;
    int length = 20;
    NumberContainer n1 = new NumberContainer();
    NumberContainer n2 = new NumberContainer();

    Fractions(){
        super();
        setSize(width, length);
        setPreferredSize(new Dimension(width, length));
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        NumberContainer[] containers = {n1, n2};
        for(int i = 0; i < containers.length; i++){
            NumberContainer n = containers[i];
            n.setLength(length);
            n.setX(x+(width-n.getLength())/2);
            if(i > 0){
                n.setY(y+height+2*margin-n.getLength());
            } else{
                n.setY(y-margin-n.getLength());
            }
            n.paintComponent(g);
        }
        
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
    }
    /*
    super.paintComponent(g);
        NumberContainer[] containers = {n1, n2};
        for(int i = 0; i < containers.length; i++){
            NumberContainer n = containers[i];
            n.setLength(length);
            n.setX(x+(width-n.getLength())/2);
            if(i > 0){
                n.setY(y+height+2*margin-n.getLength());
            } else{
                n.setY(y-margin-n.getLength());
            }
            n.paintComponent(g);
        }
        
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
     */
    
    @Override
    public void setLocation(int x, int y){
        this.x = x; 
        this.y = y;
        //repaint();
        revalidate();
    }

    @Override
    public Point getLocation(){
        return new Point(this.x, this.y);
    }
}
