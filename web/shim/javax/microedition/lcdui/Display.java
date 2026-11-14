package javax.microedition.lcdui;
import javax.microedition.midlet.MIDlet;
import java.util.concurrent.*;
public class Display {
  public static final int LIST_ELEMENT = 1, CHOICE_GROUP_ELEMENT = 2, ALERT = 3;
  public static final int COLOR_BACKGROUND = 0, COLOR_FOREGROUND = 1, COLOR_HIGHLIGHTED_BACKGROUND = 2,
    COLOR_HIGHLIGHTED_FOREGROUND = 3, COLOR_BORDER = 4, COLOR_HIGHLIGHTED_BORDER = 5;
  static Display inst = new Display();
  static volatile Displayable current;
  /** Single UI thread: callSerially + repaint run here, like a real MIDP event thread. */
  public static final ExecutorService ui = Executors.newSingleThreadExecutor(r -> { Thread t = new Thread(r, "midp-ui"); t.setDaemon(true); return t; });
  public static Display getDisplay(MIDlet m) { return inst; }
  public static void initDisplay() {}
  public void setCurrent(Displayable d) {
    Displayable old = current; current = d;
    if (old instanceof Canvas && old != d) ((Canvas) old).callHideNotify();
    if (d instanceof Canvas) { ((Canvas) d).callShowNotify(); ((Canvas) d).repaint(); }
  }
  public Displayable getCurrent() { return current; }
  public void callSerially(Runnable r) { ui.execute(r); }
  public boolean flashBacklight(int d) { return false; }
  public boolean vibrate(int d) { return false; }
  public int numAlphaLevels() { return 256; }
  public int numColors() { return 1 << 24; }
  public int getBestImageHeight(int t) { return 0; }
  public int getBestImageWidth(int t) { return 0; }
  public int getBorderStyle(boolean h) { return 0; }
  public int getColor(int c) { return c == COLOR_BACKGROUND ? 0xFFFFFF : 0; }
  public boolean isColor() { return true; }
}
