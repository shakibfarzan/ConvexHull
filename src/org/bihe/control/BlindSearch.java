package org.bihe.control;

import java.awt.*;
import java.util.LinkedList;

public class BlindSearch implements ConvexHull{

    private final LinkedList<Point> points;

    public BlindSearch(LinkedList<Point> points) {
        this.points = points;
    }

    private double determinant(Point p1, Point p2, Point p3) {
        return p1.getX() * (p3.getY() - p2.getY()) - p2.getX() * (p3.getY() - p1.getY()) + p3.getX() * (p2.getY() - p1.getY());
    }

    private boolean isInTriangle(Point p, Point v1, Point v2, Point v3) {
        double det1 = determinant(p, v1, v2);
        double det2 = determinant(p, v2, v3);
        double det3 = determinant(p, v3, v1);

        return !((det1 >= 0 || det2 >= 0 || det3 >= 0) && (det1 <= 0 || det2 <= 0 || det3 <= 0));
    }

    @Override
    public LinkedList<Point> solve() {
        LinkedList<Point> correctPoints = new LinkedList<>();
        Point point = points.element();
        for (Point p : points) {
            if (p.equals(point) && !p.equals(points.element())) continue;
            boolean inTriangle = false;
            point = p;
            Loop:
            for (Point v1 : points) {
                if (p.equals(v1)) continue;
                for (Point v2 : points) {
                    if (v2.equals(p) || v2.equals(v1)) continue;
                    for (Point v3 : points) {
                        if (v3.equals(p) || v3.equals(v2) || v3.equals(v1)) continue;
                        if (isInTriangle(p, v1, v2, v3)) {
                            inTriangle = true;
                            break Loop;
                        }
                    }
                }
            }
            if(!inTriangle) correctPoints.add(point);
        }
        return correctPoints;
    }

}
