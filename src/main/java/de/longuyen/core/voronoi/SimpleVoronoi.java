package de.longuyen.core.voronoi;

import de.longuyen.core.Transformer;

import java.awt.image.BufferedImage;
import java.util.List;

public class SimpleVoronoi implements Transformer {
    public List<Triangle2D> triangulate(final List<Vector2D> points){
        TriangleCollection triangleCollection = new TriangleCollection();

        if (points == null || points.size() < 3) {
            throw new RuntimeException("Less than three points in point set.");
        }

        /*
         * In order for the in circumcircle test to not consider the vertices of
         * the super triangle we have to start out with a big triangle
         * containing the whole point set. We have to scale the super triangle
         * to be very large. Otherwise the triangulation is not convex.
         */
        double maxOfAnyCoordinate = 0.0d;

        for (Vector2D vector : points) {
            maxOfAnyCoordinate = Math.max(Math.max(vector.x, vector.y), maxOfAnyCoordinate);
        }

        maxOfAnyCoordinate *= 16.0d;

        Vector2D p1 = new Vector2D(0.0d, 3.0d * maxOfAnyCoordinate);
        Vector2D p2 = new Vector2D(3.0d * maxOfAnyCoordinate, 0.0d);
        Vector2D p3 = new Vector2D(-3.0d * maxOfAnyCoordinate, -3.0d * maxOfAnyCoordinate);

        Triangle2D superTriangle = new Triangle2D(p1, p2, p3);

        triangleCollection.add(superTriangle);

        /*
         * If no containing triangle exists, then the vertex is not
         * inside a triangle (this can also happen due to numerical
         * errors) and lies on an edge. In order to find this edge we
         * search all edges of the triangle soup and select the one
         * which is nearest to the point we try to add. This edge is
         * removed and four new edges are added.
         *
         * The vertex is inside a triangle.
         */
        for (Vector2D point : points) {
            Triangle2D triangle = triangleCollection.findContainingTriangle(point);

            if (triangle == null) {
                /*
                 * If no containing triangle exists, then the vertex is not
                 * inside a triangle (this can also happen due to numerical
                 * errors) and lies on an edge. In order to find this edge we
                 * search all edges of the triangle soup and select the one
                 * which is nearest to the point we try to add. This edge is
                 * removed and four new edges are added.
                 */
                Edge2D edge = triangleCollection.findNearestEdge(point);

                Triangle2D first = triangleCollection.findOneTriangleSharing(edge);
                Triangle2D second = triangleCollection.findNeighbour(first, edge);

                Vector2D firstNoneEdgeVertex = first.getNoneEdgeVertex(edge);
                Vector2D secondNoneEdgeVertex = second.getNoneEdgeVertex(edge);

                triangleCollection.remove(first);
                triangleCollection.remove(second);

                Triangle2D triangle1 = new Triangle2D(edge.a, firstNoneEdgeVertex, point);
                Triangle2D triangle2 = new Triangle2D(edge.b, firstNoneEdgeVertex, point);
                Triangle2D triangle3 = new Triangle2D(edge.a, secondNoneEdgeVertex, point);
                Triangle2D triangle4 = new Triangle2D(edge.b, secondNoneEdgeVertex, point);

                triangleCollection.add(triangle1);
                triangleCollection.add(triangle2);
                triangleCollection.add(triangle3);
                triangleCollection.add(triangle4);

                legalizeEdge(triangleCollection, triangle1, new Edge2D(edge.a, firstNoneEdgeVertex), point);
                legalizeEdge(triangleCollection, triangle2, new Edge2D(edge.b, firstNoneEdgeVertex), point);
                legalizeEdge(triangleCollection, triangle3, new Edge2D(edge.a, secondNoneEdgeVertex), point);
                legalizeEdge(triangleCollection, triangle4, new Edge2D(edge.b, secondNoneEdgeVertex), point);
            } else {
                /*
                 * The vertex is inside a triangle.
                 */
                Vector2D a = triangle.a;
                Vector2D b = triangle.b;
                Vector2D c = triangle.c;

                triangleCollection.remove(triangle);

                Triangle2D first = new Triangle2D(a, b, point);
                Triangle2D second = new Triangle2D(b, c, point);
                Triangle2D third = new Triangle2D(c, a, point);

                triangleCollection.add(first);
                triangleCollection.add(second);
                triangleCollection.add(third);

                legalizeEdge(triangleCollection, first, new Edge2D(a, b), point);
                legalizeEdge(triangleCollection, second, new Edge2D(b, c), point);
                legalizeEdge(triangleCollection, third, new Edge2D(c, a), point);
            }
        }

        /*
         * Remove all triangles that contain vertices of the super triangle.
         */
        triangleCollection.removeTrianglesUsing(superTriangle.a);
        triangleCollection.removeTrianglesUsing(superTriangle.b);
        triangleCollection.removeTrianglesUsing(superTriangle.c);

        return triangleCollection.getTriangles();
    }

    /**
     * This method legalizes edges by recursively flipping all illegal edges.
     *
     * @param triangle The triangle
     * @param edge The edge to be legalized
     * @param newVertex The new vertex
     */
    private void legalizeEdge(TriangleCollection triangleCollection, Triangle2D triangle, Edge2D edge, Vector2D newVertex) {
        Triangle2D neighbourTriangle = triangleCollection.findNeighbour(triangle, edge);

        /*
         * If the triangle has a neighbor, then legalize the edge
         */
        if (neighbourTriangle != null) {
            if (neighbourTriangle.isPointInCircumcircle(newVertex)) {
                triangleCollection.remove(triangle);
                triangleCollection.remove(neighbourTriangle);

                Vector2D noneEdgeVertex = neighbourTriangle.getNoneEdgeVertex(edge);

                Triangle2D firstTriangle = new Triangle2D(noneEdgeVertex, edge.a, newVertex);
                Triangle2D secondTriangle = new Triangle2D(noneEdgeVertex, edge.b, newVertex);

                triangleCollection.add(firstTriangle);
                triangleCollection.add(secondTriangle);

                legalizeEdge(triangleCollection, firstTriangle, new Edge2D(noneEdgeVertex, edge.a), newVertex);
                legalizeEdge(triangleCollection, secondTriangle, new Edge2D(noneEdgeVertex, edge.b), newVertex);
            }
        }
    }

    @Override
    public BufferedImage convert(BufferedImage bufferedImage) {
        return null;
    }
}
