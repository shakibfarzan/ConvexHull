package org.bihe.main;

import org.bihe.control.*;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

public class Coordinator {
    public static void main(String[] args) {
        try {
            experiment(500,20, 50, 50);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long getTime() {
        return System.nanoTime();
    }

    public static void testAlgorithms(LinkedList<Point> points3, LinkedList<Point> minYs3, Point minY3, LinkedList<Point> grahamSol) {
        grahamSol.sort(Comparator.comparingDouble(p -> MainController.reverseSlope(minY3, p)));
        points3.removeIf(p -> p.getY() == minY3.getY());
        while (!minYs3.isEmpty()) {
            Point p = minYs3.pollLast();
            points3.addFirst(p);
        }
    }

    public static void experiment(int n, int maxRep, int from, int step) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
        BufferedWriter writer2 = new BufferedWriter(new FileWriter("output_of_faults.txt"));
        for (int num = from; num <= n; num += step)
        {
            int blind = 0, quick = 0, graham = 0;
            for (int rep = 0; rep < maxRep; rep++) {
                System.out.println("Testing n= " + num);
                ArrayList<LinkedList<Point>> pointsLists = new ArrayList<>();
                LinkedList<Point> points0 = new LinkedList<>();
                LinkedList<Point> points1 = new LinkedList<>();
                LinkedList<Point> points2 = new LinkedList<>();
                LinkedList<Point> points3 = new LinkedList<>();
                pointsLists.add(points0);
                pointsLists.add(points1);
                pointsLists.add(points2);
                pointsLists.add(points3);
                Random r = new Random();
                for (int j = 0; j < num; j++) {
                    int x = r.nextInt(10000);
                    int y = r.nextInt(10000);
                    for (LinkedList<Point> pointLinkedList: pointsLists){
                        pointLinkedList.add(new Point(x,y));
                    }
                }

                Point minY = null;
                LinkedList<Point> minYs = new LinkedList<>();
                for (var pointLinkedList: pointsLists){
                    minYs = MainController.minY(pointLinkedList);
                    minY = minYs.element();
                    minYs.sort(Comparator.comparingDouble(Point::getX));
                }
                ArrayList<ConvexHull> hulls = new ArrayList<>();
                OrgBlind orgBlind = new OrgBlind(points0);
                LinkedList<Point> orgBlindSol = orgBlind.solve();
                testAlgorithms(points0,minYs,minY,orgBlindSol);
                BlindSearch blindSearch = new BlindSearch(points1);
                QuickHull quickHull = new QuickHull(points2);
                GrahamScan grahamScan = new GrahamScan(points3);
                hulls.add(blindSearch);
                hulls.add(quickHull);
                hulls.add(grahamScan);

                writer.write(num + ",");
                int i = 1;
                for (ConvexHull hull : hulls) {
                    long begin = getTime();
                    LinkedList<Point> sol =  hull.solve();
                    long finish = getTime();
                    writer.write((finish - begin) + ",");
                    testAlgorithms(pointsLists.get(i),minYs,minY,sol);
                    i++;
                    boolean isCorrect = orgBlindSol.equals(sol);
                    if (!isCorrect){
                        switch (i) {
                            case 1 -> blind++;
                            case 2 -> quick++;
                            case 3 -> graham++;
                        }
                    }
                }
                writer.write("\n");

            }
            writer2.write("Testing on n="+num+" with "+maxRep+" repeat"+"\n"+"BlindSearch faults: " + blind+"\n"+"QuickHull faults: " + quick+"\n"+"GrahamScan faults: " + graham+"\n\n");
        }
        writer.close();
        writer2.close();
    }
}
