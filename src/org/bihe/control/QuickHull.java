package org.bihe.control;

import java.awt.*;
import java.util.LinkedList;

public class QuickHull implements ConvexHull {

    private final LinkedList<Point> orgPoints;
    private final LinkedList<Point> solution;

    public QuickHull(LinkedList<Point> orgPoints){
        this.orgPoints = orgPoints;
        this.solution = new LinkedList<>();
    }

    @Override
    public LinkedList<Point> solve() {
        Point firstPoint = maxYPoint(minXPoints());
        Point secondPoint = maxYPoint(maxXPoints());
        Point thirdPoint = minYPoint(minXPoints());
        Point forthPoint = minYPoint(maxXPoints());
        solution.add(firstPoint);
        solution.add(secondPoint);
        if (!thirdPoint.equals(firstPoint)) solution.add(thirdPoint);
        if (!forthPoint.equals(secondPoint)) solution.add(forthPoint);
        LinkedList<Point> top = checkSidedList(firstPoint,secondPoint,orgPoints);
        LinkedList<Point> bottom = checkSidedList(forthPoint,thirdPoint,orgPoints);

        quickHull(top,firstPoint,secondPoint);
        quickHull(bottom,forthPoint,thirdPoint);
        return solution;
    }

    private void quickHull(LinkedList<Point> points, Point p1, Point p2){
        if (points.isEmpty()) return;
        if (points.size() == 1){
            solution.add(points.element());
            return;
        }

        double maxArea = -1;
        Point maxPoint = null;
        for (Point point: points){
            double area = area(p1,p2,point);
            if (area > maxArea){
                maxArea = area;
                maxPoint = point;
            }
        }
        solution.add(maxPoint);
        LinkedList<Point> leftPoints = checkSidedList(p1,maxPoint,points);
        LinkedList<Point> rightPoints = checkSidedList(maxPoint,p2,points);
        quickHull(leftPoints, p1, maxPoint);
        quickHull(rightPoints, maxPoint, p2);
    }

    private LinkedList<Point> minXPoints(){
        LinkedList<Point> minXs = new LinkedList<>();
        int minX = Integer.MAX_VALUE;
        for (Point point: orgPoints){
            if (point.x <= minX){
                minX = point.x;
                minXs.add(point);
            }
        }
        return minXs;
    }

    private LinkedList<Point> maxXPoints(){
        LinkedList<Point> maxXs = new LinkedList<>();
        int maxX = 0;
        for (Point point: orgPoints){
            if (point.x >= maxX){
                maxX = point.x;
                maxXs.add(point);
            }
        }
        return maxXs;
    }

    private Point minYPoint(LinkedList<Point> points){
        if (points.size()==1) return points.element();
        Point minYPoint = null;
        int minY = 0;
        for (Point point: points){
            if (point.y > minY){
                minYPoint = point;
            }
        }
        return minYPoint;
    }

    private Point maxYPoint(LinkedList<Point> points){
        if (points.size()==1) return points.element();
        Point maxYPoint = null;
        int maxY = Integer.MAX_VALUE;
        for (Point point: points){
            if (point.y < maxY){
                maxYPoint = point;
            }
        }
        return maxYPoint;
    }

    private LinkedList<Point> checkSidedList(Point p1, Point p2, LinkedList<Point> givenPoints){
        LinkedList<Point> points = new LinkedList<>();
        for (Point point: givenPoints){
            if (side(p1, p2, point) <= 0){
                if (point.equals(p1) || point.equals(p2)) continue;
                points.add(point);
            }
        }
        return points;
    }

    public static double area(Point p1, Point p2, Point p3){
        return Math.abs((p1.x * (p2.y - p3.y) + p2.x * (p3.y - p1.y) + p3.x * (p1.y - p2.y))) >> 1;
    }

    public static int side(Point p1, Point p2, Point p3){
        return (p3.y - p1.y) * (p2.x - p1.x) - (p2.y - p1.y) * (p3.x - p1.x);
    }
}
