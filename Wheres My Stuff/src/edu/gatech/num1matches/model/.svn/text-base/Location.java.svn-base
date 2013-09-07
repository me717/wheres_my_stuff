package edu.gatech.num1matches.model;

/**
 * The location an object was found
 * 
 * @author Albert
 * 
 */
public class Location {
	private String address, city, state;
	private int zipCode;

	/**
	 * The only constructor
	 * 
	 * @param address
	 *            the street adress
	 * @param city
	 *            the city
	 * @param state
	 *            the state
	 * @param zipCode
	 *            the zip code
	 */
	public Location(String address, String city, String state, int zipCode) {
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}

	@Override
	public boolean equals(Object o) {
		if ((o instanceof Location) && (((Location) o).address.equals(address))
				&& (((Location) o).city.equals(city))
				&& (((Location) o).state.equals(state))
				&& (((Location) o).zipCode == this.zipCode)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @return the street address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * 
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * 
	 * @return the zip code
	 */
	public int getZipCode() {
		return zipCode;
	}

	@Override
	public String toString() {
		return address + ", " + city + ", " + state + ", "
				+ Integer.toString(zipCode);
	}
	
/*	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Location)
		{
			Location loc = (Location) o;
			if(this.address.equals(loc.getAddress()) 
					&& this.city.equals(loc.getCity()) 
					&& this.state.equals(loc.getState()) 
					&& this.zipCode == loc.getZipCode())
			{
				return true;
			}
		}
		return false;
	} */
}
