package com.github.martinfrank.games.ninasgame.client.view;

import com.github.martinfrank.games.ninasgame.client.control.Control;
import com.github.martinfrank.games.ninasgame.client.model.Model;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;

@Component
public class View {

    @Autowired
    private final Model model;

    @Autowired
    private final Control control;

    @Autowired
    private final ApplicationContext appContext;


    private final JFrame frame;

    public View(ApplicationContext appContext, Model model, Control control) {
        this.appContext = appContext;
        this.model = model;
        this.control = control;
        frame = new JFrame();
        createGui();
    }

    private void createGui() {
        frame.setPreferredSize(new Dimension(400, 300));
        frame.getContentPane().add(new MainPanel(model, control), BorderLayout.CENTER);
        frame.getContentPane().add(new ControlPanel(model, control), BorderLayout.SOUTH);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                closeApp();
            }
        });

    }

    private void closeApp() {
        control.shutdown();
        closeFrame();
        stopApplication();
    }

    private void stopApplication() {
        final int returnCode = 0;
        if(appContext != null){
            SpringApplication.exit(appContext, () -> returnCode);
            System.exit(returnCode);
        }
    }

    private void closeFrame() {
        frame.setVisible(false);
        frame.dispose();
    }

    @PostConstruct
    public void show() {
        SwingUtilities.invokeLater(() -> {
            frame.pack();
            frame.setVisible(true);
        });
    }
}
