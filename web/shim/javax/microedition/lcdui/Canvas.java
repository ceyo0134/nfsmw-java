package javax.microedition.lcdui;
import java.awt.image.BufferedImage;
public abstract class Canvas extends Displayable {
  public static final int KEY_POUND = 35, KEY_STAR = 42, KEY_NUM0 = 48, KEY_NUM1 = 49, KEY_NUM2 = 50, KEY_NUM3 = 51,
    KEY_NUM4 = 52, KEY_NUM5 = 53, KEY_NUM6 = 54, KEY_NUM7 = 55, KEY_NUM8 = 56, KEY_NUM9 = 57;
  public static final int KEY_UP = -1, KEY_DOWN = -2, KEY_LEFT = -3, KEY_RIGHT = -4, KEY_FIRE = -5,
    KEY_SOFT_LEFT = -6, KEY_SOFT_RIGHT = -7, KEY_CLEAR = -8, KEY_SEND = -10, KEY_END = -11;
  public static final int UP = 1, LEFT = 2, RIGHT = 5, DOWN = 6, FIRE = 8, GAME_A = 9, GAME_B = 10, GAME_C = 11, GAME_D = 12;
  /** Last painted frame; the web/desktop host reads this. */
  public volatile BufferedImage frame;
  private boolean painting;
  public static int convertKeyCode(int k) { return k; }
  public static void setScale(boolean a, boolean b, int c) {}
  public static void setBackgroundColor(int c) {}
  public static void setFilterBitmap(boolean f) {}
  public static void setHasTouchInput(boolean t) {}
  public static void setGraphicsMode(int m, boolean p) {}
  public static void setForceFullscreen(boolean f) {}
  public static void setShowFps(boolean s) {}
  public static void setLimitFps(int f) {}
  public int getKeyCode(int a) { switch (a) { case UP: return KEY_UP; case DOWN: return KEY_DOWN; case LEFT: return KEY_LEFT; case RIGHT: return KEY_RIGHT; case FIRE: return KEY_FIRE; } return 0; }
  public int getGameAction(int k) { switch (k) { case KEY_UP: case KEY_NUM2: return UP; case KEY_DOWN: case KEY_NUM8: return DOWN; case KEY_LEFT: case KEY_NUM4: return LEFT; case KEY_RIGHT: case KEY_NUM6: return RIGHT; case KEY_FIRE: case KEY_NUM5: return FIRE; } return 0; }
  public String getKeyName(int k) { return String.valueOf(k); }
  public void postKeyPressed(int k) { Display.ui.execute(() -> keyPressed(k)); }
  public void postKeyReleased(int k) { Display.ui.execute(() -> keyReleased(k)); }
  public void postKeyRepeated(int k) { Display.ui.execute(() -> keyRepeated(k)); }
  public void callShowNotify() { showNotify(); }
  public void callHideNotify() { hideNotify(); }
  public void clearDisplayableView() {}
  public void setFullScreenMode(boolean f) {}
  public boolean hasPointerEvents() { return true; }
  public boolean hasPointerMotionEvents() { return true; }
  public boolean hasRepeatEvents() { return false; }
  public boolean isDoubleBuffered() { return true; }
  public final void repaint() { Display.ui.execute(this::doPaint); }
  public final void repaint(int x, int y, int w, int h) { repaint(); }
  public final void serviceRepaints() { doPaint(); }
  public boolean isShown() { return Display.current == this; }
  public void flushBuffer(Image i, int x, int y, int w, int h) {}
  public void flushBuffer(Image i, int x, int y) {}
  public void setVisible(boolean v) {}
  private void doPaint() {
    if (painting) return; painting = true;
    try {
      BufferedImage f = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
      Graphics g = new Graphics(f);
      paint(g);
      frame = f;
    } catch (Throwable t) { t.printStackTrace(); } finally { painting = false; }
  }
  protected void showNotify() {}
  protected void hideNotify() {}
  protected abstract void paint(Graphics g);
  public void keyPressed(int k) {}
  public void keyRepeated(int k) {}
  public void keyReleased(int k) {}
  public void pointerPressed(int p, float x, float y) { pointerPressed((int) x, (int) y); }
  public void pointerDragged(int p, float x, float y) { pointerDragged((int) x, (int) y); }
  public void pointerReleased(int p, float x, float y) { pointerReleased((int) x, (int) y); }
  public void pointerPressed(int x, int y) {}
  public void pointerDragged(int x, int y) {}
  public void pointerReleased(int x, int y) {}
}
