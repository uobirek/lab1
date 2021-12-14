package gui;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton {
    private static final int DEFAULT_WIDTH = 2;
    private static final int DEFAULT_HEIGHT = 1;

    public CustomButton(
            String text,
            int row,
            int col,
            boolean enabled,
            JPanel jPanel,
            GridBagConstraints constraints
    ){
        super(text);
        constraints.gridx = col;
        constraints.gridy = row;
        constraints.gridwidth = DEFAULT_WIDTH;
        constraints.gridheight = DEFAULT_HEIGHT;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        setEnabled(enabled);
        jPanel.add(this, constraints);

    }
}
