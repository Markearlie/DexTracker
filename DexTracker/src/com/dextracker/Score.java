package com.dextracker;

import net.epsilonlabs.datamanagementefficient.annotations.Id;

public class Score {
	@Id
	private int id;
	public double speedScore, accuracyScore;

	public double getSpeedScore() {
		return speedScore;
	}

	public void setSpeedScore(double speedScore) {
		this.speedScore = speedScore;
	}

	public double getAccuracyScore() {
		return accuracyScore;
	}

	public void setAccuracyScore(double accuracyScore) {
		this.accuracyScore = accuracyScore;
	}

	public Score() {
	}

	public Score(double speed, double accuracy) {
		this.speedScore = speed;
		this.accuracyScore = accuracy;
	}
	
	@Override
	public String toString() {
		return "Score [id=" + id + ", speed=" + speedScore + 
				", accuracy=" + accuracyScore + "]";
	}
	
	public int getId(){
		return id;
	}
}
