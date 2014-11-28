package com.dextracker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import net.epsilonlabs.datamanagementefficient.library.DataManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class SubmitScoreDialogFragment extends DialogFragment {

	Context context;
	private int score, miss;
	EditText input;

	Button btnAgain, btnSubmit;
	TextView tv1 , tv2;
	
	Calendar cal = new GregorianCalendar();

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getMiss() {
		return miss;
	}

	public void setMiss(int miss) {
		this.miss = miss;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		input = new EditText(getActivity());
		input.setHint("Name");
		
		builder.setTitle("Submit Score?")
		.setView(input)
		.setMessage("Hit: "  + getScore() + "\n" + " Missed: " + getMiss() )
		.setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				try{
					
					String alias = input.getText().toString();
					if(!alias.isEmpty())
					{
					//Add this code to database layer
					DexTrackerDAO dao = new DexTrackerDAO(context);
					int pID, sID;
					
					
					//Check if existing player
					/* if(exists)
					 * 		use existing player id
					 * else
					 * 		create new player
					 */
					ArrayList<Player> players = dao.getAllPlayers();
					if(checkExistingPlayer(players,alias))
					{
						//Get players id
						pID = dao.getPlayer(alias).getId();
					}
					else{
						//create new player
						Player p = new Player(alias);
						pID = dao.storePlayer(p);
					}

					Score s = new Score(getScore(), getMiss());
					sID = dao.storeScore(s);

					Game g = new Game(pID, sID, "Sequential");
					dao.storeGame(g);
					
					}

				}catch(Exception ex){
					ex.printStackTrace();
				}
			}

			private boolean checkExistingPlayer(ArrayList<Player> players,String alias) {
				for(Player p: players)
				{
					if(p.getAlias().equals(alias))
					{
						Log.i(p.getAlias(),alias);
						return true;
					}
				}
				return false;
			}
		})
		.setNegativeButton(R.string.play_again, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

				//Play again code here, perhaps relaunch activity - see overhead/time required.

			}
		});
		// Create the AlertDialog object and return it

		return builder.create();
		
//		
//		final Dialog dialog = new Dialog(getActivity());  
//		  dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);  
//		  dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
//		    WindowManager.LayoutParams.FLAG_FULLSCREEN);  
//		  dialog.setContentView(R.layout.submit_dialog);
//		  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//		  dialog.show();  
//		  btnAgain = (Button) dialog.findViewById(R.id.button1);  
//		  tv1 = (TextView) dialog.findViewById(R.id.textView1);  
//		  btnAgain.setOnClickListener(new OnClickListener() { 
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			dismiss();  
//		}  
//		  });  
//		  return dialog;  
	}

	public void setContext(Context context) {
		this.context = context;

	}
}