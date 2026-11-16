
package ui;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/** Loads food photos and paints them to fill the menu-card image area. */
public final class ImageLoader {

    private static final Color PLACEHOLDER_BACKGROUND =
            new Color(248, 249, 251);

    private static final Color PLACEHOLDER_FOREGROUND =
            new Color(107, 114, 128);

    private static final Color BORDER_COLOR =
            new Color(230, 233, 238);

    private ImageLoader() {
        // Utility class.
    }

    public static JLabel createFoodImage(
            String fileName, int width, int height) {

        BufferedImage image = loadImage(fileName);

        JLabel label = new JLabel(
                image == null ? missingImageText(fileName) : "",
                SwingConstants.CENTER) {

            @Override
            protected void paintComponent(Graphics graphics) {

                super.paintComponent(graphics);

                if (image != null
                        && getWidth() > 0
                        && getHeight() > 0) {

                    Graphics2D g2 =
                            (Graphics2D) graphics.create();

                    try {
                        g2.setRenderingHint(
                                RenderingHints.KEY_INTERPOLATION,
                                RenderingHints.VALUE_INTERPOLATION_BICUBIC);

                        g2.setRenderingHint(
                                RenderingHints.KEY_RENDERING,
                                RenderingHints.VALUE_RENDER_QUALITY);

                        g2.setRenderingHint(
                                RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);

                        g2.setRenderingHint(
                                RenderingHints.KEY_ALPHA_INTERPOLATION,
                                RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

                        // Fill the complete image area while preserving
                        // the original image proportions.
                        double scale = Math.max(
                                (double) getWidth() / image.getWidth(),
                                (double) getHeight() / image.getHeight());

                        int scaledWidth =
                                (int) Math.round(image.getWidth() * scale);

                        int scaledHeight =
                                (int) Math.round(image.getHeight() * scale);

                        int x = (getWidth() - scaledWidth) / 2;
                        int y = (getHeight() - scaledHeight) / 2;

                        g2.drawImage(
                                image,
                                x,
                                y,
                                scaledWidth,
                                scaledHeight,
                                null);

                    } finally {
                        g2.dispose();
                    }
                }
            }
        };

        label.setOpaque(true);
        label.setBackground(PLACEHOLDER_BACKGROUND);
        label.setForeground(PLACEHOLDER_FOREGROUND);
        label.setFont(new Font("SansSerif", Font.PLAIN, 13));

        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        label.setPreferredSize(new Dimension(width, height));
        label.setMinimumSize(new Dimension(120, 150));

        label.setBorder(
                BorderFactory.createLineBorder(BORDER_COLOR));

        label.setToolTipText(
                image == null
                        ? "Add " + fileName + " to the assets folder"
                        : fileName);

        return label;
    }

    private static String missingImageText(String fileName) {

        return "<html><center>Food photo not found<br><br>"
                + "Place <b>" + escapeHtml(fileName)
                + "</b> inside the <b>assets</b> folder"
                + "</center></html>";
    }

    private static BufferedImage loadImage(String fileName) {

        Path path = Paths.get("assets", fileName);

        if (Files.isRegularFile(path)) {

            try {
                BufferedImage image = ImageIO.read(path.toFile());

                if (image != null) {
                    return image;
                }

                System.err.println(
                        "Unsupported or invalid image: " + path);

            } catch (IOException exception) {

                System.err.println(
                        "Could not read image " + path
                                + ": " + exception.getMessage());
            }
        }

        // Also support images packaged in the application's classpath.
        try (InputStream stream =
                     ImageLoader.class.getResourceAsStream(
                             "/assets/" + fileName)) {

            if (stream != null) {
                return ImageIO.read(stream);
            }

        } catch (IOException exception) {

            System.err.println(
                    "Could not read bundled image "
                            + fileName + ": "
                            + exception.getMessage());
        }

        return null;
    }

    private static String escapeHtml(String value) {

        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}
