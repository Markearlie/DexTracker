package com.dextracker;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Leaderboard extends Activity {

	private ListView lv;
	private Context context = Leaderboard.this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leaderboard);
		
		lv = (ListView) findViewById(R.id.listView1);
		
		ArrayList<Player> players = new ArrayList<Player>();
		ArrayList<Score> scores = new ArrayList<Score>();
		
		DexTrackerDAO dao = new DexTrackerDAO(context);
		try{
			players = dao.getAllPlayers();
			scores = dao.getAllScores();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		ArrayList<String> stringPlayers = new ArrayList<String>();
		ArrayList<String> stringScores = new ArrayList<String>();
		
		for(Player p: players){
			stringPlayers.add(p.getAlias() + scores.get(0).toString());
		}


        // This is the array adapter, it takes the context of the activity as a 
        // first parameter, the type of list view as a second parameter and your 
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, 
                android.R.layout.simple_list_item_1,
                stringPlayers );

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
}
