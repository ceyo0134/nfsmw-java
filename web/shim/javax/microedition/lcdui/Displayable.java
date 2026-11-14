package javax.microedition.lcdui;
public abstract class Displayable {
  static int vw = 240, vh = 320;
  Ticker ticker; String title;
  public Displayable() {}
  public static void setVirtualSize(int w, int h) { vw = w; vh = h; }
  public static int getVirtualWidth() { return vw; }
  public static int getVirtualHeight() { return vh; }
  public void setTitle(String t) { title = t; }
  public String getTitle() { return title; }
  public boolean isShown() { return Display.current == this; }
  public void clearDisplayableView() {}
  public int countCommands() { return 0; }
  public int getWidth() { return vw; }
  public int getHeight() { return vh; }
  public void setTicker(Ticker t) { ticker = t; }
  public Ticker getTicker() { return ticker; }
  public void sizeChanged(int w, int h) {}
  public boolean menuItemSelected(int id) { return false; }
}
