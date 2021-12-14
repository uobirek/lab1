package gui;

import simulation.JsonParser;
import simulation.Simulation;
import simulation.SimulationParams;
import simulation.WorldMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel implements ActionListener {
    private MapPanel mapPanel;
    private final Timer timer;
    private GridBagConstraints constraints = new GridBagConstraints();
    private int nextRow = 0;
    private final static int DEFAULT_COLUMN = 0;
    private CustomTextArea statsInput;
    private final static int MAP_COL = 2;
    private final static int MAP_ROW = 0;
    private final static String STATS_FILENAME = "stats.json";


    public MainPanel(){
        initLayout();

        CustomButton startBtn = createButton("Start new game");
        startBtn.addActionListener((ActionEvent e) -> startApp());
        CustomButton stopBtn = createButton("Pause");
        stopBtn.addActionListener((ActionEvent e) -> stopApp());
        CustomButton continueBtn = createButton("Continue game");
        continueBtn.addActionListener((ActionEvent e) -> continueApp());
        CustomButton dumpToJsonBtn = createButton("Dump stats to json");
        dumpToJsonBtn.addActionListener((ActionEvent e) -> dumpStatistics());
        SimulationParams.getParamsMap().forEach((fieldName, value) ->
                new CustomTextField(fieldName, value, nextRow++, DEFAULT_COLUMN, constraints, this)
        );
        statsInput = new CustomTextArea(
                "Press 'start new game'\nto reset game",
                LayoutConstants.TEXT_AREA_HEIGHT,
                LayoutConstants.TEXT_AREA_WIDTH,
                nextRow++,
                DEFAULT_COLUMN,
                this,
                constraints
        );
        mapPanel = new MapPanel(Simulation.getWorldMap());
       // mapPanel.setPreferredSize(new Dimension(700, 500));
        addMapPanel();
        timer = new Timer(1000/ SimulationParams.getField("speed"), this);
        timer.start();
    }
    private void addMapPanel() {
        constraints.gridx = MAP_COL;
        constraints.gridy = MAP_ROW;
        constraints.gridwidth = 1;
        constraints.gridheight = nextRow + 1;
        constraints.ipadx = LayoutConstants.MAP_SCALE * Simulation.getWorldMap().getWidth();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(mapPanel, constraints);
    }

        private void rerenderMap(){
        if(Simulation.getWorldMap().getAnimalLocation().isEmpty()){
            timer.stop();
            return;
        }
        Simulation.simulateDay();
        mapPanel.repaint();
        statsInput.setText(Simulation.getWorldMap().getStatistics().toString());
    }


    private void initLayout (){
        setLayout(new GridBagLayout());
        setBackground(CustomColors.BACKGROUND_COLOR);
        constraints.insets = new Insets(
                LayoutConstants.TOP_MARGIN,
                LayoutConstants.LEFT_MARGIN,
                LayoutConstants.BOTTOM_MARGIN,
                LayoutConstants.RIGHT_MARGIN
        );
        constraints.anchor= GridBagConstraints.LINE_START;
    }
    private CustomButton createButton (String text){
        return new CustomButton(text, nextRow++, DEFAULT_COLUMN, true, this, constraints);
    }

    private void startApp(){
        if (timer.isRunning()) timer.stop();
        Simulation.setSimulation();
        mapPanel.repaint();
        timer.start();

    }
    private void stopApp(){
        if(timer.isRunning()) timer.stop();

    }
    private void continueApp(){
        if(!timer.isRunning()) timer.start();
    }

    private void dumpStatistics() {
        JsonParser.dumpStatisticsToJsonFile(STATS_FILENAME, Simulation.getWorldMap().getStatistics());
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        rerenderMap();
    }




}
