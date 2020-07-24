package de.longuyen.core.voronoi;

import de.longuyen.core.utils.Vector2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestVoronoiTriangulator {
    @Test
    void testTriangulator() throws IOException {
        List<Vector2D> points = new ArrayList<>(Short.MAX_VALUE);
        Random random = new Random();
        for(int i = 0; i < Short.MAX_VALUE; i++){
            points.add(new Vector2D(random.nextDouble(), random.nextDouble()));
        }
        VoronoiTriangulator voronoiTriangulator = new VoronoiTriangulator();
        List<Triangle2D> triangle2DS = voronoiTriangulator.triangulate(points);
        Assertions.assertFalse(triangle2DS.isEmpty());
    }
}
