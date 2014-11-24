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

	public ArrayList<Integer> globalNums;
	
	public int[] onScreenNums = new int[5];

	CountDownTimer cdt;
	boolean gameStart;
	TextView tv1, tv2, tv3, tv4, tv5, tv6;
	
	//DM TESTING
	private DataManager dm;
	private int score, miss;

	
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        public void run() {
        	 new CountDownTimer(30000, 1000) {
        		 
        	     public void onTick(long millisUntilFinished) {
        	       tv6.setText(Long.toString(millisUntilFinished/1000));
        	     }

        	     public void onFinish() {
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
		tv2 = (TextView)findViewById(R.id.textView2);
		tv3 = (TextView)findViewById(R.id.textView3);
		tv4 = (TextView)findViewById(R.id.textView4);
		tv5 = (TextView)findViewById(R.id.textView5);
		tv6 = (TextView)findViewById(R.id.textView6);
		
		
		NumGen ng = new NumGen();
		globalNums = ng.getNumberArray(15);
		createNumberLabel(globalNums);
		
		
		//Testing DataManager
		
		//Testing GIT Conectionsadasd
		
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
		}
		//Uses tags assigned to the button in XML to avoid many duplicate methods/case statements
		int numClicked = Integer.parseInt(v.getTag().toString());
		
		if(!globalNums.isEmpty() && globalNums.get(0) == numClicked)
		{
			if(globalNums.size() > 3)
			{
				globalNums.remove(0);
				pushForward(numClicked,true,globalNums,false);
			}
			else
			{	
				globalNums.remove(0);
				pushForward(numClicked,true,globalNums,true);
			}
		}
		else if(!globalNums.isEmpty() && globalNums.get(0) != numClicked)
		{
			if(globalNums.size() > 3)
			{
				globalNums.remove(0);
				pushForward(numClicked,false,globalNums,false);
				
			}
			else
			{	
				globalNums.remove(0);
				pushForward(numClicked,false,globalNums,true);
			}
		}
		if(globalNums.isEmpty())
		{
			Toast.makeText(GameOne.this,"Its all ogre now", Toast.LENGTH_SHORT).show();
		}
		

	}
	
	private void pushForward(Integer integer, boolean correct, ArrayList<Integer> remainingNums, boolean numDone) {
		
		//numDone is True for cases where remainingNums.size > 3.....
		if(correct && !numDone){
			score++;
			adjustOnScreenNums(remainingNums.get(2),correct);
		}
		else if(correct && numDone){
			score++;
			finishOnScreenNums(correct);
		}
		else if(!correct && !numDone){
			miss++;
			adjustOnScreenNums(remainingNums.get(2),correct);
		}
		else if(!correct && numDone){
			miss++;
			finishOnScreenNums(correct);
		}
			
			
	}
		

	private void finishOnScreenNums(boolean correct) {

		tv5.setText(tv4.getText());
		tv4.setText(tv3.getText());
		tv3.setText(tv2.getText());
		tv2.setText(tv1.getText());
		tv1.setText("_");
		
	}

	private void adjustOnScreenNums(int newNum,boolean correct) {
		
		
		onScreenNums[4] = onScreenNums[3];
		onScreenNums[3] = onScreenNums[2];
		onScreenNums[2] = onScreenNums[1];
		onScreenNums[1] = onScreenNums[0];
		onScreenNums[0] = newNum;
		
		tv1.setText(Integer.toString(newNum));
		tv2.setText(Integer.toString(onScreenNums[1]));
		tv3.setText(Integer.toString(onScreenNums[2]));
		
		
		//To carry on the  "-/+" signs to pos4&5
		tv5.setText(tv4.getText());
		if(correct){
		tv4.setText(onScreenNums[3]+"+");
		}else{
		tv4.setText(onScreenNums[3]+"-");
		}
		//old way
		//	tv5.setText(Integer.toString(onScreenNums[4]));
		
		
	}

	private void createNumberLabel(ArrayList<Integer> numbers){
		
		tv1.setText(Integer.toString(numbers.get(2)));
		tv2.setText(Integer.toString(numbers.get(1)));
		tv3.setText(Integer.toString(numbers.get(0)));
		tv4.setText("_");
		tv5.setText("_");
		
		onScreenNums[0] = numbers.get(2);
		onScreenNums[1] = numbers.get(1);
		onScreenNums[2] = numbers.get(0);
		onScreenNums[3] = 0;
		onScreenNums[4] = 0;
		
	
	}
}
