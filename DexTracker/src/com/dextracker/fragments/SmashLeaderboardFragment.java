package com.dextracker.fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.dextracker.DexTrackerDAO;
import com.dextracker.Leader;
import com.dextracker.Player;
import com.dextracker.R;
import com.dextracker.Score;
import com.dextracker.adapter.CustomListViewAdapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SmashLeaderboardFragment extends Fragment {

	private ListView lv;
	DexTrackerDAO dao;
	Context context;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.leaderboard_sequential_fragment, container, false);
		lv = (ListView) rootView.findViewById(R.id.sequentialListView);
		context = rootView.getContext();
		dao = new DexTrackerDAO(context);
		
		fillSequentialLeadeboard();
		
		return rootView;
	}
	
	
	private void fillSequentialLeadeboard() {

		ArrayList<Player> players = new ArrayList<Player>();

		
		try{
			players = dao.getAllPlayers();
		}catch(Exception ex){
			ex.printStackTrace();
		}

		ArrayList<Leader> leaders = new ArrayList<Leader>();
		int score, acc;
		for(Player p: players){
			//score + accuracy of best score
			if(getHighestScore(p)!=null)
			{
				score = (int) getHighestScore(p)[0];
				acc = (int) getHighestScore(p)[1];
				Leader leader = new Leader(p.getAlias(), score, acc);
				leaders.add(leader);
			}

		}
		Collections.sort(leaders, new Comparator<Leader>() {
			@Override public int compare(Leader p1, Leader p2) {
				return p2.getSpeedScore() - p1.getSpeedScore() ; // Ascending
			}
		});
		for(Leader l: leaders){
			Log.i("Leader", l.getAlias().toString());
		}



		// This is the array adapter, it takes the context of the activity as a 
		// first parameter, the type of list view as a second parameter and your 
		// array as a third parameter.
		CustomListViewAdapter arrayAdapter = new CustomListViewAdapter(context, leaders);
		lv.setAdapter(arrayAdapter);
	}
	
	
	//NOTE: IF THERES NO GAMES FROM A PLAYER WITH THE MATCHING GAME TYPE IT SHOULD RETURN NULL....think of how to do this
	
	
	public double[] getHighestScore(Player p){
			
		ArrayList<Score> playerScores = dao.getPlayerScores(p,"Smash");
		double highestScore = 0;
		
		double[] scores = new double[2];
		if(playerScores.size()!=0)
		{
		for(Score score: playerScores)
		{
			if(score.getSpeedScore() > highestScore){
				highestScore = score.getSpeedScore();
				scores[0] = score.getSpeedScore();
				scores[1] = score.getAccuracyScore();
			}
		}
		}
		else
			return null;
		
		return scores;
	}
}
