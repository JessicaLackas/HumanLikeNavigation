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

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import de.dfki.iui.datatypes.Odometry;
import de.dfki.iui.datatypes.OdometryWrapped;
import de.dfki.iui.datatypes.Twist;
import java.util.Timer;
import java.util.TimerTask;
import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import ros.RosBridge;
import ros.RosListenDelegate;

/**
 *
 * @author Jessica Lackas
 */
public class BMB implements Robot {

    private RosBridge rb;
    private Gson gson;

    private Vector3d velocity = new Vector3d(0, 0, 0);
    private double torque = 0;

    private final Matrix4d odomRotation;
    private final Matrix4d odomTranslation;
    private final Timer timer = new Timer();

    public BMB() {

        this.odomRotation = new Matrix4d();
        this.odomRotation.setIdentity();
        this.odomTranslation = new Matrix4d();
        this.odomTranslation.setIdentity();

        gson = new Gson();
        velocity = new Vector3d();

        rb = new RosBridge();
        rb.connect("ws://localhost:9090", true);
        //rb.connect("ws://robot-baxter-02.mrk40.dfki.lan:9090", true);
        rb.advertise("/mobility_base/cmd_vel_safe", "geometry_msgs/Twist");
        rb.subscribe("/odom", "nav_msgs/Odometry", new RosListenDelegate() {
            Gson gson = new Gson();

            int i = 0;

            @Override
            public void receive(JsonNode data, String stringRep) {
                i++;
                if (i % 50 == 0) {
                    Odometry odom = gson.fromJson(stringRep, OdometryWrapped.class).msg;
                    onOdometry(odom);
                }
            }
        });

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                setRemoteVelocity();
            }
        }, 50, 50);//task, delay, period
    }

    private synchronized void setRemoteVelocity() {

        if (this.getVelocity().length() != 0 || this.getTorque() != 0) {            
            Twist twist = new Twist(this.getVelocity(), new Vector3d(0, 0, this.getTorque()));
            //System.out.println(twist);
            rb.publishJsonMsg("/mobility_base/cmd_vel_safe", "geometry_msgs/Twist", gson.toJson(twist));
        }

    }

    @Override
    public double getHeight() {
        return 0.7;//in m
    }

    @Override
    public double getWidth() {
        return 0.8;//in m
    }

    @Override
    public double getDepth() {
        return 0.8;//in m
    }

    @Override
    public synchronized Point3d getPosition() {
        Point3d origin = new Point3d(0, 0, 0);
        odomTranslation.transform(origin);

        return origin;
    }

    @Override
    public synchronized void setPosition(Point3d position) {
        throw new UnsupportedOperationException("Baxter Robot cannot teleport!");
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
        Vector3d rotated = new Vector3d(0, -1, 0);
        odomRotation.transform(rotated);
        return vectorsOrientation(rotated);
    }

    @Override
    public synchronized void setOrientation(double ori) {
        throw new UnsupportedOperationException("Baxter Robot cannot teleport!");
    }

    private synchronized void onOdometry(Odometry odom) {
        
        this.odomRotation.setIdentity();
        this.odomTranslation.setIdentity();

        this.odomRotation.set(odom.pose.pose.orientation);
        this.odomTranslation.setTranslation(new Vector3d(odom.pose.pose.position));

        this.odomRotation.invert();
    }

    private double vectorsOrientation(Vector3d v) {

        Vector3d origin = new Vector3d(0, -1, 0);
        Vector3d left = new Vector3d(-1, 0, 0);
        Vector3d right = new Vector3d(1, 0, 0);

        double alpha = v.angle(origin);

        if (v.angle(left) > v.angle(right)) {
            alpha = 2 * Math.PI - alpha;
        }
        return alpha;
    }
}
