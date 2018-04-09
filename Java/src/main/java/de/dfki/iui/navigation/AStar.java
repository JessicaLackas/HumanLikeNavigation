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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import de.dfki.iui.map.FlowMap;

/**
 *
 * @author Jessica Lackas
 */
public class AStar {

    private FlowMap map;
    private Robot robot;

    public AStar(FlowMap map, Robot robot) {
        this.map = map;
        this.robot = robot;
    }

    public boolean isBlocked(HexCoordinate c) {

        /*        
        #############
        # . . u . d #
        # . . u d . #
        # . . c v v #
        # . . . . . #
        # . . . . . #
        #############        
         */
        double u = robot.getDepth() / (2 * map.getHexSizeM()) + 2;
        double v = robot.getWidth() / (2 * map.getHexSizeM()) + 2;

        int d = (int) Math.sqrt(v * v + u * u);

        boolean blocked = false;

        for (int x = c.getX() - d; x < c.getX() + d; x++) {
            for (int y = c.getY() - d; y < c.getY() + d; y++) {
                blocked = blocked || map.getCost(x, y) >= 1;
            }
        }
        return blocked;
    }

    public LinkedList<HexCoordinate> findPath(HexCoordinate start, HexCoordinate end) {

        PriorityQueue<Node> open = new PriorityQueue<>(new NodeComparator());
        HashMap<HexCoordinate, Node> closed = new HashMap<>();

        //Put start in queue
        Node startNode = new Node(null, start, null);
        startNode.move_cost = 0;
        startNode.estimated_cost = start.distance(end);
        open.add(startNode);

        //While there are still nodes in queue
        while (!open.isEmpty()) {
            //Get node with lowest cost
            Node current = open.poll();

            //Put it in the closed list
            closed.put(current.coordinates, current);

            //If end node is found end search
            if (current.coordinates.equals(end)) {
                break;
            }

            //Visit all neighbours
            for (Direction direction : Direction.values()) {

                if (!(isBlocked(current.coordinates.getNeighbour(direction)))) {

                    //Check whether direction changes in vertical/horizontal
                    Direction currentDirection = current.come_from_direction;
                    double addedCost = 0.0;
                    if (currentDirection != direction) {
                        if (!(currentDirection == Direction.DOWNLEFT && direction == Direction.DOWNRIGHT ||
                              currentDirection == Direction.DOWNRIGHT && direction == Direction.DOWNLEFT ||
                              currentDirection == Direction.UPLEFT && direction == Direction.UPRIGHT ||
                              currentDirection == Direction.UPRIGHT && direction == Direction.UPLEFT)){
                        addedCost = 1;//0.1;
                        }
                    }

                    Node neighbour = new Node(direction, current.coordinates.getNeighbour(direction), current);
                    neighbour.move_cost = current.move_cost + 1 + getCost(neighbour.coordinates) + addedCost - getFamiliarityValue(neighbour.coordinates);
                    neighbour.estimated_cost = neighbour.coordinates.distance(end);

                    //If not blocked and not in closed list
                    if (!closed.values().contains(neighbour)) {
                        if (!open.contains(neighbour)) {
                            open.add(neighbour);
                        } else {
                            //If cost to this node are smaller than to the old one
                            PriorityQueue<Node> tmp = new PriorityQueue<>(new NodeComparator());
                            while (!open.isEmpty()) {
                                Node node = open.poll();
                                if (node.equals(neighbour) && node.getCost() > neighbour.getCost()) {
                                    node = neighbour;
                                }
                                tmp.add(node);
                            }
                            open = tmp;
                        }
                    }
                }
            }
        }

        LinkedList<HexCoordinate> path = new LinkedList<>();
        Node tmp = closed.get(end);
        if (tmp == null) {
            System.out.println("No path found");
            return path;
        }
        while (!tmp.coordinates.equals(start)) {
            path.addFirst(tmp.coordinates);
            tmp = tmp.getParent();
        }
        return path;
    }

    private double getCost(HexCoordinate c) {

        /*        
        #############
        # . . u . d #
        # . . u d . #
        # . . c v v #
        # . . . . . #
        # . . . . . #
        #############        
         */
        double u = robot.getDepth() / (2 * map.getHexSizeM()) + 1;
        double v = robot.getWidth() / (2 * map.getHexSizeM()) + 1;
        int d = (int) Math.sqrt(v * v + u * u);
        
        double cost = 0;

        for (int x = c.getX() - d; x < c.getX() + d; x++) {
            for (int y = c.getY() - d; y < c.getY() + d; y++) {
                cost = cost + map.getCost(x, y);
            }
        }
        return cost;
    }
    
    private double getFamiliarityValue(HexCoordinate c) {
        return map.getFamiliarityValue(c.getX(), c.getY());
    }
}
