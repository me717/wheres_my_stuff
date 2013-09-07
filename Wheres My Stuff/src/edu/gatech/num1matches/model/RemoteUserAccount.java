package edu.gatech.num1matches.model;

import android.app.Activity;

/**
 * A sub-class of UserAccount that represents Accounts not logged in on the phone.
 * @author Andrew Dehn
 *
 */
public class RemoteUserAccount extends UserAccount 
{

	/**
	 * Creates a RemoteUserAccount using the same constructor as in UserAccount.
	 * @param username The email address of the user.
	 * @param password The password of the user. There is no reason that the phone should know this data.
	 * @param loginIdentifier The loginIdentifier of the user. There is no reason that the phone should know this data.
	 * @param contactInfo The ContactInfo object representing the user's contact information.
	 * @param blocked The blocked status of the user.
	 */
	public RemoteUserAccount(String username, String password, String loginIdentifier, ContactInfo contactInfo, boolean blocked) 
	{
		super(username, password, loginIdentifier, contactInfo, blocked);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Creates a RemoteUserAccount using just the username.
	 * @param username The username of the user.
	 */
	public RemoteUserAccount(String username)
	{
		super(username, "", "", null, false);
	}
	
	/**
	 * Creates a RemoteUserAccount using the username and blocked status.
	 * @param username The username of the user.
	 * @param blocked The blocked status of the user.
	 */
	public RemoteUserAccount(String username, boolean blocked)
	{
		super(username, "", "", null, blocked);
	}
	
	/**
	 * Overrides the default UserAccount contactInfo() method and separately retrieves the contact information for the user, if it is not already present.
	 * @param act An app Activity that is necessary to completing the operation.
	 * @return The ContactInfo representing the user.
	 */
	public ContactInfo contactInfo(Activity act)
	{
		if(contactInfo == null)
		{
			this.contactInfo = ExternalNetwork.getExternalNetwork(act).getUserContactInfo(this.username);
		}
		return contactInfo;
	}

}
