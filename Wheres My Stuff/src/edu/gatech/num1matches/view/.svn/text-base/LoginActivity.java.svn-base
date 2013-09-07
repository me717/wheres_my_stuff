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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Number 1 Matches
 * @since  2/21/2013
 *
 */
public class LoginActivity extends Activity implements OnClickListener {

	public final String TAG = "LoginActivity";
	private Button loginButton;
	private Button signUpButton;
	private TextView emailTextView;
	private TextView passwordTextView;
	//private TextView promptTextView;
	private int loginAttempts;
	private String prevEmail;

	
    /**
     * onCreate method constructs login activity
     * @param savedInstanceState - 
     */
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(this);
        emailTextView = (TextView) findViewById(R.id.emailTextField);  
        passwordTextView = (TextView) findViewById(R.id.passwordTextField);
        //promptTextView = (TextView) findViewById(R.id.promptText);
        //promptTextView.setTextColor(Color.RED);
        loginAttempts = 0;
        prevEmail = "";
    }

	/*@Override
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
	}*/
	
	@Override
	public void onBackPressed() {
		Intent myIntent = new Intent(this, LoginActivity.class);
	    this.startActivity(myIntent);
	}

    /**
     * onClick method handles click events
     * @param v - 
     */
	@Override
	public void onClick(View v) {
		
		// Login button is clicked
		if(loginButton == v){
			Log.d(TAG, "Login Button Pressed"); // debug
			
			// Hide the keyboard on button press
			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(emailTextView.getWindowToken(), 0);
			imm.hideSoftInputFromWindow(passwordTextView.getWindowToken(),0);
			
			// Read Email/Password Fields
			String email = emailTextView.getText().toString();
			String password = passwordTextView.getText().toString();
			
			// Check for empty email/password fields
			if (email.isEmpty() || !Pattern.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",email)){
				Toast.makeText(this, "Please enter valid email address", Toast.LENGTH_LONG).show();

				
			}
			else if (password.isEmpty()){
				Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
			}
			
			// Check if email and password are valid
			else if (ExternalNetwork.getExternalNetwork(this).login(email, password) != null){
				if (ExternalNetwork.getExternalNetwork(this).login(email,password).isBlocked()){
					Toast.makeText(this, "Account is Blocked",Toast.LENGTH_LONG).show();
				}
				else{
				Intent myIntent = new Intent(v.getContext(), HomeActivity.class);
			    v.getContext().startActivity(myIntent);
				}
			}
			else{
				if (loginAttempts<2){
					if (prevEmail.equals(email)){
						loginAttempts++;
					}
					else{
						loginAttempts=0;
						prevEmail = email;
					}
					Toast.makeText(this, "Incorrect login attempt",Toast.LENGTH_LONG).show();
				}
				else{
					if (prevEmail.equals(email)){
						if (ExternalNetwork.getExternalNetwork(this).blockUser(email)){
							Toast.makeText(this, "Too many incorrect attempts, account is blocked",Toast.LENGTH_LONG).show();
						}
						else{
							Toast.makeText(this, "Incorrect login attempt",Toast.LENGTH_LONG).show();
						}
					}
					else{
						loginAttempts=0;
					}
				}
			}
		}
		
		// Sign Up Button is pressed
		if(signUpButton == v){
			Log.d(TAG, "Sign Up Button Pressed"); //debug
			Intent myIntent = new Intent(v.getContext(), RegisterActivity.class);
		    v.getContext().startActivity(myIntent);
		}
		
	}
    
}
