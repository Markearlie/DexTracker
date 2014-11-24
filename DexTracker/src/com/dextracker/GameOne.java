package com.dextracker;

import java.util.ArrayList;
import java.util.List;

import net.epsilonlabs.datamanagementefficient.library.DataManager;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class GameOne extends Activity {

	public int[] onScreenNums = new int[5];

	CountDownTimer cdt;
	boolean gameStart;
	TextView tv1, tv2, tv3, tv4, tv5, tv6;
	
	//DM TESTING
	private DataManager dm;
	private int score, miss;
	NumGen ng = new NumGen();
	
	//Countdown Timer Codee
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        public void run() {
        	 new CountDownTimer(30100, 1000) {
        		 
        	     public void onTick(long millisUntilFinished) {
        	       tv6.setText(Long.toString(millisUntilFinished/1000));
        	     }
        	     public void onFinish() {
        	    	 gameStart = false;
        	    	 
        	    	 tv6.setText("Score:" + score + "  Missed:" + miss);
        	     				
				}
        	  }.start();
        }
    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_one);
		
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
		
		List<Player> listOfPlayers = new ArrayList<Player>();
		dm = DataManager.getInstance(this);
		dm.open();
		
		listOfPlayers = dm.getAll(Player.class);
		for(Player p: listOfPlayers)
		{
			Log.i("Player+" ,p.toString());
		}
		
		
		
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
			tv4.setBackgroundResource(R.drawable.green_circle);
		}
		//Not correct number!!
		else
		{
			miss++;
			adjustOnScreenNums();
			tv4.setBackgroundResource(R.drawable.red_circle);
		}
		
	}


	private void resetScore() {
		score = 0;
		miss = 0;
		
	}

	private void createNumberLabel(){
		
		onScreenNums[0] = ng.getRandomNum();
		onScreenNums[1] = ng.getRandomNum();
		onScreenNums[2] = ng.getRandomNum();
		onScreenNums[3] = 0;
		onScreenNums[4] = 0;
		
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
}
