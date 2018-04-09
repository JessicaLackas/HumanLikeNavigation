/*
 * MIT License
 *
 * Copyright (c) 2018 Jessica Lackas
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.dfki.iui.gui;

import java.awt.Graphics;
import javax.swing.JFrame;
import de.dfki.iui.map.*;
import de.dfki.iui.navigation.HexCoordinate;
import de.dfki.iui.navigation.Navigator;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.vecmath.Point3d;

/**
 *
 * @author Jessica Lackas
 */
public class WebGUI extends JFrame implements MouseListener, KeyListener, MouseMotionListener {

    public static int GUI_HEX_HEIGHT = 100;
    public static int GUI_HEX_WIDTH = 86;
    public static int GUI_HEX_Y_OFFSET = 75;
    public static int GUI_HEX_X_MIDDLE = 43;
    public static int GUI_HEX_Y_MIDDLE = 50;
    
    MapImage mapImage;

    private final FlowMap map;
    private final Navigator nav;

    Timer timer = new Timer();

    int c = 0;

    public WebGUI(FlowMap map, Navigator nav, MapImage mapImage) {
        
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.map = map;
        this.nav = nav;
        this.mapImage = mapImage;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, 100, 100);
        this.addMouseListener(this);
        this.addKeyListener(this);
        this.addMouseMotionListener(this);
    }

    @Override
    public void paint(Graphics g) {
        
        g.drawImage(mapImage.getMapImageBuffered(), 0, 0, this);
        

    }



    @Override
    public void mouseClicked(MouseEvent e) {

        Point3d p = toMCoordinate(e.getX(), e.getY());
        
        System.out.println("clicket on: " + p);

        HexCoordinate hc = map.toHexCoordinate(p.getX(), p.getY());


        if (e.isControlDown()) {
            map.setObstacle(hc.getX(), hc.getY(), !map.isObstacle(hc.getX(), hc.getY()));
        } else {
            nav.setTarget(p);
        }
        repaint();
    }


    @Override
    public void mousePressed(MouseEvent e) {
        //ignore
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //ignore
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //ignore
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //ignore
    }


   

    @Override
    public void keyTyped(KeyEvent e) {
        //ignore
    }

    @Override
    public void keyPressed(KeyEvent e) {
        mapImage.keyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //ignore
    }

    private Point3d toMCoordinate(int x, int y) {

        Point2D.Double source = new Point2D.Double(x, y);
        Point2D.Double target = new Point2D.Double();
        try {
            mapImage.getTransform().createInverse().transform(source, target);
        } catch (NoninvertibleTransformException ex) {
            Logger.getLogger(WebGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Point3d((target.x / GUI_HEX_HEIGHT) * map.getHexSizeM(), (target.y / GUI_HEX_HEIGHT) * map.getHexSizeM(), 0);

    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

}
