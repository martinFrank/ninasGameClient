package com.github.martinfrank.games.rpgclient.view;

import com.github.martinfrank.games.rpgclient.control.Control;
import com.github.martinfrank.games.rpgclient.model.Model;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlPanel extends JPanel {

    private final Model modelView;
    private final Control control;
    public ControlPanel(Model modelView, Control control) {
        this.modelView = modelView;
        this.control = control;
        createControls();
    }

    private void createControls() {
        JButton loadMapButton = new JButton("load map");
        loadMapButton.addActionListener(e -> control.loadMap());
        add(loadMapButton);

        JButton gotToWoodButton = new JButton("go to wood");
        gotToWoodButton.addActionListener(e -> control.goToWood());
        add(gotToWoodButton);
    }
}
