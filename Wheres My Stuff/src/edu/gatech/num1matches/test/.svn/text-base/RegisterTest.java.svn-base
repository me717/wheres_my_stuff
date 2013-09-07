package edu.gatech.num1matches.test;

import edu.gatech.num1matches.model.*;
import edu.gatech.num1matches.view.*;
import edu.gatech.num1matches.presenter.*;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import edu.gatech.num1matches.*;

public class RegisterTest extends
		ActivityInstrumentationTestCase2<RegisterActivity> {

	EditText nameText;
	EditText emailText;
	EditText passText;
	EditText confirmText;
	EditText monthText;
	EditText dayText;
	EditText yearText;
	CheckBox agreeBox;
	Button regButton;

	public RegisterTest() {
		super("edu.gatech.num1matches", RegisterActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		RegisterActivity regAct = getActivity();
		setActivityInitialTouchMode(false);
		nameText = (EditText) regAct.findViewById(R.id.enterName);
		emailText = (EditText) regAct.findViewById(R.id.enterEmail);
		passText = (EditText) regAct.findViewById(R.id.enterPassword);
		confirmText = (EditText) regAct.findViewById(R.id.enterConfirmPassword);
		monthText = (EditText) regAct.findViewById(R.id.enterMonth);
		dayText = (EditText) regAct.findViewById(R.id.enterDay);
		yearText = (EditText) regAct.findViewById(R.id.enterYear);
		agreeBox = (CheckBox) regAct.findViewById(R.id.IAgreeRegistrationCheckBox);	
		regButton = (Button) regAct.findViewById(R.id.registerButton);
	}

	@MediumTest
	public void testCorrectRegistration(){
		String testName = "Correct";
		String testEmail = "testCorrect@matches.com";
		String testPass = "test";
		int month = 4;
		int day = 5;
		int year = 2001;
		
		nameText.setText(testName);
		emailText.setText(testEmail);
		passText.setText(testPass);
		confirmText.setText(testPass);
		monthText.setText(Integer.toString(month));
		dayText.setText(Integer.toString(day));
		yearText.setText(Integer.toString(year));
		agreeBox.setChecked(true);
		
		regButton.performClick();

		assertTrue(ExternalNetwork.getExternalNetwork(getActivity()).checkEmailAlreadyRegistered(testEmail));
	}
	
	@MediumTest
	public void testDifferentPasswords(){
		String testName = "DifferentPassword";
		String testEmail = "testDifferentPasswords@matches.com";
		String testPass = "test";
		String testConfirm = "notTest";
		int month = 4;
		int day = 5;
		int year = 2001;
		
		nameText.setText(testName);
		emailText.setText(testEmail);
		passText.setText(testPass);
		confirmText.setText(testConfirm);
		monthText.setText(Integer.toString(month));
		dayText.setText(Integer.toString(day));
		yearText.setText(Integer.toString(year));
		agreeBox.setChecked(true);
		
		regButton.performClick();

		assertTrue(!ExternalNetwork.getExternalNetwork(getActivity()).checkEmailAlreadyRegistered(testEmail));	
	}
	
	@MediumTest
	public void testInvalidDate(){
		String testName = "Invalid Date";
		String testEmail = "testInvalidDate@smatches.com";
		String testPass = "test";
		String testConfirm = "test";
		int month = 40;
		int day = 5;
		int year = 2001;
		
		nameText.setText(testName);
		emailText.setText(testEmail);
		passText.setText(testPass);
		confirmText.setText(testConfirm);
		monthText.setText(Integer.toString(month));
		dayText.setText(Integer.toString(day));
		yearText.setText(Integer.toString(year));
		agreeBox.setChecked(true);
		
		regButton.performClick();

		assertTrue(!ExternalNetwork.getExternalNetwork(getActivity()).checkEmailAlreadyRegistered(testEmail));	
	}
	
	@MediumTest
	public void testNotAgreed(){
			String testName = "Not Agreed";
			String testEmail = "testNotAgreed@matches.com";
			String testPass = "test";
			String testConfirm = "test";
			int month = 4;
			int day = 5;
			int year = 2001;
			
			nameText.setText(testName);
			emailText.setText(testEmail);
			passText.setText(testPass);
			confirmText.setText(testConfirm);
			monthText.setText(Integer.toString(month));
			dayText.setText(Integer.toString(day));
			yearText.setText(Integer.toString(year));
			agreeBox.setChecked(false);
			
			regButton.performClick();

			assertTrue(!ExternalNetwork.getExternalNetwork(getActivity()).checkEmailAlreadyRegistered(testEmail));	
		}

}
