package org.bihe.main;

import org.bihe.control.*;
import org.bihe.gui.MainFrame;

import java.awt.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
//        MainFrame mf = new MainFrame();
//        mf.setVisible(true);
//        MainController mc = new MainController(mf);
//        test();
//        System.out.println(GrahamScan.side(new Point(0,0), new Point(3,3),new Point(5,5)));
        BlindSearch blindSearch = new BlindSearch(new LinkedList<>());
        System.out.println(blindSearch.getClass().getName().split("\\.")[3]);
    }

    private static void test() {
        int blind = 0, quick = 0, graham = 0;
        LinkedList<Point> orgBlindSol = null;
        for (int i = 0; i < 100; i++) {
            LinkedList<Point> points0 = new LinkedList<>();
            LinkedList<Point> points1 = new LinkedList<>();
            LinkedList<Point> points2 = new LinkedList<>();
            LinkedList<Point> points3 = new LinkedList<>();
            Random r = new Random();
            for (int j = 0; j < 100; j++) {
                int x = r.nextInt(1000);
                int y = r.nextInt(1000);
                points0.add(new Point(x, y));
                points1.add(new Point(x, y));
                points2.add(new Point(x, y));
                points3.add(new Point(x, y));
            }
            LinkedList<Point> minYs0 = MainController.minY(points0);
            Point minY0 = minYs0.element();
            minYs0.sort(Comparator.comparingDouble(Point::getX));

            LinkedList<Point> minYs1 = MainController.minY(points1);
            Point minY1 = minYs1.element();
            minYs1.sort(Comparator.comparingDouble(Point::getX));

            LinkedList<Point> minYs2 = MainController.minY(points2);
            Point minY2 = minYs2.element();
            minYs2.sort(Comparator.comparingDouble(Point::getX));

            LinkedList<Point> minYs3 = MainController.minY(points3);
            Point minY3 = minYs3.element();
            minYs3.sort(Comparator.comparingDouble(Point::getX));

            OrgBlind orgBlind = new OrgBlind(points0);
            orgBlindSol = orgBlind.solve();

            BlindSearch blindSearch = new BlindSearch(points1);
            LinkedList<Point> blindSol = blindSearch.solve();

            QuickHull quickHull = new QuickHull(points2);
            LinkedList<Point> quickSol = quickHull.solve();

            GrahamScan grahamScan = new GrahamScan(points3);
            LinkedList<Point> grahamSol = grahamScan.solve();

            Coordinator.testAlgorithms(points0, minYs0, minY0, orgBlindSol);
            Coordinator.testAlgorithms(points1, minYs1, minY1, blindSol);
            Coordinator.testAlgorithms(points2, minYs2, minY2, quickSol);
            Coordinator.testAlgorithms(points3, minYs3, minY3, grahamSol);
            System.out.println("\nNumber of tests: " + (i + 1));
            System.out.println("True Size: " + orgBlindSol.size());
            boolean blindOK = orgBlindSol.equals(blindSol);
            boolean quickOK = orgBlindSol.equals(quickSol);
            boolean grahamOK = orgBlindSol.equals(grahamSol);
            if (!blindOK) {
                blind++;
                System.out.println(blindSol);
            }
            if (!quickOK) {
                quick++;
                System.out.println(quickSol);
            }
            if (!grahamOK) {
                graham++;
                System.out.println(grahamSol);
            }
            System.out.println("BlindSearch: " + blindOK + " " + blindSol.size());
            System.out.println("QuickHull: " + quickOK + " " + quickSol.size());
            System.out.println("GrahamScan: " + grahamOK + " " + grahamSol.size());
        }
        System.out.println();
        System.out.println("BlindSearch faults: " + blind);
        System.out.println("QuickHull faults: " + quick);
        System.out.println("GrahamScan faults: " + graham);
        System.out.println();
        System.out.println(orgBlindSol);
    }
}
