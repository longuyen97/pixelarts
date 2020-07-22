package de.longuyen.core;

import java.awt.image.BufferedImage;

/**
 * Interface for transforming an image file into a ascii work art
 */
public interface Transformer {

    /**
     * Convert the content of the input stream
     *
     * @param bufferedImage targeted input stream
     * @return the ascii form of the art
     */
    BufferedImage convert(final BufferedImage bufferedImage);
}
