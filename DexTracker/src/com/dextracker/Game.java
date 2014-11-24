package com.dextracker;

import java.util.Date;

import net.epsilonlabs.datamanagementefficient.annotations.Id;

public class Game {
	@Id
	private int id;
	private double playerId, scoreId;
	private Date date;
	
	public enum GameMode{
		TYPE, SEQUENTIAL, SMASH
	}
	private GameMode gm;

	


	public Game() {
	}

	public Game(double playerId, double scoreId,GameMode gm, Date date) {
		this.playerId = playerId;
		this.scoreId = scoreId;
		this.gm = gm;
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "Game [id=" + id
				+ ", player=" + playerId
				+ ", score=" + scoreId 
				+ ", mode=" + gm
				+ ", date=" + date +
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
