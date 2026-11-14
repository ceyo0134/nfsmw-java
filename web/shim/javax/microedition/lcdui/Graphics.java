package javax.microedition.lcdui;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
public class Graphics {
  public static final int HCENTER = 1, VCENTER = 2, LEFT = 4, RIGHT = 8, TOP = 16, BOTTOM = 32, BASELINE = 64;
  public static final int SOLID = 0, DOTTED = 1;
  final BufferedImage target; final Graphics2D g; int color = 0xFF000000, stroke, tx, ty;
  int cx, cy, cw, ch; javax.microedition.lcdui.Font font = javax.microedition.lcdui.Font.getDefaultFont();
  public Graphics(BufferedImage b) {
    target = b; g = b.createGraphics(); cw = b.getWidth(); ch = b.getHeight(); g.setFont(font.awt);
    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
  }
  public void reset() { g.setClip(null); tx = ty = 0; cx = cy = 0; cw = target.getWidth(); ch = target.getHeight(); g.setTransform(new AffineTransform()); }
  public Canvas getCanvas() { return null; }
  public void fillPolygon(int[] xs, int xo, int[] ys, int yo, int n) { int[] a = new int[n], b = new int[n]; System.arraycopy(xs, xo, a, 0, n); System.arraycopy(ys, yo, b, 0, n); g.fillPolygon(a, b, n); }
  public void drawPolygon(int[] xs, int xo, int[] ys, int yo, int n) { int[] a = new int[n], b = new int[n]; System.arraycopy(xs, xo, a, 0, n); System.arraycopy(ys, yo, b, 0, n); g.drawPolygon(a, b, n); }
  private void apply() { g.setColor(new Color(color, true)); }
  public void setColor(int c) { color = 0xFF000000 | c; apply(); }
  public void setColorAlpha(int c) { color = c; apply(); }
  public void setColor(int r, int gr, int b) { setColor((r << 16) | (gr << 8) | b); }
  public void setGrayScale(int v) { setColor(v, v, v); }
  public int getGrayScale() { return (getRedComponent() + getGreenComponent() + getBlueComponent()) / 3; }
  public int getRedComponent() { return (color >> 16) & 255; } public int getGreenComponent() { return (color >> 8) & 255; } public int getBlueComponent() { return color & 255; }
  public int getColor() { return color & 0xFFFFFF; }
  public int getDisplayColor(int c) { return c; }
  public void setStrokeStyle(int s) { stroke = s; g.setStroke(s == DOTTED ? new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, new float[]{1f, 2f}, 0f) : new BasicStroke(1f)); }
  public int getStrokeStyle() { return stroke; }
  public void setFont(javax.microedition.lcdui.Font f) { font = f; g.setFont(f.awt); }
  public javax.microedition.lcdui.Font getFont() { return font; }
  public void setClip(int x, int y, int w, int h) { cx = x; cy = y; cw = w; ch = h; g.setClip(x, y, w, h); }
  public void clipRect(int x, int y, int w, int h) { g.clipRect(x, y, w, h); Rectangle r = g.getClipBounds(); if (r != null) { cx = r.x; cy = r.y; cw = r.width; ch = r.height; } }
  public int getClipX() { return cx; } public int getClipY() { return cy; } public int getClipWidth() { return cw; } public int getClipHeight() { return ch; }
  public void translate(int dx, int dy) { tx += dx; ty += dy; g.translate(dx, dy); cx -= dx; cy -= dy; }
  public int getTranslateX() { return tx; } public int getTranslateY() { return ty; }
  public void drawLine(int a, int b, int c, int d) { g.drawLine(a, b, c, d); }
  public void drawArc(int x, int y, int w, int h, int s, int a) { g.drawArc(x, y, w, h, s, a); }
  public void fillArc(int x, int y, int w, int h, int s, int a) { g.fillArc(x, y, w, h, s, a); }
  public void drawRect(int x, int y, int w, int h) { g.drawRect(x, y, w, h); }
  public void fillRect(int x, int y, int w, int h) { g.fillRect(x, y, w, h); }
  public void drawRoundRect(int x, int y, int w, int h, int aw, int ah) { g.drawRoundRect(x, y, w, h, aw, ah); }
  public void fillRoundRect(int x, int y, int w, int h, int aw, int ah) { g.fillRoundRect(x, y, w, h, aw, ah); }
  public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) { g.fillPolygon(new int[]{x1, x2, x3}, new int[]{y1, y2, y3}, 3); }
  public void drawChar(char c, int x, int y, int a) { drawString(String.valueOf(c), x, y, a); }
  public void drawChars(char[] d, int o, int l, int x, int y, int a) { drawString(new String(d, o, l), x, y, a); }
  public void drawString(String t, int x, int y, int a) {
    java.awt.FontMetrics fm = font.fm; int w = fm.stringWidth(t);
    if ((a & HCENTER) != 0) x -= w / 2; else if ((a & RIGHT) != 0) x -= w;
    int base;
    if ((a & BOTTOM) != 0) base = y - fm.getDescent(); else if ((a & BASELINE) != 0) base = y; else base = y + fm.getAscent();
    g.drawString(t, x, base);
  }
  public void drawSubstring(String s, int o, int l, int x, int y, int a) { drawString(s.substring(o, o + l), x, y, a); }
  private int[] anchorXY(int x, int y, int w, int h, int a) {
    if ((a & HCENTER) != 0) x -= w / 2; else if ((a & RIGHT) != 0) x -= w;
    if ((a & VCENTER) != 0) y -= h / 2; else if ((a & BOTTOM) != 0) y -= h;
    return new int[]{x, y};
  }
  public void drawImage(Image im, int x, int y, int a) { int[] p = anchorXY(x, y, im.getWidth(), im.getHeight(), a); g.drawImage(im.img, p[0], p[1], null); }
  static BufferedImage transform(BufferedImage s, int t) {
    if (t == 0) return s; int w = s.getWidth(), h = s.getHeight(); boolean sw = t >= 4;
    BufferedImage o = new BufferedImage(sw ? h : w, sw ? w : h, BufferedImage.TYPE_INT_ARGB);
    for (int y = 0; y < h; y++) for (int x = 0; x < w; x++) {
      int nx, ny;
      switch (t) {
        case 1: nx = w - 1 - x; ny = y; break;          // mirror
        case 2: nx = x; ny = h - 1 - y; break;          // mirror rot180
        case 3: nx = w - 1 - x; ny = h - 1 - y; break;  // rot180
        case 4: nx = y; ny = x; break;                  // mirror rot270
        case 5: nx = h - 1 - y; ny = x; break;          // rot90
        case 6: nx = y; ny = w - 1 - x; break;          // rot270
        default: nx = h - 1 - y; ny = w - 1 - x; break; // mirror rot90
      }
      o.setRGB(nx, ny, s.getRGB(x, y));
    }
    return o;
  }
  public void drawRegion(Image im, int sx, int sy, int w, int h, int t, int dx, int dy, int a) {
    BufferedImage r = transform(im.img.getSubimage(sx, sy, w, h), t);
    int[] p = anchorXY(dx, dy, r.getWidth(), r.getHeight(), a); g.drawImage(r, p[0], p[1], null);
  }
  public void drawRGB(int[] rgb, int off, int scan, int x, int y, int w, int h, boolean alpha) {
    BufferedImage b = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    int[] p = new int[w * h];
    for (int j = 0; j < h; j++) for (int i = 0; i < w; i++) { int v = rgb[off + j * scan + i]; p[j * w + i] = alpha ? v : (v | 0xFF000000); }
    b.setRGB(0, 0, w, h, p, 0, w); g.drawImage(b, x, y, null);
  }
  public void copyArea(int xs, int ys, int w, int h, int xd, int yd, int a) {
    int[] p = anchorXY(xd, yd, w, h, a); g.copyArea(xs, ys, w, h, p[0] - xs, p[1] - ys);
  }
  public void getPixels(int[] px, int off, int stride, int x, int y, int w, int h) { target.getRGB(x + tx, y + ty, w, h, px, off, stride); }
}
