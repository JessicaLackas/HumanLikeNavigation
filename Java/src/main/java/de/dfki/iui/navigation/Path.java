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

import java.util.Iterator;
import java.util.LinkedList;
import javax.vecmath.Point3d;

/**
 *
 * @author Jessica Lackas
 */
public class Path implements Iterable<Point3d> {

    private long time;
    private boolean possible;
    private boolean smoothed;

    private LinkedList<HexCoordinate> hexPath;
    private LinkedList<Point3d> path;

    public void setTime(long time) {
        this.time = time;
    }

    public void setHexPath(LinkedList<HexCoordinate> hexPath) {
        this.hexPath = hexPath;
        possible = hexPath != null && !hexPath.isEmpty();
    }

    public void setPath(LinkedList<Point3d> path) {
        this.path = path;
        smoothed = path != null && !path.isEmpty();
    }

    public boolean isSmoothed() {
        return smoothed;
    }

    public LinkedList<HexCoordinate> getHexPath() {
        return hexPath;
    }

    public LinkedList<Point3d> getPath() {
        return path;
    }

    public long getTime() {
        return time;
    }

    public boolean isPossible() {
        return possible;
    }

    public int getLength() {
        return path.size();
    }

    public Point3d getPoint(int i) {
        return path.get(i);
    }

    public Point3d getPointSave(int i) {
        return path.get(Math.min(i, path.size() - 1));
    }

    public int getClosestPointIndex(final Point3d p) {
        int result = 0;
        for (int i = 0; i < path.size(); i++) {
            if (getPoint(i).distance(p) < getPoint(result).distance(p)) {
                result = i;
            }
        }
        return result;
    }

    @Override
    public Iterator<Point3d> iterator() {
        return path.iterator();
    }

}
