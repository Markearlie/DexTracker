package com.dextracker;

import net.epsilonlabs.datamanagementefficient.annotations.Id;

public class Player {

	@Id
	private int id;
	public String alias;

	public Player() {
	}

	public Player(String alias) {
		this.alias = alias;
	}
	
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	@Override
	public String toString() {
		return "Player [id=" + id + ", alias=" + alias + "]";
	}
	
	public int getId(){
		return id;
	}
}
