package de.longuyen;

import java.io.File;
import java.io.InputStream;

public interface Transformer {
    String convert(final InputStream file);
}
