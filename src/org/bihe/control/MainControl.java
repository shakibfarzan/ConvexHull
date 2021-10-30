package org.bihe.control;

import org.bihe.gui.MainFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class MainControl {
    private MainFrame frame;
    private static int ADDX = 190;
    private static int ADDY = 22;
    private static int ROUND = 6;
    public MainControl(MainFrame frame){
        this.frame = frame;
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
        frame.getClearButton().addActionListener(this::clear);
        frame.getConnectAll().addActionListener(this::connectAllPoints);
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
    }


    private static double reverseSlope(Point a, Point b){
        return ((b.getX() - a.getX()))/(b.getY() - a.getY());
    }

    private void connectAllPoints(ActionEvent e){
        LinkedList<Point> points = frame.getPoints();
        if (points.isEmpty()){
            return;
        }
        connectPoints(points);
    }

    private void connectPoints(LinkedList<Point> points){
        Point minY = minY(points);
        points.sort(Comparator.comparingDouble(p -> reverseSlope(minY, p)));
        points.remove(minY);
        points.addFirst(minY);
        Graphics g = frame.getGraphics();
        Iterator<Point> itr = points.iterator();
        Point prev = itr.next();
        Point first = prev;
        Point current;
        while (itr.hasNext()){
            current = itr.next();
            g.setColor(Color.GREEN);
            g.fillOval(prev.x, prev.y, ROUND,ROUND);
            g.drawLine(prev.x, prev.y, current.x, current.y);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            prev = current;
        }
        g.setColor(Color.GREEN);
        g.fillOval(prev.x, prev.y, ROUND,ROUND);
        g.drawLine(prev.x, prev.y, first.x, first.y);
    }

    private static Point minY(LinkedList<Point> points){
        Point min = points.getFirst();
        for (Point p: points){
            if(p.getY() > min.getY()){
                min = p;
            }
        }
        return min;
    }
}