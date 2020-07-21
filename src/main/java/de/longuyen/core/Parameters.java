package de.longuyen.core;

import lombok.Data;

@Data
public class Parameters {
    private int interpolation;
    private int windowSize;
    public Parameters(final int interpolation, final int windowSize) {
        this.interpolation = interpolation;
        this.windowSize = windowSize;
    }
}
