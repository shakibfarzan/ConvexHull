package org.bihe.main;

import org.bihe.control.BlindSearch;
import org.bihe.control.MainController;
import org.bihe.control.QuickHull;
import org.bihe.gui.MainFrame;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MainFrame mf = new MainFrame();
        mf.setVisible(true);
        MainController mc = new MainController(mf);
    }
}
