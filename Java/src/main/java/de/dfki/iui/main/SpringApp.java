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
import de.dfki.iui.map.FlowMapController;
import de.dfki.iui.navigation.Navigator;
import de.dfki.iui.robot.Robot;
import de.dfki.iui.robot.SimulationRobot;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.vecmath.Point3d;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import de.dfki.iui.map.FlowMap;

@SpringBootApplication
public class SpringApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringApp.class);
    }
    
    public static BMBServlet servlet;
    public static SetTargetService targetService;

    public static void main(String[] args) throws IOException {
         
        
        FlowMap map = new FlowMap(ImageIO.read(BaxterMain.class.getResourceAsStream("/P4PHalleBaW.png")));
        

        FlowMapController mc = new FlowMapController(map, false);

        Robot robot = new SimulationRobot();//new BaxterTestRobot();
        robot.setPosition(new Point3d(22.5, 19.5, 0));
        robot.setOrientation(Math.PI/4);

        Navigator nav = new Navigator(robot, map);
        nav.transformVelocity = true;
        nav.start();
        
        
        MapImage mi = new MapImage(map, robot, nav);
        
        
        servlet = new BMBServlet(nav, robot);
        targetService = new SetTargetService(mi);

        SpringApplication.run(SpringApp.class, args);
    }

    @Bean
    public ServletRegistrationBean setTargetBean(){//@Autowired SetTargetService targetService) {
        ServletRegistrationBean bean;
        bean = new ServletRegistrationBean(targetService, "/BMB/*");
        
        
        return bean;

    }

}
