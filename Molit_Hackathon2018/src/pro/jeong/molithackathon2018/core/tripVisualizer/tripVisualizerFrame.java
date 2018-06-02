package pro.jeong.molithackathon2018.core.tripVisualizer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class tripVisualizerFrame extends JFrame {
    tripVisualizerPanel panel = null;

    tripVisualizerFrame(tripVisualizerPanel panel) {
        this.panel = panel;
        setSize(new Dimension(1000, 1000));
        setLayout(new BorderLayout());


        add(panel, BorderLayout.CENTER);
        BufferedImage image = getScreenShot(panel); // Takes a screen shot.
        saveImageToFile(image);
        System.out.println(image.getWidth() + "   " + image.getHeight());

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static void saveImageToFile(BufferedImage image) {
        File outputFile = new File(new Date().getTime() + "_Img.jpeg");
        try {
            ImageIO.write(image, "jpeg", outputFile);
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    private static BufferedImage getScreenShot(Component component) {
        BufferedImage image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);
        // paints into image's Graphics
        image.getGraphics().drawImage(image, 0, 0, Color.BLACK, null);
        component.paint(image.getGraphics());
        return image;
    }
}
