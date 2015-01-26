package com.dextracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class Analysis extends Activity {
	
	private XYPlot plot;
	Spinner spnPlayers;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 
        // fun little snippet that prevents users from taking screenshots
        // on ICS+ devices :-)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                                 WindowManager.LayoutParams.FLAG_SECURE);
 
        setContentView(R.layout.activity_analysis);
        
        spnPlayers = (Spinner) findViewById(R.id.spnPlayers);
        
        DexTrackerDAO dao = new DexTrackerDAO(getApplicationContext());
        //SET UP SPINNER-------------------------------------------
        ArrayList<Player> players = new ArrayList<Player>();
        players = dao.getAllPlayers();
        List<String> spinnerArray =  new ArrayList<String>();    
        for(Player p: players){
        	spinnerArray.add(p.getAlias());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
        	    this, android.R.layout.simple_spinner_item, spinnerArray);

        	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        	spnPlayers.setAdapter(adapter);
        //
        	
        ArrayList<Score> scores = dao.getPlayerScores(players.get(spnPlayers.getSelectedItemPosition()), "Sequential");
        List<Number> scoreSeries = new ArrayList<Number>();
        for(Score score : scores){
        	scoreSeries.add((int) score.getSpeedScore());
        }
        Number[] realScoreSeries = new Number[scoreSeries.size() - 1];
        
        for(int i = 0; i < scoreSeries.size() -1; i++){
        	realScoreSeries[i] = scoreSeries.get(i);

        }


     // initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.mySimpleXYPlot);
 
        // Create a couple arrays of y-values to plot:
        Number[] series1Numbers = {1, 8, 5, 2, 7, 4};
 
        // Turn the above arrays into XYSeries':
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(realScoreSeries),          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "Series1");                             // Set the display title of the series

        // Create a formatter to use for drawing a series using LineAndPointRenderer
        // and configure it from xml:
        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());
        series1Format.configure(getApplicationContext(),
                R.xml.line_point_formatter_with_plf1);
 
        plot.addSeries(series1, series1Format);

        // reduce the number of range labels
        plot.setTicksPerRangeLabel(1);
        plot.getGraphWidget().setDomainLabelOrientation(-45);
 
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

