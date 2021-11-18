package org.bihe.control;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class OrgBlind implements ConvexHull{
    private ArrayList<Point> points = new ArrayList<>();
    public OrgBlind(LinkedList<Point> points){
        this.points.addAll(points);
    }
    @Override
    public LinkedList<Point> solve() {
        LinkedList<Point> extrems = new LinkedList<>();
        Point point = points.get(0);
        for (Point p : points) {
            if (point.equals(p) && !p.equals(points.get(0))) continue;
            point = p;
            boolean inside = false;
            for (int i = 0; i < points.size() && !inside; i++) {
                for (int j = 0; j < points.size() && !inside; j++) {
                    for (int k = 0; k < points.size(); k++) {
                        Point p1 = points.get(i);
                        Point p2 = points.get(j);
                        Point p3 = points.get(k);
                        if ((angleTurn(p1, p2, p) < 0) && (angleTurn(p2, p3, p) < 0)
                                && (angleTurn(p3, p1, p) < 0)) {
                            inside = true;
                            break;
                        }
                    }
                }
            }
            if (!inside) {
                extrems.add(p);
            }
        }
        return extrems;
    }
    public double angleTurn( Point p1,Point p2,Point p3)
    {
        return (double)(p2.getX()-p1.getX())*(p3.getY()-p1.getY())-
                (p3.getX()-p1.getX())*(p2.getY()-p1.getY());
    }
}
