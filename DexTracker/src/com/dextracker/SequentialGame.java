package com.dextracker;



import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dextracker.basegameutils.BaseGameActivity;
import com.google.android.gms.games.Games;

public class SequentialGame extends BaseGameActivity{

	public int[] onScreenNums = new int[5];

	CountDownTimer cdt;
	boolean gameStart,active;
	TextView tv1, tv2, tv3, tv4, tv5, tv6;
	Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12;
	ArrayList<Button> buttons;
	Context ctx = this;
	private int score, miss;
	private enum LastNumberState{
		TRUE, FALSE, NONE
	}
	private LastNumberState lns = LastNumberState.NONE;
	NumGen ng = new NumGen();
	LinearLayout bubble;

	//Countdown Timer Code
	Handler handler = new Handler();
	StoppableRunnable runnable = new StoppableRunnable() {	
		public void stoppableRun() {
			{
				new CountDownTimer(10100, 1000) {
					public void onTick(long millisUntilFinished) {
						tv6.setText(Long.toString(millisUntilFinished/1000));
					}
					public void onFinish() {
						// (Active) Prevents crashes when runnable finishes and app is not front of stack
						disableButtons();
						if(active)
						{
							Log.i("Dialog", "Shown");
							
							if(getApiClient().isConnected())
							{
								Games.Leaderboards.submitScore(getApiClient(), getString(R.string.sequential_leaderboard), score);
							}
							tv6.setText("30");
							createNumberLabel();
							final FragmentManager fm = getSupportFragmentManager();
							final SubmitScoreDialogFragment submitPopup = new SubmitScoreDialogFragment();
							submitPopup.setScore(score);
							submitPopup.setMiss(miss);
							submitPopup.setContext(getBaseContext());
							submitPopup.setButtons(buttons);
							submitPopup.setCancelable(false);
							submitPopup.setGameMode("Sequential");
							Runnable launchTask = new Runnable() {
							    @Override
							    public void run() {
							    	submitPopup.show(fm, "fragment_edit_name");
							    }
							}; 
							Handler h = new Handler();
							h.postDelayed(launchTask, 1000);

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


		initButtons();
		disableButtons();
		SharedPreferences prefs = getSharedPreferences("PREF_NAME", MODE_PRIVATE); 

		boolean popup = prefs.getBoolean("popup", true);	
		bubble = (LinearLayout) findViewById(R.id.bubbleLayout);
		if(!popup){
			bubble.setVisibility(View.GONE);
			enableButtons();
		}else{
			ImageButton bubbleClose = (ImageButton) bubble.findViewById(R.id.bubbleclose);
			bubbleClose.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					bubble.setVisibility(View.GONE);
					enableButtons();
				}
			});
		}
		createNumberLabel();

	}

	private void initButtons() {

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
	}
	
	private void disableButtons() {
		for(Button button: buttons){
			button.setEnabled(false);
		}
	}
	private void enableButtons() {
		for(Button button: buttons){
			button.setEnabled(true);
		}
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
