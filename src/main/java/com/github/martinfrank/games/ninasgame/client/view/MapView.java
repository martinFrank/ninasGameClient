package com.github.martinfrank.games.ninasgame.client.view;

import com.github.martinfrank.games.ninasgame.client.control.Control;
import com.github.martinfrank.games.ninasgame.client.model.Model;
import org.mapeditor.core.MapLayer;
import org.mapeditor.core.TileLayer;
import org.mapeditor.view.OrthogonalRenderer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class MapView extends JPanel {

    private final Model model;

    private final Control control;

    private OrthogonalRenderer orthogonalRenderer;

    public MapView(Model model, Control control) {
        this.model = model;
        this.control = control;
        orthogonalRenderer = new OrthogonalRenderer(model.getMap());
        setPreferredSize(orthogonalRenderer.getMapSize());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for(MapLayer mapLayer: model.getMap().getLayers()){
            TileLayer tileLayer = (TileLayer) mapLayer;
            Graphics2D graphics2D = (Graphics2D) g;
            orthogonalRenderer.paintTileLayer(graphics2D, tileLayer);
        }
    }
}
