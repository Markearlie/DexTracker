package com.dextracker.fragments;

import com.dextracker.fragments.SmashLeaderboardFragment;
import com.dextracker.fragments.SequentialLeaderboardFragment;
import com.dextracker.fragments.TypeLeaderboardFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

//credit: http://www.androidhive.info/2013/10/android-tab-layout-with-swipeable-views-1/
public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new SequentialLeaderboardFragment();
		case 1:
			// Games fragment activity
			return new TypeLeaderboardFragment();
		case 2:
			// Movies fragment activity
			return new TypeRightLeaderboardFragment();
		case 3:
			// Movies fragment activity
			return new SmashLeaderboardFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 4;
	}

}
