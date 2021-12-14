package gui;

import javax.swing.*;
import java.awt.*;

public class SimulationFrame extends JFrame {
    public SimulationFrame(){
        MainPanel mainPanel = new MainPanel();
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
