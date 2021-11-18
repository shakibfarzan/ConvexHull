package org.bihe.control;

import org.bihe.gui.MainFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class MainController {
    private final MainFrame frame;
    private final LinkedList<Point> connectedPoints;
    private static int ADDX = 185;
    private static int ADDY = 25;
    private static int ROUND = 6;
    public MainController(MainFrame frame){
        this.frame = frame;
        this.connectedPoints = new LinkedList<>();
        init();
    }

    private void init(){
        frame.getPointsPane().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setPoint(e);
            }
        });
        frame.getxAndYCheckBox().addActionListener(this::xAndYCheckBoxListener);
        frame.getClearAllButton().addActionListener(this::clear);
        frame.getConnectAll().addActionListener(this::connectAllPoints);
        frame.getClearLinesButton().addActionListener(this::clearLines);
        frame.getBlindSearchButton().addActionListener(this::blindSearchAction);
        frame.getQuickHallButton().addActionListener(this::quickHullAction);
        frame.getGrahamScanButton().addActionListener(this::grahamScanAction);
    }

    private void setPoint(MouseEvent e){
        int x = e.getX()+ADDX;
        int y = e.getY()+ADDY;
        Graphics g = frame.getGraphics();
        g.setColor(Color.red);
        g.fillOval(x, y, ROUND, ROUND);
        Point p = new Point(x, y);
        frame.getPoints().add(p);
    }

    private void xAndYCheckBoxListener(ActionEvent e){
        Graphics g = frame.getGraphics();
        if (frame.getxAndYCheckBox().isSelected()){
            for (Point p: frame.getPoints()){
                g.setColor(Color.red);
                g.drawString(p.getX()+", "+p.getY(), p.x,p.y);
            }
        }else{
            for (Point p: frame.getPoints()){
                g.setColor(Color.BLACK);
                g.drawString(p.getX()+", "+p.getY(), p.x,p.y);
            }
        }
    }

    private void clear(ActionEvent e){
        Graphics g = frame.getGraphics();
        frame.paint(g);
        frame.getPoints().clear();
        connectedPoints.clear();
    }

    private void clearLines(ActionEvent e){
        if(connectedPoints.isEmpty()) return;
        Graphics g = frame.getGraphics();
        Iterator<Point> itr = connectedPoints.iterator();
        Point prev = itr.next();
        Point first = prev;
        Point current;
        while (itr.hasNext()){
            current = itr.next();
            g.setColor(Color.RED);
            g.fillOval(prev.x, prev.y, ROUND,ROUND);
            g.setColor(Color.BLACK);
            g.drawLine(prev.x, prev.y, current.x, current.y);
            prev = current;
        }
        g.setColor(Color.RED);
        g.fillOval(prev.x, prev.y, ROUND,ROUND);
        g.setColor(Color.BLACK);
        g.drawLine(prev.x, prev.y, first.x, first.y);
        connectedPoints.clear();
    }

    public static double reverseSlope(Point a, Point b){
        return ((b.getX() - a.getX()))/(b.getY() - a.getY());
    }

    private void connectAllPoints(ActionEvent e){
        LinkedList<Point> points = frame.getPoints();
        if (points.isEmpty()){
            return;
        }
        connectPoints(points, e);
    }

    private void connectPoints(LinkedList<Point> points, ActionEvent event){
        clearLines(event);
        LinkedList<Point> minYs = minY(points);
        Point minY = minYs.element();
        points.sort(Comparator.comparingDouble(p -> reverseSlope(minY, p)));
        minYs.sort(Comparator.comparingDouble(Point::getX));
        points.removeIf(p -> p.getY() == minY.getY());
        while(!minYs.isEmpty()){
            Point p = minYs.pollLast();
            points.addFirst(p);
        }
        Graphics g = frame.getGraphics();
        Iterator<Point> itr = points.iterator();
        Point prev = itr.next();
        Point first = prev;
        Point current;
        while (itr.hasNext()){
            connectedPoints.add(prev);
            current = itr.next();
            g.setColor(Color.GREEN);
            g.fillOval(prev.x, prev.y, ROUND,ROUND);
            g.drawLine(prev.x, prev.y, current.x, current.y);
            prev = current;
        }
        connectedPoints.add(prev);
        g.setColor(Color.GREEN);
        g.fillOval(prev.x, prev.y, ROUND,ROUND);
        g.drawLine(prev.x, prev.y, first.x, first.y);
    }

    public static LinkedList<Point> minY(LinkedList<Point> points){
        Point min = points.getFirst();
        for (Point p: points){
            if(p.getY() > min.getY()){
                min = p;
            }
        }
        LinkedList<Point> mins = new LinkedList<>();
        for (Point p: points){
            if (p.getY() == min.getY()){
                mins.add(p);
            }
        }
        return mins;
    }

    private void blindSearchAction(ActionEvent event){
        BlindSearch blindSearch = new BlindSearch(frame.getPoints());
//        OrgBlind blindSearch = new OrgBlind(frame.getPoints());
        LinkedList<Point> correctPoints = blindSearch.solve();
        if (correctPoints.isEmpty()) return;
        connectPoints(correctPoints, event);
    }

    private void quickHullAction(ActionEvent event){
        if (frame.getPoints().isEmpty()) return;
        QuickHull quickHull = new QuickHull(frame.getPoints());
        LinkedList<Point> correctPoints = quickHull.solve();
        if (correctPoints.isEmpty()) return;
        connectPoints(correctPoints, event);
    }

    private void grahamScanAction(ActionEvent event){
        if (frame.getPoints().isEmpty()) return;
        GrahamScan grahamScan = new GrahamScan(frame.getPoints());
        LinkedList<Point> correctPoints = grahamScan.solve();
        if (correctPoints.isEmpty()) return;
        connectPoints(correctPoints, event);
    }
}
