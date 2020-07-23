package de.longuyen.core.voronoi;

import de.longuyen.core.Transformer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.*;
import java.util.List;

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
        ColorModel cm = bufferedImage.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bufferedImage.copyData(null);
        BufferedImage returnValue = new BufferedImage(cm, raster, isAlphaPremultiplied, null);

        Random random = new Random();
        Set<Vector2D> points = new HashSet<>();
        for(int i = 0; i < parameters.points; i++){
            int x = random.nextInt(bufferedImage.getWidth());
            int y = random.nextInt(bufferedImage.getHeight());
            points.add(new Vector2D(x, y));
        }
        points.add(new Vector2D(0, 0));
        points.add(new Vector2D(0, bufferedImage.getHeight()));
        points.add(new Vector2D(bufferedImage.getWidth(), 0));
        points.add(new Vector2D(bufferedImage.getWidth(), bufferedImage.getHeight()));
        List<Triangle2D> triangles = new VoronoiTriangulator().triangulate(new ArrayList<>(points));

        Map<Triangle2D, List<Integer>> trianglesPixelsMap = new HashMap<>();
        for(Triangle2D triangle2D : triangles){
            trianglesPixelsMap.put(triangle2D, new ArrayList<>());
        }

        for(int y = 0; y < bufferedImage.getHeight(); y++){
            for(int x = 0; x < bufferedImage.getWidth(); x++){
                Vector2D currentPixel = new Vector2D(x, y);
                for(final Triangle2D triangle2D : triangles){
                    if(triangle2D.contains(currentPixel)){
                        trianglesPixelsMap.get(triangle2D).add(bufferedImage.getRGB(x, y));
                        break;
                    }
                }
            }
        }

        Map<Triangle2D, Color> trianglesColorMap = new HashMap<>();
        for(Map.Entry<Triangle2D, List<Integer>> entry: trianglesPixelsMap.entrySet()){
            double redAverage = 0.f;
            double greenAverage = 0.f;
            double blueAverage = 0.f;
            for(Integer color : entry.getValue()) {
                int blue = color & 0xff;
                int green = (color & 0xff00) >> 8;
                int red = (color & 0xff0000) >> 16;
                redAverage += red;
                greenAverage += green;
                blueAverage += blue;
            }
            redAverage /= entry.getValue().size();
            greenAverage /= entry.getValue().size();
            blueAverage /= entry.getValue().size();

            assert redAverage < 256 && greenAverage < 256 && blueAverage < 256 : "" + redAverage + ", " + greenAverage + ", " + blueAverage + ", " + entry.getValue().size() + ", " + entry.getKey().toString();
            trianglesColorMap.put(entry.getKey(), new Color((int)redAverage, (int)greenAverage, (int)blueAverage));
        }

        TriangleCollection triangleCollection = new TriangleCollection(triangles);
        for(int y = 0; y < returnValue.getHeight(); y++) {
            for (int x = 0; x < returnValue.getWidth(); x++) {
                boolean dangling = true;
                Vector2D currentPixel = new Vector2D(x, y);
                for (Map.Entry<Triangle2D, Color> entry : trianglesColorMap.entrySet()) {
                    if (entry.getKey().contains(currentPixel)) {
                        returnValue.setRGB(x, y, entry.getValue().getRGB());
                        dangling = false;
                        break;
                    }
                }
                if (dangling){
                    Triangle2D nearestTriangle = triangleCollection.findNearestTriangle(currentPixel);
                    returnValue.setRGB(x, y, trianglesColorMap.get(nearestTriangle).getRGB());
                }
            }
        }
        return returnValue;
    }
}
