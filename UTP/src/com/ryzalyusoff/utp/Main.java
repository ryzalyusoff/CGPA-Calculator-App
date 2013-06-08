package com.ryzalyusoff.utp;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity {

	private Button btnSubmit;
	
	// grades
	private Spinner grade1,grade2,grade3,grade4,grade5,grade6,grade7,grade8;
	private double vgrade1=0.0,vgrade2=0.0,vgrade3=0.0,vgrade4=0.0,vgrade5=0.0,vgrade6=0.0,vgrade7=0.0,vgrade8=0.0;

	// store Credit value of credit Hours
	private Spinner hour1,hour2,hour3,hour4,hour5,hour6,hour7,hour8;
	private double vhour1=0.0,vhour2=0.0,vhour3=0.0,vhour4=0.0,vhour5=0.0,vhour6=0.0,vhour7=0.0,vhour8=0.0;
		
	// stands for Grade Point (Pointer x Credit Hour)
	private double gpoint1=0.0,gpoint2=0.0,gpoint3=0.0,gpoint4=0.0,gpoint5=0.0,gpoint6=0.0,gpoint7=0.0,gpoint8=0.0;

	
	// store last semester CGPA , Total Credit Hours & Grade Points
	public EditText  lastCGPA,lastchour;
	private double lastgpoint = 0.0;
	
	
	private double currentpoint = 0.0;
	private double currentHour = 0.0; 
	
	// store final grades/points etc.
	private double finalgpoint = 0.0;
	private double finalGPA = 0.0;
	private double finalCGPA = 0.0;
	private double finalchour = 0.0;

	private static final int DIALOG_ALERT = 10;
	private String info;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
				
		addListenerOnButton();
		
	}

	public void addListenerOnButton() {
		
		btnSubmit = (Button) findViewById(R.id.calculate);

		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// retrieve all required Value
				retrieve();
				
				// calculate all values needed
				Calculate();	
							
				// make Pop up using Builder
				showDialog(DIALOG_ALERT);	
				
			}

		});
		
	}
	
	public void retrieve(){
		
		//retrieve Grades
		grade1 = (Spinner) findViewById(R.id.spinner1);
		grade2 = (Spinner) findViewById(R.id.spinner2);
		grade3 = (Spinner) findViewById(R.id.spinner3);
		grade4 = (Spinner) findViewById(R.id.spinner4);
		grade5 = (Spinner) findViewById(R.id.spinner5);
		grade6 = (Spinner) findViewById(R.id.spinner6);
		grade7 = (Spinner) findViewById(R.id.spinner7);
		grade8 = (Spinner) findViewById(R.id.spinner8);
		
		// retrieve Credit Hours
		hour1 = (Spinner) findViewById(R.id.chour1);
		hour2 = (Spinner) findViewById(R.id.chour2);
		hour3 = (Spinner) findViewById(R.id.chour3);
		hour4 = (Spinner) findViewById(R.id.chour4);
		hour5 = (Spinner) findViewById(R.id.chour5);
		hour6 = (Spinner) findViewById(R.id.chour6);
		hour7 = (Spinner) findViewById(R.id.chour7);
		hour8 = (Spinner) findViewById(R.id.chour8);

		
		// retrieve the selected value of the grade & convert it to equivalent pointer
		vgrade1 = convertGrade(String.valueOf(grade1.getSelectedItem()));
		vgrade2 = convertGrade(String.valueOf(grade2.getSelectedItem()));
		vgrade3 = convertGrade(String.valueOf(grade3.getSelectedItem()));
		vgrade4 = convertGrade(String.valueOf(grade4.getSelectedItem()));
		vgrade5 = convertGrade(String.valueOf(grade5.getSelectedItem()));
		vgrade6 = convertGrade(String.valueOf(grade6.getSelectedItem()));
		vgrade7 = convertGrade(String.valueOf(grade7.getSelectedItem()));
		vgrade8 = convertGrade(String.valueOf(grade8.getSelectedItem()));
		
		// retrieve the selected value of Credit hour & convert it to number(Double)
		vhour1 = convertDouble(String.valueOf(hour1.getSelectedItem()));
		vhour2 = convertDouble(String.valueOf(hour2.getSelectedItem()));
	    vhour3 = convertDouble(String.valueOf(hour3.getSelectedItem()));
		vhour4 = convertDouble(String.valueOf(hour4.getSelectedItem()));
		vhour5 = convertDouble(String.valueOf(hour5.getSelectedItem()));
		vhour6 = convertDouble(String.valueOf(hour6.getSelectedItem()));
		vhour7 = convertDouble(String.valueOf(hour7.getSelectedItem()));
		vhour8 = convertDouble(String.valueOf(hour8.getSelectedItem()));
		
	}
	
	public void Calculate(){
		
		// Calculate value of Grade Point for each subject
		gpoint1 = vgrade1*vhour1;
		gpoint2 = vgrade2*vhour2;
		gpoint3 = vgrade3*vhour3;
		gpoint4 = vgrade4*vhour4;
		gpoint5 = vgrade5*vhour5;
		gpoint6 = vgrade6*vhour6;
		gpoint7 = vgrade7*vhour7;
		gpoint8 = vgrade8*vhour8;
		
		
		// retrieve Last semester CGPA and Total Credit Hours
		lastCGPA = (EditText) findViewById(R.id.currentCGPA);
		lastchour = (EditText) findViewById(R.id.currentCredit);
			
		
		// To find Grade Points for last semester 
		// CGPA = lastgpoint/lastchour
		lastgpoint = convertDouble((lastCGPA.getText().toString())) * convertDouble((lastchour.getText().toString())) ;
		
		
		// To find Grade Points for current semester
		currentpoint =  gpoint1 + gpoint2 + gpoint3 + gpoint4 + gpoint5 + gpoint6 + gpoint7 + gpoint8;
		// To find Credit Hours for this semester
		currentHour = vhour1 + vhour2 + vhour3 + vhour4 + vhour5 + vhour6 + vhour7 + vhour8;
		

		// To find Accumulated Grade Points (Current Points + Previous Points)
		finalgpoint += currentpoint + lastgpoint;
		// To find Accumulated Credit Hours (Current cHours + Previous cHours)
		finalchour +=  currentHour + convertDouble((lastchour.getText().toString()));
		
		// To find final GPA
		// GPA = (Current Semester Grade Points) /  (Current Semester Credit hours)
		finalGPA = currentpoint /  currentHour;
		
		// To find final CGPA
		// CGPA = Total Accumulated Points / Total Accumulated Credit Hours
		finalCGPA= finalgpoint/finalchour;
		
	}
	
	
	// function for converting Grade to equivalent pointer 
	public double convertGrade(String grade){
		double cgrade = 0.0;
		
		if(grade.equals("A+")){
			cgrade = 4.0;		
		} else if(grade.equals("A-")){
			cgrade = 3.75; 
		} else if(grade.equals("B+")){
			cgrade = 3.5;
		} else if(grade.equals("B")) { 
			cgrade = 3.0;
		} else if(grade.equals("C+")){
			cgrade = 2.5;
		} else if(grade.equals("C")){
			cgrade = 2.0;	
		} else if(grade.equals("D+")){
			cgrade = 1.5;  	
		} else if(grade.equals("D")) {
			cgrade = 1.0;
		} else if(grade.equals("F")) {
			cgrade = 0.0;
		} else {
			cgrade = 0.0;
		}
		
		return cgrade;
	}
	
	
	// function for converting string to Double.
	// bcoz Double.parseDouble seems to crashing the system, thus need to make own function
	public double convertDouble(String hour){
		double chour = 0.0;
		
		try {
			 String eAm = hour;
	         DecimalFormat dF = new DecimalFormat("0.00");
	         Number num = dF.parse(eAm);
	         chour = num.doubleValue();
		} catch (Exception e) {
			 chour = 0.0;
		}
		
		return chour;
	}
	

	// Pop Up Starts Here
	// Handling all the Pop Up Dialog Elements
	@Override
	  protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case DIALOG_ALERT:
	      // Create out AlterDialog
	      Builder builder = new AlertDialog.Builder(this);
	      
	      	DecimalFormat df = new DecimalFormat("0.00");
			DecimalFormat nf = new DecimalFormat("0");
			
			info = "Total Credit Hours : " + nf.format(finalchour) 
					+ "\nTotal Grade Points : " + df.format(finalgpoint)
					+ "\nYour GPA  : " + df.format(finalGPA)
					+ "\nYour CGPA  : " + df.format(finalCGPA);

	      builder.setMessage(info);
	      builder.setCancelable(true);
	      builder.setPositiveButton("Exit", new exitListener());
	      builder.setNegativeButton("Go Back", new backListener());
	      AlertDialog dialog = builder.create();
	      dialog.show();
	    }
	    return super.onCreateDialog(id);
	  }

	  private final class backListener implements
	      DialogInterface.OnClickListener {
	    public void onClick(DialogInterface dialog, int which) {
	    	reset();
	      Toast.makeText(getApplicationContext(), "Calculate Another CGPA :)",
	          Toast.LENGTH_LONG).show();
	    }
	  }

	  private final class exitListener implements
	      DialogInterface.OnClickListener {
		  public void onClick(DialogInterface dialog, int which) {
			  Main.this.finish();
	    }
	  }
	  
	// Pop Up Stops Here
	  
	  
	  // function to reset all the value if the user choose to Go Back
	  private void reset(){
		vgrade1=0.0;vgrade2=0.0;vgrade3=0.0;vgrade4=0.0;vgrade5=0.0;vgrade6=0.0;vgrade7=0.0;vgrade8=0.0;

		vhour1=0.0;vhour2=0.0;vhour3=0.0;vhour4=0.0;vhour5=0.0;vhour6=0.0;vhour7=0.0;vhour8=0.0;
		
		gpoint1=0.0;gpoint2=0.0;gpoint3=0.0;gpoint4=0.0;gpoint5=0.0;gpoint6=0.0;gpoint7=0.0;gpoint8=0.0;

		lastgpoint = 0.0;
		
		currentpoint = 0.0;
		currentHour = 0.0; 

		finalgpoint = 0.0;
		finalGPA = 0.0;
		finalCGPA = 0.0;
		finalchour = 0.0;
	  }

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
