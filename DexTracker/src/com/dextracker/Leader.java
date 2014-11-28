package com.dextracker;

public class Leader {

	private String alias;
	private int accuracyScore;
	private int speedScore;
	
	public Leader(String alias, int speedScore,int accScore){
		this.alias = alias;
		this.accuracyScore = accScore;
		this.speedScore = speedScore;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public int getAccuracyScore() {
		return accuracyScore;
	}
	public void setAccuracyScore(int accuracyScore) {
		this.accuracyScore = accuracyScore;
	}
	public int getSpeedScore() {
		return speedScore;
	}
	public void setSpeedScore(int speedScore) {
		this.speedScore = speedScore;
	}
}
