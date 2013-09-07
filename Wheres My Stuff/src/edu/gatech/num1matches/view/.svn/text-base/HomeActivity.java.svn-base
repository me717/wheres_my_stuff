package edu.gatech.num1matches.view;

import edu.gatech.num1matches.*;
import edu.gatech.num1matches.model.*;
import edu.gatech.num1matches.presenter.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeActivity extends Activity implements OnClickListener{
	
	public final String TAG = "LoginActivity";
	private Button browseButton;
	private Button postButton;
	private Button usersButton;

    /**
     * onCreate method constructs login activity
     * @param savedInstanceState - 
     */
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        browseButton = (Button) findViewById(R.id.browseButton);
        browseButton.setOnClickListener(this);
        postButton = (Button) findViewById(R.id.postButton);
        postButton.setOnClickListener(this);
        usersButton = (Button) findViewById(R.id.viewUsersButton);
        usersButton.setOnClickListener(this);
		UserAccount user=ExternalNetwork.getExternalNetwork(this).getLoggedInUser();
		if (!user.isAdmin()){
			usersButton.setVisibility(View.GONE);
		}
    }
    
    /**
     * onCreateOptionsMenu method constructs options menu
     * @param menu - Menu that is created in the activity 
     * @return true 
     */
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
    
    /**
     * onOptionsItemSelected method gives functionality to menu options (login and home)
     * @param MenuItem item - The item in the menu that has been clicked
     */
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
     * onClick method handles click events
     * @param v 
     */
	@Override
	public void onClick(View v) {
		if (browseButton == v){
			Intent myIntent = new Intent(v.getContext(), FilterActivity.class);
		    v.getContext().startActivity(myIntent);
		}
		if (postButton == v){
			Intent myIntent = new Intent(v.getContext(), PostItemActivity.class);
		    v.getContext().startActivity(myIntent);
		}
		if (usersButton == v){
			Intent myIntent = new Intent(v.getContext(), UserViewActivity.class);
		    v.getContext().startActivity(myIntent);
		}
	}
}
