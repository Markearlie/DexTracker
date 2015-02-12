package com.dextracker;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Settings extends Activity {
	
	Switch toggle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		
		
		
		toggle = (Switch) findViewById(R.id.switch1);
		toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (isChecked) {
		        	SharedPreferences.Editor editor = getSharedPreferences("PREF_NAME", MODE_PRIVATE).edit();
		        	 editor.putBoolean("popup", true);
		        	 editor.commit();
		        	 
		        } else {
		        	SharedPreferences.Editor editor = getSharedPreferences("PREF_NAME", MODE_PRIVATE).edit();
		        	editor.putBoolean("popup", false);
		        	editor.commit();
		        }
		    }
		});
		
		SharedPreferences prefs = getSharedPreferences("PREF_NAME", MODE_PRIVATE); 
		boolean popup = prefs.getBoolean("popup", true);	
		toggle.setChecked(popup);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
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
