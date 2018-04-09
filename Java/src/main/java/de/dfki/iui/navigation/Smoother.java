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
import java.util.LinkedList;
import javax.vecmath.Point3d;
import de.dfki.iui.map.FlowMap;

/**
 *
 * @author Jessica Lackas
 */
public class Smoother {

    private Robot robot;
    private FlowMap map;

    public Smoother(Robot robot, FlowMap map) {
        this.map = map;
        this.robot = robot;
    }

    public LinkedList<Point3d> smooth(LinkedList<HexCoordinate> path) throws IllegalArgumentException{
        
        if (path == null || path.isEmpty()) throw new IllegalArgumentException();

        LinkedList<Point3d> points = new LinkedList<>();

        for (HexCoordinate hc : path) {
            points.add(map.toMCoordinate(hc.getX(), hc.getY()));
        }

        //1st iteration        
        LinkedList<Point3d> points1 = new LinkedList<>();

        Point3d current;
        boolean first;

        current = points.getFirst();
        points1.add(current);
        first = true;

        for (Point3d p : points) {
            Point3d last = current;
            current = p;
            if (!first) {
                Point3d middle = new Point3d((last.getX() + current.getX()) / 2, 
                                     (last.getY() + current.getY()) / 2, 0);
                
                points1.add(middle);
            } else {
                first = false;
            }
        }
        points1.add(points.getLast());

        //2nd iteration        
        LinkedList<Point3d> points2 = new LinkedList<>();

        current = points1.getFirst();
        points2.add(current);
        first = true;

        for (Point3d p : points1) {
            Point3d last = current;
            current = p;
            if (!first) {
                Point3d middle = new Point3d((last.getX() + current.getX()) / 2, 
                                     (last.getY() + current.getY()) / 2, 0);
                points2.add(middle);
                
            } else {
                first = false;
            }
        }
        points2.add(points1.getLast());
        
        return points2;
    }
}
