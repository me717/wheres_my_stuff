package edu.gatech.num1matches.view;

import edu.gatech.num1matches.*;
import edu.gatech.num1matches.model.*;
import edu.gatech.num1matches.presenter.*;

import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private static final String TAG = "RegisterActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
	}

	/**
	 * Occurs when the button is clicked. If possible, registers the user.
	 * 
	 * @param view
	 *            the view that triggered the event; in this case, this can only
	 *            be the register button
	 */
	public void registerUser(View view) {
		// Get GUI elements
		EditText nameText = (EditText) findViewById(R.id.enterName);
		EditText emailText = (EditText) findViewById(R.id.enterEmail);
		EditText passText = (EditText) findViewById(R.id.enterPassword);
		EditText confirmText = (EditText) findViewById(R.id.enterConfirmPassword);
		EditText monthText = (EditText) findViewById(R.id.enterMonth);
		EditText dayText = (EditText) findViewById(R.id.enterDay);
		EditText yearText = (EditText) findViewById(R.id.enterYear);
		CheckBox agreeBox = (CheckBox) findViewById(R.id.IAgreeRegistrationCheckBox);

		// Get text from gui elements
		String name = nameText.getText().toString();
		String email = emailText.getText().toString();
		String password = passText.getText().toString();
		String confirmPass = confirmText.getText().toString();
		int month, day, year;
		try {
			month = Integer.parseInt(monthText.getText().toString());
			day = Integer.parseInt(dayText.getText().toString());
			year = Integer.parseInt(yearText.getText().toString());
			// Passwords are not the same
			if (name.equals("") || email.equals("") || password.equals("")
					|| confirmPass.equals("")) {
				Toast.makeText(this, "Please fill in the entire form.",
						Toast.LENGTH_LONG).show();
			} else if (!Pattern
					.matches(
							"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
									+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
							email)) {
				Toast.makeText(this, "Please enter a valid email address.",
						Toast.LENGTH_SHORT);
			} else if (day > 31 || day <= 0 || month <= 0 || month > 12
					|| year < 0) {
				Toast.makeText(this, "Date is improperly formatted",
						Toast.LENGTH_LONG).show();
			} else if (!password.equals(confirmPass)) {
				Toast.makeText(this, "Passwords do not match",
						Toast.LENGTH_LONG).show();
				passText.setText("");
				confirmText.setText("");
				// User has not agreed to the terms
			} else if (!agreeBox.isChecked()) {
				Toast.makeText(this, "Please agree to terms and conditions.",
						Toast.LENGTH_LONG).show();
			} else {
				if (ExternalNetwork.getExternalNetwork(this)
						.checkEmailAlreadyRegistered(email)) {
					if (!ExternalNetwork.getExternalNetwork(this).register(
							name, email, password, month, day, year)) {
						Toast.makeText(this, "Error with registration",
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(this, "Account created.",
								Toast.LENGTH_LONG).show();
						Intent myIntent = new Intent(view.getContext(),
								LoginActivity.class);
						view.getContext().startActivity(myIntent);
					}
				} else {
					Toast.makeText(
							this,
							"Email address already registered. Please use a different email address.",
							Toast.LENGTH_LONG).show();
				}
			}
		} catch (NumberFormatException e) {
			Log.e(TAG, "Invalid input for birthday", e);
			Toast.makeText(this, "Invalid input", Toast.LENGTH_LONG).show();
		}
	}

}
