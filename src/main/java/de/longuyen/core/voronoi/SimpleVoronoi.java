package de.longuyen.core.voronoi;

import de.longuyen.core.Transformer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleVoronoi implements Transformer {
    private final Parameters parameters;

    public SimpleVoronoi(Parameters parameters) {
        this.parameters = parameters;
    }

    public static class Parameters {
        public final int points;

        public Parameters(int points) {
            this.points = points;
        }
    }

    @Override
    public BufferedImage convert(BufferedImage bufferedImage) {
        BufferedImage returnValue = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = returnValue.createGraphics();

        // Make the final image default white
        graphics.setColor(new Color(255, 255, 255));
        graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight() * bufferedImage.getHeight());

        Random random = new Random();
        List<Vector2D> points = new ArrayList<>();
        for(int i = 0; i < parameters.points; i++){
            int x = random.nextInt(bufferedImage.getWidth());
            int y = random.nextInt(bufferedImage.getHeight());
            points.add(new Vector2D(x, y));
        }
        List<Triangle2D> triangles = new VoronoiTriangulator().triangulate(points);

        graphics.setColor(new Color(0, 0, 0));
        for(Triangle2D triangle2D : triangles){
            graphics.drawLine((int)triangle2D.a.x, (int)triangle2D.a.y, (int)triangle2D.b.x, (int)triangle2D.b.y);
            graphics.drawLine((int)triangle2D.a.x, (int)triangle2D.a.y, (int)triangle2D.c.x, (int)triangle2D.c.y);
            graphics.drawLine((int)triangle2D.b.x, (int)triangle2D.b.y, (int)triangle2D.c.x, (int)triangle2D.c.y);
        }
        return returnValue;
    }
}
