package edu.gatech.num1matches.model;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * A class that handles all interactions between the Android app and the local database that handles item storage.
 * @author Andrew Dehn
 *
 */
public class PersistentStorage 
{
	public final String TAG = "PersistentStorage";
	
	private static PersistentStorage storageObject;
	
	
	//private final String itemsFile = "items.txt";
	
	private String filesDir;
	private SqliteHelper sqlHelper;
	
	
	/**
	 * Constructs a new PeresistentStorage object. 
	 * @param act An activity context from the application.
	 */
	private PersistentStorage(Activity act)
	{
		this.filesDir = act.getFilesDir().getAbsolutePath();
		Log.d(TAG, "Created PersistentStorage object with path " + filesDir);
		this.sqlHelper = new SqliteHelper(act);
	}
	
	/**
	 * Retrieves the PeresistentStorage object for the interaction with PersistentStorage services.
	 * @param act An activity context from the application.
	 * @return
	 */
	public static PersistentStorage getPersistentStorage(Activity act)
	{
		if(PersistentStorage.storageObject == null)
		{
			PersistentStorage.storageObject = new PersistentStorage(act);
		}
		return storageObject;
	}
	
	
	/**
	 * Gets all the items.
	 * 
	 * Right now, this gets the items from the database, plus 3 predefined items for debug purposes.
	 * 
	 * @return An ArrayList<Items> containing all the items.
	 */
	public ArrayList<Item> getAllItemsArrayList()
	{
		ArrayList<Item> list = new ArrayList<Item>();
		/*UserAccount user = new UserAccount("andrew@matches.net", "password", "loginIdentifier", null, false);
		Location loc = new Location("Georgia Tech" ,"Atlanta", "Gerogia", 30332);
		list.add(new Item(1, false, "Toaster", null, user, loc, "A toaster", "A fancy toaster with gold trimming.", "3/1/2013", ""));
		list.add(new Item(1, false, "Blender", null, user, loc, "A blender", "A fancy blender with silver trimming.", "3/1/2013", ""));
		list.add(new Item(1, false, "Nether Portal", null, user, loc, "A nether portal", "A port to your doom.", "3/1/2013", ""));*/
		getAllItemsFromDatabase(list);
		return list;
	}
	
	/**
	 * Gets all the items, as an Item[].
	 * @return An Item[] containing all the items.
	 */
	public Item[] getAllItems()
	{
		return getAllItemsArrayList().toArray(new Item[0]);
	}
	
	/**
	 * Stores items into the local database.
	 * @param items An Item[] of items to store into the local database.
	 * @return True if the items are store, false if there is problem while storing.
	 */
	public boolean storeItems(Item[] items)
	{
		return storeItemsInDatabase(items);
	}
	
	/**
	 * Returns a list of items meeting the specified parameters.
	 * @param categories An array of categories that the items can be in.
	 * @param earlydate The earliest date that the items can be posted. If null, no date is filtered and the latedate variable is ignored.
	 * @param latedate The latest date that the items can be posted.
	 * @param lowreward The lowest reward that the item can have. If -1, then no reward is filtered and the highreward variable is ignored.
	 * @param highreward The highest reward that the item can have.
	 * @return All the items that fit the parameters.
	 */
	public ArrayList<Item> filterItems(Category[] categories, String earlydate, String latedate, double lowreward, double highreward, boolean lost, boolean found)
	{
		ArrayList<Item> list = new ArrayList<Item>();
		filterItemsFromDatabase(list, categories, earlydate, latedate, lowreward, highreward, lost, found);
		return list;
	}
	
	
	/**
	 * Retrieves all items from the local database and returns it as an ArrayList<Item>
	 * @param list Optional ArrayList<Item> that the items will be added to. If null, a new ArrayList<Item> is created and returned.
	 * @return If list is not null, then the 'list' parameter is returned after the items have been added. If list is null, returns a new ArrayList<Item> that contains the items. 
	 */
	private ArrayList<Item> getAllItemsFromDatabase(ArrayList<Item> list)
	{
		if(list == null)
		{
			list = new ArrayList<Item>();
		}
		
		SQLiteDatabase db = sqlHelper.getWritableDatabase();
		Cursor cursor = db.query(SqliteHelper.ITEMS_TABLE_NAME, null, null, null, null, null, null);
		cursor.moveToFirst();
		Log.d(TAG, "5th colum is " + cursor.getColumnName(4));
		String[] cat_column = {"Cname"};
		
		while(!(cursor.isAfterLast()))
		{
			int itemID = cursor.getInt(0);
			String[] selectionArgs = {Integer.toString(itemID)};
			Cursor cat_cursor = db.query(SqliteHelper.ITEMCATEGORY_TABLE_NAME, cat_column, "ItemID = ?", selectionArgs, null, null, null);
			cat_cursor.moveToFirst();
			
			int cat_length = cat_cursor.getCount();
			Category[] cat_array = new Category[cat_length];
			for(int i = 0; i < cat_length; i++)
			{
				cat_cursor.moveToPosition(i);
				cat_array[i] = new Category(cat_cursor.getString(0));
			}
			
			list.add(new Item(itemID, ((cursor.getInt(1) == 1) ? true : false), cursor.getString(2), cat_array, new RemoteUserAccount(cursor.getString(3)), new Location(cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getInt(10)), cursor.getString(4), cursor.getString(5), databaseToDate(cursor.getString(6)), cursor.getString(11)));
			cursor.moveToNext();
		}
		
		db.close();
		return list;
	}
	
	/**
	 * Stores items in the given array into the items database.
	 * @param items An array of items to be inserted into the database. Non of the elements are allowed to be null.
	 * @return True if items were successfully inserted, false otherwise.
	 */
	private boolean storeItemsInDatabase(Item[] items)
	{
		SQLiteDatabase db = sqlHelper.getWritableDatabase();
		long insertResponse;
		
		Log.d(TAG, "Storing " + items.length + " items");
		
		for(int i = 0; i < items.length; i++)
		{
			Log.d(TAG, "Processing item index " + i);
			ContentValues cv = new ContentValues(15);
			//cv.put("ItemID", items[i].getItemID());
			cv.put("Found", items[i].wasFound());
			cv.put("Name", items[i].getName());
			cv.put("PostedBy", items[i].postedBy().getUsername());
			cv.put("ShortDescription", items[i].getShortDescription());
			cv.put("LongDescription", items[i].getLongDescription());
			cv.put("Date", dateToDatabase(items[i].getDate()));
			cv.put("LocationAddress", items[i].getLocation().getAddress());
			cv.put("LocationCity", items[i].getLocation().getCity());
			cv.put("LocationState", items[i].getLocation().getState());
			cv.put("LocationZipCode", items[i].getLocation().getZipCode());
			cv.put("ImageURL", items[i].getImageURL());
			
			
		/*	int day, month, year;
			String[] parsedDate = items[i].getDate().split("/");
			if(parsedDate.length != 3)
			{
				//TODO ERROR CODE 
			}
			month = Integer.parseInt(parsedDate[0]);
			day = Integer.parseInt(parsedDate[1]);
			year = Integer.parseInt(parsedDate[2]);
			cv.put("Month", month);
			cv.put("Day", day);
			cv.put("Year", year);
			*/
			
			
			Log.d(TAG, "INSERTING: " + cv.toString());
			insertResponse = db.insertOrThrow(SqliteHelper.ITEMS_TABLE_NAME, null, cv);
			if(insertResponse == -1)
			{
				db.close();
				return false;
			}
			
			//ContentValues[] catCvs = new ContentValues[items[i].getCategories().length];
			
			Log.d(TAG, "Categories length = " + items[i].getCategories().length);
			for(int j = 0; j < items[i].getCategories().length; j++)
			{
				ContentValues catcv = new ContentValues(2);
				catcv.put("ItemID", items[i].getItemID());
				catcv.put("Cname", items[i].getCategories()[j].getTitle());
				insertResponse = db.insertOrThrow(SqliteHelper.ITEMCATEGORY_TABLE_NAME, null, catcv);
				if(insertResponse == -1)
				{
					db.close();
					return false;
				}
			}
		}
		
		db.close();
		return true;
	}
	
	
	/**
	 * Returns a list of items meeting the specified parameters from the local database. DOES NOT currently implement reward filtering.
	 * @param categories An array of categories that the items can be in.
	 * @param earlydate The earliest date that the items can be posted. If null, no date is filtered and the latedate variable is ignored.
	 * @param latedate The latest date that the items can be posted.
	 * @param lowreward The lowest reward that the item can have. If -1, then no reward is filtered and the highreward variable is ignored.
	 * @param highreward The highest reward that the item can have.
	 * @param lost Search for lost items.
	 * @param found Search for found items.
	 * @return All the items that fit the parameters.
	 */
	private ArrayList<Item> filterItemsFromDatabase(ArrayList<Item> list, Category[] categories, String earlydate, String latedate, double lowreward, double highreward, boolean lost, boolean found)
	{
		if(list == null)
		{
			list = new ArrayList<Item>();
		}
		
		String newEarlyDate = dateToDatabase(earlydate);
		String newLateDate = dateToDatabase(latedate);
		
		SQLiteDatabase db = sqlHelper.getWritableDatabase();
		//Cursor cursor = db.query(SqliteHelper.ITEMS_TABLE_NAME, null, null, null, null, null, null);
		String[] cols = {"ItemID", "Found", "Name", "PostedBy", "ShortDescription", "LongDescription", "Date", "LocationAddress", "LocationCity", "LocationState", "LocationZipCode", "ImageURL"};
		String selection = "";
		if(earlydate != null && latedate != null)
		{
			selection = "Date >= " + newEarlyDate + " AND Date <= " + newLateDate; //TODO finish the selection based on date
		}
		
		if(lost && !found)
		{
			if(!selection.equals(""))
			{
				selection += " AND";
			}
			selection += " Found = 0";
		}
		else
		if(!lost && found)
		{
			if(!selection.equals(""))
			{
				selection += " AND";
			}
			selection += " Found = 1";
		}
		
		if(!selection.equals(""))
		{
			selection += " AND ";
		}
		if(categories.length > 0)
		{
			selection += "(";
		}
		for(int i = 0; i < categories.length; i++)
		{
			selection += "Cname = \"" + categories[i].getTitle() + "\"";
			if(i != (categories.length -1))
			{
				selection += " OR ";
			}
		}
		if(categories.length > 0)
		{
			selection += ")";
		}
		
		//String[] selectArgs = {newEarlyDate, newLateDate};
		Log.d(TAG, "Selection: " + selection);
		Cursor cursor = db.query(true, SqliteHelper.ITEMS_TABLE_NAME + " NATURAL JOIN " + SqliteHelper.ITEMCATEGORY_TABLE_NAME, cols, selection, null, null, null, null, null);
		cursor.moveToFirst();
		Log.d(TAG, "5th colum is " + cursor.getColumnName(4));
		String[] cat_column = {"Cname"};
		
		while(!(cursor.isAfterLast()))
		{
			int itemID = cursor.getInt(0);
			String[] selectionArgs = {Integer.toString(itemID)};
			Cursor cat_cursor = db.query(SqliteHelper.ITEMCATEGORY_TABLE_NAME, cat_column, "ItemID = ?", selectionArgs, null, null, null);
			cat_cursor.moveToFirst();
			
			int cat_length = cat_cursor.getCount();
			Category[] cat_array = new Category[cat_length];
			for(int i = 0; i < cat_length; i++)
			{
				cat_cursor.moveToPosition(i);
				cat_array[i] = new Category(cat_cursor.getString(0));
			}
			
			list.add(new Item(itemID, ((cursor.getInt(1) == 1) ? true : false), cursor.getString(2), cat_array, new RemoteUserAccount(cursor.getString(3)), new Location(cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getInt(10)), cursor.getString(4), cursor.getString(5), databaseToDate(cursor.getString(6)), cursor.getString(11)));
			cursor.moveToNext();
		}
		
		db.close();
		return list;
	}
	
	
	/**
	 * Converts a date from MM/DD/YYYY format to YYYYMMDD format.
	 * @param date The date in MM/DD/YYYY format to convert.
	 * @return The date in YYYYMMDD format. Returns null if 'date' is not in MM/DD/YYY format.
	 */
	private String dateToDatabase(String date)
	{
		Log.d(TAG, "dateToDatabase() Input: " + date);
		String[] parsedDate = date.split("/");
		Log.d(TAG, "dateToDatabase(), length=" + parsedDate.length + ", src=\"" + date + "\"");
		
		if(parsedDate.length != 3)
		{
			Log.e(TAG, "dateToDatabase(): Date format error: " + date);
			return null;
		}
		
		if(parsedDate[2].length() != 4)
		{
			String str = "";
			for(int i = parsedDate[2].length(); i < 4; i++)
			{
				str += "0";
			}
			parsedDate[2] = str + parsedDate[2];
		}
		if(parsedDate[1].length() != 2)
		{
			String str = "";
			for(int i = parsedDate[1].length(); i < 2; i++)
			{
				str += "0";
			}
			parsedDate[1] = str + parsedDate[1];
		}
		if(parsedDate[0].length() != 2)
		{
			String str = "";
			for(int i = parsedDate[0].length(); i < 2; i++)
			{
				str += "0";
			}
			parsedDate[0] = str + parsedDate[0];
		}
		
		return (parsedDate[2] + parsedDate[0] + parsedDate[1]);
	}
	
	/**
	 * Converts a date from YYYYMMDD format to MM/DD/YYYY format.
	 * @param date The date in YYYYMMDD format.
	 * @return The date in MM/DD/YYYY format. Returns null if 'date' is not in YYYYMMDD format.
	 */
	private String databaseToDate(String date)
	{
		if(date.length() != 8)
		{
			Log.e(TAG, "databaseToDate(): Not right date format: " + date);
			return null;
		}
		
		//TODO More format checking here
		
		String month, day, year;
		month = date.substring(4, 6);
		day = date.substring(6, 8);
		year = date.substring(0, 4);
		
		return (month + "/" + day + "/" + year);
	}
	
	/**
	 * A private class used to access and manipulate the local persistent items database.
	 * @author Andrew Dehn
	 *
	 */
	private class SqliteHelper extends SQLiteOpenHelper
	{
		public static final String DATABASE_NAME = "WheresMyStuffDB1";
		public static final int DATABASE_VERSION = 2;
		public static final String ITEMS_TABLE_NAME = "item";
		private static final String ITEMS_TABLE_CREATE ="CREATE TABLE " + ITEMS_TABLE_NAME + " (" +
		                "ItemID" + " INTEGER PRIMARY KEY, " +
		                "Found" + " BOOL, " +
		                "Name" + " TEXT, " + 
		                "PostedBy" + " TEXT, " + 
		                "ShortDescription" + " TEXT, " +
		                "LongDescription" + " TEXT, " + 
		                "Date" + " TEXT, " + 
		                "LocationAddress" + " TEXT, " + 
		                "LocationCity" + " TEXT, " + 
		                "LocationState" + " TEXT, " + 
		                "LocationZipCode" + " TEXT, " + 
		                "ImageURL" + " TEXT" + 
		                "Day" + " INTEGER, " + 
						"Month" + " INTEGER, " + 
						"Year" + " INTEGER" +
		                 ");";
//		private static final String CATEGORY_TABLE_NAME = "Category";
//		private static final String CATEGORY_TABLE_CREATE = "CREATE TABLE " + CATEGORY_TABLE_NAME + " (" +
//						"cname" + " TEXT" + ");";
		public static final String ITEMCATEGORY_TABLE_NAME = "itemcategory";
		private static final String ITEMCATEGORY_TABLE_CREATE = "CREATE TABLE " + ITEMCATEGORY_TABLE_NAME + " (" +
						"ItemID" + " INTEGER, " + 
						"Cname" + " TEXT, " +
						"PRIMARY KEY(ItemID, Cname)" +
						");";

		
		/**
		 * Constructor to access the persistent items database.
		 * @param context The Context of the app.
		 */
		public SqliteHelper(Context context) 
		{
			super(context, SqliteHelper.DATABASE_NAME, null, SqliteHelper.DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}
		
		/**
		 * Default constructor.
		 * @param context Context of the app.
		 * @param name Name of the database.
		 * @param factory Provided SQLiteDatabase.CursorFactory
		 * @param version Version of the database.
		 * @param errorHandler Provided DatabaseErrorHandler.
		 */
		public SqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler)
		{
			super(context, name, factory, version, errorHandler);
			// TODO Auto-generated constructor stub
		}

		@Override
		/**
		 * Creates the persistent items database.
		 */
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(ITEMS_TABLE_CREATE);
			Log.d("SqliteHelper", ITEMCATEGORY_TABLE_CREATE);
			db.execSQL(ITEMCATEGORY_TABLE_CREATE);
		}

		@Override
		/**
		 * Handles upgrading the persistent items database if necessary.
		 */
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{
			String items_table_upgrade = "";
			String itemscategory_table_upgrade = "";
			
			int upgrade = oldVersion + 1;
			
			switch(upgrade)
			{
			case 2:
				items_table_upgrade += 
						"ItemID" + " INTEGER PRIMARY KEY, " +
						"Found" + " BOOL, " +
				        "Name" + " TEXT, " + 
				        "PostedBy" + " TEXT, " + 
				        "ShortDescription" + " TEXT, " +
				        "LongDescription" + " TEXT, " + 
				        "Date" + " TEXT, " + 
				        "LocationAddress" + " TEXT, " + 
				        "LocationCity" + " TEXT, " + 
				        "LocationState" + " TEXT, " + 
				        "LocationZipCode" + " TEXT, " + 
				        "ImageURL" + " TEXT";
				itemscategory_table_upgrade += 
						"ItemID" + " INTEGER, " + 
						"Cname" + " TEXT, " +
						"PRIMARY KEY(ItemID, Cname)";
				if(newVersion == 2)
					break;
				
		/*	case 3:
				if(upgrade < 3)
				{
					items_table_upgrade += ", ";
				}
				
				items_table_upgrade += 
						"Day" + " INTEGER, " + 
						"Month" + " INTEGER, " + 
						"Year" + " INTEGER";
				if(newVersion == 3)
					break; */
			}
			
			if(!items_table_upgrade.equals(""))
			{
				db.execSQL("ALTER TABLE " + ITEMS_TABLE_NAME + " ADD(" + items_table_upgrade + ");");
			}
			if(!itemscategory_table_upgrade.equals(""))
			{
				db.execSQL("ALTER TABLE " + ITEMCATEGORY_TABLE_NAME + " ADD(" + itemscategory_table_upgrade + ");");
			}
			
		}
		
	}
	
}
