package javax.microedition.lcdui;
import java.awt.image.BufferedImage;
public class Font {
  public static final int FACE_MONOSPACE = 32, FACE_PROPORTIONAL = 64, FACE_SYSTEM = 0;
  public static final int SIZE_LARGE = 16, SIZE_MEDIUM = 0, SIZE_SMALL = 8;
  public static final int STYLE_BOLD = 1, STYLE_ITALIC = 2, STYLE_PLAIN = 0, STYLE_UNDERLINED = 4;
  static final BufferedImage dummy = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
  final int face, style, size; final java.awt.Font awt; final java.awt.FontMetrics fm;
  private Font(int f, int st, int sz) {
    face = f; style = st; size = sz;
    int px = sz == SIZE_SMALL ? 10 : sz == SIZE_LARGE ? 16 : 13;
    String fam = f == FACE_MONOSPACE ? java.awt.Font.MONOSPACED : java.awt.Font.SANS_SERIF;
    awt = new java.awt.Font(fam, ((st & 1) != 0 ? java.awt.Font.BOLD : 0) | ((st & 2) != 0 ? java.awt.Font.ITALIC : 0), px);
    java.awt.Graphics2D g = dummy.createGraphics(); fm = g.getFontMetrics(awt); g.dispose();
  }
  public static void setApplyDimensions(boolean b) {}
  public static void setSize(int s, float v) {}
  public static Font getFont(int spec) { return new Font(0, 0, 0); }
  public static Font getFont(int face, int style, int size) { return new Font(face, style, size); }
  public static Font getDefaultFont() { return new Font(0, 0, 0); }
  public boolean isSmall() { return size == SIZE_SMALL; }
  public int getFace() { return face; } public int getStyle() { return style; } public int getSize() { return size; }
  public boolean isUnderlined() { return (style & 4) != 0; }
  public int getHeight() { return fm.getHeight(); }
  public int getBaselinePosition() { return fm.getAscent(); }
  public int charWidth(char c) { return fm.charWidth(c); }
  public int charsWidth(char[] ch, int o, int l) { return fm.charsWidth(ch, o, l); }
  public int stringWidth(String t) { return fm.stringWidth(t); }
  public int substringWidth(String s, int o, int l) { return fm.stringWidth(s.substring(o, o + l)); }
  public boolean isBold() { return (style & 1) != 0; } public boolean isPlain() { return style == 0; } public boolean isItalic() { return (style & 2) != 0; }
}
