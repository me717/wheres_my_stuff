package edu.gatech.num1matches.view;

import edu.gatech.num1matches.*;
import edu.gatech.num1matches.model.*;
import edu.gatech.num1matches.presenter.*;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

/**
 * UserViewActivity represents the activity that presents a scrollable list of
 * UserAccounts with a String username and buttons for admins to handle certain actions for each
 * UserAccount in the System
 * 
 * @author Jacob Waddell
 * @version 1.0
 */
public class UserViewActivity extends Activity 
{
	private ListView list;
	private UserListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_view);
		
		list = (ListView) findViewById(R.id.userList);
		
		UserAccount[] users = ExternalNetwork.getExternalNetwork(this).getAllUsers();
		UserAccount me =  ExternalNetwork.getExternalNetwork(this).getLoggedInUser();
		ArrayList<UserAccount> userList = new ArrayList<UserAccount>(Arrays.asList(users));
		userList.remove(me);
		users = userList.toArray(new UserAccount[1]);
		
		adapter = new UserListAdapter(this, users);
		list.setAdapter(adapter);
	}
	
	@Override
	public void onDestroy()
	{
		list.setAdapter(null);
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		/* Inflate the menu; this adds items to the action bar if it is present. */
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
	    /* Handle item selection */
		Intent myIntent;
	    switch (item.getItemId()) 
	    {
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
}
