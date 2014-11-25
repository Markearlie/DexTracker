package com.dextracker;


import net.epsilonlabs.datamanagementefficient.annotations.Id;

public class Game {
	@Id
	private int id;
	private double playerId, scoreId;
	private String gm;

	


	public Game() {
	}

	public Game(double playerId, double scoreId,String gm) {
		this.playerId = playerId;
		this.scoreId = scoreId;
		this.gm = gm;
	}
	
	@Override
	public String toString() {
		return "Game [id=" + id
				+ ", player=" + playerId
				+ ", score=" + scoreId 
				+ ", mode=" + gm +
				"]";
	}
	
	public int getId(){
		return id;
	}
	
	
	//Getters and setters
	public double getPlayerId() {
		return playerId;
	}

	public void setPlayerId(double playerId) {
		this.playerId = playerId;
	}

	public double getScoreId() {
		return scoreId;
	}

	public void setScoreId(double scoreId) {
		this.scoreId = scoreId;
	}

}
