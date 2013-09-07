package edu.gatech.num1matches.model;

public class Category 
{
	public String name;
	public Item[] items;
	
	/**
	 * Creates the category
	 * @param name the name of the category
	 */
	public Category(String name)
	{
		this.name = name;
	}
	
	/**
	 * Creates the category
	 * @param name the name of the category
	 * @param items the items included in the category
	 */
	public Category(String name, Item[] items)
	{
		this.name = name;
		this.items = items;
	}
	
	/**
	 * 
	 * @return the name of the category
	 */
	public String getTitle()
	{
		return name;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if((o instanceof Category) && ((Category) o).name.equals(name))
		{
			return true;
		}
		return false;
	}
}
