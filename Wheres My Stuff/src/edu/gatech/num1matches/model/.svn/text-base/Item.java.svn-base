package edu.gatech.num1matches.model;

/**
 * Represents an item (lost or found)
 * 
 * @author Albert
 * 
 */
public class Item {
	/**
	 * A unique int for each item
	 */
	public int itemID;
	/**
	 * False if the item is lost, true if it is found
	 */
	public boolean found;
	/**
	 * The name of the item
	 */
	public String name;
	/**
	 * The categories the item belongs to
	 */
	public Category[] categories;
	/**
	 * The user who posted the item
	 */
	public UserAccount postedBy;
	/**
	 * A short description of the item
	 */
	public String shortDescription;
	/**
	 * A long description of the item
	 */
	public String longDescription;
	/**
	 * The date the item was posted
	 */
	public String date;
	/**
	 * The location the item was lost/found
	 */
	public Location location;
	/**
	 * a link to a picture of the image
	 */
	public String imageURL;

	/**
	 * Creates an item with te given info
	 * 
	 * @param itemID
	 *            the items id, unique to each item
	 * @param found
	 *            whether the item is lost or found
	 * @param name
	 *            the name of the item
	 * @param categories
	 *            a list of categories that this item is included in
	 * @param postedBy
	 *            the user who posted the item
	 * @param location
	 *            the location the item was found (or lost)
	 * @param shortDescription
	 *            a one-line description of the the item
	 * @param longDescription
	 *            a longer description of the item
	 * @param date
	 *            when the item was found
	 * @param imageURL
	 *            the location of the items image
	 */
	public Item(int itemID, boolean found, String name, Category[] categories,
			UserAccount postedBy, Location location, String shortDescription,
			String longDescription, String date, String imageURL) {
		this.itemID = itemID;
		this.found = found;
		this.name = name;
		this.categories = categories;
		this.postedBy = postedBy;
		this.location = location;
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
		this.date = date;
		this.imageURL = imageURL;
	}

	/**
	 * 
	 * @return the item ID
	 */
	public int getItemID() {
		return itemID;
	}

	/**
	 * 
	 * @return true if the item was found, false if lost
	 */
	public boolean wasFound() {
		return found;
	}

	/**
	 * 
	 * @return the name of the item
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return the categories the item belongs too
	 */
	public Category[] getCategories() {
		return categories;
	}

	/**
	 * 
	 * @return the user who posted the item
	 */
	public UserAccount postedBy() {
		return postedBy;
	}

	/**
	 * 
	 * @return a short description of the item
	 */
	public String getShortDescription() {
		return shortDescription;
	}

	/**
	 * 
	 * @return a long description of the item
	 */
	public String getLongDescription() {
		return longDescription;
	}

	/**
	 * 
	 * @return the date the item was posted
	 */
	public String getDate() {
		return date;
	}

	/**
	 * 
	 * @return the location the item was lost/found
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * 
	 * @return the item's image url
	 */
	public String getImageURL() {
		return imageURL;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Item)
		{
			Item other = (Item) o;
			if(!(this.itemID == other.getItemID()))
			{
				return false;
			}
			if(!(this.found == other.wasFound()))
			{
				return false;
			}
			if(!(this.name.equals(other.getName())))
			{
				return false;
			}
			if(!(this.postedBy.getUsername().equals(other.postedBy().getUsername())))
			{
				return false;
			}
			if(!(this.shortDescription.equals(other.getShortDescription())))
			{
				return false;
			}
			if(!(this.longDescription.equals(other.getLongDescription())))
			{
				return false;
			}
			if(!(this.date.equals(other.getDate())))
			{
				return false;
			}
			if(!(this.location.equals(other.getLocation())))
			{
				return false;
			}
			if(!(this.imageURL.equals(other.getImageURL())))
			{
				return false;
			}
			
			//Come back to categories
			Category[] otherCategories = other.getCategories();
			for(Category cat : this.categories)
			{
				boolean allCatsPresent = true;
				for(int i = 0; i < otherCategories.length; i++)
				{
					if(cat.equals(otherCategories[i]))
					{
						allCatsPresent = true;
						break;
					}
					else
					{
						allCatsPresent = false;
					}
				}
				if(!(allCatsPresent))
				{
					return false;
				}
			}
			
			return true;
		}
		else
		{
			return false;
		}
	}
}
