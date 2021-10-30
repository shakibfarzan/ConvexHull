package org.bihe.main;

import org.bihe.control.MainController;
import org.bihe.gui.MainFrame;

public class Main {
    public static void main(String[] args) {
        MainFrame mf = new MainFrame();
        mf.setVisible(true);
        MainController mc = new MainController(mf);
    }
}
