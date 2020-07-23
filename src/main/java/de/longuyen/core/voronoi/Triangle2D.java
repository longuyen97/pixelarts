package de.longuyen.core.voronoi;

import de.longuyen.core.utils.Edge2D;
import de.longuyen.core.utils.EdgeDistancePack;
import de.longuyen.core.utils.Vector2D;

import java.util.Arrays;

/**
 * 2D triangle class implementation.
 */
public class Triangle2D {

    public Vector2D a;
    public Vector2D b;
    public Vector2D c;
    public Triangle2D(Vector2D a, Vector2D b, Vector2D c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Tests if a 2D point lies inside this 2D triangle.
     *
     * @param point The point to be tested
     * @return Returns true if the point lies inside this 2D triangle
     */
    public boolean contains(Vector2D point) {
        double pab = point.sub(a).cross(b.sub(a));
        double pbc = point.sub(b).cross(c.sub(b));
        if (!hasSameSign(pab, pbc)) {
            return false;
        }
        double pca = point.sub(c).cross(a.sub(c));
        return hasSameSign(pab, pca);
    }

    /**
     * Tests if a given point lies in the circumcircle of this triangle.
     *
     * @param point The point to be tested
     * @return Returns true if the point lies inside the circumcircle through  the three points a, b, and c of the triangle
     */
    public boolean isPointInCircumcircle(Vector2D point) {
        double a11 = a.x - point.x;
        double a21 = b.x - point.x;
        double a31 = c.x - point.x;

        double a12 = a.y - point.y;
        double a22 = b.y - point.y;
        double a32 = c.y - point.y;

        double a13 = (a.x - point.x) * (a.x - point.x) + (a.y - point.y) * (a.y - point.y);
        double a23 = (b.x - point.x) * (b.x - point.x) + (b.y - point.y) * (b.y - point.y);
        double a33 = (c.x - point.x) * (c.x - point.x) + (c.y - point.y) * (c.y - point.y);

        double det = a11 * a22 * a33 + a12 * a23 * a31 + a13 * a21 * a32 - a13 * a22 * a31 - a12 * a21 * a33
                - a11 * a23 * a32;

        if (isClockWiseOriented()) {
            return det > 0.0d;
        }

        return det < 0.0d;
    }

    /**
     * Test if this triangle is oriented counterclockwise (CCW).
     *
     * @return Returns true if the triangle ABC is oriented counterclockwise
     */
    public boolean isClockWiseOriented() {
        double a11 = a.x - c.x;
        double a21 = b.x - c.x;

        double a12 = a.y - c.y;
        double a22 = b.y - c.y;

        double det = a11 * a22 - a12 * a21;

        return det > 0.0d;
    }

    /**
     * Returns true if this triangle contains the given edge.
     *
     * @param edge The edge to be tested
     * @return Returns true if this triangle contains the edge
     */
    public boolean isNeighbour(Edge2D edge) {
        return (a == edge.a || b == edge.a || c == edge.a) && (a == edge.b || b == edge.b || c == edge.b);
    }

    /**
     * Returns the vertex of this triangle that is not part of the given edge.
     *
     * @param edge The edge
     * @return The vertex of this triangle that is not part of the edge
     */
    public Vector2D getNoneEdgeVertex(Edge2D edge) {
        if (a != edge.a && a != edge.b) {
            return a;
        } else if (b != edge.a && b != edge.b) {
            return b;
        } else if (c != edge.a && c != edge.b) {
            return c;
        }
        return null;
    }

    /**
     * Returns true if the given vertex is one of the vertices describing this
     * triangle.
     *
     * @param vertex The vertex to be tested
     * @return Returns true if the Vertex is one of the vertices describing this triangle
     */
    public boolean hasVertex(Vector2D vertex) {
        return a == vertex || b == vertex || c == vertex;
    }

    /**
     * Returns an EdgeDistancePack containing the edge and its distance nearest
     * to the specified point.
     *
     * @param point The point the nearest edge is queried for
     * @return The edge of this triangle that is nearest to the specified point
     */
    public EdgeDistancePack findNearestEdge(Vector2D point) {
        EdgeDistancePack[] edges = new EdgeDistancePack[3];
        edges[0] = new EdgeDistancePack(new Edge2D(a, b), computeClosestPoint(new Edge2D(a, b), point).sub(point).mag());
        edges[1] = new EdgeDistancePack(new Edge2D(b, c), computeClosestPoint(new Edge2D(b, c), point).sub(point).mag());
        edges[2] = new EdgeDistancePack(new Edge2D(c, a), computeClosestPoint(new Edge2D(c, a), point).sub(point).mag());
        Arrays.sort(edges);
        return edges[0];
    }

    /**
     * Computes the closest point on the given edge to the specified point.
     *
     * @param edge The edge on which we search the closest point to the specified point
     * @param point The point to which we search the closest point on the edge
     * @return The closest point on the given edge to the specified point
     */
    private Vector2D computeClosestPoint(Edge2D edge, Vector2D point) {
        Vector2D ab = edge.b.sub(edge.a);
        double t = point.sub(edge.a).dot(ab) / ab.dot(ab);

        if (t < 0.0d) {
            t = 0.0d;
        } else if (t > 1.0d) {
            t = 1.0d;
        }

        return edge.a.add(ab.mult(t));
    }

    /**
     * Tests if the two arguments have the same sign.
     *
     * @param a The first floating point argument
     * @param b The second floating point argument
     * @return Returns true iff both arguments have the same sign
     */
    private boolean hasSameSign(double a, double b) {
        return Math.signum(a) == Math.signum(b);
    }

    @Override
    public String toString() {
        return "Triangle2D[" + a + ", " + b + ", " + c + "]";
    }

}
