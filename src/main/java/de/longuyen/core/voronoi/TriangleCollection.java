package de.longuyen.core.voronoi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TriangleCollection {
    private final List<Triangle2D> triangles;

    public TriangleCollection(List<Triangle2D> triangles) {
        this.triangles = triangles;
    }

    public TriangleCollection() {
        this.triangles = new ArrayList<>();
    }

    /**
     * Adds a triangle to this triangle soup.
     *
     * @param triangle The triangle to be added to this triangle soup
     */
    public void add(Triangle2D triangle) {
        this.triangles.add(triangle);
    }

    /**
     * Removes a triangle from this triangle soup.
     *
     * @param triangle The triangle to be removed from this triangle soup
     */
    public void remove(Triangle2D triangle) {
        this.triangles.remove(triangle);
    }

    /**
     * Returns the triangle from this triangle soup that contains the specified
     * point or null if no triangle from the triangle soup contains the point.
     *
     * @param point
     *            The point
     * @return Returns the triangle from this triangle soup that contains the
     *         specified point or null
     */
    public Triangle2D findContainingTriangle(Vector2D point) {
        for (Triangle2D triangle : triangles) {
            if (triangle.contains(point)) {
                return triangle;
            }
        }
        return null;
    }

    /**
     * Returns the neighbor triangle of the specified triangle sharing the same
     * edge as specified. If no neighbor sharing the same edge exists null is
     * returned.
     *
     * @param triangle The triangle
     * @param edge The edge
     * @return The triangles neighbor triangle sharing the same edge or null if no triangle exists
     */
    public Triangle2D findNeighbour(Triangle2D triangle, Edge2D edge) {
        for (Triangle2D triangleFromSoup : triangles) {
            if (triangleFromSoup.isNeighbour(edge) && triangleFromSoup != triangle) {
                return triangleFromSoup;
            }
        }
        return null;
    }

    /**
     * Returns one of the possible triangles sharing the specified edge. Based
     * on the ordering of the triangles in this triangle soup the returned
     * triangle may differ.
     *
     * @param edge The edge
     * @return Returns one triangle that shares the specified edge
     */
    public Triangle2D findOneTriangleSharing(Edge2D edge) {
        for (Triangle2D triangle : triangles) {
            if (triangle.isNeighbour(edge)) {
                return triangle;
            }
        }
        return null;
    }

    /**
     * Returns the edge from the triangle soup nearest to the specified point.
     *
     * @param point The point
     * @return The edge from the triangle soup nearest to the specified point
     */
    public Edge2D findNearestEdge(Vector2D point) {
        List<EdgeDistancePack> edgeList = new ArrayList<>();

        for (Triangle2D triangle : triangles) {
            edgeList.add(triangle.findNearestEdge(point));
        }
        edgeList.sort(EdgeDistancePack::compareTo);
        return edgeList.get(0).edge;
    }

    public Triangle2D findNearestTriangle(Vector2D point){
        List<Pair<Triangle2D, EdgeDistancePack>> edgeList = new ArrayList<>();
        for (Triangle2D triangle : triangles) {
            edgeList.add(new Pair<>(triangle, triangle.findNearestEdge(point)));
        }
        edgeList.sort(Comparator.comparing(Pair::getRight));
        return edgeList.get(0).getLeft();
    }

    /**
     * Removes all triangles from this triangle soup that contain the specified
     * vertex.
     *
     * @param vertex
     *            The vertex
     */
    public void removeTrianglesUsing(Vector2D vertex) {
        List<Triangle2D> trianglesToBeRemoved = new ArrayList<>();

        for (Triangle2D triangle : triangles) {
            if (triangle.hasVertex(vertex)) {
                trianglesToBeRemoved.add(triangle);
            }
        }
        triangles.removeAll(trianglesToBeRemoved);
    }

    public List<Triangle2D> getTriangles() {
        return triangles;
    }

    public void legalizeEdge(Triangle2D triangle, Edge2D edge, Vector2D newVertex){
        Triangle2D neighbourTriangle = findNeighbour(triangle, edge);

        /*
         * If the triangle has a neighbor, then legalize the edge
         */
        if (neighbourTriangle != null) {
            if (neighbourTriangle.isPointInCircumcircle(newVertex)) {
                remove(triangle);
                remove(neighbourTriangle);

                Vector2D noneEdgeVertex = neighbourTriangle.getNoneEdgeVertex(edge);

                Triangle2D firstTriangle = new Triangle2D(noneEdgeVertex, edge.a, newVertex);
                Triangle2D secondTriangle = new Triangle2D(noneEdgeVertex, edge.b, newVertex);

                add(firstTriangle);
                add(secondTriangle);

                legalizeEdge(firstTriangle, new Edge2D(noneEdgeVertex, edge.a), newVertex);
                legalizeEdge(secondTriangle, new Edge2D(noneEdgeVertex, edge.b), newVertex);
            }
        }
    }
}
