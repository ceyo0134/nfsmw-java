import java.awt.image.BufferedImage;
import java.io.File;
import javax.microedition.lcdui.*;
/** Desktop test host: starts the MIDlet headless and dumps frames as PNG. */
public class Harness {
  public static void main(String[] a) throws Exception {
    System.setProperty("java.awt.headless", "true");
    int secs = a.length > 0 ? Integer.parseInt(a[0]) : 20;
    new File("frames").mkdirs();
    AppMain m = new AppMain();
    m.startApp();
    BufferedImage last = null; int n = 0;
    for (int t = 0; t < secs * 4; t++) {
      Thread.sleep(250);
      Displayable d = Display.getDisplay(null).getCurrent();
      if (d instanceof Canvas) {
        BufferedImage f = ((Canvas) d).frame;
        if (f != null && f != last) { last = f; javax.imageio.ImageIO.write(f, "png", new File("frames/f" + (n++) + ".png")); }
      }
    }
    System.out.println("frames written: " + n + ", current=" + Display.getDisplay(null).getCurrent());
    System.exit(0);
  }
}
