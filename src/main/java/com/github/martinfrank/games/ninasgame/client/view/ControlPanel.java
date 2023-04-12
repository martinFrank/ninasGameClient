package com.github.martinfrank.games.ninasgame.client.view;

import com.github.martinfrank.games.ninasgame.client.control.Control;
import com.github.martinfrank.games.ninasgame.client.model.Model;

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

        JButton listenToQueueTheBasementButton = new JButton("listen to basement");
        listenToQueueTheBasementButton.addActionListener(e -> control.goListenToTheBasement());
        add(listenToQueueTheBasementButton);

        JButton listenToQueueTheWoodsButton = new JButton("listen to woods");
        listenToQueueTheWoodsButton.addActionListener(e -> control.goListenToTheWoods());
        add(listenToQueueTheWoodsButton);
    }
}
