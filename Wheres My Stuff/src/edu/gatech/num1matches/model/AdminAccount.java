package edu.gatech.num1matches.model;

/**
 * A class that represents the data of an admin account, extends the User Account
 * @author Andrew Dehn
 *
 */

public class AdminAccount extends UserAccount
{
	/**
	 * Creates an AdminAccount based on the same constructor as in UserAccount.
	 * @param username The email address of the user.
	 * @param password The password of the user. There is no reason that the phone should know this data.
	 * @param loginIdentifier The loginIdentifier of the user. There is no reason that the phone should know this data.
	 * @param contactInfo The ContactInfo object representing the user's contact information.
	 * @param blocked The blocked status of the user.
	 */
	public AdminAccount(String username, String password, String loginIdentifier, ContactInfo contactInfo, boolean blocked)
	{
		super(username, password, loginIdentifier, contactInfo, blocked);
	}
	
	@Override
	/**
	 * Overrides the default isAdmin() functionality in UserAccount and always returns true.
	 * @return True
	 */
	public boolean isAdmin()
	{
		return true;
	}
}
