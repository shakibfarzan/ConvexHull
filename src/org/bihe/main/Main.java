package org.bihe.main;

import org.bihe.control.MainControl;
import org.bihe.gui.MainFrame;

public class Main {
    public static void main(String[] args) {
        MainFrame mf = new MainFrame();
        mf.setVisible(true);
        MainControl mc = new MainControl(mf);
    }
}
