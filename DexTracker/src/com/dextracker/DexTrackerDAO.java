package com.dextracker;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import net.epsilonlabs.datamanagementefficient.library.DataManager;

public class DexTrackerDAO {

	DataManager dm;
	Context context;

	public DexTrackerDAO(Context context) {
		this.context = context;
	}

	public int storePlayer(Player p)
	{
		dm = DataManager.getInstance(context);
		dm.open();
		
		int id = dm.add(p);
		
		dm.close();
		
		return id;
	}

	public int storeScore(Score s) {
		dm = DataManager.getInstance(context);
		dm.open();
		
		int id = dm.add(s);
		
		dm.close();
		
		return id;
	}

	public int storeGame(Game g) {
		dm = DataManager.getInstance(context);
		dm.open();
		
		int id = dm.add(g);
		
		dm.close();
		
		return id;
		
	}
	
	public ArrayList<Player> getAllPlayers() {
		ArrayList<Player> listOfPlayers = new ArrayList<Player>();
		dm = DataManager.getInstance(context);
		dm.open();

		listOfPlayers = dm.getAll(Player.class);
		dm.close();
		return listOfPlayers;
	}
	
	public Player getPlayer(String alias){
		ArrayList<Player> listOfPlayers = new ArrayList<Player>();
		Player p = new Player();
		dm = DataManager.getInstance(context);
		dm.open();
		
		listOfPlayers = dm.getAll(Player.class);
		for(Player player: listOfPlayers)
		{
			if(player.getAlias().equals(alias)){
				//Return existing player object from db
				return player;
			}
		}
		//Return empty player object
		return p;
	}

	public ArrayList<Score> getAllScores() {
		ArrayList<Score> listOfScores = new ArrayList<Score>();
		dm = DataManager.getInstance(context);
		dm.open();

		listOfScores = dm.getAll(Score.class);
		
		dm.close();
		
		return listOfScores;
	}
	
	public Score getScore(int scoreId) {
		ArrayList<Score> listOfScores = new ArrayList<Score>();
		dm = DataManager.getInstance(context);
		dm.open();

		listOfScores = dm.getAll(Score.class);
		for(Score s: listOfScores)
		{
			if(s.getId() == scoreId)
			{
				return s;
			}
		}
		
		dm.close();
		//return empty score object
		return new Score();
	}

	public ArrayList<Game> getAllGames() {
		ArrayList<Game> listOfGames = new ArrayList<Game>();
		dm = DataManager.getInstance(context);
		dm.open();

		listOfGames = dm.getAll(Game.class);

		dm.close();
		
		return listOfGames;
	}

	public ArrayList<Score> getPlayerScores(Player p, String gameMode) {
		
		ArrayList<Game> allGames = getAllGames();
		ArrayList<Score> playersScore = new ArrayList<Score>(); 
		
		//For now assuming Sequential - Add option to get other game modes too
		for(Game g: allGames )
		{
			//For a particular game mode
//if(g.getPlayerId() == p.getId() && g.getGameMode().equalsIgnoreCase(gameMode))
			if(g.getPlayerId() == p.getId())
			{
				playersScore.add(getScore(g.getScoreId()));
			}
		}
		return playersScore;
	}

}
