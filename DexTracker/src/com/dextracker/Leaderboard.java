package com.dextracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.dextracker.adapter.CustomListViewAdapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Leaderboard extends Activity {

	private ListView lv;
	private Context context = Leaderboard.this;
	DexTrackerDAO dao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leaderboard);
		
		lv = (ListView) findViewById(R.id.listView1);
		
		ArrayList<Player> players = new ArrayList<Player>();
		
		dao = new DexTrackerDAO(context);
		try{
			players = dao.getAllPlayers();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		ArrayList<Leader> leaders = new ArrayList<Leader>();
		
		for(Player p: players){
			//score + accuracy of best score
			int score = (int) getHighestScore(p)[0];
			int acc = (int) getHighestScore(p)[1];
			Leader leader = new Leader(p.getAlias(), score, acc);
			leaders.add(leader);
			
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
        CustomListViewAdapter arrayAdapter = new CustomListViewAdapter(getApplicationContext(), leaders);
        lv.setAdapter(arrayAdapter);

   }
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.leaderboard, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public double[] getHighestScore(Player p){
			
		ArrayList<Score> playerScores = dao.getPlayerScores(p,"Sequential");
		double highestScore = 0;
		
		double[] scores = new double[2];
		for(Score score: playerScores)
		{
			if(score.getSpeedScore() > highestScore){
				highestScore = score.getSpeedScore();
				scores[0] = score.getSpeedScore();
				scores[1] = score.getAccuracyScore();
			}
		}
		
		return scores;
	}
}
