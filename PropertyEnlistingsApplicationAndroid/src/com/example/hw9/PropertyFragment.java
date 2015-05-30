/**
 * 
 */
package com.example.hw9;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;

/**
 * @author sriharsha
 *
 */
@SuppressLint("NewApi")
public class PropertyFragment extends Fragment {
	ListView listView;
	String result;
	static FragmentActivity frag ;
	/**
	 * 
	 */
	public PropertyFragment() {
		// TODO Auto-generated constructor stub
	}

	public PropertyFragment(String result){
		this.result = result;
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		final ViewGroup container1 = container;
		View v = inflater.inflate(R.layout.property_layout, container,false);
		/*listView = (ListView) v.findViewById(R.id.mainListView);
		PropertyAdapter pA = new PropertyAdapter(v.getContext(), R.layout.row_layout,getActivity());
		listView.setAdapter(pA);*/
		
		try{
			frag = getActivity();
			JSONObject jsono = new JSONObject(result);
			final JSONObject mainResult = (JSONObject)jsono.get("result");

			TextView textView21 = (TextView) v.findViewById(R.id.textView21);
			textView21.setText(Html.fromHtml("<a href=\""+mainResult.getString("homeDetails") +"\">"+mainResult.getString("street") + ", " + mainResult.getString("city") + ", " + mainResult.getString("state") + "-" + mainResult.getString("zipcode")+"</a> "));
			textView21.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View view)
	            {
	                Uri adress=null;
					try {
						adress = Uri.parse(mainResult.getString("homeDetails"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
	                Intent browser= new Intent(Intent.ACTION_VIEW, adress);  
	                startActivity(browser);  
	            }

	        });
			TextView propDesc3 = (TextView) v.findViewById(R.id.property_description3);
			propDesc3.setText("Property Type");
			TextView propValue3 = (TextView) v.findViewById(R.id.property_value3);
			propValue3.setText(mainResult.getString("useCode"));
			
			TextView propDesc4 = (TextView) v.findViewById(R.id.property_description4);
			propDesc4.setText("Year Built");
			TextView propValue4 = (TextView) v.findViewById(R.id.property_value4);
			propValue4.setText(mainResult.getString("yearBuilt"));
			
			TextView propDesc5 = (TextView) v.findViewById(R.id.property_description5);
			propDesc5.setText("Lot Size");
			TextView propValue5 = (TextView) v.findViewById(R.id.property_value5);
			propValue5.setText(mainResult.getString("lotSize"));
			
			TextView propDesc6 = (TextView) v.findViewById(R.id.property_description6);
			propDesc6.setText("Finished Area");
			TextView propValue6 = (TextView) v.findViewById(R.id.property_value6);
			propValue6.setText(mainResult.getString("finishedArea"));
			
			TextView propDesc7 = (TextView) v.findViewById(R.id.property_description7);
			propDesc7.setText("Bathrooms");
			TextView propValue7 = (TextView) v.findViewById(R.id.property_value7);
			propValue7.setText(mainResult.getString("bathrooms"));
			
			TextView propDesc8 = (TextView) v.findViewById(R.id.property_description8);
			propDesc8.setText("Bedrooms");
			TextView propValue8 = (TextView) v.findViewById(R.id.property_value8);
			propValue8.setText(mainResult.getString("bedrooms"));
			
			TextView propDesc9 = (TextView) v.findViewById(R.id.property_description9);
			propDesc9.setText("Tax Assesment Year");
			TextView propValue9 = (TextView) v.findViewById(R.id.property_value9);
			propValue9.setText(mainResult.getString("taxAssesmentYear"));
			
			TextView propDesc10 = (TextView) v.findViewById(R.id.property_description10);
			propDesc10.setText("Tax Assesment");
			TextView propValue10 = (TextView) v.findViewById(R.id.property_value10);
			propValue10.setText(mainResult.getString("taxAssesment"));
			
			
			TextView propDesc11 = (TextView) v.findViewById(R.id.property_description11);
			propDesc11.setText("Last Sold Price");
			TextView propValue11 = (TextView) v.findViewById(R.id.property_value11);
			propValue11.setText(mainResult.getString("lastSoldPrice"));
			
			//"Last Sold Date",mainResult.getString("lastSoldDate")
			TextView propDesc12 = (TextView) v.findViewById(R.id.property_description12);
			propDesc12.setText("Last Sold Date");
			TextView propValue12 = (TextView) v.findViewById(R.id.property_value12);
			propValue12.setText(mainResult.getString("lastSoldDate"));
			
			//"Zestimate \u00AE Property Estimate as of "+mainResult.getString("estimateLastUpdate"),mainResult.getString("estimateAmount")
			TextView propDesc13 = (TextView) v.findViewById(R.id.property_description13);
			propDesc13.setText("Zestimate \u00AE Property Estimate as of "+mainResult.getString("estimateLastUpdate"));
			TextView propValue13 = (TextView) v.findViewById(R.id.property_value13);
			propValue13.setText(mainResult.getString("estimateAmount"));
			
			//"30 Days Overall Change",mainResult.getString("estimateValueChange"),mainResult.getString("img1")
			TextView propDesc14 = (TextView) v.findViewById(R.id.property_description14);
			propDesc14.setText("30 Days Overall Change");
			ImageView imageView1 = (ImageView) v.findViewById(R.id.arrow_img14);
			if(mainResult.getString("img1").equalsIgnoreCase("http://cs-server.usc.edu:45678/hw/hw6/up_g.gif")){
				imageView1.setImageResource(R.drawable.up_g);
			}else if(mainResult.getString("img1").equalsIgnoreCase("http://cs-server.usc.edu:45678/hw/hw6/down_r.gif")){
				imageView1.setImageResource(R.drawable.down_r);
			}
			TextView propValue14 = (TextView) v.findViewById(R.id.property_value14);
			propValue14.setText(mainResult.getString("estimateValueChange"));
			
			//"All Time Property Range",mainResult.getString("allTimePropertyRange")
			TextView propDesc15 = (TextView) v.findViewById(R.id.property_description15);
			propDesc15.setText("All Time Property Range");
			TextView propValue15 = (TextView) v.findViewById(R.id.property_value15);
			propValue15.setText(mainResult.getString("allTimePropertyRange"));
			
			//"Rent Zestimate \u00AE Property Estimate as of "+mainResult.getString("rentZestimateLastUpdate"),mainResult.getString("restimateValueChange")
			TextView propDesc16 = (TextView) v.findViewById(R.id.property_description16);
			propDesc16.setText("Rent Zestimate \u00AE Property Estimate as of "+mainResult.getString("rentZestimateLastUpdate"));
			TextView propValue16 = (TextView) v.findViewById(R.id.property_value16);
			propValue16.setText(mainResult.getString("restimateValueChange"));
			
			//"30 Days Overall Change",mainResult.getString("daysRentChange"),mainResult.getString("img2")
			TextView propDesc17 = (TextView) v.findViewById(R.id.property_description17);
			propDesc17.setText("30 Days Overall Change");
			ImageView imageView2 = (ImageView) v.findViewById(R.id.arrow_img17);
			if(mainResult.getString("img2").equalsIgnoreCase("http://cs-server.usc.edu:45678/hw/hw6/up_g.gif")){
				imageView2.setImageResource(R.drawable.up_g);
			}else if(mainResult.getString("img2").equalsIgnoreCase("http://cs-server.usc.edu:45678/hw/hw6/down_r.gif")){
				imageView2.setImageResource(R.drawable.down_r);
			}
			TextView propValue17 = (TextView) v.findViewById(R.id.property_value17);
			propValue17.setText(mainResult.getString("daysRentChange"));
			
			//"All Time Rent Range",mainResult.getString("allTimeRentRange")
			TextView propDesc18 = (TextView) v.findViewById(R.id.property_description18);
			propDesc18.setText("All Time Rent Range");
			TextView propValue18 = (TextView) v.findViewById(R.id.property_value18);
			propValue18.setText(mainResult.getString("allTimeRentRange"));
			
				
			Button connect=(Button)v.findViewById(R.id.shareButton);
			connect.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle("Post to Facebook");
                    // alert.setMessage("Message");

                    alertDialog.setPositiveButton("Post Property Details", new DialogInterface.OnClickListener() {
                        @SuppressWarnings("deprecation")
						public void onClick(DialogInterface dialog, int whichButton) {
                            String APPLICATION_ID = "1506154922980891";
                            final Facebook fb= new Facebook(APPLICATION_ID);

                            if(fb.isSessionValid())
                            {
                                try {
                                    fb.logout(container1.getContext());
                                } catch (MalformedURLException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                            else
                            {
                                fb.authorize((Activity) container1.getContext(), new Facebook.DialogListener() {
                                    @Override
                                    public void onFacebookError(FacebookError e) {
                                        // TODO Auto-generated method stub
                                        Log.i("Error",e+"");
                                        Toast.makeText(container1.getContext(), "Facebook Error : "+e, Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onError(DialogError e) {
                                        // TODO Auto-generated method stub
                                        Log.i("Error",e+"");
                                        Toast.makeText(container1.getContext(), "Error in onError method : "+e, Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onComplete(Bundle values) {
                                        // TODO Auto-generated method stub
                                        Log.i("Inside onComplete", "inside onComplete method");
                                        final String postid = values.getString("post_id");
                                        Toast.makeText(container1.getContext(), "Posted Story, ID: "+postid, Toast.LENGTH_LONG).show();

                                    }

                                    @Override
                                    public void onCancel() {
                                        // TODO Auto-generated method stub
                                        Log.i("Inside onCancel","inside onCancel method");
                                        Toast.makeText(container1.getContext(), "onCancel", Toast.LENGTH_LONG).show();
                                    }
                                });
                                //Setting the dialog
                                Bundle params = new Bundle();
                                try{
                                JSONObject jsono = new JSONObject(result);
                        		final JSONObject mainResult = (JSONObject)jsono.get("result");
                        		JSONObject chartResult = (JSONObject)jsono.get("chart");
                        		final JSONObject chartResult1 = (JSONObject)chartResult.get("year1");


                                try {
                                    params.putString("name", mainResult.getString("street") + ", " + mainResult.getString("city") + ", " + mainResult.getString("state") + "-" + mainResult.getString("zipcode"));
                                    params.putString("caption", "Property information from Zillow.com");
                                    params.putString("description", "Last Sold Price:" + mainResult.getString("lastSoldPrice") + ",\n" + "30 Days Overall Change:" + mainResult.getString("sign"));
                                    params.putString("link", mainResult.getString("homeDetails"));
                                    params.putString("picture", chartResult1.getString("url"));
                                }catch (Exception e){
                                    Log.i("Error while setting the properties of facebook" , e+" ");
                                }
                                
                                }catch(Exception exception){
                                	Log.i("Error while setting the properties of facebook" , exception+" ");
                                }
                                fb.dialog(container1.getContext(), "feed", params,new Facebook.DialogListener() {
                                    @Override
                                    public void onFacebookError(FacebookError e) {
                                        // TODO Auto-generated method stub
                                    	Toast.makeText(container1.getContext(), "Post Cancelled", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onError(DialogError e) {
                                        // TODO Auto-generated method stub
                                    	Toast.makeText(container1.getContext(), "Post Cancelled", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onComplete(Bundle values) {
                                        // TODO Auto-generated method stub
                                    	final String postid = values.getString("post_id");
                                    	//Toast.makeText(container.getContext(), "Posted Story, ID: "+postid, Toast.LENGTH_LONG).show();
                                    	//Toast.makeText(container.getContext(), "Completed!", Toast.LENGTH_LONG).show();

                                    	if (postid==null){
                                    	Toast.makeText(container1.getContext(), "Post Cancelled", Toast.LENGTH_LONG).show();
                                    	}
                                    	else{
                                    	Toast.makeText(container1.getContext(), "Posted Story, ID: "+postid, Toast.LENGTH_LONG).show();
                                    	}
                                        //Toast.makeText(container1.getContext(), "Completed!", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onCancel() {
                                        // TODO Auto-generated method stub
                                        Log.i("on Cancel", "onCancel");
                                        Toast.makeText(container1.getContext(), "Post Cancelled", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    });

                    alertDialog.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            });
                    alertDialog.show();
                }
            });	

		}catch(Exception exception){
			exception.printStackTrace();
		}
		 
		return v;
	}
}