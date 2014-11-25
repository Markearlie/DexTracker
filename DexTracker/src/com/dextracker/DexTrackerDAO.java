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

		return listOfPlayers;
	}

	public ArrayList<Score> getAllScores() {
		ArrayList<Score> listOfScores = new ArrayList<Score>();
		dm = DataManager.getInstance(context);
		dm.open();

		listOfScores = dm.getAll(Score.class);
		
		dm.close();
		
		return listOfScores;
	}

	public ArrayList<Game> getAllGames() {
		ArrayList<Game> listOfGames = new ArrayList<Game>();
		dm = DataManager.getInstance(context);
		dm.open();

		listOfGames = dm.getAll(Game.class);

		dm.close();
		
		return listOfGames;
	}
}
