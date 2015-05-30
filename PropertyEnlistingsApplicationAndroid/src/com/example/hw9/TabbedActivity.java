package com.example.hw9;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

@SuppressLint("NewApi")
public class TabbedActivity extends FragmentActivity implements ActionBar.TabListener{
ActionBar actionBar;
ViewPager viewPager;
FragmentPageAdapter fragmentPageAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/**/
		setContentView(R.layout.activity_tabbed);
		viewPager = (ViewPager) findViewById(R.id.pager);
		Intent intent = getIntent();
		fragmentPageAdapter = new FragmentPageAdapter(getSupportFragmentManager(),intent.getStringExtra("result"));
		actionBar = getActionBar();
		viewPager.setAdapter(fragmentPageAdapter);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.addTab(actionBar.newTab().setText("Basic Info").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("Horizontal Zestimate").setTabListener(this));
		
        
		/* To bind the tab selection with the current Page */
		TextView textViewBrandingline1 = (TextView) findViewById(R.id.textView2);
		textViewBrandingline1.setText(Html.fromHtml("<a href=\"http://www.zillow.com/corp/Terms.htm\">Terms of Use</a>"));
		textViewBrandingline1.setLinksClickable(true);
		textViewBrandingline1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view)
            {
                Uri adress= Uri.parse("http://www.zillow.com/corp/Terms.htm");  
                Intent browser= new Intent(Intent.ACTION_VIEW, adress);  
                startActivity(browser);  
            }

        });
		TextView textViewBrandingline2 = (TextView) findViewById(R.id.textView3);
		textViewBrandingline2.setText(Html.fromHtml("<a href=\"http://www.zillow.com/zestimate\">What's a Zestimate?</a>"));
		textViewBrandingline2.setLinksClickable(true);
		textViewBrandingline2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view)
            {
                Uri adress= Uri.parse("http://www.zillow.com/zestimate");  
                Intent browser= new Intent(Intent.ACTION_VIEW, adress);  
                startActivity(browser);  
            }

        });
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				
				actionBar.setSelectedNavigationItem(arg0);
/*				getSupportFragmentManager()
		        .beginTransaction()
		        .add(android.R.id.content, fragmentPageAdapter.getItem(arg0))
		        .commit();*/
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    PropertyFragment propertyFragment = new PropertyFragment();
	    propertyFragment.onActivityResult(requestCode, resultCode, data);
	   
	    
	    
	    /*uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
	        @Override
	        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
	            Log.e("Activity", String.format("Error: %s", error.toString()));
	        }

	        @Override
	        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
	          
	        	String postId = "Your Post Id is "+FacebookDialog.getNativeDialogPostId(data);
	        	Toast toast = Toast.makeText(this, postId, Toast.LENGTH_SHORT);
	        	toast.show();
	        	Log.i("Activity", "Success!");
	        }
	    });*/
	}
}
