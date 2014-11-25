package com.dextracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;

import android.content.DialogInterface;
import android.os.Bundle;

public class SubmitScoreDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Submit Score??????")
               .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                      
                	   //TODO: Code to submit scores here
                	   
                	   
                   }
               })
               .setNegativeButton(R.string.submit, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {

                	   //Play again code here, perhaps relaunch activity - see overhead/time required.

                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}