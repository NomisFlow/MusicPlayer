import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame 
{
    MusicPlayer m;
    JButton play;
    JButton pause;
    JButton close;

    JTextField browser;
    JButton open;

    public static void main(String[] args){
        GUI g = new GUI();
    }

    public GUI(){
        initComponents();

    }

    private void initComponents(){
        m = new MusicPlayer();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setVisible(true);

        play = new JButton("Play");
        pause = new JButton("Pause");
        close = new JButton("Close");

        browser = new JTextField("                                           ");
        open = new JButton("open");

        close.addActionListener(e -> beenden());
        open.addActionListener(e -> open());
        browser.addMouseListener(e -> browser.setText(""));

        add(play);
        add(pause);
        add(browser);
        add(open);
        add(close);

        pack();
        setSize(450, 80);
    }

    private void beenden(){
        setVisible(false);
        dispose();
        System.exit(0);
    }

    private void open(){
        m.open(browser.getText());
        m.play();
    }

    
    
    // note the absence of mouseClicked…
    interface ClickedListener extends MouseListener
    {
        @Override
        public default void mouseEntered(MouseEvent e) {}

        @Override
        public default void mouseExited(MouseEvent e) {}

        @Override
        public default void mousePressed(MouseEvent e) {}

        @Override
        public default void mouseReleased(MouseEvent e) {}
    }
}
