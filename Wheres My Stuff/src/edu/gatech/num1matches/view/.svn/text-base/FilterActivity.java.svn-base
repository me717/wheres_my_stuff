package edu.gatech.num1matches.view;

import edu.gatech.num1matches.*;
import edu.gatech.num1matches.model.*;
import edu.gatech.num1matches.presenter.*;

import java.util.regex.Pattern;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class FilterActivity extends Activity implements OnClickListener {

	private EditText searchText, beginDay, beginMonth, beginYear, endDay,
			endMonth, endYear, maxRew, minRew,enterCity,enterState;
	private CheckBox jewelryBox, antiquesBox, memBox, otherBox, lostBox,
			foundBox;
	private int bMonth, bDay, bYear, eDay, eMonth, eYear;
	private double max, min;
	private boolean lost, found, jewelry, antiques, mem, other;
	private String search,city,state;
	private Button filterButton, cancelButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);

		Log.d("filter", "Created");

		maxRew = (EditText) findViewById(R.id.maxReward);
		minRew = (EditText) findViewById(R.id.minReward);
		
		enterCity = (EditText) findViewById(R.id.enterFilterCity);
		enterState = (EditText) findViewById(R.id.enterFilterState);

		filterButton = (Button) findViewById(R.id.filterButton);
		filterButton.setOnClickListener(this);
		cancelButton = (Button) findViewById(R.id.cancelFilterButton);
		cancelButton.setOnClickListener(this);

		searchText = (EditText) findViewById(R.id.enterSearch);

		jewelryBox = (CheckBox) findViewById(R.id.filterCheckBoxJewelry);
		antiquesBox = (CheckBox) findViewById(R.id.filterCheckBoxAntiques);
		memBox = (CheckBox) findViewById(R.id.filterCheckBoxMem);
		otherBox = (CheckBox) findViewById(R.id.filterCheckBoxOther);

		beginMonth = (EditText) findViewById(R.id.enterBeginMonth);
		beginDay = (EditText) findViewById(R.id.enterBeginDay);
		beginYear = (EditText) findViewById(R.id.enterBeginYear);

		endMonth = (EditText) findViewById(R.id.enterLastMonth);
		endDay = (EditText) findViewById(R.id.enterLastDay);
		endYear = (EditText) findViewById(R.id.enterLastYear);

		foundBox = (CheckBox) findViewById(R.id.filterCheckBoxFound);
		lostBox = (CheckBox) findViewById(R.id.filterCheckBoxLost);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		Intent myIntent;
		switch (item.getItemId()) {
		case R.id.logoutButton:
			myIntent = new Intent(this, LoginActivity.class);
			startActivity(myIntent);
			return true;
		case R.id.homeButton:
			myIntent = new Intent(this, HomeActivity.class);
			startActivity(myIntent);
			return true;
		default:
			return false;
		}
	}

	@Override
	public void onClick(View v) {
		if (filterButton == v) {
			search = searchText.getText().toString();
			
			state = enterState.getText().toString();
			city = enterCity.getText().toString();

			jewelry = jewelryBox.isChecked();
			antiques = antiquesBox.isChecked();
			mem = memBox.isChecked();
			other = otherBox.isChecked();

			lost = lostBox.isChecked();
			found = foundBox.isChecked();

			try {
				if (beginMonth.getText().length() == 0)
					bMonth = 0;
				else
					bMonth = Integer.parseInt(beginMonth.getText().toString());

				Log.d("Filter", "try 1");

				if (beginDay.getText().length() == 0)
					bDay = 0;
				else
					bDay = Integer.parseInt(beginDay.getText().toString());
				Log.d("Filter", "try 2");

				if (beginYear.getText().length() == 0)
					bYear = 0;
				else
					bYear = Integer.parseInt(beginYear.getText().toString());
				Log.d("Filter", "try 3");

				if (endMonth.getText().length() == 0)
					eMonth = 0;
				else
					eMonth = Integer.parseInt(endMonth.getText().toString());

				if (endDay.getText().length() == 0)
					eDay = 0;
				else
					eDay = Integer.parseInt(endDay.getText().toString());

				if (endYear.getText().length() == 0)
					eYear = 0;
				else
					eYear = Integer.parseInt(endYear.getText().toString());

				if (maxRew.getText().length() == 0)
					max = 0;
				else
					max = Double.parseDouble(maxRew.getText().toString());

				if (minRew.getText().length() == 0)
					min = 0;
				else
					min = Double.parseDouble(minRew.getText().toString());

			} catch (Exception e) {
				Log.d("Filter", "Catch");
				Log.d("Filter", e.getMessage());
			}

			if (!jewelry && !antiques && !mem && !other) {
				Toast.makeText(this, "Please enter at least one category.",
						Toast.LENGTH_LONG).show();
			} else if ((bDay != 0 || bMonth != 0 || bYear != 0)
					&& (bDay == 0 || bMonth == 0 || bYear == 0)) // part of the
																	// beginning
																	// date was
																	// filled,
																	// but not
																	// all
			{
				Toast.makeText(this, "Please a full earliest date.",
						Toast.LENGTH_LONG).show();
			} else if (bMonth < 0 || bMonth > 12) {
				Toast.makeText(this,
						"Please enter a valid month for the earliest date.",
						Toast.LENGTH_LONG).show();
			} else if (bDay < 0 || bDay > 31) {
				Toast.makeText(this,
						"Please enter a valid day for the earliest date.",
						Toast.LENGTH_LONG).show();
			} else if ((bYear != 0 && bYear < 1900) || bYear > 2050) {
				Toast.makeText(this,
						"Please enter a valid year for the earliest date.",
						Toast.LENGTH_LONG).show();
			}

			else if ((eDay != 0 || eMonth != 0 || eYear != 0)
					&& (eDay == 0 || eMonth == 0 || eYear == 0)) {
				Toast.makeText(this, "Please a full latest date.",
						Toast.LENGTH_LONG).show();
			} else if (eMonth < 0 || eMonth > 12) {
				Toast.makeText(this,
						"Please enter a valid month for the latest date.",
						Toast.LENGTH_LONG).show();
			} else if (eDay < 0 || eDay > 31) {
				Toast.makeText(this,
						"Please enter a valid day for the latest date.",
						Toast.LENGTH_LONG).show();
			} else if ((eYear != 0 && eYear < 1900) || eYear > 2050) {
				Toast.makeText(this,
						"Please enter a valid year for the latest date.",
						Toast.LENGTH_LONG).show();
			}

			else if (min < 0) {
				Toast.makeText(this, "Please enter a valid minimum reward",
						Toast.LENGTH_LONG).show();
			} else if (max < min) {
				Toast.makeText(
						this,
						"The minimum reward can't be more than the maximum reward.",
						Toast.LENGTH_LONG).show();
			}

			else if (!lost && !found) {
				Toast.makeText(this,
						"Please select \"lost\", \"found\" or both.",
						Toast.LENGTH_LONG).show();
			}

			else {
				// make filter and fitler
				boolean sEmpty = (bMonth == 0 || bDay == 0 || bYear == 0);
				boolean eEmpty = (eMonth == 0 || eDay == 0 || eYear == 0);

				Log.d("Filter", "start Date not filled in " + sEmpty);
				Log.d("Filter", "start Date:" + bMonth + "/" + bDay + "/"
						+ bYear);
				Log.d("Filter", "end Date:" + eMonth + "/" + eDay + "/" + eYear);
				Log.d("Filter", "end Date not filled in " + eEmpty);

				String sDate, eDate;

				if (sEmpty && eEmpty) {
					sDate = "12/12/1800";
					eDate = "12/12/2100";
				} else if (sEmpty) {
					sDate = "12/12/1800";
					eDate = eMonth + "/" + eDay + "/" + eYear;
				} else if (eEmpty) {
					sDate = bMonth + "/" + bDay + "/" + bYear;
					eDate = "12/12/2100";
				} else {
					Log.d("Filter", "date filled in");
					sDate = bMonth + "/" + bDay + "/" + bYear;
					eDate = eMonth + "/" + eDay + "/" + eYear;
				}

				Log.d("Filter", sDate + "-" + eDate);
				Log.d("Filter", "end Date not filled in " + eEmpty);

				Filter f = new Filter(search, antiques, mem, jewelry, other,
						sDate, eDate, min, max, lost, found, city,state);

				Intent myIntent = new Intent(v.getContext(),
						ItemViewActivity.class);
				myIntent.putExtra("filter", f);
				v.getContext().startActivity(myIntent);
			}
		} else if (cancelButton == v) {
			Intent myIntent = new Intent(v.getContext(), HomeActivity.class);
			v.getContext().startActivity(myIntent);
		}
	}

}
