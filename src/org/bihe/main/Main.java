package org.bihe.main;

import org.bihe.control.*;
import org.bihe.gui.MainFrame;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        MainFrame mf = new MainFrame();
//        mf.setVisible(true);
//        MainController mc = new MainController(mf);

        for (int i = 0; i < 100; i++) {
            LinkedList<Point> points1 = new LinkedList<>();
            LinkedList<Point> points2 = new LinkedList<>();
            LinkedList<Point> points3 = new LinkedList<>();
            Random r = new Random();
            for (int j = 0; j < 200; j++) {
                int x = r.nextInt(1000);
                int y = r.nextInt(1000);
                points1.add(new Point(x,y));
                points2.add(new Point(x,y));
                points3.add(new Point(x,y));
            }
            LinkedList<Point> minYs1 = MainController.minY(points1);
            Point minY1 = minYs1.element();
            minYs1.sort(Comparator.comparingDouble(Point::getX));
            LinkedList<Point> minYs2 = MainController.minY(points2);
            Point minY2 = minYs2.element();
            minYs2.sort(Comparator.comparingDouble(Point::getX));
            LinkedList<Point> minYs3 = MainController.minY(points3);
            Point minY3 = minYs3.element();
            minYs3.sort(Comparator.comparingDouble(Point::getX));
            BlindSearch blindSearch = new BlindSearch(points1);
            LinkedList<Point> blindSol = blindSearch.solve();

            QuickHull quickHull = new QuickHull(points2);
            LinkedList<Point> quickSol = quickHull.solve();

//            GrahamScan grahamScan = new GrahamScan(points3);
//            LinkedList<Point> grahamSol = grahamScan.solve();

            test(points1, minYs1, minY1, blindSol);
            test(points2, minYs2, minY2, quickSol);
//            test(points3, minYs3, minY3, grahamSol);
            System.out.println("QuickHull: "+blindSol.equals(quickSol));
//            System.out.println("GrahamScan: "+blindSol.equals(grahamSol));
        }
    }

    private static void test(LinkedList<Point> points3, LinkedList<Point> minYs3, Point minY3, LinkedList<Point> grahamSol) {
        grahamSol.sort(Comparator.comparingDouble(p-> MainController.reverseSlope(minY3,p)));
        points3.removeIf(p -> p.getY() == minY3.getY());
        while(!minYs3.isEmpty()){
            Point p = minYs3.pollLast();
            points3.addFirst(p);
        }
    }
}
