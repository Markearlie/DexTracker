package com.dextracker;

import java.util.ArrayList;

import com.dextracker.basegameutils.BaseGameActivity;
import com.google.android.gms.games.Games;

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

public class TypeRightGame extends BaseGameActivity {

	TextView tv1,tv2,tv3,tv4,tv5,tv6, tvCurrWord;
	String currentWord = "";
	TypeFileHandler fileHandler;

	Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12;
	ArrayList<Button> buttons = new ArrayList<Button>();

	boolean gameStart,active;
	private int score, miss;

	private enum LastWordState{
		TRUE, FALSE, NONE
	}
	private LastWordState lns = LastWordState.NONE;
	LinearLayout bubble;

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
							disableButtons();
							tvCurrWord.setText("");
							if(getApiClient().isConnected())
							{
								Games.Leaderboards.submitScore(getApiClient(), getString(R.string.type_right_leaderboard), score);
							}
							tv6.setText("30");
							createWordLabel();
							FragmentManager fm = getSupportFragmentManager();
							SubmitScoreDialogFragment submitPopup = new SubmitScoreDialogFragment();
							submitPopup.setScore(score);
							submitPopup.setMiss(miss);
							submitPopup.setContext(getBaseContext());
							submitPopup.setButtons(buttons);
							submitPopup.setCancelable(false);
							submitPopup.setGameMode("TypeRight");
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
		setContentView(R.layout.activity_type_right_game);
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

		tvCurrWord = (TextView) findViewById(R.id.currentWord);

		fileHandler = new TypeFileHandler(getApplicationContext(), "RightHandWords.txt");
		fileHandler.openFile();
		
		createWordLabel();
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

		if(!currentWord.equalsIgnoreCase(targetWord.substring(0, currentWord.length())))
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
		}
	}

	private void resetScore() {
		score = 0;
		miss = 0;
		currentWord = "";
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
