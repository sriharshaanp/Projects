/**
 * 
 */
package com.example.hw9;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

/**
 * @author sriharsha
 *
 */
@SuppressLint("NewApi")
public class GraphFragment  extends Fragment {
	private Button img,img2;
	String result;
	private TextSwitcher mSwitcher;
	private TextView tv;
	// Array of String to Show In TextSwitcher
	String textToShow[]={"Historical Zestimate for the past 1 year","Historical Zestimate for the past 5 years",
			"Historical Zestimate for the past 10 years"};
	List<String> imgToShow = new ArrayList<String>();
	int messageCount=textToShow.length;
	Animation in,out;
	// to keep current Index of text
	int currentIndex=-1; 
	private ImageSwitcher imageSwitcher;
	View v;
	/**
	 * 
	 */
	public GraphFragment(String data) {
		result = data;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.graph_layout, container,false);
		
		tv = (TextView) v.findViewById(R.id.property_description);
		populateTheLists(result);
		JSONObject jsono;
		try {
			jsono = new JSONObject(result);
			JSONObject mainResult = (JSONObject)jsono.get("result");
			tv.setText(Html.fromHtml(mainResult.getString("street") + ", " + mainResult.getString("city") + ", " + mainResult.getString("state") + "-" + mainResult.getString("zipcode")));
			tv.setGravity(Gravity.CENTER_HORIZONTAL);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		
		imageSwitcher = (ImageSwitcher)v.findViewById(R.id.imageSwitcher1);
		img = (Button) v.findViewById(R.id.imageButton1);
		img2 = (Button) v.findViewById(R.id.imageButton2);
		
		
		img.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new NextAsyncTask().execute();
			} 
		}); 
		img2.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new PreviousAsyncTask().execute();
			} 
		}); 
		imageSwitcher.setFactory(new ViewFactory() {

			@SuppressWarnings("deprecation")
			@Override
			public View makeView() {
				ImageView myView = new ImageView(v.getContext());
				myView.setScaleType(ImageView.ScaleType.FIT_CENTER);
				myView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.
						FILL_PARENT,LayoutParams.FILL_PARENT));
				return myView;
			}

		});
		mSwitcher = (TextSwitcher) v.findViewById(R.id.textSwitcher);

		// Set the ViewFactory of the TextSwitcher that will create TextView object when asked
		mSwitcher.setFactory(new ViewFactory() {

			public View makeView() {
				// TODO Auto-generated method stub
				// create new textView and set the properties like clolr, size etc
				TextView myText = new TextView(v.getContext());
				myText.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
				myText.setTextSize(16);
				myText.setTextColor(Color.BLACK);
				return myText;
			}
		});

		return v;
	}

	private void populateTheLists(String result2) {
		JSONObject jsono;
		try {
			jsono = new JSONObject(result2);
			JSONObject mainResult = (JSONObject)jsono.get("chart");
			JSONObject year1 = (JSONObject)mainResult.get("year1");
			JSONObject years5 = (JSONObject)mainResult.get("years5");
			JSONObject years10 = (JSONObject)mainResult.get("years10");
			imgToShow.add(0,year1.getString("url"));
			imgToShow.add(1,years5.getString("url"));
			imgToShow.add(2,years10.getString("url"));
			new InitialAsyncTask().execute();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	class InitialAsyncTask extends AsyncTask<Void, Void, Drawable> {

		protected Drawable doInBackground(Void... urls) {
			Drawable d=null;
			try{
				currentIndex =0;
				InputStream is = (InputStream) new URL(imgToShow.get(0)).getContent();
				
				d = Drawable.createFromStream(is, "src name");
				/*InputStream content = (InputStream)url.getContent();
		    		    	  Drawable d = Drawable.createFromStream(content , "src");*/ 
			}catch(Exception exception){
				exception.printStackTrace();
			}
			return d;
		}

		protected void onPostExecute(Drawable feed) {
			// Declare the in and out animations and initialize them 
			/*in = AnimationUtils.loadAnimation(v.getContext(),android.R.anim.slide_out_right);
			out = AnimationUtils.loadAnimation(v.getContext(),android.R.anim.slide_in_left);

			// set the animation type of textSwitcher
			mSwitcher.setInAnimation(in);
			mSwitcher.setOutAnimation(out);
			imageSwitcher.setInAnimation(in);
			imageSwitcher.setOutAnimation(out);
			imageSwitcher.setImageDrawable(feed);*/
			// TODO Auto-generated method stub
			/*if(currentIndex==-1){
				currentIndex=messageCount-1;
			}else if(currentIndex == messageCount){
				currentIndex=0;
			}else{
				currentIndex--;
			}
			// If index reaches minimum reset it
			if(currentIndex==-1){
				currentIndex=messageCount-1;
			}*/
			imageSwitcher.setImageDrawable(feed);
			mSwitcher.setText(textToShow[0]);
		}
	}
	class PreviousAsyncTask extends AsyncTask<Void, Void, Drawable> {

		protected Drawable doInBackground(Void... urls) {
			Drawable d=null;
			try{
				if(currentIndex==-1){
					currentIndex=messageCount-1;
				}else if(currentIndex == messageCount){
					currentIndex=0;
				}else{
					currentIndex--;
				}
				// If index reaches minimum reset it
				if(currentIndex==-1){
					currentIndex=messageCount-1;
				}
				InputStream is = (InputStream) new URL(imgToShow.get(currentIndex)).getContent();
				
				d = Drawable.createFromStream(is, "src name");
				/*InputStream content = (InputStream)url.getContent();
		    		    	  Drawable d = Drawable.createFromStream(content , "src");*/ 
			}catch(Exception exception){
				exception.printStackTrace();
			}
			return d;
		}

		protected void onPostExecute(Drawable feed) {
			// Declare the in and out animations and initialize them 
			in = AnimationUtils.loadAnimation(v.getContext(),android.R.anim.slide_out_right);
			out = AnimationUtils.loadAnimation(v.getContext(),android.R.anim.slide_in_left);

			// set the animation type of textSwitcher
			mSwitcher.setInAnimation(out);
			mSwitcher.setOutAnimation(in);
			imageSwitcher.setInAnimation(out);
			imageSwitcher.setOutAnimation(in);
			imageSwitcher.setImageDrawable(feed);
			// TODO Auto-generated method stub
			
			
			mSwitcher.setText(textToShow[currentIndex]);
		}
	}
	class NextAsyncTask extends AsyncTask<Void, Void, Drawable> {

		protected Drawable doInBackground(Void... urls) {
			Drawable d=null;
			try{
				// TODO Auto-generated method stub
				currentIndex++;
				// If index reaches maximum reset it
				if(currentIndex==messageCount){
					currentIndex=0;
				}
				InputStream is = (InputStream) new URL(imgToShow.get(currentIndex)).getContent();
				d = Drawable.createFromStream(is, "src name");
				/*InputStream content = (InputStream)url.getContent();
		    		    	  Drawable d = Drawable.createFromStream(content , "src");*/ 
			}catch(Exception exception){
				exception.printStackTrace();
			}
			return d;
		}

		protected void onPostExecute(Drawable feed) {
			// Declare the in and out animations and initialize them 
			in = AnimationUtils.loadAnimation(v.getContext(),android.R.anim.slide_out_right);
			out = AnimationUtils.loadAnimation(v.getContext(),android.R.anim.slide_in_left);

			// set the animation type of textSwitcher
			mSwitcher.setInAnimation(out);
			mSwitcher.setOutAnimation(in);
			imageSwitcher.setInAnimation(out);
			imageSwitcher.setOutAnimation(in);
			imageSwitcher.setImageDrawable(feed);
			
			mSwitcher.setText(textToShow[currentIndex]);
		}
	}


}

