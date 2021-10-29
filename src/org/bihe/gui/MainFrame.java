package org.bihe.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private ArrayList<Point> points = new ArrayList<>();
    private JPanel contentPane = new JPanel();
    private JPanel pointsPane = new JPanel();
    private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int FRAME_WIDTH = 800;
    public static final int FRAME_HEIGHT = 600;

    public MainFrame(){
        setTitle("Convex Hull");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(dim.width / 2 - FRAME_WIDTH / 2, dim.height / 2 - FRAME_HEIGHT / 2, FRAME_WIDTH, FRAME_HEIGHT);
        contentPane.setBackground(Color.WHITE);
        contentPane.setLayout(null);
        setContentPane(contentPane);
        setResizable(false);
        pointsPane.setBounds(dim.width / 2 - (FRAME_WIDTH+200) / 2, dim.height / 2 - (FRAME_HEIGHT+180) / 2, FRAME_WIDTH, FRAME_HEIGHT);
        pointsPane.setBackground(Color.BLACK);
        add(pointsPane);
       pointsPane.addMouseListener(new MouseAdapter() {
           @Override
           public void mousePressed(MouseEvent e) {
               int r = 6;
               int x = e.getX()+190;
               int y = e.getY()+22;
               Graphics g = getGraphics();
               g.setColor(Color.WHITE);
               g.fillOval(x, y, r, r);
               Point p = new Point(x, y);
               points.add(p);
           }
       });
    }
}
