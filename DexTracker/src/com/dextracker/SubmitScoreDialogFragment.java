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
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

public class SubmitScoreDialogFragment extends DialogFragment {

	Context context;
	private int score, miss;
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
		builder.setMessage("Score = "  + getScore() + "\n" + "You missed: " + getMiss())

		.setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

				//                		List<Player> listOfPlayers = new ArrayList<Player>();
				//                		dm = DataManager.getInstance(this);
				//                		dm.open();
				//
				//                		listOfPlayers = dm.getAll(Player.class);
				//                		for(Player p: listOfPlayers)
				//                		{
				//                			Log.i("Player+" ,p.toString());
				//                		}

				try{
					//Add this code to database layer
					DexTrackerDAO dao = new DexTrackerDAO(context);
					int pID, sID;

					Player p = new Player("Mark");
					pID = dao.storePlayer(p);

					Score s = new Score(getScore(), getMiss());
					sID = dao.storeScore(s);


					Game g = new Game(pID, sID, "Sequential");
					dao.storeGame(g);
					


				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		})
		.setNegativeButton(R.string.play_again, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

				//Play again code here, perhaps relaunch activity - see overhead/time required.

			}
		});
		// Create the AlertDialog object and return it

		return builder.create();
	}

	public void setContext(Context context) {
		this.context = context;

	}
}