package de.longuyen.image;

import java.awt.image.BufferedImage;
import java.io.InputStream;

public interface ImageTransformer {
    BufferedImage convert(final InputStream inputStream);
}
