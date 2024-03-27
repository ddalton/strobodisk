import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.Stroke;
import java.io.File;
import java.io.IOException;
import java.lang.Math;

import javax.imageio.ImageIO;

public class Strobodisk {
  private static final Stroke BASIC_STROKE = new BasicStroke(1f);
  private static final int MARGIN = 150;
  private static final int DIAMETER = 4096;
  private static final int WIDTH = DIAMETER+(MARGIN*2);
  private static final int HEIGHT = WIDTH;
  private static final int NUM_OCTAVES = 8;
  private static final int INNER_DIAMETER = 764;
  private static final int BAR_HEIGHT = ((DIAMETER-INNER_DIAMETER)/NUM_OCTAVES)/2;
  private static final int CENTER_X = WIDTH/2;
  private static final int CENTER_Y = HEIGHT/2;
  private static final Color TRANSPARENT = new Color(0, true);

  static private void drawOctave(Graphics2D ig2, int n) {
      int num_segments = (int) Math.round(Math.pow(2, n));
      int c = NUM_OCTAVES + 1 - n;
      int d = DIAMETER-(c*BAR_HEIGHT*2);
      int m = MARGIN + c*BAR_HEIGHT;

      ig2.setColor(Color.black);
      ig2.setComposite(AlphaComposite.SrcOver);
      double sector_angle = 360.0/num_segments;
      for(int i = 0; i < num_segments; i+=2) {
	      double startAngle = i * sector_angle;
	      ig2.fill(new Arc2D.Double(m, m, d, d, startAngle, sector_angle, Arc2D.PIE));
      }
      ig2.setColor(TRANSPARENT);
      ig2.setComposite(AlphaComposite.Src);
      ig2.fillArc(m+BAR_HEIGHT, m+BAR_HEIGHT, d-(BAR_HEIGHT*2), d-(BAR_HEIGHT*2), 0, 360);
  }

  static public void main(String args[]) throws Exception {
    try {
      BufferedImage bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
      Graphics2D ig2 = bi.createGraphics();

      ig2.setStroke(BASIC_STROKE);
      ig2.setColor(Color.black);

      for(int i = 9; i > 1; i--) {
        drawOctave(ig2, i);
      }

      ImageIO.write(bi, "PNG", new File("Strobodisk.png"));
    } catch (IOException ie) {
      ie.printStackTrace();
    }

  }
}
