import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/** Browser host (runs under CheerpJ): shows the MIDlet canvas in an AWT window and forwards keys. */
public class WebMain {
  static int map(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_UP: return javax.microedition.lcdui.Canvas.KEY_UP;
      case KeyEvent.VK_DOWN: return javax.microedition.lcdui.Canvas.KEY_DOWN;
      case KeyEvent.VK_LEFT: return javax.microedition.lcdui.Canvas.KEY_LEFT;
      case KeyEvent.VK_RIGHT: return javax.microedition.lcdui.Canvas.KEY_RIGHT;
      case KeyEvent.VK_ENTER: case KeyEvent.VK_SPACE: return javax.microedition.lcdui.Canvas.KEY_FIRE;
      case KeyEvent.VK_F1: return javax.microedition.lcdui.Canvas.KEY_SOFT_LEFT;
      case KeyEvent.VK_F2: case KeyEvent.VK_ESCAPE: return javax.microedition.lcdui.Canvas.KEY_SOFT_RIGHT;
    }
    char c = e.getKeyChar();
    if ((c >= 48 && c <= 57) || c == 42 || c == 35) return c;
    return 0;
  }

  static javax.microedition.lcdui.Canvas current() {
    javax.microedition.lcdui.Displayable d = javax.microedition.lcdui.Display.getDisplay(null).getCurrent();
    return d instanceof javax.microedition.lcdui.Canvas ? (javax.microedition.lcdui.Canvas) d : null;
  }

  public static void main(String[] args) throws Exception {
    final AppMain midlet = new AppMain();
    midlet.startApp();
    final Frame frame = new Frame("NFS Most Wanted");
    final java.awt.Canvas view = new java.awt.Canvas() {
      public void paint(Graphics g) {
        javax.microedition.lcdui.Canvas c = current();
        if (c != null && c.frame != null) g.drawImage(c.frame, 0, 0, getWidth(), getHeight(), null);
      }
      public void update(Graphics g) { paint(g); }
    };
    view.setSize(240, 320);
    view.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) { post(e, true); }
      public void keyReleased(KeyEvent e) { post(e, false); }
      void post(KeyEvent e, boolean down) {
        int k = map(e);
        javax.microedition.lcdui.Canvas c = current();
        if (k == 0 || c == null) return;
        if (down) c.postKeyPressed(k); else c.postKeyReleased(k);
      }
    });
    frame.add(view);
    frame.pack();
    frame.setVisible(true);
    view.requestFocus();
    while (true) { view.repaint(); Thread.sleep(33); }
  }
}
