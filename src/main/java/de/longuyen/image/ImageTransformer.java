package de.longuyen.image;

import java.awt.image.BufferedImage;

public interface ImageTransformer {
    BufferedImage convert(final BufferedImage bufferedImage);
}
