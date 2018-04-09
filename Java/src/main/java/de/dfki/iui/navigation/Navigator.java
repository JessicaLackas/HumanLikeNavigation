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

package de.dfki.iui.navigation;

import de.dfki.iui.robot.Robot;
import java.util.HashSet;
import javax.vecmath.Matrix3d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import de.dfki.iui.map.FlowMap;

/**
 *
 * @author Jessica Lackas
 */
public class Navigator extends Thread{

    

    public boolean transformVelocity;

    public static final int LOOKAHEAD = 8;
    public static final double TOLERANCE = 0.2;
    public static final int TOLERANCE_DEGREE = 5;
    public static final int INITIAL_TOLERANCE_DEGREE = 10;
    public static final int TORQUE_SPEED_MIN = 0;//5;
    public static final int TORQUE_SPEED_MAX = 50;
    
    private long time;

    public static final double TORQUE_DEGREE_RATIO = 0.5;
    
    private int navCounter = 0;
    private int tryCounter = 0;

    public static enum State {
        REPLAN, NAVIGATE, ARRIVED, INITIAL_TURN, FINAL_TURN, TRY_AGAIN
    }

    private State state = State.ARRIVED;
    private Point3d target;
    private double targetOrientation;
    private final Robot robot;
    private final FlowMap map;
    private final Smoother smoother;
    private final AStar aStar;

    private Path currentPath;

    public Navigator(Robot robot, FlowMap map) {
        target = new Point3d(robot.getPosition());
        this.robot = robot;
        this.map = map;
        smoother = new Smoother(robot, map);
        aStar = new AStar(map, robot);
    }

    public synchronized Path getCurrentPath() {
        return currentPath;
    }

    public Point3d getTargetPosition() {
        return target;
    }

    public double getTargetOrientation() {
        return targetOrientation;
    }

    public synchronized void setTarget(Point3d newtarget) {
        target = newtarget;
        targetOrientation = Double.NaN;
        state = State.REPLAN;
        obstacles.clear();
        notifyAll();
    }

    public synchronized void setTarget(Point3d newtarget, double neworientation) {
        target = newtarget;
        targetOrientation = neworientation;
        state = State.REPLAN;
        obstacles.clear();
        notifyAll();
    }

    @Override
    public void run() {
        try {
            HexCoordinate currentHex = new HexCoordinate(0, 0);
            final Point3d currentTarget = new Point3d();
            Path path = new Path();
            path.setTime(0);

            while (!isInterrupted()) {
                synchronized (this) {
                    switch (state) {
                        case ARRIVED:
                            stopRobot();
                            System.out.println("I ARRIVED");
                            wait(); //do nothing until a target is set!
                            break;
                            case TRY_AGAIN:
                            if (tryCounter > 20){
                                System.out.println("I'M RETRYING");
                                state = State.REPLAN;
                            }
                            tryCounter++;
                            break;
                        case REPLAN:
                            stopRobot();
                            System.out.println("I AM REPLANNING");
                            currentTarget.set(target);

                            time = System.currentTimeMillis();
                            //find new path
                            
                            HexCoordinate hexTarget = map.toHexCoordinate(currentTarget);
                            HexCoordinate hexStart = map.toHexCoordinate(robot.getPosition());

                            path.setHexPath(aStar.findPath(hexStart, hexTarget));
                            path.setTime(System.currentTimeMillis());

                            //smooth new path, if a path was found
                            if (path.isPossible()) {

                                path.setPath(smoother.smooth(path.getHexPath()));
                                this.currentPath = path;
                                state = State.INITIAL_TURN;
                            } else {
                                state = State.TRY_AGAIN;
                                tryCounter = 0;
                            }
                            System.out.println("Planning took: " + (System.currentTimeMillis() - time) + " milliseconds");
                            break;
                        case NAVIGATE:
                            //Map has changed in immediate surrounding of the robot since the path has been calculated, we need to replan
                            if (mapChanged(path)) {
                                stopRobot();
                                state = State.REPLAN;
                                break;
                            }

                            //Find vectors to calculate velocity
                            int closestIndex = path.getClosestPointIndex(robot.getPosition());
                            Point3d closestPoint = path.getPoint(closestIndex);
                            Vector3d closest = vectorToPosition(closestPoint);
                            Vector3d ahead = vectorToPosition(path.getPointSave(closestIndex + LOOKAHEAD));

                            Vector3d velocity = calcVelocity(closest, ahead);
                            velocity.scale(0.2);
                            double orientation = vectorsOrientation(velocity);

                            //Turn towards new moving orientation
                            if (Math.abs(orientation - robot.getOrientation()) > (TOLERANCE_DEGREE / 180.0) * Math.PI) {
                                double torqueSpeed = getTorqueSpeed(orientation);
                                robot.setTorque(torqueSpeed);
                            }

                            if (transformVelocity) {
                                transformVelocity(velocity, orientation);
                            }
                            //start with slower speed
                            if (navCounter < 20) velocity.scale(0.5);
                            robot.setVelocity(velocity);

                            if (reachedTarget(currentTarget, TOLERANCE)) {
                                state = State.FINAL_TURN;
                                break;
                            }
                            if (tooFarOff(closestPoint)) {
                                state = State.REPLAN;
                                break;
                            }
                            if (!currentHex().equals(currentHex)) {
                                currentHex = currentHex();
                                map.setFamiliar(currentHex);
                            }
                            navCounter++;

                            break;
                        case INITIAL_TURN:
                            System.out.println("INITIAL TURN");
                            orientation = vectorsOrientation(vectorToPosition(path.getPoint(0)));

                            if (Math.abs(orientation - robot.getOrientation()) > (INITIAL_TOLERANCE_DEGREE / 180.0) * Math.PI) {
                                double torqueSpeed = getTorqueSpeed(orientation);
                                robot.setTorque(torqueSpeed);

                            } else {
                                robot.setTorque(0);
                                navCounter = 0;
                                state = State.NAVIGATE;
                                System.out.println("NAVIGATING");
                            }
                            break;
                        case FINAL_TURN:
                            if (targetOrientation == Double.NaN) {
                                state = State.ARRIVED;
                            } else if (Math.abs(targetOrientation - robot.getOrientation()) > (INITIAL_TOLERANCE_DEGREE / 180.0) * Math.PI) {
                                double torqueSpeed = getTorqueSpeed(targetOrientation);
                                robot.setTorque(torqueSpeed);

                            } else {
                                robot.setTorque(0);
                                state = State.ARRIVED;
                            }
                            break;

                    }
                }
                Thread.sleep(50);
            }
        } catch (InterruptedException ie) {
            System.out.println("Navigator has been shutdown by interrupt");
        }
    }

    private void stopRobot() {
        robot.setVelocity(new Vector3d(0, 0, 0));
        robot.setTorque(0.0);
    }

    private boolean reachedTarget(Point3d target, double tolerance) {
        double distance = robot.getPosition().distance(target);
        return distance <= tolerance;
    }

    //returns Vector from robot's current position to p
    private Vector3d vectorToPosition(Point3d p) {
        Point3d point = new Point3d();
        point.set(robot.getPosition());
        point.negate();
        point.add(p);
        return new Vector3d(point.getX(), point.getY(), 0);
    }

    //returns v's orientation in radians
    private double vectorsOrientation(Vector3d v) {

        Vector3d origin = new Vector3d(0, 1, 0);
        Vector3d left = new Vector3d(-1, 0, 0);
        Vector3d right = new Vector3d(1, 0, 0);

        double alpha = v.angle(origin);

        if (v.angle(left) > v.angle(right)) {
            alpha = 2 * Math.PI - alpha;
        }
        return alpha;
    }

    private Vector3d calcVelocity(Vector3d closest, Vector3d ahead) {
        Vector3d c = new Vector3d();
        c.set(closest);
        c.add(ahead);
        c.normalize();
        //c.scale(0.5);
        return c;
    }

    private boolean clockwise(double target) {

        double current = robot.getOrientation();

        if (current < target) {
            return target - current > Math.PI;
        } else {
            return current - target < Math.PI;
        }
    }

    private boolean tooFarOff(Point3d point) {
        return robot.getPosition().distance(point) > 40;
    }

    private double getTorqueSpeed(double orientation) {
        double torqueSpeed = Math.abs(orientation - robot.getOrientation()) * TORQUE_DEGREE_RATIO;
        torqueSpeed = Math.min(torqueSpeed, TORQUE_SPEED_MAX * (Math.PI / 180.0));
        torqueSpeed = Math.max(torqueSpeed, TORQUE_SPEED_MIN * (Math.PI / 180.0));

        if (torqueSpeed < 5 * (Math.PI / 180.0)) {
            torqueSpeed = torqueSpeed / 2;
        }

        if (clockwise(orientation)) {
            return -torqueSpeed;
        } else {
            return torqueSpeed;
        }
    }

    private void transformVelocity(Vector3d v, double o) {
        if (v.length() == 0) {
            return;
        }
        Matrix3d trans = new Matrix3d();
        trans.rotZ(-(o + 0.5 * Math.PI));//rotate to match map offset and robot's orientation
        trans.transform(v);
    }

    private HexCoordinate currentHex() {
        return map.toHexCoordinate(robot.getPosition());
    }

    public boolean reachedTarget() {
        return state == State.ARRIVED;
    }

    private boolean mapChanged(Path path) {
        if (map.getLastChangeObst() < path.getTime()) {
            return false;
        } else {
            return newObstacleAhead();
        }
    }

    private HashSet<HexCoordinate> obstacles = new HashSet();

    private boolean newObstacleAhead() {//TODO: improve this?
        Point3d ul = new Point3d(robot.getPosition());

        Point3d or = new Point3d(robot.getPosition());

        double ori = robot.getOrientation();
        double width = robot.getWidth();
        if (ori > (15 / 8.0) * Math.PI || ori < (1 / 8.0) * Math.PI) {
            ul.add(new Point3d(-width, 0, 0));
            or.add(new Point3d(1.5 * width, width, 0));

        } else if (ori < (3 / 8.0) * Math.PI) {
            or.add(new Point3d(1.5 * width, 1.5 * width, 0));

        } else if (ori < (5 / 8.0) * Math.PI) {
            ul.add(new Point3d(0, -width, 0));
            or.add(new Point3d(1.5 * width, 1.5 * width, 0));

        } else if (ori < (7 / 8.0) * Math.PI) {
            ul.add(new Point3d(0, - 1.5 * width, 0));
            or.add(new Point3d(1.5 * width, 0, 0));

        } else if (ori < (9 / 8.0) * Math.PI) {
            ul.add(new Point3d(-width, - 1.5 * width, 0));
            or.add(new Point3d(width, 0, 0));

        } else if (ori < (11 / 8.0) * Math.PI) {
            ul.add(new Point3d(- 1.5 * width, - 1.5 * width, 0));

        } else if (ori < (13 / 8.0) * Math.PI) {
            ul.add(new Point3d(- 1.5 * width, width, 0));
            or.add(new Point3d(0, width, 0));

        } else {
            ul.add(new Point3d(- 1.5 * width, 0, 0));
            or.add(new Point3d(0, 1.5 * width, 0));
        }

        boolean buffer = false;
        HexCoordinate hcUl = map.toHexCoordinate(ul);
        HexCoordinate hcOr = map.toHexCoordinate(or);
        //System.out.println("robot at " + map.toHexCoordinate(robot.getPosition()));
        for (int x = hcUl.getX(); x < hcOr.getX(); x++) {
            for (int y = hcUl.getY(); y < hcOr.getY(); y++) {
                HexCoordinate hc = new HexCoordinate(x, y);
                //System.out.println("checking " + hc);
                //System.out.println(map.isProducer(x, y));
                buffer = buffer || (map.isObstacle(x, y) && !obstacles.contains(hc));
                obstacles.add(hc);
                if (buffer) return true;
            }
        }

        return buffer;

    }
}
