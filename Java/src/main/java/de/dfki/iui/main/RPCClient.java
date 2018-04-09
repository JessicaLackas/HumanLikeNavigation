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

import de.dfki.iui.hrc.general3d.EulerAngles;
import de.dfki.iui.hrc.general3d.Point3d;
import de.dfki.iui.hrc.general3d.Pose;
import de.dfki.iui.hrc.baxter.BMB;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 *
 * @author Jessica Lackas
 */
public class RPCClient {

    public static void main(String[] args) throws TTransportException, TException, InterruptedException {
        TTransport trans = new THttpClient("http://robot-baxter-01:9080/bmb.rpc");
        //TTransport trans = new THttpClient("http://localhost:9080/bmb.rpc");
        TJSONProtocol prot = new TJSONProtocol(trans);
        
        BMB.Client client = new BMB.Client(prot);
        

        //while (true){
            System.out.println("Back");
            client.setTargetPose(new Pose(new Point3d(20.5, 18.5, 0), new EulerAngles(0, 0, Math.PI)));
            while (! client.hasReachedTarget()){
                Thread.sleep(1000);
            }
            /*System.out.println("and forth");
            client.setTargetPose(new Pose(new Point3d(22.70, 16.60, 0), new EulerAngles(0, 0, Math.PI)));
            while (! client.hasReachedTarget()){
                Thread.sleep(1000);
            }*/
        //}
        
        //client.savePose("A", new Pose(new Point3d(17.90, 16.20, 0), new EulerAngles(0, 0, Math.PI)));
        
        //client.setTarget("A");
    }
}
