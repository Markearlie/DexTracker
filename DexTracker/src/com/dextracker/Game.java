package com.dextracker;


import net.epsilonlabs.datamanagementefficient.annotations.Id;

public class Game {
	@Id
	private int id;
	private int playerId, scoreId;
	private String gameMode;

	


	public Game() {
	}

	public Game(int playerId, int scoreId,String gm) {
		this.playerId = playerId;
		this.scoreId = scoreId;
		this.gameMode = gm;
	}
	
	@Override
	public String toString() {
		return "Game [id=" + id
				+ ", player=" + playerId
				+ ", score=" + scoreId 
				+ ", mode=" + gameMode +
				"]";
	}
	
	public int getId(){
		return id;
	}
	
	
	//Getters and setters
	public double getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getScoreId() {
		return scoreId;
	}

	public void setScoreId(int scoreId) {
		this.scoreId = scoreId;
	}


	public String getGameMode() {
		return gameMode;
	}

	public void setGameMode(String gameMode) {
		this.gameMode = gameMode;
	}
}
