package com.dextracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainMenu extends Activity{


		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		setContentView(R.layout.activity_main_menu);
	}
	
	
	@Override
	protected void onStart(){
		super.onStart();
	}
	@Override
	public void onStop(){
		super.onStop();
	}
	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
	}
	 public void btnGameOne_onClick(View v)
	    {
	    	Intent intent = new Intent(this, SequentialGame.class);
	    	startActivity(intent);
	    }
	    public void btnGameTwo_onClick(View v)
	    {
	    	Intent intent = new Intent(this, SmashGame.class);
	    	startActivity(intent);
	    }
	    public void btnGameThree_onClick(View v)
	    {
	    	Intent intent = new Intent(this, SmashGame.class);
	    	startActivity(intent);
	    }
	    public void btnLeaderboard_onClick(View v)
	    {
			Intent intent = new Intent(this, Leaderboard.class);
			startActivity(intent);
	    }
//	    public void btnSettings_onClick(View v)
//	    {
//	    	Intent intent = new Intent(this, Settings.class);
//	    	startActivity(intent);
//	    }
//	    

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
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
