package com.github.martinfrank.games.ninasgame.client.view;

import com.github.martinfrank.games.ninasgame.client.control.Control;
import com.github.martinfrank.games.ninasgame.client.model.Model;
import com.github.martinfrank.games.ninasgame.client.view.notification.ViewNotification;
import com.github.martinfrank.games.ninasgame.client.view.notification.ViewNotificationType;
import com.github.martinfrank.games.ninasgame.client.view.notification.ViewReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Dimension;

public class MainPanel extends JPanel implements ViewReference {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainPanel.class);
        private final Model model;
    private final Control control;

    public MainPanel(Model model, Control control) {
        this.model = model;
        this.control = control;
        control.addViewReference(this);
        setContent(new LoginView(model, control));
    }

    private void setContent(JComponent accountView) {
        removeAll();
        add(accountView);
    }

    @Override
    public void updateView(ViewNotification viewNotification) {
        LOGGER.debug("get new ViewNotification {}",viewNotification.type);
        if(viewNotification.type == ViewNotificationType.SHOW_MAP_PANEL ){
            JScrollPane jScrollPane = new JScrollPane(new MapView(model, control));
            jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            jScrollPane.setPreferredSize(getSize());
            setContent(jScrollPane);
            updateUI();
        }
    }
}
