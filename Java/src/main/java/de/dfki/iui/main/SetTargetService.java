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

/**
 *
 * @author Jessica Lackas
 */
import de.dfki.iui.gui.MapImage;
import de.dfki.iui.hrc.baxter.BMB;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.transport.TIOStreamTransport;

//@Service
public class SetTargetService extends HttpServlet {
    
    
    MapImage mi;
    
    public SetTargetService(MapImage mi){
        this.mi = mi;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            BMB.Processor processor = new BMB.Processor(SpringApp.servlet);
            TIOStreamTransport transport = new TIOStreamTransport(req.getInputStream(), resp.getOutputStream());

            if (req.getPathInfo().endsWith("target.json")) {
                TJSONProtocol json = new TJSONProtocol(transport);
                processor.process(json, json);
                resp.setStatus(200);
            } else if (req.getPathInfo().endsWith("target.bin")) {
                TBinaryProtocol bin = new TBinaryProtocol(transport);
                processor.process(bin, bin);
                resp.setStatus(200);

            } else if (req.getPathInfo().endsWith("target.compact")) {
                TCompactProtocol compact = new TCompactProtocol(transport);
                processor.process(compact, compact);
                resp.setStatus(200);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (TException texception) {
            texception.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getPathInfo().endsWith("map.png")) { 
            resp.setContentType("image/png");
            resp.setStatus(200);
            ImageIO.write(mi.getMapImageBuffered(), "PNG", resp.getOutputStream());
        }
    }

}
