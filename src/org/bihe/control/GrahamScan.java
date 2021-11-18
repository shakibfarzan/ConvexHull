package org.bihe.control;

import java.awt.*;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class GrahamScan implements ConvexHull {
    private static class Node {
        Point value;
        Node next;
        Node prev;

        public Node(Point value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Value: " + value + " Next: " + next.value + " Prev: " + prev.value;
        }
    }

    private final LinkedList<Point> orgPoints;
    private LinkedList<Point> minYs;
    private Node head;
    private PriorityQueue<Point> pointPriorityQueue;
    private PriorityQueue<Point> minYPQ;
    private Point minY;
    private LinkedList<Point> points;

    public GrahamScan(LinkedList<Point> orgPoints) {
        this.orgPoints = orgPoints;
        this.minYs = MainController.minY(orgPoints);
        this.minY = minYs.element();
        this.pointPriorityQueue = new PriorityQueue<>(this::compare);
        this.minYPQ = new PriorityQueue<>(Comparator.comparingDouble(Point::getX));
    }

    private int compare(Point p1, Point p2){
        double rs1 = MainController.reverseSlope(minY, p1);
        double rs2 = MainController.reverseSlope(minY, p2);
        if (rs1 != rs2){
            return Double.compare(rs1,rs2);
        }else {
            return Integer.compare(p1.x,p2.x);
        }
    }

    public void init() {
        sort();
        head = new Node(this.points.element());
        Node current = head;
        for (Point point : points) {
            if (point.equals(current.value)) continue;
            Node node = new Node(point);
            node.prev = current;
            current.next = node;
            current = current.next;
        }
    }

    private void sort() {
        points = new LinkedList<>();
        for (Point point : orgPoints) {
            pointPriorityQueue.add(point);
        }
        while (!pointPriorityQueue.isEmpty()) {
            Point t = pointPriorityQueue.remove();
            if (t.y != minY.y) points.add(t);
        }
        for (Point point : minYs) {
            minYPQ.add(point);
        }
        while (!minYPQ.isEmpty()) {
            points.addFirst(minYPQ.remove());
        }
    }

    @Override
    public LinkedList<Point> solve() {
        LinkedList<Point> solution = new LinkedList<>();
        init();
        graham();
        Node current = head;
        while (current != null) {
            solution.add(current.value);
            current = current.next;
        }
        return solution;
    }

    private void graham() {
        Node p1Node = head.next;
        if (p1Node == null) return;
        Node p2Node = p1Node.next;
        if (p2Node == null) return;
        Node p3Node = p2Node.next;
        if (p3Node == null) return;
        while (p3Node != null) {
            if (p1Node == null || side(p1Node.value, p3Node.value, p2Node.value) >= 0) {
                p1Node = p2Node;
                p2Node = p3Node;
                p3Node = p3Node.next;
            } else {
                p1Node.next = p3Node;
                p3Node.prev = p1Node;
                p2Node = p1Node;
                p1Node = p2Node.prev;
            }
        }
    }

    public static int side(Point p1, Point p2, Point p3) {
        return (p3.y - p1.y) * (p2.x - p1.x)-(p2.y - p1.y) * (p3.x - p1.x);
    }
}
