#include "ros/ros.h"
#include "std_msgs/String.h"
#include "geometry_msgs/TwistStamped.h"
#include "nav_msgs/Odometry.h"
#include "math.h"
#include <tf/transform_broadcaster.h>
#include <sstream>


ros::Publisher odom_pub;
tf::TransformBroadcaster * odom_broadcaster;
ros::Time last_time;
ros::Time last_print_time;
double x = 0.0;
double y = 0.0;
double r = 0.0;
double r_offset = 0.0;


void twistCallback(const geometry_msgs::TwistStamped::ConstPtr& msg)
{
  //ROS_INFO("I heard: [%d]", (int) msg->twist.linear.x);
  ros::Time current_time;
  current_time = ros::Time::now();
  

//-----------------------------------------------------------------
      
    
        //twist information
        double vx = (msg->twist.linear.x);
        double vy = (msg->twist.linear.y);
        double vr = (msg->twist.angular.z);
    
        current_time = ros::Time::now();
   
        //compute odometry in a typical way given the velocities of the robot
        double dt = (current_time - last_time).toSec();
        double delta_x = (vx * cos(r) - vy * sin(r)) * dt;
        double delta_y = (vx * sin(r) + vy * cos(r)) * dt;
        double delta_r = vr * dt;
    
        x += delta_x;
        y += delta_y;
        r += delta_r;
    
        //since all odometry is 6DOF we'll need a quaternion created from yaw
        geometry_msgs::Quaternion odom_quat = tf::createQuaternionMsgFromYaw(r + r_offset);
    
        //first, we'll publish the transform over tf
        geometry_msgs::TransformStamped odom_trans;
        odom_trans.header.stamp = current_time;
        odom_trans.header.frame_id = "odom";
        odom_trans.child_frame_id = "base_footprint";
    
        odom_trans.transform.translation.x = x;
        odom_trans.transform.translation.y = y;
        odom_trans.transform.translation.z = 0.0;
        odom_trans.transform.rotation = odom_quat;
    
        //send the transform
        odom_broadcaster->sendTransform(odom_trans);
    
        //next, we'll publish the odometry message over ROS
        nav_msgs::Odometry odom;
        odom.header.stamp = current_time;
        odom.header.frame_id = "odom";
        odom.child_frame_id = "base_footprint";
     
        //set the position
        odom.pose.pose.position.x = x;
        odom.pose.pose.position.y = y;
        odom.pose.pose.position.z = 0.0;
        odom.pose.pose.orientation = odom_quat;
   
        //set the velocity
        odom.twist.twist.linear.x = vx;
        odom.twist.twist.linear.y = vy;
        odom.twist.twist.angular.z = vr;
   
        //publish the message
        odom_pub.publish(odom);


        //print the message every 5 secs
        if ((current_time - last_print_time).toSec() > 5)
        {
          ROS_INFO("Odometry( x: %f, y: %f, r: %f)", x, y, r + r_offset);
          last_print_time = ros::Time::now();
        }
   
        last_time = current_time;
//-----------------------------------------------------------------
}


int main(int argc, char* argv[])
{
  if (argc > 4)
  { 

    std::string input = argv[1];
    std::stringstream ss(input);

    ss >> x;

    input = argv[2];
    std::stringstream ss2(input);

    ss2 >> y;

    input = argv[3];
    std::stringstream ss3(input);

    ss3 >> r;
    r = r * M_PI / 180.0;

    input = argv[4];
    std::stringstream ss4(input);

    ss4 >> r_offset;
    r_offset = r_offset * M_PI / 180.0;

  }
  ros::init(argc, argv, "odom");
  ros::NodeHandle n;
  last_time = ros::Time::now();;
  odom_broadcaster = new tf::TransformBroadcaster(); 

  odom_pub = n.advertise<nav_msgs::Odometry>("odom", 50);

  ros::Subscriber sub = n.subscribe("/mobility_base/twist", 1000, twistCallback);
  ros::spin(); 
}
