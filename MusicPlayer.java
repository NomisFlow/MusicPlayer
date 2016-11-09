import java.io.File;
import java.util.*;
import java.util.logging.*;
import javazoom.jlgui.basicplayer.*;

/**
 * MusicPlayer bietet grundlegende Funktionalität zum Abspielen von Musikdateien.
 * 
 * Im Wesentlichen ist MusicPlayer eine Wrapper-Klasse für BasicPlayer von javazoom.com,
 * um die Benutzung dieser Klassen zu vereinfachen.
 * Es reicht sich die Dokumentation anzuschauen oder ein Objekt zu erzeugen und zu testen.
 * (Für die Doku im Editor von BlueJ von Quelltext auf Dokumentation umschalten)
 * 
 * Wer näheres wissen will, kann sich den Quelltext der Datei anschauen.
 * Wer den Code komplett versteht, kann sich in Informatik erst mal zurück lehnen ;-)
 *
 * @author Thomas Karp
 */
public class MusicPlayer {

    public static final int PAUSED = SimplePlayer.PAUSED;
    public static final int PLAYING = SimplePlayer.PLAYING;
    public static final int STOPPED = SimplePlayer.STOPPED;
    public static final int OPENED = SimplePlayer.OPENED;
    public static final int SEEKING = SimplePlayer.SEEKING;
    public static final int UNKNOWN = SimplePlayer.UNKNOWN;
    
    // Delegation-Muster, um einige Methoden zu verbergen.
    private SimplePlayer player;
    /**
     * Angemeldete Listener
     */
    private ArrayList<MusicPlayerListener> listeners;
    /**
     * Position des Audiomaterials in der Datei
     */
    private int startPos;

    /**
     * Erzeugt einen neuen MusicPlayer.
     */
    public MusicPlayer() {
        player = new SimplePlayer();
        player.addBasicPlayerListener(player);
        listeners = new ArrayList<MusicPlayerListener>();
    }

    /**
     * Gibt den aktuellen Status des MusicPlayers zurück (siehe Konstanten)
     * @return Status des MusicPlayer
     */
    public int getStatus() {
        return player.getStatus();
    }

    /**
     * Gibt prozentuale Position zurück.
     * @return Aktuelle Position in Prozent.
     */
    public int getPositionInPercent() {
        int audioLength = player.getLength() - startPos;
        int audioPos = player.getPosition() - startPos;
        double fraq = audioPos * 100 / audioLength;
        return (int) fraq;
    }

    /**
     * Öffnet eine Datei über ihren Dateinamen.
     * 
     * @param filename Name der Datei
     * @return true, falls das öffnen erfolgreich war, ansonsten false
     */
    public boolean open(String filename) {
        try {
            player.open(new File(filename));
            startPos = player.getPosition();
        } catch (BasicPlayerException ex) {
            Logger.getLogger(MusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    /**
     * Spielt den aktuellen Song ab.
     * Falls dieser schon abgespielt wird, passiert nichts.
     */
    public void play() {
        try {
            player.play();
        } catch (BasicPlayerException ex) {
            Logger.getLogger(MusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Beendet das Abspielen des aktuellen Songs.
     */
    public void stop() {
        try {
            player.stop();
        } catch (BasicPlayerException ex) {
            Logger.getLogger(MusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Pausiert das Abspielen des aktuellen Songs.
     */
    public void pause() {
        try {
            player.pause();
        } catch (BasicPlayerException ex) {
            Logger.getLogger(MusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Setzt das Abspielen nach dem Pausieren fort.
     */
    public void resume() {
        try {
            player.resume();
        } catch (BasicPlayerException ex) {
            Logger.getLogger(MusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Setzt die Lautstärke im Intervall von 0..100
     * @param gain Neue Lautstärke
     */
    public void setGain(int gain) {
        try {
            player.setGain((double) gain / 100.0);
        } catch (BasicPlayerException ex) {
            Logger.getLogger(MusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Spult den Song etwas vor.
     */
    public void fastForward() {
        fastForward(5);
    }

    /**
     * Spult den Song vor.
     * @param amount Prozentualer Sprung.
     */
    public void fastForward(int amount) {
        this.seek(this.getPositionInPercent() + amount);
    }

    /**
     * Spult den Song etwas zurück.
     */
    public void rewind() {
        rewind(5);
    }

    /**
     * Spult den Song zurück.
     * @param amount Prozentualer Sprung.
     */
    public void rewind(int amount) {
        this.seek(this.getPositionInPercent() - amount);
    }

    /**
     * Setzt die aktuelle Position des Songs.
     * @param pos Prozentualle Position
     */
    public void seek(int pos) {
        try {
            player.seek(pos * (player.getLength() / 100));
        } catch (BasicPlayerException ex) {
            Logger.getLogger(MusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Fügt neuen Listener hinzu.
     * Alle Listener werden über die Schnittstelle "MusicPlayerListener" über
     * entsprechende Ereignisse benachrichtigt.
     * @param listener Objekt, das über Veränderungen benachrichtigt werden soll.
     */
    public void addListener(MusicPlayerListener listener) {
        listeners.add(listener);
    }

    /**
     * Entfernt Listener.
     * @param listener Zu entfernender Listener
     */
    public void removeListener(MusicPlayerListener listener) {
        listeners.remove(listener);
    }

    // Als innere Klasse eine Unterklasse von BasicPlayer, um Klasse unsichtbar zu machen nach außen.
    private class SimplePlayer extends BasicPlayer implements BasicPlayerListener {

        public int getLength() {
            return super.encodedLength;
        }

        public int getPosition() {
            return super.getEncodedStreamPosition();
        }

        public void opened(Object arg0, Map arg1) {
        }

        public void progress(int arg0, long arg1, byte[] arg2, Map arg3) {
            for (MusicPlayerListener listener : listeners) {
                listener.positionChanged(getPositionInPercent());
            }
        }

        public void stateUpdated(BasicPlayerEvent arg0) {
        }

        public void setController(BasicController arg0) {
        }
    }
}
