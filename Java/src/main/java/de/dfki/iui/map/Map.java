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
import javax.vecmath.Point3d;

/**
 *
 * @author Jessica Lackas
 */
public interface Map {
    

    public int getHeight();

    public int getWidth();

    public double getCost(int x, int y);

    public void setCost(int x, int y, double cost);

    public boolean isObstacle(int x, int y);

    public void setObstacle(int x, int y, boolean v);

    public HexCoordinate toHexCoordinate(double x, double y);//x and y in cm with 0,0 in lower left corner

    public HexCoordinate toHexCoordinate(Point3d p);

    public Point3d toMCoordinate(int x, int y);//x, y HexCoordinate

    public double getHexSizeM();

    public boolean inRange(int x, int y);

}
