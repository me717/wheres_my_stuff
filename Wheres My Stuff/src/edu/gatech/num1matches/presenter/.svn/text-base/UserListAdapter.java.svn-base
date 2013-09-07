package edu.gatech.num1matches.presenter;

import edu.gatech.num1matches.*;
import edu.gatech.num1matches.model.*;
import edu.gatech.num1matches.view.*;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast; // needed for testing statements that are currently commented out below

/**
 * UserListAdapter is a custom adapter that handles the UserAccounts and data that will
 * be passed to the UserList for the UserViewActivity so that the UserAccounts are
 * correctly displayed and cached to the AdminAccount
 * 
 * @author Jacob Waddell
 * @version 1.0
 */
public class UserListAdapter extends BaseAdapter
{
	private Activity activity;
	private ArrayList<UserAccount> data;
	private static LayoutInflater inflater = null;
	
	/**
	 * Constructor for UserListAdapter, sets up corresponding activity and
	 * inflater. Will throw a NullPointerException if a is null.
	 * 
	 * @param a  The corresponding activity for the adapter
	 */
	public UserListAdapter(Activity a) 
	{
		activity = a;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		data = new ArrayList<UserAccount>();
	}
	
	/**
	 * Constructor for UserListAdapter, sets up corresponding activity,
	 * inflater, and initial data set. Will throw a
	 * NullPointerException if a is null or d is null
	 * 
	 * @param a  The corresponding activity for the adapter
	 * @param d  The array of UserAccounts to initialize the data with
	 */
	public UserListAdapter(Activity a, UserAccount[] d) 
	{
		this(a);
		data = new ArrayList<UserAccount>(Arrays.asList(d));
	}
	
	/**
	 * Adds the UserAccount to the internal ArrayList of UserAccounts to display with this
	 * Adapter
	 * 
	 * @param user  The UserAccount to add
	 */
	public void addItem(UserAccount user) 
	{
		data.add(user);
		notifyDataSetChanged();
	}
	
	/**
	 * Refreshes the data in the adapter by re-fetching all of the
	 * UserAccount data from the database.
	 */
	public void updateData()
	{
		data = new ArrayList<UserAccount>(Arrays.asList(ExternalNetwork.getExternalNetwork(activity).getAllUsers()));
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
	 * Returns a view that displays a given UserAccount and its information
	 * 
	 * @param position  The position of the desired item in the items list
	 * @param convertView  The view that will be modified then returned.
	 * @param parent  The parent of the convertView
	 * @return the converted view
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View view = convertView;
		if (convertView == null)
			view = inflater.inflate(R.layout.user_row, null);
		
		TextView username = (TextView) view.findViewById(R.id.userNameView);
		Button blockButton = (Button) view.findViewById(R.id.blockUserButton);
		Button removeButton = (Button) view.findViewById(R.id.removeUserButton);
		Button promoteButton = (Button) view.findViewById(R.id.promoteUserButton);
		
		if (data.get(position).isAdmin())
			username.setText(data.get(position).getUsername() + " [ADMIN]");
		else
			username.setText(data.get(position).getUsername());
		
		if (data.get(position).isBlocked())
			blockButton.setText(R.string.unblockUserText);
		else
			blockButton.setText(R.string.blockUserText);
		
		if (data.get(position).isAdmin())
			promoteButton.setVisibility(View.GONE);
		else
			promoteButton.setVisibility(View.VISIBLE);
		
		/* Tags are used so that each individual user entry stores its relevant
		 * information for later usage
		 */
		ButtonListener bl = new ButtonListener(view, this);
		blockButton.setTag(data.get(position));
		removeButton.setTag(data.get(position));
		promoteButton.setTag(data.get(position));
		
		blockButton.setOnClickListener(bl);
		removeButton.setOnClickListener(bl);
		promoteButton.setOnClickListener(bl);
		
		return view;
	}
	
	/**
	 * ButtonListener class handles all user click events for the buttons that
	 * are present on each User entry in the list of Users displayed to the screen
	 * 
	 * @author Jacob Waddell
	 * @version 1.0
	 */
	private class ButtonListener implements OnClickListener
	{
		private View parentView;
		private BaseAdapter adapter;
		
		/**
		 * Constructor initializes the parent view and
		 * adapter to fetch data from
		 * @param v  The parent view in which all events will take place
		 * @param a  The adapter from which to fetch data after an action occurs
		 */
		public ButtonListener(View v, BaseAdapter a)
		{
			parentView = v;
			adapter = a;
		}

		@Override
		public void onClick(View v) 
		{
			UserAccount user = (UserAccount) v.getTag();
			
			if (v == parentView.findViewById(R.id.blockUserButton))
			{
				if(user.blocked)
				{
					ExternalNetwork.getExternalNetwork(activity).unBlockUser(user);
					((Button)v).setText(R.string.blockUserText);
					//Toast.makeText(activity, "unblocking", Toast.LENGTH_LONG).show(); -- Testing Statement
				}
				else
				{
					ExternalNetwork.getExternalNetwork(activity).blockUser(user.getUsername());
					((Button)v).setText(R.string.unblockUserText);
					//Toast.makeText(activity, "blocking", Toast.LENGTH_LONG).show(); -- Testing Statement
				}
				((UserListAdapter)adapter).updateData();
				adapter.notifyDataSetChanged();
			}
			else if (v == parentView.findViewById(R.id.removeUserButton))
			{
				ExternalNetwork.getExternalNetwork(activity).banUser(user);
				((UserListAdapter)adapter).updateData();
				adapter.notifyDataSetChanged();
			}
			else if (v == parentView.findViewById(R.id.promoteUserButton))
			{
				ExternalNetwork.getExternalNetwork(activity).promoteToAdmin(user);
				((UserListAdapter)adapter).updateData();
				adapter.notifyDataSetChanged();
			}
		}
	}

}
