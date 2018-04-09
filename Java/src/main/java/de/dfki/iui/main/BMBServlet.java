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

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import de.dfki.iui.hrc.general3d.EulerAngles;
import de.dfki.iui.hrc.general3d.Pose;
import de.dfki.iui.hrc.baxter.BMB;
import de.dfki.iui.navigation.Navigator;
import de.dfki.iui.robot.Robot;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import javax.vecmath.Point3d;
import org.apache.thrift.TException;

/**
 *
 * @author Jessica Lackas
 */
public class BMBServlet implements BMB.Iface {

    private final Navigator navigator;
    private final Robot robot;

    public BMBServlet(Navigator navigator, Robot robot) {
        this.navigator = navigator;
        this.robot = robot;
    }
    
    

    @Override
    public synchronized void setTargetPose(Pose pose) throws TException {
        System.out.println("SETTING POSE "+pose);
        navigator.setTarget(new Point3d(pose.getPosition().x, pose.getPosition().y, pose.getPosition().z));
    }

    @Override
    public synchronized Pose getTargetPose() throws TException {
        Pose pose = new Pose(new de.dfki.iui.hrc.general3d.Point3d(navigator.getTargetPosition().x, navigator.getTargetPosition().y, 0),
                new EulerAngles(0, 0, navigator.getTargetOrientation()));
        return pose;
    }

    @Override
    public synchronized Pose getPose() throws TException {
        Pose pose = new Pose(new de.dfki.iui.hrc.general3d.Point3d(robot.getPosition().x, robot.getPosition().y, 0),
                new EulerAngles(0, 0, robot.getOrientation()));
        return pose;
    }

    @Override
    public synchronized boolean hasReachedTarget() throws TException {
        return navigator.reachedTarget();
    }

    @Override
    public void setTarget(String name) throws TException {
        try {
            Gson gson = new Gson();

            File dir = new File("./poses");
            dir.mkdirs();

            InputStream is = new FileInputStream(new File("./poses/" + name + ".pose"));

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            Pose pose = gson.fromJson(br.readLine(), Pose.class);

            setTargetPose(pose);

        } catch (IOException | JsonSyntaxException ex) {
            System.out.println("Exception: " + ex);
        }

    }

    @Override
    public void savePose(String name, Pose pose) throws TException {

        try {
            Gson gson = new Gson();

            File dir = new File("./poses");
            dir.mkdirs();

            File file = new File(dir, name + ".pose");

            if (!file.exists()) {
                file.createNewFile();
            }

            OutputStream out = new FileOutputStream(new File("./poses/" + name + ".pose"));

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            
            writer.write(gson.toJson(pose));
            
            writer.flush();

        } catch (IOException ex) {
            System.out.println("Exception: " + ex);
        }

    }

}
