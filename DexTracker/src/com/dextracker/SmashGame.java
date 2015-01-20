package com.dextracker;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//SmashGame
public class SmashGame extends FragmentActivity {

	Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12;
	ArrayList<Button> buttons;
	TextView tv1, tv2;
	int score = 0;
	int miss = 0;
	int[] order;
	NumGen ng;
	boolean gameStart, active;
	Context context = SmashGame.this;


	Handler handler = new Handler();
	StoppableRunnable runnable = new StoppableRunnable() {	
		public void stoppableRun() {
			{
				tv1 = (TextView) findViewById(R.id.textView1);
				
				new CountDownTimer(30100, 1000) {
					public void onTick(long millisUntilFinished) {
						tv1.setText(Long.toString(millisUntilFinished/1000));
					}
					public void onFinish() {
						// (Active) Prevents crashes when runnable finishes and app is not front of stack
						if(active){
						tv2 = (TextView) findViewById(R.id.textView2);
						tv2.setText("Score: " + score);
						tv1.setText("30");
						
						FragmentManager fm = getSupportFragmentManager();
						SubmitScoreDialogFragment submitPopup = new SubmitScoreDialogFragment();
						submitPopup.setScore(score);
						submitPopup.setMiss(miss);
						submitPopup.setContext(context);
						submitPopup.setCancelable(false);
						submitPopup.setGameMode("Smash");

						submitPopup.show(fm, "fragment_edit_name");

						gameStart = false;
					}}
				}.start();
			}
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

	    //Remove notification bar
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    
		setContentView(R.layout.activity_game_two);
		tv2 = (TextView) findViewById(R.id.textView2);
		
		

		ng = new NumGen();
		order = ng.getRandomUniqueNumbers(12);

		initButtons(order);
	}

	private void initButtons(int[] order) {

		btn1 = (Button)findViewById(R.id.button1);
		btn2 = (Button)findViewById(R.id.button2);
		btn3 = (Button)findViewById(R.id.button3);
		btn4 = (Button)findViewById(R.id.button4);
		btn5 = (Button)findViewById(R.id.button5);	
		btn6 = (Button)findViewById(R.id.button6);
		btn7 = (Button)findViewById(R.id.button7);
		btn8 = (Button)findViewById(R.id.button8);
		btn9 = (Button)findViewById(R.id.button9);
		btn10 = (Button)findViewById(R.id.button10);
		btn11 = (Button)findViewById(R.id.button11);
		btn12 = (Button)findViewById(R.id.button12);

		buttons = new ArrayList<Button>();
		buttons.add(btn1);
		buttons.add(btn2);
		buttons.add(btn3);
		buttons.add(btn4);
		buttons.add(btn5);
		buttons.add(btn6);
		buttons.add(btn7);
		buttons.add(btn8);
		buttons.add(btn9);
		buttons.add(btn10);
		buttons.add(btn11);
		buttons.add(btn12);

		drawButtons(order);
	}



	private void drawButtons(int[] values){
		for(int i = 0; i < 12; i++)
		{
			assignColor(buttons.get(i),values[i]);
		}
	}

	public void newBoard()
	{
		order = ng.getRandomUniqueNumbers(12);
		drawButtons(order);
	}	

	public void numberClick(View v){
		//Kick off the game
		if(!gameStart){
			runnable.run();
			gameStart = true;
			resetScore();
		}

		int value = Integer.parseInt((v.getTag().toString()));
		if(order[value] < 3)
		{
			score +=3;
		}
		else if(order[value] < 9)
		{
			score +=1;
		}
		else{
			score-=2;
			miss++;
		}
		tv2.setText(Integer.toString(score));

		newBoard();
	}

	private void assignColor(Button btn, int num){
		if(num < 3 ){
			btn.setBackgroundResource(R.drawable.green_smash_button);
		}
		else if(num < 9){
			btn.setBackgroundResource(R.drawable.orange_smash_button);
		}
		else{
			btn.setBackgroundResource(R.drawable.red_smash_button);
		}
	}

	private void resetScore() {
		score = 0;

	}


	@Override
	protected void onRestart()
	{
		active = true;
		super.onRestart();

		Log.i("Activity","Restarted");
	}

	@Override
	protected void onResume()
	{
		active=true;
		super.onResume();

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
		getMenuInflater().inflate(R.menu.game_two, menu);
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
