/**
 * 
 */
package com.example.hw9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;



/**
 * @author sriharsha
 *
 */
public class CustomOnItemSelectedListener implements OnItemSelectedListener {
	View currentView;
	
	public CustomOnItemSelectedListener(View currentView){
		this.currentView = currentView;
	}
	public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
		/* findViewById(R.id.error_message_state);
		stateErrorText.setVisibility(View.INVISIBLE);*/
		LayoutInflater inflater = (LayoutInflater) currentView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row = inflater.inflate(R.layout.activity_main,parent,false);
		final TextView stateErrorText = (TextView)row.findViewById(R.id.error_message_state);
		stateErrorText.setVisibility(View.INVISIBLE);
	  }
	 
	  @Override
	  public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	  }
}
