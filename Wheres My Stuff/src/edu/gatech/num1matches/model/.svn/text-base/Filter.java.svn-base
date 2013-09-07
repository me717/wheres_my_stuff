package edu.gatech.num1matches.model;

import java.io.Serializable;

public class Filter implements Serializable {
	private String search, startDate, endDate, city, state;
	private boolean antiques, mem, jewelry, other, lost, found;
	private double minReward, maxReward;

	public Filter(String s, boolean ant, boolean memor, boolean jewel,
			boolean oth, String sDate, String eDate, double minR, double maxR,
			boolean l, boolean f, String c, String st) {
		search = s;
		startDate = sDate;
		endDate = eDate;
		antiques = ant;
		mem = memor;
		jewelry = jewel;
		other = oth;
		lost = l;
		found = f;
		minReward = minR;
		maxReward = maxR;
		city = c;
		state = st;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getSearch() {
		return search;
	}

	public boolean getAntiquesTrue() {
		return antiques;
	}

	public boolean getMemTrue() {
		return mem;
	}

	public boolean getOtherTrue() {
		return other;
	}

	public boolean getJewelryTrue() {
		return jewelry;
	}

	public boolean getLostTrue() {
		return lost;
	}

	public boolean getFoundTrue() {
		return found;
	}

	public double getMinReward() {
		return minReward;
	}

	public double getMaxReward() {
		return maxReward;
	}

	public String getState() {
		return state;
	}

	public String getCity() {
		return city;
	}
}
