package edu.gatech.num1matches.view;

import edu.gatech.num1matches.*;
import edu.gatech.num1matches.model.*;
import edu.gatech.num1matches.presenter.*;

import java.util.ArrayList;
import java.util.Scanner;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

/**
 * ItemViewActivity represents the activity that presents a scrollable list of
 * Items including an image and other relevant information for each item
 * 
 * @author Jacob Waddell
 * @version 1.0
 */
public class ItemViewActivity extends Activity 
{
	
	private ListView list;
	private ItemListAdapter adapter;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_view);
		
		list = (ListView)findViewById(R.id.itemList);
		Item[] items;
		
		Filter filter=(Filter) getIntent().getSerializableExtra("filter");
		if(filter!=null)	//if got here by way of Filter class
		{
			Log.d("ItemView","Using a filter");
			ArrayList<Category> cat = new ArrayList<Category>();
			
			if(filter.getAntiquesTrue()) 
				cat.add(new Category("Antiques"));
			if(filter.getMemTrue()) 
				cat.add(new Category("Memorabilia"));
			if(filter.getJewelryTrue()) 
				cat.add(new Category("Jewelry"));
			if(filter.getOtherTrue()) 
				cat.add(new Category("Other"));

			Category[] categories = (Category[])(cat.toArray(new Category[0]));
		
			Log.d("IV-FIlter","mem" + filter.getMemTrue());
			Log.d("IV-FIlter","Ant" + filter.getAntiquesTrue());
			Log.d("IV-FIlter","Jewelry" + filter.getJewelryTrue());
			Log.d("IV-FIlter","Other" + filter.getOtherTrue());
			Log.d("IV-FIlter","Start Date: " + filter.getStartDate());
			Log.d("IV-FIlter","End Date: " + filter.getEndDate());
			
			ArrayList<Item> is = PersistentStorage.getPersistentStorage(this).filterItems(categories, filter.getStartDate(),
									filter.getEndDate(), filter.getMinReward(), filter.getMaxReward(), filter.getLostTrue(),
									filter.getFoundTrue());
			
			searchItems(is, filter.getSearch());
			searchState(is, filter.getState());
			searchCity(is, filter.getCity());
			
			items = is.toArray(new Item[0]);
			if(is.size() == 0) 
				Toast.makeText(this, "Your filter yielded no results.", Toast.LENGTH_LONG).show();
			//filterItems(Category[] categories, String earlydate, String latedate, double lowreward, double highreward, boolean lost, boolean found)

		}
		else	//if got here after posting an item
		{
			Log.d("ItemView", "Not using a filter");
			items = PersistentStorage.getPersistentStorage(this).getAllItems();
		}
		
		adapter = new ItemListAdapter(this, items);
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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
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
	 * Searches though the passed in list of items and removes
	 * all entries within the list that do not contain the
	 * passed in search string
	 * 
	 * @param items  List of items to search through
	 * @param search  The string that will be searched for
	 */
	public void searchItems(ArrayList<Item> items, String search)
	{
		Scanner scan = new Scanner(search);
		ArrayList<String> searchList = new ArrayList<String>();
		
		if (search.length() == 0)
			return;
		
		while (scan.hasNext())
			searchList.add(scan.next());
		
		boolean keep = false;
		for (int i = 0; i < items.size(); i++)
		{
			for (String token : searchList)
			{
				if (items.get(i).getName().toLowerCase().contains(token.toLowerCase()))
					keep = true;
			}
			
			if (!keep)
			{
				items.remove(i);
				i--;
			}
			else
				keep = false;
		}
	}
	
	/**
	 * Searches though the passed in list of items and removes
	 * all entries within the list that do not contain the passed
	 * in city string within their city field
	 * 
	 * @param items  List of items to search through
	 * @param city  The string representing the city that will be searched for
	 */
	public void searchCity(ArrayList<Item> items, String city)
	{
		for (int i = 0; i < items.size(); i++)
		{
			if (!items.get(i).getLocation().getCity().toLowerCase().contains(city.toLowerCase()))
			{
				items.remove(i);
				i--;
			}
		}
	}
	
	/**
	 * Searches though the passed in list of items and removes
	 * all entries within the list that do not contain the
	 * passed in state string within their state field
	 * 
	 * @param items  List of items to search through
	 * @param state  The string representing the state that will be searched for
	 */
	public void searchState(ArrayList<Item> items, String state)
	{
		if(state.length()==0) 
			return;
		for (int i = 0; i < items.size(); i++)
		{
			if (!items.get(i).getLocation().getState().toLowerCase().contains(state.toLowerCase()))
			{
				items.remove(i);
				i--;
			}
		}
	}
}