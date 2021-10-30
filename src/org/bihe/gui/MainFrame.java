package org.bihe.gui;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class MainFrame extends JFrame {
    private JCheckBox xAndYCheckBox;
    private JRadioButton grahamScanButton;
    private JRadioButton quickHallButton;
    private JRadioButton connectAll;
    private LinkedList<Point> points = new LinkedList<>();
    private JPanel contentPane = new JPanel();
    private JPanel pointsPane = new JPanel();
    private JButton clearAllButton;
    private JButton clearLinesButton;
    private JRadioButton blindSearchButton;
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
        getContentPane().add(pointsPane);

        clearAllButton = new JButton("Clear All");
        clearAllButton.setBackground(Color.GRAY);
        clearAllButton.setForeground(Color.WHITE);
        clearAllButton.setBounds(43, 236, 100, 23);
        contentPane.add(clearAllButton);

        clearLinesButton = new JButton("Clear Lines");
        clearLinesButton.setBackground(Color.GRAY);
        clearLinesButton.setForeground(Color.WHITE);
        clearLinesButton.setBounds(43, 201, 100, 23);
        contentPane.add(clearLinesButton);

        ButtonGroup bg = new ButtonGroup();

        blindSearchButton = new JRadioButton("Blind Search");
        blindSearchButton.setBackground(Color.WHITE);
        blindSearchButton.setBounds(6, 25, 109, 23);
        contentPane.add(blindSearchButton);

        quickHallButton = new JRadioButton("Quick-Hall");
        quickHallButton.setBackground(Color.WHITE);
        quickHallButton.setBounds(6, 61, 109, 23);
        contentPane.add(quickHallButton);

        grahamScanButton = new JRadioButton("Graham-Scan");
        grahamScanButton.setBackground(Color.WHITE);
        grahamScanButton.setBounds(6, 96, 109, 23);
        contentPane.add(grahamScanButton);

        connectAll = new JRadioButton("Connect All Points");
        connectAll.setBackground(Color.WHITE);
        connectAll.setBounds(6, 131, 130, 23);
        contentPane.add(connectAll);

        bg.add(blindSearchButton);
        bg.add(quickHallButton);
        bg.add(grahamScanButton);
        bg.add(connectAll);

        xAndYCheckBox = new JCheckBox("Show( x & y)");
        xAndYCheckBox.setBackground(Color.WHITE);
        xAndYCheckBox.setBounds(6, 166, 97, 23);
        contentPane.add(xAndYCheckBox);
    }

    public JCheckBox getxAndYCheckBox() {
        return xAndYCheckBox;
    }

    public JRadioButton getGrahamScanButton() {
        return grahamScanButton;
    }

    public JRadioButton getQuickHallButton() {
        return quickHallButton;
    }

    public LinkedList<Point> getPoints() {
        return points;
    }

    public JButton getClearAllButton() {
        return clearAllButton;
    }

    public JButton getClearLinesButton() {
        return clearLinesButton;
    }

    public JRadioButton getBlindSearchButton() {
        return blindSearchButton;
    }

    public JRadioButton getConnectAll(){
        return connectAll;
    }

    public JPanel getPointsPane(){
        return pointsPane;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillOval(getX(), getY(), 50, 50);
    }
}