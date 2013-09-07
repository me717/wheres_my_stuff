package edu.gatech.num1matches.model;

/*import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException; */
import java.util.ArrayList;

import android.database.Cursor;

//import edu.gatech.num1matches.PersistentStorage.SqliteHelper;

//import edu.gatech.num1matches.PersistentStorage.SqliteHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * A class that handles all interactions between the Android App and external data from our database.
 * @author Andrew Dehn
 *
 * Currently, this class simulates external network interactions by using a locally stored database.
 */
public class ExternalNetwork 
{
	public final String TAG = "ExternalNetwork";
	
	private static ExternalNetwork networkObject = null;
	private static UserAccount loggedInUser = null;
	
	//private final String accountsFileName = "accounts.txt";
	
	//private Activity activity;
	private String filesDir;
	private SqliteLoginHelper sqlHelper;
	
	/**
	 * Creates a ExternalNetwork object that utilizes context data from the given activity
	 * @param act An active Activity from the application.
	 */
	private ExternalNetwork(Activity act)
	{
		//this.activity = act;
		this.filesDir = act.getFilesDir().getAbsolutePath();
		Log.d(TAG, "Created ExternalNetwork object with path " + filesDir);
		this.sqlHelper = new SqliteLoginHelper(act);
	}
	
	/**
	 * Retrieves or generates the ExternalNetwork object.
	 * @param act The activity that is calling the method.
	 * @return The ExternalNetwork object.
	 */
	public static ExternalNetwork getExternalNetwork(Activity act)
	{
		if(ExternalNetwork.networkObject == null)
		{
			ExternalNetwork.networkObject = new ExternalNetwork(act);
		}
		return networkObject;
	}
	
	
	/**
	 * Returns the user who is currently logged in.
	 * @return Logged in user.
	 */
	public UserAccount getLoggedInUser()
	{
		return loggedInUser;
	}
	
	/**
	 * Checks a user's login credentials.
	 * @param email Email address of user. This input must be a valid email address. No input sanitisation is performed.
	 * @param password The password for the account. This must be a valid password according to our password policy. No input sanitisation is performed.
	 * @return A UserAccount for the user if the account was sucessfully logged in, else returns null.
	 */
	public UserAccount login(String email, String password)
	{
		//return fileLogin(email, password);
		
		/*if(email.equals("user@matches.net") && password.equals("team38"))
		{
			ContactInfo ci = new ContactInfo("Test User", "user@matches.net", "404-404-4040", "Georgia Tech", "Atlanta", "Georgia", "30332");
			return new UserAccount("user@matches.net", "team38", "", ci, false);
		}*/
		
		UserAccount user = databaseLogin(email, password);
		loggedInUser = user;
		if(user == null)
		{
			Log.e(TAG, "USER IS NULL");
			return null;
		}
		return user;
	}
	
	/**
	 * Logs out the currently logged user.
	 */
	public void logout()
	{
		loggedInUser = null;
	}
	
	/**
	 * Registers a user account.
	 * @param name The user's name.
	 * @param email The user's email address.
	 * @param password The user's password.
	 * @param month The month of the user's birth.
	 * @param day The day of the user's birth. 
	 * @param year The year of the user's birth.
	 * @return Returns true if the user was registered, returns false if there was a problem.
	 */
	public boolean register(String name, String email, String password, int month, int day, int year)
	{
		if(checkEmailAlreadyRegistered(email))
		{
			//return fileRegister(name, email, password, month, day, year);
			return databaseRegister(name, email, password, month, day, year);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Checks if an email address is already registered.
	 * @param email The email address of the user.
	 * @return True if the user is not registered, false otherwise.
	 */
	public boolean checkEmailAlreadyRegistered(String email)
	{
		//return fileCheckEmailAlreadyRegistered(email);
		return databaseCheckEmailAlreadyRegistered(email);
	}
	
	
	/**
	 * Contacts the central server and requests that the user be blocked.
	 * @param user The user to block.
	 * @return Whether the user is actually blocked.
	 */
	public boolean blockUser(String email)
	{
		//return fileBlockUser(user);
		//return false;
		return databaseBlockUser(email);
	}
	
	
	/** 
	 * Retrieves the ContactInfo of a specific user.
	 * @param email The email address of the user.
	 * @return A ContactInfo for the user.
	 */
	public ContactInfo getUserContactInfo(String email)
	{
		//return null;
		return databaseGetUserContactInfo(email);
	}
	
	/**
	 * Retrieves all registered users.
	 * @return An array of UserAccount objects. Does not include banned users. Returns null if unable to get users.
	 */
	public UserAccount[] getAllUsers()
	{
		return databaseGetAllUsers();
	}
	
	/**
	 * Retrieves all admin users.
	 * @return An array of UserAccount admin objects. Returns null if unable to get admins.
	 */
	public UserAccount[] getAdmins()
	{
		return databaseGetAdmins();
	}
	
	/**
	 * Gets a specific user.
	 * @param email The username/email of the user.
	 * @return A UserAccount representing the user.
	 */
	public UserAccount getUser(String email)
	{
		return databaseGetUser(email);
	}
	
	/**
	 * Bans a user defined by the specified email address.
	 * @param user The user to ban
	 * @return True if user was successfully banned, otherwise false.
	 */
	public boolean banUser(UserAccount user)
	{
		return databaseBanUser(user);
	}
	
	/**
	 * Unblocks a user's account.
	 * @param user The user to unblock.
	 * @return True if successfully unblocked, false otherwise.
	 */
	public boolean unBlockUser(UserAccount user)
	{
		return databaseUnBlockUser(user);
	}
	
	/**
	 * Promotes the specified user to an admin.
	 * @param user The user to promote.
	 * @return True if successfully promoted, false otherwise.
	 */
	public boolean promoteToAdmin(UserAccount user)
	{
		return databasePromoteToAdmin(user);
	}
	
	
	
	/**
	 * Checks a user's login credentials against the local login database
	 * @param email Email address of user. This input must be a valid email address. No input sanitisation is performed.
	 * @param password The password for the account. This must be a valid password according to our password policy. No input sanitisation is performed.
	 * @return A UserAccount for the user if the account was sucessfully logged in, else returns null.
	 */
	private UserAccount databaseLogin(String username, String password)
	{
		SQLiteDatabase db = sqlHelper.getWritableDatabase();
		
		String[] columns = {"Password", "Blocked", "IsAdmin"};
		String selection = "EmailAddress = ? AND Banned <> ?";
		String[] selectionArgs = {username, "1"};
		Cursor cursor = db.query(SqliteLoginHelper.USERS_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
		cursor.moveToFirst();

		if(cursor.getCount() != 0)
		{
			String pass = cursor.getString(0);
			if(password.equals(pass))
			{
				if(cursor.getInt(2) == 1)
				{
					return new AdminAccount(username, password, "", databaseGetUserContactInfo(username), (cursor.getInt(1)==1 ? true : false));
				}
				else
				{
					return new UserAccount(username, password, "", databaseGetUserContactInfo(username), (cursor.getInt(1)==1 ? true : false));
				}
			}
		}
		db.close();
		return null;
	}
	
	/**
	 * Registers a user account in local user database.
	 * @param name The user's name.
	 * @param email The user's email address.
	 * @param password The user's password.
	 * @param month The month of the user's birth.
	 * @param day The day of the user's birth. 
	 * @param year The year of the user's birth.
	 * @return
	 */
	private boolean databaseRegister(String name, String email, String password, int month, int day, int year)
	{
		SQLiteDatabase db = sqlHelper.getWritableDatabase();
		
		Log.d(TAG, "Attempting register " + email);
		
		ContentValues cv = new ContentValues(9);
		cv.put("EmailAddress", email);
		cv.put("Password", password);
		cv.put("Blocked", false);
		cv.put("Name", name);
		cv.put("BirthMonth", month);
		cv.put("BirthDay", day);
		cv.put("BirthYear", year);
		cv.put("IsAdmin", false);
		cv.put("Banned", false);
		long value = db.insert(SqliteLoginHelper.USERS_TABLE_NAME, null, cv);
		
		Log.d(TAG, "Insert return " + value);
		
		if(value == -1)
		{
			return false;
		}
		db.close();
		return true;
	}
	
	/**
	 * Checks if an email address is already registered with the local database.
	 * @param email The email address of the user.
	 * @return True if the user is not registered, false otherwise.
	 */
	private boolean databaseCheckEmailAlreadyRegistered(String email)
	{
		SQLiteDatabase db = sqlHelper.getWritableDatabase();
		
		String[] columns = {"EmailAddress"};
		String selection = "EmailAddress = ?";
		String[] selectionArgs = {email};
		Cursor cursor = db.query(SqliteLoginHelper.USERS_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
		cursor.moveToFirst();
		
		if(cursor.getCount() == 1)
		{
			return false;
		}
		db.close();
		return true;
	}
	
	/**
	 * Blocks the specified user in the local database.
	 * @param user The user to block.
	 * @return Whether the user is actually blocked.
	 */
	private boolean databaseBlockUser(String email)
	{
		SQLiteDatabase db = sqlHelper.getWritableDatabase();
		
		ContentValues cv = new ContentValues(1);
		cv.put("Blocked", true);
		String whereClause = "EmailAddress = ?";
		String[] whereArgs = {email};
		db.update(SqliteLoginHelper.USERS_TABLE_NAME, cv, whereClause, whereArgs);
		
		db.close();
		return true;
	}
	
	/** 
	 * Retrieves the ContactInfo of a specific user from the local database.
	 * @param email The email address of the user.
	 * @return A ContactInfo for the user, or null if the user does not exist in the local database.
	 */
	private ContactInfo databaseGetUserContactInfo(String email)
	{
		SQLiteDatabase db = sqlHelper.getWritableDatabase();
		
		String[] columns = {"Name", "PhoneNumber", "StreetAddress", "City", "State", "ZipCode"};
		String selection = "EmailAddress = ?";
		String[] selectionArgs = {email};
		Cursor cursor = db.query(SqliteLoginHelper.CONTACTINFO_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
		cursor.moveToFirst();
		
		ContactInfo info = null;
		
		if(cursor.getCount() != 0)
		info = new ContactInfo(cursor.getString(0), email, cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
		
		db.close();
		return info;
	}
	
	
	/**
	 * Retrieves all registered users from the local user database.
	 * @return An array of UserAccount objects. Does not include banned users. Returns null if unable to get users.
	 */
	private UserAccount[] databaseGetAllUsers()
	{
		Log.d(TAG, "Getting all users from local user database.");
		
		ArrayList<UserAccount> users = new ArrayList<UserAccount>();
		SQLiteDatabase db = sqlHelper.getReadableDatabase();
		
		String[] columns = {"EmailAddress", "IsAdmin", "Blocked"};
		String selection = "Banned <> ?";
		String[] selectionArgs = {"1"};
		Cursor cursor = db.query(SqliteLoginHelper.USERS_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast())
		{
			if(cursor.getInt(1) == 1)
			{
				users.add(new RemoteAdminAccount(cursor.getString(0), (cursor.getInt(2) == 1 ? true : false)));
			}
			else
			{
				users.add(new RemoteUserAccount(cursor.getString(0), (cursor.getInt(2) == 1 ? true : false)));
			}
			cursor.moveToNext();
		}
		
		db.close();
		return users.toArray(new UserAccount[0]);
	}
	
	/**
	 * Retrieves all admin users from the local user database.
	 * @return An array of UserAccount admin objects. Returns null if unable to get admins.
	 */
	private UserAccount[] databaseGetAdmins()
	{
		Log.d(TAG, "Getting all admins from the local user database.");
		
		ArrayList<UserAccount> users = new ArrayList<UserAccount>();
		SQLiteDatabase db = sqlHelper.getReadableDatabase();
		
		String[] columns = {"EmailAddress", "Blocked"};
		String selection = "Banned <> ? AND IsAdmin = ?";
		String[] selectionArgs = {"1", "1"};
		Cursor cursor = db.query(SqliteLoginHelper.USERS_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast())
		{
			users.add(new RemoteAdminAccount(cursor.getString(0), (cursor.getInt(1) == 1 ? true : false)));
			cursor.moveToNext();
		}
		
		db.close();
		return users.toArray(new UserAccount[0]);
	}
	
	/**
	 * Gets a specific user.
	 * @param email The username/email of the user.
	 * @return A UserAccount representing the user. Returns null if the user does not exist.
	 */
	private UserAccount databaseGetUser(String email)
	{
		Log.d(TAG, "Getting single user " + email);
		
		SQLiteDatabase db = sqlHelper.getReadableDatabase();
		
		String[] columns = {"EmailAddress", "IsAdmin", "Blocked"};
		String selection = "EmailAddress = ? AND Banned <> ?";
		String[] selectionArgs = {email, "1"};
		Cursor cursor = db.query(SqliteLoginHelper.USERS_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
		cursor.moveToFirst();
		
		UserAccount user = null;
		if(cursor.getCount() != 0 && !cursor.isAfterLast())
		{
			if(cursor.getInt(1) == 1)
			{
				user = (new RemoteAdminAccount(cursor.getString(0), (cursor.getInt(2) == 1 ? true : false)));
			}
			else
			{
				user = (new RemoteUserAccount(cursor.getString(0), (cursor.getInt(2) == 1 ? true : false)));
			}
			cursor.moveToNext();
		}
		
		db.close();
		return user;
	}
	
	/**
	 * Bans a user defined by the specified email address in the local user database.
	 * @param user The user to ban
	 * @return True if user was successfully banned, otherwise false.
	 */
	private boolean databaseBanUser(UserAccount user)
	{
		Log.d(TAG, "Banning user " + user.getUsername());
		
		SQLiteDatabase db = sqlHelper.getReadableDatabase();
		
		ContentValues cv = new ContentValues(1);
		cv.put("Banned", true);
		String whereClause = "EmailAddress = ?";
		String[] whereArgs = {user.getUsername()};
		int returnValue = db.update(SqliteLoginHelper.USERS_TABLE_NAME, cv, whereClause, whereArgs);
		
		db.close();
		if(returnValue == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * Unblocks a user's account.
	 * @param user The user to unblock.
	 * @return True if successfully unblocked, false otherwise.
	 */
	private boolean databaseUnBlockUser(UserAccount user)
	{
		Log.d(TAG, "Unblocking user " + user.getUsername());
		
		SQLiteDatabase db = sqlHelper.getReadableDatabase();
		
		ContentValues cv = new ContentValues(1);
		cv.put("Blocked", false);
		String whereClause = "EmailAddress = ?";
		String[] whereArgs = {user.getUsername()};
		int returnValue = db.update(SqliteLoginHelper.USERS_TABLE_NAME, cv, whereClause, whereArgs);
		
		db.close();
		if(returnValue == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * Promotes the specified user to an admin in the local user database.
	 * @param user The user to promote.
	 * @return True if successfully promoted, false otherwise.
	 */
	private boolean databasePromoteToAdmin(UserAccount user)
	{
		Log.d(TAG, "Promoting user " + user.getUsername() + " to admin.");
		
		SQLiteDatabase db = sqlHelper.getReadableDatabase();
		
		ContentValues cv = new ContentValues(1);
		cv.put("IsAdmin", true);
		String whereClause = "EmailAddress = ?";
		String[] whereArgs = {user.getUsername()};
		int returnValue = db.update(SqliteLoginHelper.USERS_TABLE_NAME, cv, whereClause, whereArgs);
		
		db.close();
		if(returnValue == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	
	/**
	 * Private class used to access and manipulate the local user database.
	 * @author Andrew Dehn
	 *
	 */
	private class SqliteLoginHelper extends SQLiteOpenHelper
	{
		public static final String DATABASE_NAME = "WheresMyStuffLoginDB";
		public static final int DATABASE_VERSION = 3; //Changed from v2 to v3 on 3/9/2013
		public static final String USERS_TABLE_NAME = "users";
		private static final String USERS_TABLE_CREATE ="CREATE TABLE " + USERS_TABLE_NAME + " (" +
						"EmailAddress" + " TEXT PRIMARY KEY, " + 
						"Password" + " TEXT, " + 
						"Blocked" + " BOOL, " + 
						"Name" + " TEXT, " + 
						"BirthMonth" + " INTEGER, " + 
						"BirthDay" + " INTEGER, " + 
						"BirthYear" + " INTEGER, " + 
						"IsAdmin" + " BOOL, " + 
						"Banned" + " BOOL" + 
						");";
		public static final String CONTACTINFO_TABLE_NAME = "contactinfo";
		private static final String CONTACTINFO_TABLE_CREATE = "CREATE TABLE " + CONTACTINFO_TABLE_NAME + " (" +
						"EmailAddress" + " TEXT PRIMARY KEY, " + 
						"Name" + " TEXT, " + 
						"PhoneNumber" + " TEXT, " + 
						"StreetAddress" + " TEXT, " + 
						"City" + " TEXT, " + 
						"State" + " TEXT, " + 
						"ZipCode" + " TEXT" + 
						");";
		
		/**
		 * Constructor to access the user database.
		 * @param context The Context of the app.
		 */
		public SqliteLoginHelper(Context context) 
		{
			super(context, SqliteLoginHelper.DATABASE_NAME, null, SqliteLoginHelper.DATABASE_VERSION);
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
		public SqliteLoginHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler)
		{
			super(context, name, factory, version, errorHandler);
			// TODO Auto-generated constructor stub
		}

		@Override
		/**
		 * Creates the user database and inserts a predefined user and admin.
		 */
		public void onCreate(SQLiteDatabase db) 
		{
			// TODO Auto-generated method stub
			db.execSQL(USERS_TABLE_CREATE);
			db.execSQL(CONTACTINFO_TABLE_CREATE);
			
			Log.d(TAG, "Adding default user and admin.");
			
			ContentValues cv1 = new ContentValues(9);
			cv1.put("EmailAddress", "user@matches.net");
			cv1.put("Password", "team38");
			cv1.put("Blocked", false);
			cv1.put("Name", "Test User");
			cv1.put("BirthMonth", 1);
			cv1.put("BirthDay", 12);
			cv1.put("BirthYear", 2000);
			cv1.put("IsAdmin", false);
			cv1.put("Banned", false);
			long value1 = db.insert(SqliteLoginHelper.USERS_TABLE_NAME, null, cv1);
			
			ContentValues cv2 = new ContentValues(9);
			cv2.put("EmailAddress", "admin@matches.net");
			cv2.put("Password", "team38");
			cv2.put("Blocked", false);
			cv2.put("Name", "Test Admin");
			cv2.put("BirthMonth", 1);
			cv2.put("BirthDay", 12);
			cv2.put("BirthYear", 2000);
			cv2.put("IsAdmin", true);
			cv2.put("Banned", false);
			long value2 = db.insert(SqliteLoginHelper.USERS_TABLE_NAME, null, cv2);
			
			Log.d(TAG, "Preconfigure 1 return " + value1);
			Log.d(TAG, "Preconfigure 2 return " + value2);
		}

		@Override
		/**
		 * Handles upgrading the user database if necessary.
		 */
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{
			String user_table_upgrade = "";
			String contactinfo_table_upgrade = "";
			
			int upgrade = oldVersion + 1;
			
			switch(upgrade)
			{
			case 2:
				user_table_upgrade += 
						"EmailAddress" + " TEXT PRIMARY KEY, " + 
						"Password" + " TEXT, " + 
						"Blocked" + " BOOL, " + 
						"Name" + " TEXT, " + 
						"BirthMonth" + " INTEGER, " + 
						"BirthDay" + " INTEGER, " + 
						"BirthYear" + " INTEGER";
				contactinfo_table_upgrade += 
						"EmailAddress" + " TEXT PRIMARY KEY, " + 
						"Name" + " TEXT, " + 
						"PhoneNumber" + " TEXT, " + 
						"StreetAddress" + " TEXT, " + 
						"City" + " TEXT, " + 
						"State" + " TEXT, " + 
						"ZipCode" + " TEXT";
				if(newVersion == 2)
					break;
				
			case 3:
				if(upgrade < 3)
				{
					user_table_upgrade += ", ";
				}
				
				user_table_upgrade += 
						"IsAdmin" + " BOOL, " + 
						"Banned" + " BOOL";
				if(newVersion == 3)
					break;
			}
			
			if(!user_table_upgrade.equals(""))
			{
				db.execSQL("ALTER TABLE " + USERS_TABLE_NAME + " ADD(" + user_table_upgrade + ");");
			}
			if(!contactinfo_table_upgrade.equals(""))
			{
				db.execSQL("ALTER TABLE " + CONTACTINFO_TABLE_NAME + " ADD(" + contactinfo_table_upgrade + ");");
			}
			
			
		}
		
	}
}
