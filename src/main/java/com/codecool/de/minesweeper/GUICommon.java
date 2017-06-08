package com.codecool.de.minesweeper;

import javax.swing.*;
import java.awt.*;

public class GUICommon {
    public void setFramePos(JFrame frame) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();
        int xPos = (dim.width / 2) - (frame.getWidth() / 2);
        int yPos = (dim.height / 2) - (frame.getHeight() / 2);
        frame.setLocation(xPos, yPos);
    }
}

