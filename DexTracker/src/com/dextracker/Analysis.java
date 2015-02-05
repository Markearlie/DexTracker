package com.dextracker;

import java.util.ArrayList;
import java.util.List;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendForm;
import com.github.mikephil.charting.utils.Legend.LegendPosition;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class Analysis extends Activity {


	Spinner spnPlayers;
	Spinner spnModes;
	DexTrackerDAO dao;
	ArrayList<Player> players;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// fun little snippet that prevents users from taking screenshots
		// on ICS+ devices :-)
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
				WindowManager.LayoutParams.FLAG_SECURE);

		setContentView(R.layout.activity_analysis);

		spnPlayers = (Spinner) findViewById(R.id.spnPlayers);
		spnModes = (Spinner)findViewById(R.id.spnGameMode);
		dao = new DexTrackerDAO(getApplicationContext());
		//SET UP SPINNER-------------------------------------------
		players = new ArrayList<Player>();
		players = dao.getAllPlayers();
		List<String> spinnerArray =  new ArrayList<String>();    
		for(Player p: players){
			spinnerArray.add(p.getAlias());
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, spinnerArray);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnPlayers.setAdapter(adapter);
		
		spnPlayers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	            public void onNothingSelected(AdapterView<?> parent) {
	                // Do nothing, just another required interface callback
	            }

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					try{
						ArrayList<Score> scores = dao.getPlayerScores(players.get(spnPlayers.getSelectedItemPosition()), spnModes.getSelectedItem().toString());
						Number[] lastScores = createArray(scores);
						drawChart(lastScores);
						
						
					}
					catch(Exception ex){
						ex.printStackTrace();
						Toast.makeText(getApplicationContext(), "Error retrieving scores", Toast.LENGTH_LONG).show();
					}
				}
	    });

		spnModes = (Spinner)findViewById(R.id.spnGameMode);
		ArrayAdapter<String> spnModesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.game_modes));
		spnModes.setAdapter(spnModesArrayAdapter);
		spnModes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing, just another required interface callback
            }

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				try{
					ArrayList<Score> scores = dao.getPlayerScores(players.get(spnPlayers.getSelectedItemPosition()), spnModes.getSelectedItem().toString());
					Number[] lastScores = createArray(scores);
					drawChart(lastScores);		
				}
				catch(Exception ex){
					ex.printStackTrace();
					Toast.makeText(getApplicationContext(), "Error retrieving scores", Toast.LENGTH_LONG).show();
				}
			}
    });
		
	}

	private Number[] createArray(ArrayList<Score> scores){
		List<Number> scoreSeries = new ArrayList<Number>();
		for(Score score : scores){
			scoreSeries.add((int) score.getSpeedScore());
		}
		Number[] realScoreSeries = new Number[scoreSeries.size()];

		for(int i = 0; i < scoreSeries.size(); i++){
			realScoreSeries[i] = scoreSeries.get(i);
		}

		return realScoreSeries;
	}
	
	private void drawChart(Number[] figures){
		//Chart stuff begins here
		LineChart chart = (LineChart) findViewById(R.id.chart);
		
		ArrayList<Entry> valsComp1 = new ArrayList<Entry>();
		
		for(int i = 0; i < figures.length; i++){
				Entry ent = new Entry(figures[i].floatValue(), i);
				valsComp1.add(ent);		
		}

	    LineDataSet setComp1 = new LineDataSet(valsComp1, "Attempts");
	    setComp1.setLineWidth(2.5f);
	    
	    ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
	    dataSets.add(setComp1);
	    
	    ArrayList<String> xVals = new ArrayList<String>();
	    xVals.add("1");xVals.add("2");xVals.add("3");xVals.add("4");xVals.add("5");
	    xVals.add("6");xVals.add("7");xVals.add("8");xVals.add("9");xVals.add("10");
	    
	    LineData data = new LineData(xVals, dataSets);
	    
		chart.setData(data);
		 Legend l = chart.getLegend();
		 l.setFormSize(10f); // set the size of the legend forms/shapes
		 l.setForm(LegendForm.CIRCLE); // set what type of form/shape should be used
		 l.setPosition(LegendPosition.BELOW_CHART_LEFT);
		 l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
		 l.setYEntrySpace(5f); // set the space between the legend entries on the y-axis

		 chart.setDescription("This is a chart");


		 chart.animateXY(2000,2000);//Animates the chart up and across

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

