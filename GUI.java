import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame 
{
    MusicPlayer m;
    JButton play;
    JButton pause;
    JButton close;
    JLabel percentage;
    
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
        percentage = new JLabel("Fortschritt: __%");

        browser = new JTextField("song.mp3");
        open = new JButton("open");

        close.addActionListener(e -> beenden());
        open.addActionListener(e -> open());
        browser.addMouseListener((ClickedListener)(e -> browser.setText("")));
        pause.addActionListener(e -> m.pause());
        play.addActionListener(e -> play());
        

        add(play);
        add(pause);
        add(percentage);
        add(browser);
        add(open);
        add(close);
        
        while(m.getPositionInPercent() <= 99){
            percentage.setText("Fortschritt:" + m.getPositionInPercent() + " %");
            
        }
        
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
    
    private void play(){
        if(m.getPositionInPercent() == 0)
            m.play();
        else if(m.getPositionInPercent() > 0)
            m.resume();
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
