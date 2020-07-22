package de.longuyen.core.voronoi;

import de.longuyen.core.Transformer;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SimpleVoronoi implements Transformer {
    public static class Triangle {
        private final Point2D A;
        private final Point2D B;
        private final Point2D C;

        public Triangle(Point2D a, Point2D b, Point2D c) {
            A = a;
            B = b;
            C = c;
        }
    }

    public List<Triangle> triangulate(final List<Point2D> points){
        return new ArrayList<>();
    }

    @Override
    public BufferedImage convert(BufferedImage bufferedImage) {
        return null;
    }
}
