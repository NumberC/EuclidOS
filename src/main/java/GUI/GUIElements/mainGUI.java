package GUI.GUIElements;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;

import java.util.ArrayList;
import java.util.List;

public class mainGUI extends JPanel{
    int margin = 5;
    List<GUIElementInterface> contents = new ArrayList<GUIElementInterface>();
    Cursor cursor = new Cursor();
    int x = 5;
    int y = 50;
    int contentsIndex = contents.size()-1;

    void keyPressed(KeyEvent e){
        print(e.getKeyCode());
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                contentsIndex = (contentsIndex < contents.size()-1) ? ++contentsIndex:contentsIndex;
                break;
            
            case KeyEvent.VK_LEFT:
                contentsIndex = (contentsIndex > 0) ? --contentsIndex:contentsIndex;
                break;
            
            case KeyEvent.VK_SLASH:
                Fractions f = new Fractions();
                //f.setLocation(100, 100);
                add(f);
                break;
            
            case KeyEvent.VK_N:
                NumberContainer n = new NumberContainer();
                n.setLocation(50, 55);
                add(n);
                break;
            
            
            case KeyEvent.VK_BACK_SPACE:
                System.out.println(this.getComponentCount());
                break;
            
            default:
                Text t = new Text(e.getKeyChar());
                add(t);
                revalidate();
                break;
        }
    }
    
    public static void main(String[] args){
        mainGUI testPane = new mainGUI();
        testPane.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent arg0) {
                print(arg0.getKeyCode());
                testPane.keyPressed(arg0);
            }

            @Override
            public void keyReleased(KeyEvent arg0) {}

            @Override
            public void keyTyped(KeyEvent arg0) {}
        });
        
        testPane.setBackground(Color.white);
        testPane.setLayout(null);
        NumberContainer n = new NumberContainer();
        NumberContainer n2 = new NumberContainer();

        n.setLocation(150, 50);
        n2.setLocation(440, 10);
        n2.setSize(12, 12);
        n2.setDashLength(1);

        testPane.add(n);
        testPane.add(n2);
        JLabel h = new JLabel("what");
        h.setBounds(new Rectangle(new Point(0, 0), h.getPreferredSize()));
        testPane.add(h);

        System.out.println(testPane.getComponentCount());

        JFrame frame = new JFrame("Testing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(testPane);
        frame.setSize(500, 300); 
        frame.setVisible(true);
        testPane.requestFocus();
        
    }
    
    static void print(Object p){
       System.out.println(p);
    }
}