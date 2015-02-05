package com.dextracker;



import net.epsilonlabs.datamanagementefficient.library.DataManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dextracker.basegameutils.BaseGameActivity;
import com.google.android.gms.games.Games;

public class SequentialGame extends BaseGameActivity{

	public int[] onScreenNums = new int[5];
	
	CountDownTimer cdt;
	boolean gameStart,active;
	TextView tv1, tv2, tv3, tv4, tv5, tv6;
	private DataManager dm;
	private int score, miss;
	private enum LastNumberState{
		TRUE, FALSE, NONE
	}
	   public static final String PREFS_NAME = "MyPrefsFile1";
	    public CheckBox dontShowAgain;
	private LastNumberState lns = LastNumberState.NONE;
	NumGen ng = new NumGen();

	//Countdown Timer Code
	Handler handler = new Handler();
	StoppableRunnable runnable = new StoppableRunnable() {	
		public void stoppableRun() {
			{
				new CountDownTimer(30100, 1000) {
					public void onTick(long millisUntilFinished) {
						tv6.setText(Long.toString(millisUntilFinished/1000));
					}
					public void onFinish() {
						// (Active) Prevents crashes when runnable finishes and app is not front of stack
						if(active)
						{
							
							if(getApiClient().isConnected())
							{
								Games.Leaderboards.submitScore(getApiClient(), getString(R.string.sequential_leaderboard), score);
							}
							tv6.setText("30");
							createNumberLabel();
							FragmentManager fm = getSupportFragmentManager();
							SubmitScoreDialogFragment submitPopup = new SubmitScoreDialogFragment();
							submitPopup.setScore(score);
							submitPopup.setMiss(miss);
							submitPopup.setContext(getBaseContext());
							submitPopup.setCancelable(false);
							submitPopup.setGameMode("Sequential");
							submitPopup.show(fm, "fragment_edit_name");
	
							gameStart = false;
						}else{
							gameStart = false;
						}	
					}
					
				}.start();
			}
		}
	};


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_one);
		
		//Set static backgrounds in XML	
		tv1 = (TextView)findViewById(R.id.textView1);
		tv1.setBackgroundResource(R.drawable.gray_circle);

		tv2 = (TextView)findViewById(R.id.textView2);
		tv2.setBackgroundResource(R.drawable.gray_circle);

		tv3 = (TextView)findViewById(R.id.textView3);
		tv3.setBackgroundResource(R.drawable.blue_circle);

		tv4 = (TextView)findViewById(R.id.textView4);
		tv5 = (TextView)findViewById(R.id.textView5);
		tv6 = (TextView)findViewById(R.id.textView6);

		createNumberLabel();

	}


	
	@Override
	protected void onRestart()
	{
		active = true;
		super.onRestart();
		createNumberLabel();
		Log.i("Activity","Restarted");
	}

	@Override
	protected void onResume()
	{     
		active=true;
		super.onResume();
		createNumberLabel();
		Log.i("Activity","Resumed");
	}

	@Override
	protected void onPause()
	{
		active=false;
		super.onPause();
		Log.i("Activity","Paused");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_one, menu);
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

	public void numberClick(View v){

		if(!gameStart)
		{
			runnable.run();
			gameStart = true;
			resetScore();
		}
		//Uses tags assigned to the button in XML to avoid many duplicate methods/case statements
		int numClicked = Integer.parseInt(v.getTag().toString());

		//Correct number!!!
		if(numClicked == onScreenNums[2])
		{
			score++;
			adjustOnScreenNums();

			if(lns==LastNumberState.TRUE){
				tv5.setBackgroundResource(R.drawable.green_circle);
			}else if(lns==LastNumberState.FALSE){
				tv5.setBackgroundResource(R.drawable.red_circle);
			}

			tv4.setBackgroundResource(R.drawable.green_circle);
			lns = LastNumberState.TRUE;
		}
		//Not correct number!!
		else
		{
			miss++;

			if(lns==LastNumberState.TRUE){
				tv5.setBackgroundResource(R.drawable.green_circle);
			}else if(lns==LastNumberState.FALSE){
				tv5.setBackgroundResource(R.drawable.red_circle);
			}

			adjustOnScreenNums();
			tv4.setBackgroundResource(R.drawable.red_circle);
			lns = LastNumberState.FALSE;
		}

	}


	private void resetScore() {
		score = 0;
		miss = 0;

		lns = LastNumberState.NONE;

	}



	private void createNumberLabel(){

		onScreenNums[0] = ng.getRandomNum();
		onScreenNums[1] = ng.getRandomNum();
		onScreenNums[2] = ng.getRandomNum();
		onScreenNums[3] = 0;
		onScreenNums[4] = 0;

		tv4.setBackgroundResource(R.drawable.gray_circle);
		tv5.setBackgroundResource(R.drawable.gray_circle);

		updateScreen();
	}

	private void adjustOnScreenNums() {

		onScreenNums[4] = onScreenNums[3];
		onScreenNums[3] = onScreenNums[2];
		onScreenNums[2] = onScreenNums[1];
		onScreenNums[1] = onScreenNums[0];
		onScreenNums[0] = ng.getRandomNum();

		updateScreen();
	}



	private void updateScreen() {
		tv1.setText(Integer.toString(onScreenNums[0]));
		tv2.setText(Integer.toString(onScreenNums[1]));
		tv3.setText(Integer.toString(onScreenNums[2]));
		tv4.setText(Integer.toString(onScreenNums[3]));
		tv5.setText(Integer.toString(onScreenNums[4]));
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
