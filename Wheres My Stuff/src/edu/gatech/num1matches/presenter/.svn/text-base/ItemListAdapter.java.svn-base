package edu.gatech.num1matches.presenter;

import java.util.ArrayList;
import java.util.Arrays;

import edu.gatech.num1matches.*;
import edu.gatech.num1matches.model.*;
import edu.gatech.num1matches.view.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * ItemListAdapter is a custom adapter that handles the Items and data that will
 * be passed to the ItemList for the ItemViewActivity so that the items are
 * correctly displayed and cached
 * 
 * @author Jacob Waddell
 * @version 1.0
 */
public class ItemListAdapter extends BaseAdapter 
{
	private Activity activity;
	private ArrayList<Item> data;
	private static LayoutInflater inflater = null;

	/**
	 * Constructor for ItemListAdapter, sets up corresponding activity and
	 * inflater. Will throw a NullPointerException if a is null.
	 * 
	 * @param a  The corresponding activity for the adapter
	 */
	public ItemListAdapter(Activity a) 
	{
		activity = a;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		data = new ArrayList<Item>();
	}

	/**
	 * Constructor for ItemListAdapter, sets up corresponding activity,
	 * inflater, and initial data set. Will throw a
	 * NullPointerException if a or d is null
	 * 
	 * @param a  The corresponding activity for the adapter
	 * @param d  The array of Items to initialize the data with
	 */
	public ItemListAdapter(Activity a, Item[] d) 
	{
		this(a);
		data = new ArrayList<Item>(Arrays.asList(d));
	}

	/**
	 * Adds the item to the internal ArrayList of items to display with this
	 * Adapter
	 * 
	 * @param i  The item to add
	 */
	public void addItem(Item i) 
	{
		data.add(i);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() 
	{
		return data.size();
	}

	@Override

	public Object getItem(int position) 
	{
		return data.get(position);
	}
	
	@Override
	public long getItemId(int position) 
	{
		return position;
	}

	/**
	 * Returns a view that displays a given item
	 * 
	 * @param position the position of the desired item in the items list
	 * @param convertView the view that will be modified then returned.
	 * @param parent the parent of the convertView
	 * @return the converted view
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view = convertView;
		if (convertView == null)
			view = inflater.inflate(R.layout.item_row, null);

		/* Item Images not yet implemented */
		//ImageView itemImage = (ImageView) view.findViewById(R.id.itemImage);
		TextView itemTitle = (TextView) view.findViewById(R.id.itemTitle);
		TextView itemStatus = (TextView) view.findViewById(R.id.itemStatus);
		TextView itemDescription = (TextView) view.findViewById(R.id.itemDescription);
		TextView itemLocation = (TextView) view.findViewById(R.id.itemLocation);
		TextView itemDate = (TextView) view.findViewById(R.id.itemDate);
		TextView itemCategories = (TextView) view.findViewById(R.id.itemCategories);

		itemTitle.setText(data.get(position).name);
		itemStatus.setText(data.get(position).found ? "Found" : "Lost");
		itemDescription.setText(data.get(position).shortDescription);
		itemLocation.setText(data.get(position).location.toString());
		itemDate.setText(data.get(position).date);
		
		// Building String list of categories for the current item
		String categories = "";
		if (data.get(position) != null && data.get(position).categories != null) 
		{
			for (Category cat : data.get(position).categories) 
			{
				categories += cat.getTitle() + ", ";
			}
			categories = categories.substring(0, categories.length() - 2);
			itemCategories.setText(categories);
		}
		
		/* Setting up listener for dialog box */
		view.setTag(getItem(position));
		ItemListener il = new ItemListener(view);
		view.setOnClickListener(il);

		return view;
	}
	
	/**
	 * ItemListener class listens to click events on list of
	 * Items displayed to the screen
	 * 
	 * @author Jacob Waddell
	 */
	private class ItemListener implements OnClickListener
	{	
		private Item item;
		
		/**
		 * Constructor for ItemListener that retrieves the Item
		 * data corresponding to the item that was clicked on screen
		 * @param v  The View (Item) that was clicked
		 */
		public ItemListener(View v)
		{
			item = (Item) v.getTag();
		}
		
		@Override
		public void onClick(View v) 
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
			builder.setMessage("Would you like to view possible matches for this item?").setTitle("View Matches");
			builder.setPositiveButton("Yes", new DialogListener(v, 0));
			builder.setNegativeButton("No", new DialogListener(v, 1));
			AlertDialog dialog = builder.create();
			dialog.show();
			
			/* The following code begins a new activity once an item is selected WITHOUT displaying a dialog box */
			/*Intent myIntent = new Intent(v.getContext(), ItemViewActivity.class);
			Filter f = new Filter(item.getName(), true, true, true, true, "12/12/1800", "12/12/2100", 0, 0, 
							item.found, !item.found, item.getLocation().getCity(), item.getLocation().getState());
			myIntent.putExtra("filter", f);
			v.getContext().startActivity(myIntent);*/
		}
		
		/**
		 * DialogListener listens to click events on the dialog box that
		 * appears once a user has selected an item from the list of items
		 * on screen
		 * 
		 * @author Jacob Waddell
		 */
		private class DialogListener implements DialogInterface.OnClickListener
		{
			private View parentView;
			private int id;
			
			/**
			 * Constructor for DialogListener that establishes the parent
			 * View of this dialog box (to handle changing activities) and
			 * an id that represents whether the "YES" (id = 1) or "NO" button
			 * was clicked (id = anything but 1)
			 * 
			 * @param v  The parent view of the dialog box
			 * @param id  The ID that corresponds to a "YES" or "NO" click
			 */
			public DialogListener(View v, int id)
			{
				parentView = v;
				this.id = id;
			}

			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
				
				if (id == 0)
				{	
					Intent myIntent = new Intent(parentView.getContext(), ItemViewActivity.class);
					Filter f = new Filter(item.getName(), true, true, true, true, "12/12/1800", "12/12/2100", 0, 0, 
									item.found, !item.found, item.getLocation().getCity(), item.getLocation().getState());
					myIntent.putExtra("filter", f);
					parentView.getContext().startActivity(myIntent);
				}
			}
			
		}
	}
}
