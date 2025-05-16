package com.raytracer.utils;

import com.raytracer.core.ColorRGB;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageWriter {
    public static void saveImage(ColorRGB[][] pixels, String filename) {
        int width  = pixels.length;
        int height = pixels[0].length;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // Au lieu de (x,y), on écrit en (x, hauteur-1-y) sinon l'image générée est inversée
                img.setRGB(x, height - 1 - y, pixels[x][y].toInt());
            }
        }

        try {
            ImageIO.write(img, "png", new File(filename));
        } catch (IOException e) {
            System.err.println("Erreur écriture image : " + e.getMessage());
        }
    }
}