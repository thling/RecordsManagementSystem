/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team8;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 * Misc functions
 * 
 * @author sam
 */
public class Util {
    /**
     * Sets the parameter frame in the middle of the screen
     * @param frame The JFrame object to be set in the middle of screen
     */
    public static void setFrameInMiddle(JFrame frame) {
        int height = frame.getHeight();
        int width = frame.getWidth();
        
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int scnHeight = screenDimension.height;
        int scnWidth = screenDimension.width;
        
        frame.setLocation(
                scnWidth / 2 - width / 2,
                scnHeight / 2 - height / 2
        );
    }
}
