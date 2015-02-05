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
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class TypeModeDialogFragment extends DialogFragment {

	Context context;
	EditText input;
	String gameMode;
	

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Choose your side!")
		.setView(input)
		.setMessage("Left or Right hand?")
		.setPositiveButton("Right", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
		    	Intent intent = new Intent(context, TypeRightGame.class);
		    	startActivity(intent);
			}
		})
		.setNegativeButton("Left", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
		    	Intent intent = new Intent(context, TypeGame.class);
		    	startActivity(intent);
			}
		});
		return builder.create();
	}

	public void setContext(Context context) {
		this.context = context;
	}
}