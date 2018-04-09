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

import de.dfki.iui.robot.Robot;
import java.util.Timer;
import java.util.TimerTask;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;


/**
 *
 * @author Jessica Lackas
 */
public class Physics extends Timer {

    private Robot r;

    public Physics(Robot r) {
        this.r = r;
        scheduleAtFixedRate(new PhysicsTask(r), 10, 10);//task, delay, period
    }

}

class PhysicsTask extends TimerTask {

    private final Robot r;
    private long lastT;

    public PhysicsTask(Robot r) {
        this.r = r;
        lastT = System.currentTimeMillis();
    }

    @Override
    public void run() {
        double dt = (System.currentTimeMillis() - lastT) / 1000.0;
        lastT = System.currentTimeMillis();

        //should robot be moved?
        synchronized (r) {
            if (r.getVelocity().length() != 0) {
                Point3d pos = new Point3d();
                pos.set(r.getPosition());
                Vector3d v = new Vector3d();
                v.set(r.getVelocity());
                v.scale(dt);
                pos.add(v);
                r.setPosition(pos);
            }

            //should robot be turned?
            if (r.getTorque() != 0) {
                double orientation = r.getOrientation();
                orientation += r.getTorque() * dt;
                r.setOrientation(orientation);
            }
        }

    }

}
