package javax.microedition.lcdui;
import java.awt.image.BufferedImage;
import java.io.*;
public class Image {
  BufferedImage img; boolean mutable; boolean bwAlpha;
  Image(BufferedImage b, boolean m) { img = b; mutable = m; }
  public static Image createTransparentImage(int w, int h) { return new Image(new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB), true); }
  public Canvas getCanvas() { return null; }
  public static Image createImage(int w, int h) {
    BufferedImage b = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    java.awt.Graphics2D g = b.createGraphics(); g.setColor(java.awt.Color.WHITE); g.fillRect(0, 0, w, h); g.dispose();
    return new Image(b, true);
  }
  public static Image createImage(String res) throws IOException {
    InputStream in = Image.class.getResourceAsStream(res.startsWith("/") ? res : "/" + res);
    if (in == null) throw new IOException(res); return createImage(in);
  }
  public static Image createImage(InputStream s) throws IOException {
    BufferedImage b = javax.imageio.ImageIO.read(s); if (b == null) throw new IOException("bad image");
    BufferedImage c = new BufferedImage(b.getWidth(), b.getHeight(), BufferedImage.TYPE_INT_ARGB);
    java.awt.Graphics2D g = c.createGraphics(); g.drawImage(b, 0, 0, null); g.dispose(); return new Image(c, false);
  }
  public static Image createImage(byte[] d, int o, int l) {
    try { return createImage(new ByteArrayInputStream(d, o, l)); } catch (IOException e) { throw new IllegalArgumentException(e.toString()); }
  }
  public static Image createImage(Image src, int x, int y, int w, int h, int t) {
    BufferedImage b = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    java.awt.Graphics2D g = b.createGraphics(); g.drawImage(src.img.getSubimage(x, y, w, h), 0, 0, null); g.dispose();
    return new Image(Graphics.transform(b, t), false);
  }
  public static Image createImage(Image s) {
    BufferedImage b = new BufferedImage(s.getWidth(), s.getHeight(), BufferedImage.TYPE_INT_ARGB);
    java.awt.Graphics2D g = b.createGraphics(); g.drawImage(s.img, 0, 0, null); g.dispose(); return new Image(b, false);
  }
  public static Image createRGBImage(int[] rgb, int w, int h, boolean alpha) {
    BufferedImage b = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    int[] p = new int[w * h]; for (int i = 0; i < p.length; i++) p[i] = alpha ? rgb[i] : (rgb[i] | 0xFF000000);
    b.setRGB(0, 0, w, h, p, 0, w); return new Image(b, false);
  }
  public Graphics getGraphics() { return new Graphics(img); }
  public boolean isMutable() { return mutable; }
  public int getWidth() { return img.getWidth(); } public int getHeight() { return img.getHeight(); }
  public void getRGB(int[] out, int off, int scan, int x, int y, int w, int h) { img.getRGB(x, y, w, h, out, off, scan); }
  public Graphics getSingleGraphics() { return getGraphics(); }
  public boolean isBlackWhiteAlpha() { return bwAlpha; }
  public void setBlackWhiteAlpha(boolean b) { bwAlpha = b; }
}
