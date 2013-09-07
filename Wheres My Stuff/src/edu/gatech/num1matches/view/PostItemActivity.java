package edu.gatech.num1matches.view;

import edu.gatech.num1matches.*;
import edu.gatech.num1matches.model.*;
import edu.gatech.num1matches.presenter.*;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;

import java.util.ArrayList;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class PostItemActivity extends Activity implements OnClickListener {
	
	//declares variables
	public final String TAG = "PostItemActivity";
	private Button cancelButton;
	private Button submitButton;
	private EditText enterName;
	private EditText enterDescription;
	private EditText enterAddress;
	private EditText enterCity;
	private EditText enterState;
	private EditText enterZip;
	private EditText enterMonth;
	private EditText enterDay;
	private EditText enterYear;
	private CheckBox antiques;
	private CheckBox mem;
	private CheckBox jewelry;
	private CheckBox other;
	private RadioGroup lostOrFound;
	private static int itemId=1;
	
	@Override
	/**
	 * Sets up listeners and all above variables.
	 * @param savedInstanceStates 
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_item);
		cancelButton = (Button) findViewById(R.id.buttonCancel);
        cancelButton.setOnClickListener(this);
        submitButton = (Button) findViewById(R.id.buttonSubmit);
        submitButton.setOnClickListener(this);
        enterName = (EditText) findViewById(R.id.enterName);
		enterDescription = (EditText) findViewById(R.id.enterDescription);
		enterAddress = (EditText) findViewById(R.id.enterAddress);
		enterCity = (EditText) findViewById(R.id.enterCity);
		enterState = (EditText) findViewById(R.id.enterState);
		enterZip = (EditText) findViewById(R.id.enterZipCode);
		enterMonth = (EditText) findViewById(R.id.enterMonth1);
		enterDay = (EditText) findViewById(R.id.enterDay1);
		enterYear = (EditText) findViewById(R.id.enterYear1);
		antiques = (CheckBox) findViewById(R.id.checkBoxAntiques);
		mem = (CheckBox) findViewById(R.id.checkBoxMemorabilia);
		jewelry = (CheckBox) findViewById(R.id.checkBoxJewelry);
		other = (CheckBox) findViewById(R.id.checkBoxOther);
		lostOrFound = (RadioGroup) findViewById(R.id.radioLostorFound);
	}

	/**
	 * 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
		Intent myIntent;
	    switch (item.getItemId()) {
	        case R.id.logoutButton:
				myIntent = new Intent(this, LoginActivity.class);
			    startActivity(myIntent);
	            return true;
	        case R.id.homeButton:
				myIntent = new Intent(this, HomeActivity.class);
			    startActivity(myIntent);
	            return true;
	        default:
	            return false;
	    }
	}
	
	/**
	 * Decides what to do when the user clicks one of the buttons
	 * @param v the current view your in
	 */
	public void onClick(View v) {
		if(submitButton == v){
			String name = enterName.getText().toString();
			String descript = enterDescription.getText().toString();
			String address = enterAddress.getText().toString();
			String city = enterCity.getText().toString();
			String state = enterState.getText().toString();
			
			int zip;
			if(enterZip.getText().toString()==null||enterZip.getText().toString().equals("")) zip=-1;
			else zip= Integer.parseInt(enterZip.getText().toString());
			
			int month;
			if(enterMonth.getText().toString()==null||enterMonth.getText().toString().equals("")) month=-1;
			else month= Integer.parseInt(enterMonth.getText().toString());
			
			int day;
			if(enterDay.getText().toString()==null||enterDay.getText().toString().equals("")) day=-1;
			else day= Integer.parseInt(enterDay.getText().toString());
			
			int year;
			if(enterYear.getText().toString()==null||enterYear.getText().toString().equals("")) year=-1;
			else year= Integer.parseInt(enterYear.getText().toString());
			

			boolean antiquesChecked = antiques.isChecked();
			boolean memChecked = mem.isChecked();
			boolean jewelryChecked = jewelry.isChecked();
			boolean otherChecked = other.isChecked();
			
			boolean found=(lostOrFound.getCheckedRadioButtonId()==R.id.radioFound);
			
			if(name==null||name.equals(""))
			{
				Toast.makeText(this, "Please enter the item's name.",Toast.LENGTH_LONG).show();
			}
			else if(descript==null||descript.equals(""))
			{
				Toast.makeText(this, "Please enter a description for your item.",Toast.LENGTH_LONG).show();
			}
			else if(address==null||address.equals(""))
			{
				Toast.makeText(this, "Please enter an address for where you found or lost the item.",Toast.LENGTH_LONG).show();
			}
			else if(city==null||city.equals(""))
			{
				Toast.makeText(this, "Please enter a city for where you found or lost the item.",Toast.LENGTH_LONG).show();
			}
			else if(state==null||state.equals(""))
			{
				Toast.makeText(this, "Please enter a state for where you found or lost the item.",Toast.LENGTH_LONG).show();
			}
			else if (day > 31|| day<=0|| month<=0 || month > 12|| year<0) 
			{
				Toast.makeText(this, "Please enter a valid date.", Toast.LENGTH_LONG).show();
			}
			else if(!memChecked&&!jewelryChecked&&!antiquesChecked&&!otherChecked)
			{
				Toast.makeText(this, "Please choose a category for your item.", Toast.LENGTH_LONG).show();
			}
			else if(zip==-1)
			{
				Toast.makeText(this, "Please enter a zip code.", Toast.LENGTH_LONG).show();
			}
			else if(month==-1)
			{
				Toast.makeText(this, "Please enter a month.", Toast.LENGTH_LONG).show();
			}
			else if(day==-1)
			{
				Toast.makeText(this, "Please enter a day.", Toast.LENGTH_LONG).show();
			}
			else if(year==-1)
			{
				Toast.makeText(this, "Please enter a year.", Toast.LENGTH_LONG).show();
			}
			else
			{
				//make item
				Location loc = new Location(address,city,state,zip);
				UserAccount postedBy=ExternalNetwork.getExternalNetwork(this).getLoggedInUser();
				ArrayList<Category> cats= new ArrayList<Category>();
				if(antiquesChecked) cats.add(new Category("Antiques"));
				if(memChecked) cats.add(new Category("Memorabilia"));
				if(jewelryChecked) cats.add(new Category("Jewelry"));
				if(otherChecked) cats.add(new Category("Other"));
				Category[] catArray= cats.toArray(new Category[0]);
				String shortDescript;
				if(descript.length()<15) shortDescript=descript.substring(0,descript.length());
				else shortDescript = descript.substring(0,15)+"...";
				Item i=new Item(itemId,found, name, catArray,postedBy, loc, shortDescript, descript, (month+"/"+day+"/"+year), "www.google.com");
				itemId++;
				Item[] items=new Item[1];
				items[0]=i;
				boolean posted=PersistentStorage.getPersistentStorage(this).storeItems(items);
				if(posted) Toast.makeText(this, "Your item has been posted.", Toast.LENGTH_LONG).show();
				else Toast.makeText(this, "There was an error putting in your item. Please try again.", Toast.LENGTH_LONG).show();
				Intent myIntent = new Intent(v.getContext(), ItemViewActivity.class);
			    v.getContext().startActivity(myIntent);
			}
		}
		
		if(cancelButton == v){
			Log.d(TAG, "Cancel Button Pressed"); //debug
			Intent myIntent = new Intent(v.getContext(), HomeActivity.class);
		    v.getContext().startActivity(myIntent);
		}
	}
}