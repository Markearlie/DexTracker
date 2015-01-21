package com.dextracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.util.Log;

public class TypeFileHandler {

	Context context;
	String fileName;


	private int totalLines;

	public TypeFileHandler(Context context, String fileName){
		this.context = context;
		this.fileName = fileName;
	}

	//Opens the file and counts the lines in the file
	public int openFile()
	{

		try 
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));

			totalLines = 0;
			while(reader.readLine()!=null)
			{
				totalLines++;
			}
			Log.i("Lines in File", totalLines+"");
			reader.close();

			return totalLines;

		}
		//Something went wrong with the file
		catch (IOException e) {
			Log.e("TypeGame", "Could not open file & count lines");
			e.printStackTrace();
		}
		return 0;
	}

	public String getRandomWord(){

		NumGen ng = new NumGen();
		int lineNum = ng.getRandomTypeNum(totalLines);
		
		try{

			BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));
			
			int currLine = 0;
			String currWord = "";
			
			while((currWord = reader.readLine())!=null)
			{
				currLine++;
				if(currLine==lineNum){
					reader.close();
					return currWord.toUpperCase();
				}
				
			}
			return getRandomWord();
		}
		catch(IOException e){
			Log.e("TypeGame", "Could not get a random word");
			e.printStackTrace();
		}

		return getRandomWord();
	}


}
