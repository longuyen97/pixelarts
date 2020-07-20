package de.longuyen.core;

import java.io.InputStream;

/**
 * Interface for transforming an image file into a ascii work art
 */
public interface Transformer {
    /**
     * Convert the content of the input stream
     *
     * @param file targeted input stream
     * @return the ascii form of the art
     */
    String convert(final InputStream file);
}
