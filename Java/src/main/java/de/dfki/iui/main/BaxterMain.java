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

package de.dfki.iui.main;

import de.dfki.iui.gui.GUI;
import de.dfki.iui.map.FlowMapController;
import de.dfki.iui.navigation.Navigator;
import de.dfki.iui.robot.Robot;
import java.io.IOException;
import javax.imageio.ImageIO;
import de.dfki.iui.map.FlowMap;
import java.io.File;

/**
 *
 * @author Jessica Lackas
 */
public class BaxterMain {

    public static void main(String[] args) throws IOException {
        //FlowMap map = new FlowMap(ImageIO.read(BaxterMain.class.getResourceAsStream("/P4Pneu.png")));
        FlowMap map = new FlowMap(ImageIO.read(new File("P4Pneu.png")));
        FlowMapController mc = new FlowMapController(map, true);

        Robot robot = new de.dfki.iui.robot.BMB();

        Navigator nav = new Navigator(robot, map);
        nav.transformVelocity = true;
        nav.start();

        //BMBServlet servlet = new BMBServlet(nav, robot);
        //ThriftWebservice webservice = new ThriftWebservice();
        //webservice.registerHandler(new ThriftServiceHandler("/bmb.rpc", new BMB.Processor<>(servlet)));
        //webservice.start(9080);
        //System.out.println("Started bmb server on 9080");
        System.out.println("starting GUI");

        GUI gui = new GUI(map, robot, nav);

        gui.setSize(800, 750);
        gui.setVisible(true);

    }
}
