package com.dextracker;

import com.dextracker.basegameutils.BaseGameActivity;
import com.google.android.gms.games.Games;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class LeaderboardMenu extends BaseGameActivity {
	Context ctx = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leaderboard_menu);
		
		final Button button1 = (Button) findViewById(R.id.btnSeqLocal);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
    			Intent intent = new Intent(ctx, Leaderboard.class);
    			Bundle b = new Bundle();
    			b.putInt("pos", 0);
    			intent.putExtras(b);
    			startActivity(intent);
            }
        });
        final Button button2 = (Button) findViewById(R.id.btnTypeRightLocal);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(ctx, Leaderboard.class);
            	Bundle b = new Bundle();
    			b.putInt("pos", 2);
    			intent.putExtras(b);
    			startActivity(intent);
            }
        });
        final Button button3 = (Button) findViewById(R.id.btnTypeLeftLocal);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(ctx, Leaderboard.class);
            	Bundle b = new Bundle();
    			b.putInt("pos", 1);
    			intent.putExtras(b);
    			startActivity(intent);
            }
        });
        final Button button4 = (Button) findViewById(R.id.btnSmashLocal);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(ctx, Leaderboard.class);
            	Bundle b = new Bundle();
    			b.putInt("pos", 3);
    			intent.putExtras(b);
    			startActivity(intent);
            }
        });
        final Button button5 = (Button) findViewById(R.id.btnSeqOnline);
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
    	    	        getApiClient(), getString(R.string.sequential_leaderboard)), 2);
            }
        });
        final Button button6 = (Button) findViewById(R.id.btnTypeLeftOnline);
        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
    	    	        getApiClient(), getString(R.string.type_left_leaderboard)), 2);
            }
        });
        final Button button7 = (Button) findViewById(R.id.btnTypeRightOnline);
        button7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
    	    	        getApiClient(), getString(R.string.type_right_leaderboard)), 2);
            }
        });
        final Button button8 = (Button) findViewById(R.id.btnSmashOnline);
        button8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
    	    	        getApiClient(), getString(R.string.smash_leaderboard)), 2);
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.leaderboard_menu, menu);
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
