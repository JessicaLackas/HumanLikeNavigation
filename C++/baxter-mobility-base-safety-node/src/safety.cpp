#include "ros/ros.h"
#include "std_msgs/String.h"
#include "geometry_msgs/TwistStamped.h"
#include "sensor_msgs/LaserScan.h"
#include "mobility_base_core_msgs/SetMaxSpeed.h"
#include "geometry_msgs/Twist.h"
#include "geometry_msgs/Point.h"
#include "nav_msgs/Odometry.h"
#include "math.h"
#include <tf/transform_broadcaster.h>


ros::Publisher vel_pub;
ros::Publisher obst_pub;
ros::Time last_time;
ros::Time last_print_time;

double min_distance = 0.0;

double REDUCE_DISTANCE = 2.8;
double STOP_DISTANCE = 0.6;
double max_speed_factor = 1.0;

double obst_ori = 0.0;
geometry_msgs::Point obst_p;

double pos_x = 0.0;
double pos_y = 0.0;
double pos_ori = 0.0;

void laserCallback(const sensor_msgs::LaserScan scan){
  	ros::Time current_time;
  	current_time = ros::Time::now();

	//laser information
        min_distance = 30.0;
	for(unsigned int i = 0; i < scan.ranges.size(); i++){
       		if (scan.ranges[i] < min_distance){
			min_distance = scan.ranges[i];
			obst_ori = scan.angle_increment * i + (270 / 180) * M_PI;
			if (obst_ori > 2 * M_PI){
				obst_ori = obst_ori - 2 * M_PI;
			}		
		}       		
     	}
    
        current_time = ros::Time::now();
   
        //reduce max_speed according to distance to obstacle
	if (min_distance < REDUCE_DISTANCE)	{
		if (min_distance < STOP_DISTANCE){
			max_speed_factor = 0.0;
		}else{
		max_speed_factor =  (STOP_DISTANCE - min_distance) / (STOP_DISTANCE - REDUCE_DISTANCE);
		}	
	}else{
	max_speed_factor = 1.0;
	}

	//print the message every 10 secs
        if ((current_time - last_print_time).toSec() > 10){
          ROS_INFO("Closest obstacle in %f m at %f degrees. Obstacle at x: %f y: %f.", min_distance, obst_ori * (180 / M_PI), obst_p.x, obst_p.y);
          last_print_time = ros::Time::now();
        }
   
        last_time = current_time;

	//publish obstacle
	if (max_speed_factor < 1){

		if (pos_ori < 0){
			pos_ori = pos_ori + 2 * M_PI;
			if (pos_ori < 0){
				pos_ori = pos_ori + 2 * M_PI;
			}
		} else 
		if (pos_ori > 2 * M_PI){
			pos_ori = pos_ori - 2 * M_PI;
			if (pos_ori > 2 * M_PI){
				pos_ori = pos_ori - 2 * M_PI;
			}
		}

		double r = pos_ori + obst_ori;

		double delta_x = - sin(r) * min_distance;
        	double delta_y = cos(r) * min_distance;
		
		obst_p.x = pos_x + delta_x;
		obst_p.y = pos_y + delta_y;

		obst_pub.publish(obst_p);
	}
        
}

	
void velCallback(const geometry_msgs::Twist twist){

	//twist information
        double x = twist.linear.x;
        double y = twist.linear.y;


	double max_speed = max_speed_factor * 2.2 ;
	double max_rot = max_speed_factor * (2.0 / 3) * M_PI;
	double speed = sqrt(x*x + y*y);
	double rot = twist.angular.z;

	geometry_msgs::Twist safe_twist;

	if (speed > max_speed){
		x = max_speed * (x / speed);
		y = max_speed * (y / speed);
	}

	safe_twist.linear.x = x;
	safe_twist.linear.y = y;

	safe_twist.angular = twist.angular;

	if(rot > max_rot){
		safe_twist.angular.z = max_rot;
	}


	//publish the message
        vel_pub.publish(safe_twist);

		
}

void odomCallback(const nav_msgs::Odometry odom){

	pos_x = odom.pose.pose.position.x;
	pos_y = odom.pose.pose.position.y;
	pos_ori = tf::getYaw(odom.pose.pose.orientation);

}

int main(int argc, char* argv[]){
  
	ros::init(argc, argv, "safety_node");
	ros::NodeHandle n;
	last_time = ros::Time::now();

	vel_pub = n.advertise<geometry_msgs::Twist>
	("/mobility_base/cmd_vel_raw", 50);

	obst_pub = n.advertise<geometry_msgs::Point>
	("/obstacle", 50);

	ros::Subscriber laser_sub = n.subscribe
	("/laser_birdcage_r2000/scan_filtered", 1000, laserCallback);

	ros::Subscriber vel_safe_sub = n.subscribe
	("/mobility_base/cmd_vel_safe", 1000, velCallback);

	ros::Subscriber odom_sub = n.subscribe
	("/odom", 1000, odomCallback);
	ros::spin(); 

}



