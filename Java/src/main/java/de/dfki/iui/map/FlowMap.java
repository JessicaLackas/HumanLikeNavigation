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

package de.dfki.iui.map;

import de.dfki.iui.navigation.HexCoordinate;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import javax.vecmath.Point3d;

/**
 *
 * @author Jessica Lackas
 */
public final class FlowMap implements Map {

    public static double PRODUCER = Double.POSITIVE_INFINITY;
    public static int HEX_HEIGHT = 100;
    public static int HEX_WIDTH = 86;
    public static int HEX_Y_OFFSET = 75;
    public static int HEX_X_MIDDLE = 43;
    public static int HEX_Y_MIDDLE = 50;

    public static double RESOLUTION = 0.05;

    private final int height;
    private final int width;
    // private final Tile[][] map;

    private long lastChangeObst = System.currentTimeMillis();
    private long lastChangeFam = System.currentTimeMillis();

    private double[] familiarity;
    private double[] newfamiliarity;
    private double[] cost;
    private double[] newcost;
    private boolean flowObstNeeded = true;
    private boolean flowFamNeeded = true;

    private final LinkedList<MapPoint> obstchanges = new LinkedList<MapPoint>();
    private final LinkedList<MapPoint> famchanges = new LinkedList<MapPoint>();

    public FlowMap(BufferedImage bi) throws IOException {
        width = (int) (((bi.getWidth() * RESOLUTION * HEX_HEIGHT) / HEX_WIDTH) / getHexSizeM());
        height = (int) (((bi.getHeight() * RESOLUTION * HEX_HEIGHT) / HEX_Y_OFFSET) / getHexSizeM());
        cost = new double[width * height];
        newcost = new double[width * height];
        familiarity = new double[width * height];
        newfamiliarity = new double[width * height];

        int biX, biY;

        System.out.println("Width: " + width + " Height: " + height);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Point3d p = toMCoordinate(x, y);
                //p.scale(0.01);
                biX = Math.min(bi.getWidth() - 1, (int) (p.getX() / RESOLUTION));
                biY = Math.min(bi.getHeight() - 1, (int) (p.getY() / RESOLUTION));
                if (bi.getRGB(biX, biY) != -1) {
                    setObstacle(x, y, true);
                    //System.out.println("Producer " + x + " " + y);
                }
            }
        }

    }

    @Override
    public synchronized void setCost(int x, int y, double c) {
        cost[y * width + x] = c;
        lastChangeObst = System.currentTimeMillis();

    }

    public synchronized void setNewCost(int x, int y, double c) {
        newcost[y * width + x] = c;
    }
    
    public synchronized void setNewFamiliarityValue(int x, int y, double c) {
        newfamiliarity[y * width + x] = c;
    }
    

    @Override
    public synchronized void setObstacle(int x, int y, boolean v) {
        if (v) {
            cost[y * width + x] = PRODUCER;
            newcost[y * width + x] = PRODUCER;
        } else {
            cost[y * width + x] = 0;
            newcost[y * width + x] = 0;

        }
        obstchanges.add(new MapPoint(x, y));
        flowObstNeeded = true;
        lastChangeObst = System.currentTimeMillis();
    }

    @Override
    public synchronized boolean inRange(int x, int y) {
        return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    @Override
    public synchronized double getCost(int x, int y) {
        if (inRange(x, y)) {
            double result = cost[y * width + x];
            if (result == PRODUCER) {
                return 1;
            }
            return result;
        } else {
            return 1;
        }
    }

    public synchronized double getNewCost(int x, int y) {
        if (inRange(x, y)) {
            double result = newcost[y * width + x];
            if (result == PRODUCER) {
                return 1;
            }
            return result;
        } else {
            return 1;
        }
    }

    @Override
    public int getHeight() {
        return height;
    }

    public synchronized boolean hasObstChanges() {
        return !obstchanges.isEmpty();
    }

    public synchronized MapPoint nextObstChange() {
        return obstchanges.removeFirst();
    }
    
    public synchronized boolean hasFamChanges() {
        return !famchanges.isEmpty();
    }

    public synchronized MapPoint nextFamChange() {
        return famchanges.removeFirst();
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public HexCoordinate toHexCoordinate(final double x, final double y) {
        int ry = (int) Math.floor(((y * HEX_HEIGHT) / HEX_Y_OFFSET) / getHexSizeM());
        double rx = (x * HEX_HEIGHT) / getHexSizeM();
        if (ry % 2 != 0) {
            rx = rx - HEX_X_MIDDLE;
        }
        rx = Math.floor(rx / HEX_WIDTH);
        return new HexCoordinate((int) rx, ry);

    }

    @Override
    public final Point3d toMCoordinate(int x, int y) {

        double xShift;
        double yShift;

        if (y % 2 == 0) {
            xShift = (double) x * HEX_WIDTH;
            yShift = (double) y * HEX_Y_OFFSET;
        } else {
            xShift = HEX_X_MIDDLE + (double) x * HEX_WIDTH;
            yShift = (double) y * HEX_Y_OFFSET;
        }
        return new Point3d(((HEX_X_MIDDLE + xShift) / HEX_HEIGHT) * getHexSizeM(),
                ((HEX_Y_MIDDLE + yShift) / HEX_HEIGHT) * getHexSizeM(), 0);
    }

    @Override
    public double getHexSizeM() {
        return 0.1;
        //return 0.15;
    }

    public long getLastChangeObst() {
        return lastChangeObst;
    }
    
     public long getLastChangeFam() {
        return lastChangeFam;
    }

    public synchronized boolean isObstFlowNeeded() {
        return flowObstNeeded;
    }

    public synchronized void setObstFlowNeeded(boolean flowNeeded) {
        this.flowObstNeeded = flowNeeded;
    }
    
    public synchronized boolean isFamFlowNeeded() {
        return flowFamNeeded;
    }

    public synchronized void setFamFlowNeeded(boolean flowNeeded) {
        this.flowFamNeeded = flowNeeded;
    }

    @Override
    public HexCoordinate toHexCoordinate(Point3d p) {
        return toHexCoordinate(p.getX(), p.getY());
    }

    public synchronized double getFamiliarityValue(int x, int y) {
        if (inRange(x, y)) {
            double result = familiarity[y * width + x];
            if (result == PRODUCER) {
                return 1;
            }
            return result;
        } else {
            return 1;
        }
    }

    public void setFamiliar(HexCoordinate hc) {
        int x = hc.getX();
        int y = hc.getY();
        familiarity[y * width + x] = PRODUCER;
        newfamiliarity[y * width + x] = PRODUCER;

        famchanges.add(new MapPoint(x, y));
        flowFamNeeded = true;

    }

    public void switchObstCosts() {
        cost = newcost;
        newcost = Arrays.copyOf(cost, cost.length);        
    }
    
    public void switchFamCosts() {        
        familiarity = newfamiliarity;
        newfamiliarity = Arrays.copyOf(familiarity, familiarity.length);
    }

    @Override
    public boolean isObstacle(int x, int y) {
        if (!inRange(x, y)) {
            return true;
        }
        int i = x + y * width;
        return cost[i] == PRODUCER;
    }
    
    public boolean isFamiliar(int x, int y) {
        if (!inRange(x, y)) {
            return true;
        }
        int i = x + y * width;
        return familiarity[i] == PRODUCER;
    }

}
