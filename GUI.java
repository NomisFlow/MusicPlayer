import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame 
{
    private MusicPlayer m;
    private JButton play;
    private JButton pause;
    private JButton close;
    private JProgressBar percentage;

    private JTextField browser;
    private JButton open;

    public static void main(String[] args){
        GUI g = new GUI();

    }

    public GUI(){
        initComponents();
        countUp();

    }
    
    private void initComponents(){
        m = new MusicPlayer();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setVisible(true);

        play = new JButton("Play");
        pause = new JButton("Pause");
        close = new JButton("Close");
        percentage = new JProgressBar(0, 100);
        browser = new JTextField("song.mp3");
        open = new JButton("open");
        percentage.setValue(0);
        percentage.setString("0");
        percentage.setStringPainted(true);

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

        pack();
        setSize(750, 80);
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

    private void countUp(){
        for(int i = percentage.getValue(); i <= 100; i = m.getPositionInPercent()){
            percentage.setValue(m.getPositionInPercent());
            percentage.setString("Fortschritt: " + m.getPositionInPercent() + "%");
        }

    }

    // Every method of MouseListener except for MouseClicked()
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
