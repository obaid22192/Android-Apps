package com.golfapp.vistex;

public class DistanceCalculaor {
    private GolfCourse Gcourse;
    private float distance;
    
    public DistanceCalculaor(GolfCourse Gcourse , float dis)
    {
    	this.Gcourse = Gcourse;
    	this.distance = dis;
    }

	public GolfCourse getGcourse() {
		return Gcourse;
	}

	public void setGcourse(GolfCourse gcourse) {
		Gcourse = gcourse;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}
	
	
	
}
