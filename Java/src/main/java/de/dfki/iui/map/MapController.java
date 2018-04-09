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

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import de.dfki.iui.datatypes.PointWrapped;
import de.dfki.iui.navigation.HexCoordinate;
import java.util.logging.Level;
import java.util.logging.Logger;
import ros.RosBridge;
import ros.RosListenDelegate;

/**
 *
 * @author Jessica Lackas
 */
public abstract class MapController {

    private Map map;
    private RosBridge rb;

    public MapController(Map map, boolean connect) {
        this.map = map;

        if (connect) {
            connect();
        }
    }

    private void onObstacle(PointWrapped p) {
        HexCoordinate hc = map.toHexCoordinate(p.msg.x, p.msg.y);
        map.setObstacle(hc.getX(), hc.getY(), true);

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    this.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FlowMapController.class.getName()).log(Level.SEVERE, null, ex);
                }
                map.setObstacle(hc.getX(), hc.getY(), false);
            }
        };
        thread.start(); //remove the newly detected obstacle after 5 seconds
    }

    private void connect() {
        rb = new RosBridge();
        rb.connect("ws://localhost:9090", true);
        //rb.connect("ws://robot-baxter-02.mrk40.dfki.lan:9090", true);
        rb.subscribe("/obstacle", "geometry_msgs/Point", new RosListenDelegate() {
            Gson gson = new Gson();

            int i = 0;

            @Override
            public void receive(JsonNode data, String stringRep) {
                i++;
                if (i % 50 == 0) {
                    PointWrapped obst = gson.fromJson(stringRep, PointWrapped.class);
                    onObstacle(obst);
                }
            }
        });
    }
}
