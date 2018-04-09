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

import de.dfki.iui.navigation.Direction;
import de.dfki.iui.navigation.HexCoordinate;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Jessica Lackas
 */
public class FlowMapController extends MapController {

    private FlowMap map;
    private final double flowValue = (1.0 / 6) * 1.03;
    private Timer timer;
    private boolean[] changedobst;
    private boolean[] changedfam;

    public FlowMapController(FlowMap map, boolean connect) {
        super(map, connect);
        this.map = map;
        changedobst = new boolean[map.getWidth() * map.getHeight()];
        changedfam = new boolean[map.getWidth() * map.getHeight()];

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    flowobst();
                    flowfam();
                }
            }
        }, 1, 10);

    }

    int iterations = 0;

    private void flowobst() {

        while (map.hasObstChanges()) {
            MapPoint change = map.nextObstChange();
            setNeighboursChangedObst(change.getX(), change.getY());
        }

        if (map.isObstFlowNeeded()) {
            //long time = System.currentTimeMillis();

            for (int i = 0; i < 50; i++) {

                map.setObstFlowNeeded(false);

                for (int x = 0; x < map.getWidth(); x++) {
                    for (int y = 0; y < map.getHeight(); y++) {

                        int changedIndex = y * map.getWidth() + x;

                        if (changedobst[changedIndex] && !(map.isObstacle(x, y))) {
                            double cur = map.getCost(x, y);
                            double score = Math.round(0.95 * getNeighbourScore(x, y) * 100) / 100.0;

                            if (score != cur) {
                                map.setNewCost(x, y, score);
                                map.setObstFlowNeeded(true);
                                setNeighboursChangedObst(x, y);

                            } else {
                                changedobst[x + y * map.getWidth()] = false;
                            }
                        }
                    }
                }

                if (!map.isObstFlowNeeded()) {
                    //System.out.println("Finished Map Iteration " + iterations);
                }
                map.switchObstCosts();
            }

            //iterations++;
        }
    }

    private void flowfam() {

        while (map.hasFamChanges()) {
            MapPoint change = map.nextFamChange();
            setNeighboursChangedFam(change.getX(), change.getY());
        }

        if (map.isFamFlowNeeded()) {
            //long time = System.currentTimeMillis();

            for (int i = 0; i < 50; i++) {

                map.setFamFlowNeeded(false);

                for (int x = 0; x < map.getWidth(); x++) {
                    for (int y = 0; y < map.getHeight(); y++) {

                        int changedIndex = y * map.getWidth() + x;

                        if (changedfam[changedIndex] && !(map.isFamiliar(x, y))) {
                            double cur = map.getFamiliarityValue(x, y);
                            double score = Math.round(0.95 * getNeighbourFamScore(x, y) * 100) / 100.0;

                            if (score != cur) {
                                map.setNewFamiliarityValue(x, y, score);
                                map.setFamFlowNeeded(true);
                                setNeighboursChangedFam(x, y);

                            } else {
                                changedfam[x + y * map.getWidth()] = false;
                            }
                        }
                    }
                }

                if (!map.isFamFlowNeeded()) {
                    //System.out.println("Finished Map Iteration " + iterations);
                }
                map.switchFamCosts();
            }

            //iterations++;
        }
    }

    private void setNeighboursChangedObst(int x, int y) {
        HexCoordinate current = new HexCoordinate(x, y);
        changedobst[x + y * map.getWidth()] = true;
        for (Direction d : Direction.values()) {
            HexCoordinate neightbour = current.getNeighbour(d);
            if (map.inRange(neightbour.getX(), neightbour.getY())) {
                changedobst[neightbour.getX() + neightbour.getY() * map.getWidth()] = true;
            }
        }
    }

    private void setNeighboursChangedFam(int x, int y) {
        HexCoordinate current = new HexCoordinate(x, y);
        changedfam[x + y * map.getWidth()] = true;
        for (Direction d : Direction.values()) {
            HexCoordinate neightbour = current.getNeighbour(d);
            if (map.inRange(neightbour.getX(), neightbour.getY())) {
                changedfam[neightbour.getX() + neightbour.getY() * map.getWidth()] = true;
            }
        }
    }

    private double getNeighbourScore(int x, int y) {
        HexCoordinate current = new HexCoordinate(x, y);
        double tmp = 0;

        for (Direction d : Direction.values()) {
            HexCoordinate neightbour = current.getNeighbour(d);
            tmp = tmp + map.getCost(neightbour.getX(), neightbour.getY());
        }
        return flowValue * tmp;
    }

    private double getNeighbourFamScore(int x, int y) {
        HexCoordinate current = new HexCoordinate(x, y);
        double tmp = 0;

        for (Direction d : Direction.values()) {
            HexCoordinate neightbour = current.getNeighbour(d);
            tmp = tmp + map.getFamiliarityValue(neightbour.getX(), neightbour.getY());
        }
        return flowValue * tmp;
    }

}
