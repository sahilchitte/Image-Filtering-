import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;


public class ImageUtils {

  // Frame to display on screen.
  private JFrame frame;
  // Tabbed panes to switch between
  private JTabbedPane tabbedPane;

 
  ImageUtils() {
    // Create a new frame to display on screen.
    frame = new JFrame("Project Images");

    // The exit application default window close operation.
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Set up TabbedPanes to switch between images.
    tabbedPane = new JTabbedPane();

    // Set the Content pane
    frame.setContentPane(tabbedPane);
  }
  
  /**
   * Loads in a 2D Color array (an image) from the specified filepath.
   * @param filepath to the image.
   * @return the 2D Color array.
   */
  public Color[][] loadImage(String filepath) {
    // Load in the image.
    BufferedImage buffImg = loadBufferedImage(filepath);
    // Convert that image to the 2D array of colors and return it.
    Color[][] colorImg = convertTo2DFromBuffered(buffImg);
    return colorImg;
  }

  /**
   * Adds a tab to the frame which displays a given image.
   * @param img the image to be displayed on the tab.
   * @param tabName the name to be given to the tab.
   */
  public void addImage(Color[][] img, String tabName) {
    // Convert the 2D Color array to BufferedImage
    BufferedImage buffImg = convertToBufferedFrom2D(img);

    // Create icon for the image itself.
    ImageIcon icon = new ImageIcon(buffImg);
    icon.getImage().flush();

    // Create icon to be displayed on the tab, scaled to 32x32.
    ImageIcon tabIcon = new ImageIcon(
        buffImg.getScaledInstance(32, 32, Image.SCALE_SMOOTH));

    // Create a label, and add the icon to it.
    JLabel label = new JLabel();
    label.setIcon(icon);

    // Add the tab to the pane.
    tabbedPane.addTab(tabName, tabIcon, label);
  }
  
  /**
   * Packs the frame, sets the preferred size, and makes it visible.
   */
  public void display() {
    // Pack the frame.
    frame.pack();

    // Sets the size of the frame to the preferred size of the images.
    frame.setMinimumSize(frame.getPreferredSize());

    // Makes the frame visible.
    frame.setVisible(true);
  }
  
  /**
   * Clone an array into a new one. This is done because if you do
   * Color[][] tmp = orig;
   * and then edit tmp, the edits persist in orig as well. Thus, need to go
   * value by value to get a true clone of an array.
   * 
   * NOTE: this is a static method, not an instance method.
   * 
   * @param orig the array to copy from.
   * @return the new array that was copied.
   */
  public static Color[][] cloneArray(Color[][] orig) {
    
    Color[][] copy = new Color[orig.length][orig[0].length];
    
    for (int i = 0; i < orig.length; i++) {
      for (int j = 0; j < orig[i].length; j++) {
        copy[i][j] = orig[i][j];
      }
    }
 
    return copy;
  }
  
  
  private static BufferedImage loadBufferedImage(String filepath) {
 
    BufferedImage img = null;

    
    try {
    
      img = ImageIO.read(new File(filepath));
    } catch (IOException e) {
      System.out.println("Could not load the image, please ensure the filepath"
          + " was properly specified.");
      e.printStackTrace();
      System.exit(1);
    }

    
    return img;
  }


  private static BufferedImage convertToBufferedFrom2D(Color[][] img) {
    // Create new BufferedImage of specified width and height
    int width = img.length;
    int height = img[0].length;
    BufferedImage bufImg = new BufferedImage(width, height, 1);

    // Set the RGB value of each pixel in the BufferedImage
    for (int x = 0; x < width; x++) {
      for(int y = 0; y < height; y++) {
        bufImg.setRGB(x,  y, img[x][y].getRGB());
      }
    }

    // Return the BufferedImage
    return bufImg;
  }

 
  private static Color[][] convertTo2DFromBuffered(BufferedImage img) {
   
    int width = img.getWidth();
    int height = img.getHeight();
    Color[][] result = new Color[width][height];

    // Iterate through the array, adding new Colors from the intRGB values.
    for (int row = 0; row < width; row++) {
      for (int col = 0; col < height; col++) {
      
        int intRGB = img.getRGB(row, col);
        int red = (intRGB >> 16)&255;
        int green = (intRGB >> 8)&255;
        int blue = intRGB&255;
        // Set the pixel to the Color.
        result[row][col] = new Color(red, green, blue);
      }
    }
    
   
    return result;
  }

}