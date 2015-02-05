package com.dextracker;

import com.dextracker.basegameutils.BaseGameActivity;
import com.google.android.gms.games.Games;

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
import android.widget.TextView;




public class TypeGame extends BaseGameActivity {

	TextView tv1,tv2,tv3,tv4,tv5,tv6;
	String currentWord = "";
	TypeFileHandler fileHandler;

	boolean gameStart,active;
	private int score, miss;

	private enum LastWordState{
		TRUE, FALSE, NONE
	}
	private LastWordState lns = LastWordState.NONE;


	public String[] onScreenWords = new String[5];


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
								Games.Leaderboards.submitScore(getApiClient(), getString(R.string.type_left_leaderboard), score);
							}
							tv6.setText("30");
							createWordLabel();
							FragmentManager fm = getSupportFragmentManager();
							SubmitScoreDialogFragment submitPopup = new SubmitScoreDialogFragment();
							submitPopup.setScore(score);
							submitPopup.setMiss(miss);
							submitPopup.setContext(getBaseContext());
							submitPopup.setCancelable(false);
							submitPopup.setGameMode("TypeLeft");
							submitPopup.show(fm, "fragment_edit_name");

							gameStart = false;
							resetScore();
							createWordLabel();
							tv6.setText("");
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
		setContentView(R.layout.activity_type_game);
		//initialize textviews
		tv1 = (TextView) findViewById(R.id.textView1);
		tv1.setBackgroundResource(R.drawable.gray_circle);
		tv2 = (TextView) findViewById(R.id.textView2);
		tv2.setBackgroundResource(R.drawable.gray_circle);
		tv3 = (TextView) findViewById(R.id.textView3);
		tv3.setBackgroundResource(R.drawable.blue_circle);
		
		tv4 = (TextView) findViewById(R.id.textView4);
		tv5 = (TextView) findViewById(R.id.textView5);
		
		tv6 = (TextView) findViewById(R.id.textView6);

		fileHandler = new TypeFileHandler(getApplicationContext(), "LeftHandWords.txt");
		fileHandler.openFile();

		createWordLabel();



	}
	
	@Override
	protected void onRestart()
	{
		active = true;
		super.onRestart();
		createWordLabel();
		Log.i("Activity","Restarted");
	}

	@Override
	protected void onResume()
	{
		active=true;
		super.onResume();
		createWordLabel();
		Log.i("Activity","Resumed");
	}

	@Override
	protected void onPause()
	{
		active=false;
		super.onPause();
		Log.i("Activity","Paused");
	}



	public void letterClick(View v){

		Log.i("Score ", "  "+score);
		Log.i("Miss ", "  "+miss);

				
				if(!gameStart)
				{
					runnable.run();
					gameStart = true;
					resetScore();
				}

		String letterClicked = v.getTag().toString();

		TextView txtCurrentWord = (TextView) findViewById(R.id.currentWord);

		tv3 = (TextView) findViewById(R.id.textView3);
		String targetWord = tv3.getText().toString();

		if(!letterClicked.equalsIgnoreCase("null") && letterClicked!=null){
			currentWord += letterClicked;
		}

		txtCurrentWord.setText(currentWord);

		if(targetWord.length() <= currentWord.length())
		{

			//Correct Word
			if(currentWord.equalsIgnoreCase(onScreenWords[2]))
			{
				score++;
				adjustOnScreenWords();

				if(lns==LastWordState.TRUE){
					tv5.setBackgroundResource(R.drawable.green_circle);
				}else if(lns==LastWordState.FALSE){
					tv5.setBackgroundResource(R.drawable.red_circle);
				}

				tv4.setBackgroundResource(R.drawable.green_circle);
				lns = LastWordState.TRUE;

				txtCurrentWord.setText("");
				currentWord = "";
			}
			//Not correct number!!
			else
			{
				miss++;

				if(lns==LastWordState.TRUE){
					tv5.setBackgroundResource(R.drawable.green_circle);
				}else if(lns==LastWordState.FALSE){
					tv5.setBackgroundResource(R.drawable.red_circle);
				}

				adjustOnScreenWords();
				tv4.setBackgroundResource(R.drawable.red_circle);
				lns = LastWordState.FALSE;
				txtCurrentWord.setText("");
				currentWord = "";
			}
		}
	}

	private void resetScore() {
		score = 0;
		miss = 0;

		lns = LastWordState.NONE;

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.type_game, menu);
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

	private void createWordLabel(){

		onScreenWords[0] = fileHandler.getRandomWord();
		onScreenWords[1] = fileHandler.getRandomWord();
		onScreenWords[2] = fileHandler.getRandomWord();
		onScreenWords[3] = "";
		onScreenWords[4] = "";

		tv4.setBackgroundResource(R.drawable.gray_circle);
		tv5.setBackgroundResource(R.drawable.gray_circle);

		updateScreen();
	}

	private void adjustOnScreenWords() {

		onScreenWords[4] = onScreenWords[3];
		onScreenWords[3] = onScreenWords[2];
		onScreenWords[2] = onScreenWords[1];
		onScreenWords[1] = onScreenWords[0];
		onScreenWords[0] = fileHandler.getRandomWord();

		updateScreen();
	}



	private void updateScreen() {
		tv1.setText(onScreenWords[0]);
		tv2.setText(onScreenWords[1]);
		tv3.setText(onScreenWords[2]);
		tv4.setText(onScreenWords[3]);
		tv5.setText(onScreenWords[4]);
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
