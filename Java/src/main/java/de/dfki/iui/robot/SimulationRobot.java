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

package de.dfki.iui.robot;

import de.dfki.iui.gui.Physics;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

/**
 *
 * @author Jessica Lackas
 */
public class SimulationRobot implements Robot {

    private Point3d position;
    private double orientation;
    private Vector3d velocity;

    private double torque;
    private Physics p;

    public SimulationRobot() {
        position = new Point3d(0, 0, 0);
        orientation = 0;
        velocity = new Vector3d(0, 0, 0);
        torque = 0;
        p = new Physics(this);

    }

    @Override
    public double getHeight() {
        return 0.7;
    }

    @Override
    public double getWidth() {
        return 0.8;
    }

    @Override
    public double getDepth() {
        return 0.8;
    }

    @Override
    public synchronized Point3d getPosition() {
        return position;
    }

    @Override
    public synchronized void setPosition(Point3d position) {
        this.position = position;
    }

    @Override
    public synchronized void setVelocity(Vector3d v) {
        velocity = v;
    }

    @Override
    public synchronized void setTorque(double turn) {
        torque = turn;
    }

    @Override
    public synchronized Vector3d getVelocity() {
        return velocity;
    }

    @Override
    public synchronized double getTorque() {
        return torque;
    }

    @Override
    public double getOrientation() {
        return orientation;
    }

    @Override
    public synchronized void setOrientation(double ori) {
        orientation = ori;
        if (orientation < 0) {
            orientation += 2 * Math.PI;
        }
        if (orientation >= 2 * Math.PI) {
            orientation -= 2 * Math.PI;
        }
    }
}
