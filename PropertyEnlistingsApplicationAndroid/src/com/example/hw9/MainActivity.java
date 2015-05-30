package com.example.hw9;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	static String data;
	static Spinner sItems;
	
	EditText streetText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try{
			List<String> spinnerArray =  new ArrayList<String>();
			spinnerArray.add("Choose your State");
			spinnerArray.add("AL");spinnerArray.add("AK");spinnerArray.add("AZ");spinnerArray.add("AR");spinnerArray.add("CA");spinnerArray.add("CO");spinnerArray.add("CT");spinnerArray.add("DE");spinnerArray.add("DC");spinnerArray.add("FL");spinnerArray.add("GA");spinnerArray.add("HI");spinnerArray.add("ID");spinnerArray.add("IL");spinnerArray.add("IN");spinnerArray.add("IA");spinnerArray.add("KS");spinnerArray.add("KY");spinnerArray.add("LA");spinnerArray.add("ME");spinnerArray.add("MD");spinnerArray.add("MA");spinnerArray.add("MI");spinnerArray.add("MN");spinnerArray.add("MS");spinnerArray.add("MO");spinnerArray.add("MT");spinnerArray.add("NE");spinnerArray.add("NV");spinnerArray.add("NH");spinnerArray.add("NJ");spinnerArray.add("NM");spinnerArray.add("NY");spinnerArray.add("NC");spinnerArray.add("ND");spinnerArray.add("OH");spinnerArray.add("OK");spinnerArray.add("OR");spinnerArray.add("PA");spinnerArray.add("RI");spinnerArray.add("SC");spinnerArray.add("SD");spinnerArray.add("TN");spinnerArray.add("TX");spinnerArray.add("UT");spinnerArray.add("VT");spinnerArray.add("VA");spinnerArray.add("WA");spinnerArray.add("WV");spinnerArray.add("WI");spinnerArray.add("WY");
			ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, spinnerArray);
			sItems = (Spinner) findViewById(R.id.state);
			sItems.setAdapter(adapter);
			sItems.setOnItemSelectedListener(new CustomOnItemSelectedListener(getCurrentFocus()));
			addKeyListener();
			/*ImageView img = (ImageView)findViewById(R.id.foo_bar);
			img.setOnClickListener(new View.OnClickListener(){
			    public void onClick(View v){
			        Intent intent = new Intent();
			        intent.setAction(Intent.ACTION_VIEW);
			        intent.addCategory(Intent.CATEGORY_BROWSABLE);
			        intent.setData(Uri.parse("http://casidiablo.net"));
			        startActivity(intent);
			    }
			});*/
		}catch(Exception exception){
			System.out.println("Some Error "+exception);
		}
	}
	
	public void addKeyListener() {
		 
		// get edittext component
		final EditText streetText = (EditText) findViewById(R.id.street);
		final EditText cityText = (EditText) findViewById(R.id.city);
	 
		// add a keylistener to street
		streetText.setOnKeyListener(new OnKeyListener() {
		public boolean onKey(View v, int keyCode, KeyEvent event) {
	 
			// if keydown and "enter" is pressed
			final TextView streetErrorText = (TextView) findViewById(R.id.error_message_street);
			if ((event.getAction() == KeyEvent.ACTION_DOWN) && !(keyCode == KeyEvent.KEYCODE_ENTER)
					&& !(keyCode == KeyEvent.KEYCODE_DEL) && !(keyCode == KeyEvent.KEYCODE_SPACE) 
					&& !(keyCode == KeyEvent.KEYCODE_ESCAPE) && !(keyCode == KeyEvent.KEYCODE_BACK)) {
				
				
				streetErrorText.setVisibility(View.INVISIBLE);
				// display a floating message
				return true;
			}
			if(streetText.getText().toString().length()==0){
				streetErrorText.setVisibility(View.VISIBLE);
			}else{
				streetErrorText.setVisibility(View.INVISIBLE);
			}
			return false;
		}
	 });
		
		cityText.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
		 
				final TextView cityErrorText = (TextView) findViewById(R.id.error_message_city);
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && !(keyCode == KeyEvent.KEYCODE_ENTER)
						&& !(keyCode == KeyEvent.KEYCODE_DEL) && !(keyCode == KeyEvent.KEYCODE_SPACE) 
						&& !(keyCode == KeyEvent.KEYCODE_ESCAPE) && !(keyCode == KeyEvent.KEYCODE_BACK)) {
					
					cityErrorText.setVisibility(View.VISIBLE);
					// display a floating message
					return true;
				}
				if(cityText.getText().toString().length()==0){
					cityErrorText.setVisibility(View.VISIBLE);
				}else{
					cityErrorText.setVisibility(View.INVISIBLE);
				}
				return false;
			}
		 });
	}


	@Override
	public void onClick(View arg0) {

	}

	public void showMessage(View view){
		try{
			if(checkFields()){
				final EditText streetText = (EditText) findViewById(R.id.street);
				final EditText cityText = (EditText) findViewById(R.id.city);
				final String stateText = sItems.getSelectedItem().toString();
				String street = URLEncoder.encode(streetText.getText().toString(), "UTF-8");//.replace(" ", "%20");
				String city = URLEncoder.encode(cityText.getText().toString(), "UTF-8");//.replace(" ","%20");
				String state = URLEncoder.encode(stateText, "UTF-8");//.replace(" ","%20");

				String postUrl="http://homeworkcsci571-env.elasticbeanstalk.com/?state="+state+"&street="+street+"&city="+city+"";// put in your url

				new JSONAsyncTask().execute(postUrl);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private boolean checkFields() {
		final EditText streetText = (EditText) findViewById(R.id.street);
		final EditText cityText = (EditText) findViewById(R.id.city);
		final String stateText = sItems.getSelectedItem().toString();
		boolean flag = true;
		if(streetText.getText().toString().length()==0){
			final TextView streetErrorText = (TextView) findViewById(R.id.error_message_street);
			streetErrorText.setVisibility(View.VISIBLE);
			flag = false;
		}
		if(cityText.getText().toString().length()==0){
			final TextView cityErrorText = (TextView) findViewById(R.id.error_message_city);
			cityErrorText.setVisibility(View.VISIBLE);
			flag = false;
		}
		if(stateText.equals("Choose your State")){
			final TextView stateErrorText = (TextView) findViewById(R.id.error_message_state);
			stateErrorText.setVisibility(View.VISIBLE);
			flag = false;
		}
		if(flag){
	final TextView cityErrorText = (TextView) findViewById(R.id.request_error);
	cityErrorText.setVisibility(View.INVISIBLE);
}
		return flag;
	}


	public void showZillow(View view){
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.addCategory(Intent.CATEGORY_BROWSABLE);
		intent.setData(Uri.parse("http://www.zillow.com"));
		startActivity(intent);
	}

	class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(MainActivity.this);
			dialog.setMessage("Loading, please wait");
			dialog.setTitle("Connecting server");
			dialog.show();
			dialog.setCancelable(false);
		}

		@Override
		protected Boolean doInBackground(String... urls) {
			try {

				//------------------>>
				HttpGet httppost = new HttpGet(urls[0]);
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = httpclient.execute(httppost);

				// StatusLine stat = response.getStatusLine();
				int status = response.getStatusLine().getStatusCode();

				if (status == 200) {
					HttpEntity entity = response.getEntity();
					data = EntityUtils.toString(entity);
					
					if(data.equalsIgnoreCase("No exact match found -- Verify that the given address is correct.")){
						return false;
					}

					/*JSONObject jsono = new JSONObject(data);
					JSONArray jarray = jsono.getJSONArray("actors");

					for (int i = 0; i < jarray.length(); i++) {
						JSONObject object = jarray.getJSONObject(i);

						Property actor = new Property();

						actor.setName(object.getString("name"));
						actor.setDescription(object.getString("description"));
						actor.setDob(object.getString("dob"));
						actor.setCountry(object.getString("country"));
						actor.setHeight(object.getString("height"));
						actor.setSpouse(object.getString("spouse"));
						actor.setChildren(object.getString("children"));
						actor.setImage(object.getString("image"));

						propertyList.add(actor);
					}*/
					return true;
				}

				//------------------>>

			} catch (ParseException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} /*catch (JSONException e) {
				e.printStackTrace();
			}*/
			return false;
		}

		protected void onPostExecute(Boolean result) {
			dialog.cancel();
			final TextView cityErrorText = (TextView) findViewById(R.id.request_error);
			if(result == false){
				cityErrorText.setVisibility(View.VISIBLE);
			}else{
				cityErrorText.setVisibility(View.INVISIBLE);
				Intent intent  = new Intent(getApplicationContext(),TabbedActivity.class);     
				intent.putExtra("result", data.toString());   
				startActivity(intent);
			}
		}
	}
}
