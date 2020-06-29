package GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.Border;

import Core.FullEval;

public class MainPanel extends JPanel {
    JPanel content = new JPanel();
    JScrollPane scrollPane = new JScrollPane();

    FullEval eval = new FullEval();
    int contentIndex = 0;

    MainPanel(){
        setLayout(new BorderLayout());

        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);

        content.add(getCustomTextField());
        
        scrollPane.setViewportView(content);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);        
    }

    JTextField getCustomTextField(){
        JTextField txt = new JTextField();
        //txt.setBorder(BorderFactory.createEmptyBorder());
        txt.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK));
        int txtHeight = 50;
        txt.setPreferredSize(new Dimension(txt.getPreferredSize().width, txtHeight));
        txt.setMaximumSize(new Dimension(2000, txtHeight));

        attachUpDownBindings(txt);

        txt.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()){
                    case KeyEvent.VK_ENTER:
                        //On enter, give user answer and create new text field.
                        JLabel lbl = new JLabel();
                        lbl.setHorizontalAlignment(JLabel.RIGHT);
                        try{
                            String answer = eval.evaluate(txt.getText());
                            lbl.setText(answer);
                        } catch (Exception err){
                            lbl.setText("Error!");
                        }

                        lbl.setPreferredSize(new Dimension(250, 30));
                        attachUpDownBindings(lbl);
                        content.add(lbl);

                        JTextField newTxt = getCustomTextField();
                        content.add(newTxt);
                        revalidate();
                        scrollPane.getVerticalScrollBar().setValue(content.getSize().height);
                        newTxt.requestFocus();
                        
                        //txt.setEnabled(false);
                        break;
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
       });
       contentIndex = content.getComponentCount()-1;
       return txt;
    }

    void attachUpDownBindings(JComponent comp){
        Action upNavContent = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentIndex = (contentIndex > 0) ? --contentIndex:0;
                scrollPane.getVerticalScrollBar().setValue(content.getComponent(contentIndex).getLocation().y);
                content.getComponent(contentIndex).requestFocus();
            }
        };
        Action downNavContent = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentIndex = (contentIndex < content.getComponentCount()-1) ? ++contentIndex:content.getComponentCount()-1;
                scrollPane.getVerticalScrollBar().setValue(content.getComponent(contentIndex).getLocation().y);
                content.getComponent(contentIndex).requestFocus();
            }
        };

        comp.getInputMap().put(KeyStroke.getKeyStroke("UP"), "upNavContent");
        comp.getActionMap().put("upNavContent", upNavContent);
        comp.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "downNavContent");
        comp.getActionMap().put("downNavContent", downNavContent);
    }

    public static void main(String[] args){
        MainPanel main = new MainPanel();
        JFrame frame = new JFrame();

        frame.add(main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300); 
        frame.setVisible(true);
    }

    static void print(Object p){
        System.out.println(p);
    }
}