package com.github.martinfrank.games.rpgclient.view;

import com.github.martinfrank.games.rpgclient.control.Control;
import com.github.martinfrank.games.rpgclient.model.Model;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
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

//    public View(ModelView modelView, Control control) {
//        this(null, modelView, control);
//    }

    public View(ApplicationContext appContext, Model model, Control control) {
        this.appContext = appContext;
        this.model = model;
        this.control = control;
        frame = new JFrame();
        createGui();
    }

    private void createGui() {
        frame.getContentPane().add(new ControlPanel(model, control), BorderLayout.SOUTH);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                closeApp();
            }
        });

    }

    private void closeApp() {
        frame.setVisible(false);
        frame.dispose();
        final int returnCode = 0;
        if(appContext != null){
            SpringApplication.exit(appContext, () -> returnCode);
            System.exit(returnCode);
        }
    }

    @PostConstruct
    public void show() {
        SwingUtilities.invokeLater(() -> {
            frame.pack();
            frame.setVisible(true);
        });
    }
}
