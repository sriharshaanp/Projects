/**
 * 
 */
package com.example.hw9;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;




/**
 * @author sriharsha
 *
 */
public class FragmentPageAdapter extends FragmentPagerAdapter {
	String data;
	/**
	 * @param fm
	 */
	public FragmentPageAdapter(FragmentManager fm,String data) {
		super(fm);
		this.data = data;
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
	 */
	@Override
	public Fragment getItem(int arg0) {
		switch (arg0) {
		case 0:
			return new PropertyFragment(data); 
		case 1:
			return new GraphFragment(data);	

		default:
			break;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}

}
