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

/**
 *
 * @author Jessica Lackas
 */
public class HexCoordinate {
    
    private int x, y;   
   
    public HexCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public HexCoordinate getNeighbour(Direction direction) {
        switch (direction) {
            case RIGHT:
                return new HexCoordinate(getX() + 1, getY());
            
            case DOWNLEFT:
                if (getY() % 2 == 0){
                    return new HexCoordinate(getX() - 1, getY() + 1);
                } else {
                    return new HexCoordinate(getX(), getY() + 1);
                }
            
            case UPLEFT:
                if (getY() % 2 == 0){
                    return new HexCoordinate(getX() - 1, getY() - 1);
                } else {
                    return new HexCoordinate(getX(), getY() - 1);
                }
            
            case LEFT:
                return new HexCoordinate(getX() - 1, getY());
            
            case DOWNRIGHT:
                if (getY() % 2 == 0){
                    return new HexCoordinate(getX(), getY() + 1);
                } else {
                    return new HexCoordinate(getX() + 1, getY() + 1);
                }
            
            case UPRIGHT:
                if (getY() % 2 == 0){
                    return new HexCoordinate(getX(), getY() - 1);
                } else {
                    return new HexCoordinate(getX() + 1, getY() - 1);
                }
           
            default:
                throw new IllegalArgumentException("Unknown: " + direction);
        }
    }

    /*        
    function offset_distance(a, b):
    var ac = offset_to_cube(a)
    var bc = offset_to_cube(b)
    return cube_distance(ac, bc)*/
    
    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }
    
    public int distance(HexCoordinate other) {
        return Math.max(Math.max(
                Math.abs(this.getCubeX() - other.getCubeX()),
                Math.abs(this.getCubeY() - other.getCubeY())),
                Math.abs(getCubeZ() - other.getCubeZ())
        );
    }
    
    public int getCubeX() {
        return x - (y - (y + 1)) / 2;
    }
    
    public int getCubeY() {
        return -x - y;
    }
    
    public int getCubeZ() {
        return y;
    }
    
    @Override
    public boolean equals(Object obj) {
        HexCoordinate other = (HexCoordinate) obj;
        return other.getX() == x && other.getY() == y;
    }
    
    @Override
    public String toString () {
        return "X: " + getX() + " Y: " + getY();
    }
    
    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
