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

import de.dfki.iui.navigation.HexCoordinate;
import de.dfki.iui.navigation.Navigator;
import de.dfki.iui.navigation.Path;
import de.dfki.iui.robot.Robot;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.vecmath.Matrix3d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import de.dfki.iui.map.FlowMap;

/**
 *
 * @author Jessica Lackas
 */
public class MapImage {
    
    public static int GUI_HEX_HEIGHT = 100;
    public static int GUI_HEX_WIDTH = 86;
    public static int GUI_HEX_Y_OFFSET = 75;
    public static int GUI_HEX_X_MIDDLE = 43;
    public static int GUI_HEX_Y_MIDDLE = 50;
    
    private Graphics g;
    
    private final FlowMap map;

    private BufferedImage bi;
    private BufferedImage mapI;
    private long lastMapChange = 0;
    private boolean redrawMap = true;

    
    private final Robot robot;
    private boolean drawPath = true;//false;
    private final Navigator nav;

    Timer timer = new Timer();

    private int mapX = -13050 - 100;
    private int mapY = -13000 +  2000;
    private double scale = 0.09 * 0.8;

    private AffineTransform transfor;
    int c = 0;

    public MapImage(FlowMap map, Robot robot, Navigator nav) {
        this.map = map;
        this.robot = robot;
        this.nav = nav;
        bi = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        mapI = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                paint();
            }
        }, 100, 100);
        
    }

    
    public void paint(){//Graphics g) {
        

        if (bi.getWidth() != this.getWidth() || bi.getHeight() != this.getHeight()) {
            bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
            mapI = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
            lastMapChange = 0;
        }

        if (!nav.reachedTarget() || lastMapChange == 0) {
            bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        }

        Graphics2D g2d = bi.createGraphics();
        Graphics2D g2dmap = mapI.createGraphics();
        g2d.translate(0, this.getHeight());
        g2dmap.translate(0, this.getHeight());

        g2d.scale(1, -1);
        g2dmap.scale(1, -1);

        AffineTransform save = g2d.getTransform();

        g2d.scale(scale, scale);
        g2dmap.scale(scale, scale);
        g2d.translate(mapX, mapY);
        g2dmap.translate(mapX, mapY);

        this.transfor = g2d.getTransform();

        int x1 = -mapX / GUI_HEX_WIDTH - 1;
        int y1 = -mapY / GUI_HEX_Y_OFFSET - 1;
        int x2 = x1 + (int) (20 / scale);
        int y2 = y1 + (int) (13 / scale);

        if (map.getLastChangeObst() != lastMapChange) {
            for (int x = x1; x < x2; x++) {
                for (int y = y1; y < y2; y++) {
                    drawHex(g2dmap, x, y);
                }
            }
            lastMapChange = map.getLastChangeObst();
            redrawMap = true;
        }

        if (robot != null) {
            //drawRobot(g2d);
        }
        if (drawPath) {
            //drawPath(g2d);
        }

        //1 meter red x, green y
        g2d.setTransform(save);

        g2d.setColor(Color.RED);
        g2d.drawLine(10, 10, (int) (10 + map.getHexSizeM() * 10000 * scale), 10);
        g2d.setColor(Color.GREEN);
        g2d.drawLine(10, 10, 10, (int) (10 + map.getHexSizeM() * 10000 * scale));

        g2d.setColor(Color.BLACK);
        Point3d p = new Point3d(robot.getPosition());
        p.scale(map.getHexSizeM());
        Vector3d v = new Vector3d(robot.getVelocity());
        v.scale(map.getHexSizeM());

        /*if (! nav.reachedTarget() || redrawMap) {
            g.drawImage(mapI, 0, 0, null);
            redrawMap = false;
        }

        
        g.drawImage(bi, 0, 0, this);*/
        

    }

    private void drawHex(Graphics2D g2d, final int x, final int y) {

        int xShift;
        int yShift;

        if (y % 2 == 0) {
            xShift = x * GUI_HEX_WIDTH;
            yShift = y * GUI_HEX_Y_OFFSET;
        } else {
            xShift = GUI_HEX_X_MIDDLE + x * GUI_HEX_WIDTH;
            yShift = y * GUI_HEX_Y_OFFSET;
        }

        int[] xpoints = {GUI_HEX_X_MIDDLE + xShift, xShift, xShift,
            GUI_HEX_X_MIDDLE + xShift, GUI_HEX_WIDTH + xShift, GUI_HEX_WIDTH + xShift};
        int[] ypoints = {yShift, GUI_HEX_HEIGHT - GUI_HEX_Y_OFFSET + yShift,
            GUI_HEX_Y_OFFSET + yShift, GUI_HEX_HEIGHT + yShift, GUI_HEX_Y_OFFSET + yShift,
            GUI_HEX_HEIGHT - GUI_HEX_Y_OFFSET + yShift};
        Polygon hex = new Polygon(xpoints, ypoints, 6);

        double value = map.getCost(x, y);
        Color c;
        if (map.isObstacle(x, y)) {
            c = Color.BLACK;
        } else if (value == 0) {
            c = Color.WHITE;
        } else {
            c = getColorFromCost(value);
            //c = Color.WHITE;
        }

        g2d.setColor(c);
        g2d.fillPolygon(xpoints, ypoints, 6);

        if (scale > 0.1) {

            g2d.setColor(Color.BLACK);
            g2d.drawPolygon(hex);
            
        }
    }

    private final static Color TARGET = Color.BLUE;
    private final static Color SOURCE = Color.WHITE;

    public static Color getColorFromCost(double val) {
        if (val > 1) {
            val = 1;
        }
        int r = (int) Math.floor(SOURCE.getRed() + val * (TARGET.getRed() - SOURCE.getRed()));
        int g = (int) Math.floor(SOURCE.getGreen() + val * (TARGET.getGreen() - SOURCE.getGreen()));
        int b = (int) Math.floor(SOURCE.getBlue() + val * (TARGET.getBlue() - SOURCE.getBlue()));

        return new Color(r, g, b);
    }


    private void drawPath(Graphics g2d) {

        Path path = nav.getCurrentPath();
        if (path == null) {
            return;
        }

        g2d.setColor(Color.RED);

        Graphics2D g2 = (Graphics2D) g2d;
        //g2.setStroke(new BasicStroke(7));
        Point3d last = path.getPoint(0);

        for (Point3d current : path) {
            g2.draw(new Line2D.Double(last.getX() * (GUI_HEX_HEIGHT / map.getHexSizeM()), last.getY() * (GUI_HEX_HEIGHT / map.getHexSizeM()),
                    current.getX() * (GUI_HEX_HEIGHT / map.getHexSizeM()), current.getY() * (GUI_HEX_HEIGHT / map.getHexSizeM())));
            last = current;
        }

        //ignore
    }

    private void drawHex(Graphics g2d, Color c, int x, int y) {

        int xShift;
        int yShift;

        if (y % 2 == 0) {
            xShift = x * GUI_HEX_WIDTH;
            yShift = y * GUI_HEX_Y_OFFSET;
        } else {
            xShift = GUI_HEX_X_MIDDLE + x * GUI_HEX_WIDTH;
            yShift = y * GUI_HEX_Y_OFFSET;
        }

        int[] xpoints = {GUI_HEX_X_MIDDLE + xShift, 0 + xShift, 0 + xShift,
            GUI_HEX_X_MIDDLE + xShift, GUI_HEX_WIDTH + xShift, GUI_HEX_WIDTH + xShift};
        int[] ypoints = {0 + yShift, GUI_HEX_HEIGHT - GUI_HEX_Y_OFFSET + yShift,
            GUI_HEX_Y_OFFSET + yShift, GUI_HEX_HEIGHT + yShift, GUI_HEX_Y_OFFSET + yShift,
            GUI_HEX_HEIGHT - GUI_HEX_Y_OFFSET + yShift};
        Polygon hex = new Polygon(xpoints, ypoints, 6);

        g2d.setColor(c);
        g2d.fillPolygon(xpoints, ypoints, 6);

        g2d.setColor(Color.BLACK);

        g2d.drawPolygon(hex);
    }

    private void drawRobot(Graphics2D g2d) {

        Vector3d pos = new Vector3d(robot.getPosition());

        pos.scale((GUI_HEX_HEIGHT / map.getHexSizeM()));

        double alpha = robot.getOrientation();
        Matrix3d rot = new Matrix3d();
        rot.rotZ(alpha);

        /*
        #############
        # a . . . b #
        # . . . . . #
        # . .x|y. . #
        # . . . . . #
        # d . . . c #
        #############
         */
        double depth = robot.getDepth() * (GUI_HEX_HEIGHT / map.getHexSizeM());
        double width = robot.getWidth() * (GUI_HEX_HEIGHT / map.getHexSizeM());

        Vector3d a = new Vector3d(-width / 2, -depth / 2, 0);
        Vector3d b = new Vector3d(+width / 2, -depth / 2, 0);
        Vector3d c = new Vector3d(+width / 2, +depth / 2, 0);
        Vector3d d = new Vector3d(-width / 2, +depth / 2, 0);

        rot.transform(a);
        rot.transform(b);
        rot.transform(c);
        rot.transform(d);

        a.add(pos);
        b.add(pos);
        c.add(pos);
        d.add(pos);

        int[] xpoints = {(int) a.getX(), (int) b.getX(), (int) c.getX(), (int) d.getX()};
        int[] ypoints = {(int) a.getY(), (int) b.getY(), (int) c.getY(), (int) d.getY()};

        Polygon hex = new Polygon(xpoints, ypoints, 4);

        g2d.setColor(Color.WHITE);
        g2d.fillPolygon(xpoints, ypoints, 4);

        g2d.setColor(Color.BLACK);

        g2d.drawPolygon(hex);

        //eyes
        /*Point3d e1 = new Point3d(-width / 4, depth / 3, 0);
        Point3d e2 = new Point3d(+width / 4, depth / 3, 0);
        rot.transform(e1);
        rot.transform(e2);
        e1.add(pos);
        e2.add(pos);
        g2d.setColor(Color.RED);
        g2d.fillOval((int) e1.getX(), (int) e1.getY(), 50, 50);
        g2d.fillOval((int) e2.getX(), (int) e2.getY(), 50, 50);*/
        
        //arrow
        
        int[] Xpoints = {(int) ((d.getX() + c.getX()) / 2), (int) d.getX(), (int) ((a.getX() + d.getX()) / 2),
                        (int) ((d.getX() + c.getX()) / 2), (int) ((c.getX() + b.getX()) / 2),(int) c.getX(),
                        (int) ((d.getX() + c.getX()) / 2)};
        int[] Ypoints = {(int) ((d.getY() + c.getY()) / 2), (int) d.getY(), (int) ((a.getY() + d.getY()) / 2),
                        (int) ((d.getY() + c.getY()) / 2), (int) ((c.getY() + b.getY()) / 2),(int) c.getY(),
                        (int) ((d.getY() + c.getY()) / 2)};


        g2d.setColor(Color.BLACK);
        g2d.fillPolygon(Xpoints, Ypoints, 7);

        

    }


   

    private Point3d toMCoordinate(int x, int y) {

        Point2D.Double source = new Point2D.Double(x, y);
        Point2D.Double target = new Point2D.Double();
        try {
            transfor.createInverse().transform(source, target);
        } catch (NoninvertibleTransformException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Point3d((target.x / GUI_HEX_HEIGHT) * map.getHexSizeM(), (target.y / GUI_HEX_HEIGHT) * map.getHexSizeM(), 0);

    }
    
    private int getWidth() {
        int x1 = -mapX / GUI_HEX_WIDTH - 1;        
        int x2 = x1 + (int) (20 / scale);
        return (int)((x2 - x1) * 86 * scale);
        
        
    }
    
    private int getHeight() {
        int y1 = -mapY / GUI_HEX_Y_OFFSET - 1;
        int y2 = y1 + (int) (13 / scale);
        return (int)((y2 - y1) * 75 * scale);
        
            }
    
    public BufferedImage getMapImageBuffered(){
        BufferedImage buffIm = new BufferedImage(mapI.getWidth(), mapI.getHeight(), mapI.getType());
        buffIm.getGraphics().drawImage(mapI, 0, 0, null);
        buffIm.getGraphics().drawImage(bi, 0, 0, null);
        //System.out.println("requested map image");
        return buffIm;
    }

    void keyPressed(int keyCode) {

        lastMapChange = 0;
        switch (keyCode) {
            case KeyEvent.VK_PLUS:
                scale *= 1.1;
                break;
            case KeyEvent.VK_MINUS:
                if (scale > 0.05) {
                    scale *= 0.9;
                }
                break;
            case KeyEvent.VK_UP:
                mapY += GUI_HEX_Y_OFFSET * 5;
                break;
            case KeyEvent.VK_DOWN:
                mapY -= GUI_HEX_Y_OFFSET * 5;
                break;
            case KeyEvent.VK_RIGHT:
                mapX += GUI_HEX_WIDTH * 5;
                break;
            case KeyEvent.VK_LEFT:
                mapX -= GUI_HEX_WIDTH * 5;
                break;
            case 17:
                break;
            default:
                System.out.println("Unknown code: " + keyCode);
        }
    } 
    
    public  AffineTransform getTransform() {
        return transfor;
    }
    


}

