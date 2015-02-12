package com.dextracker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.dextracker.basegameutils.BaseGameActivity;

public class MainMenu extends BaseGameActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		

		//findViewById(R.id.sign_out_button).setOnClickListener(this);
	}
    
	 public void btnGameOne_onClick(View v)
	    {
	    	Intent intent = new Intent(this, SequentialGame.class);
	    	startActivity(intent);
	    }
	    public void btnGameTwo_onClick(View v)
	    {
	    	
	    	FragmentManager fm = getSupportFragmentManager();
			TypeModeDialogFragment submitPopup = new TypeModeDialogFragment();
			submitPopup.setContext(getBaseContext());
			submitPopup.show(fm, "fragment_edit_name");


	    }
	    public void btnGameThree_onClick(View v)
	    {
	    	Intent intent = new Intent(this, SmashGame.class);
	    	startActivity(intent);
	    }
	    public void btnLeaderboard_onClick(View v)
	    {
			Intent intent = new Intent(this, LeaderboardMenu.class);
			startActivity(intent);
//	    	startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
//	    	        getApiClient(), getString(R.string.sequential_leaderboard)), 2);
	    }
	    public void btnAnalysis_onClick(View v)
	    {
			Intent intent = new Intent(this, Analysis.class);
			startActivity(intent);
	    }
	    
	    public void btnSettings_onClick(View v)
	    {
			Intent intent = new Intent(this, Settings.class);
			startActivity(intent);
	    }

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

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}


}
