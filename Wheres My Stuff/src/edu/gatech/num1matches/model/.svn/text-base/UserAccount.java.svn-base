package edu.gatech.num1matches.model;


/**
 * A class that represents the data of a user's account and can perform certain user tasks.
 * @author Andrew Dehn
 *
 */
public class UserAccount 
{
	public String username;
	public String password;
	private String loginIdentifier;
	public ContactInfo contactInfo;
	public boolean blocked;
	
	/**
	 * Constructor for UserAccount, sets up necessary initial data
	 * @param username The String name of the user
	 * @param password The password for this user
	 * @param loginIdentifier The unique descriptor for this User
	 * @param contactInfo Contact information for this user
	 * @param blocked Boolean representing whether this user is blocked
	 */
	public UserAccount(String username, String password, String loginIdentifier, ContactInfo contactInfo, boolean blocked)
	{
		this.username = username;
		this.password = password;
		this.loginIdentifier = loginIdentifier;
		this.contactInfo = contactInfo;
		this.blocked = blocked;
	}
	
	/**
	 * Return the username of this UserAccount
	 * @return username The username of this UserAccount
	 */
	public String getUsername()
	{
		return username;
	}
	
	/**
	 * Return the contact information for this UserAccount
	 * @return contactInfo The contactInfo for this UserAccount
	 */
	public ContactInfo contactInfo()
	{
		return contactInfo;
	}
	
	/**
	 * Return whether the UserAccount is currently blocked (boolean)
	 * @return blocked Boolean representing whether this user is blocked (true) or not (false)
	 */
	public boolean isBlocked()
	{
		return blocked;
	}
	
	/**
	 * Return whether this UserAccount is an admin (always false for base UserAccounts)
	 * @return whether this Account is blocked (true or false)
	 */
	public boolean isAdmin()
	{
		return false;
	}
	
	/**
	 * Returns the unique loginidentifier of the user.
	 * @return the users unique loginidentifier
	 */
	public String getLoginIdentifier(){
		return loginIdentifier;
	}
}
