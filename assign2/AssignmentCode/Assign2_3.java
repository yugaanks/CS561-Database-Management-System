/*
 * REPORT 3
 * for detailed information about the program, read the comment on top of the program of report 1;
 * 					
 * 										NAME- YUGAANK ARUN SHARMA
 * 										CWID- 10419077
 * 
 */

package edu.stevens.cs561;

import java.sql.*;
import java.util.ArrayList;

public class Assign2_3 {

	public ArrayList<ArrayList<String>> info;								
																			// only one data structure which holds the result. In main multi-dimensional array list, each arraylist member of "info" contains
	Assign2_3() {															//the data about product, month, Grouping them and calculating the 																			//average for each group, number of items that were required for calculating the average  
		info = new ArrayList<ArrayList<String>>();							//average of the month, maximum of the month total no. of quantities that are between them for previous month and following month												
	}																			

	public boolean check(String product, String month) {											//function checks if the  product and month are already present in main
		int exists = 0;
		for (int i = 0; i < info.size(); i++) {
			if (info.get(i).get(0).equals(product) && info.get(i).get(1).equals(month)) {
				exists = 1;
				break;
			}									
		}
		if (exists == 1) {
			return true;															// returns true if exists otherwise false
		} else
			return false;
	}

	public void add(String product, String month, String quant) {						// adds the value of product, month and quant of that month
		ArrayList<String> xd = new ArrayList<String>();									//temporary arraylist
		xd.add(product); 																// 0. product
		xd.add(month); 																	// 1. month
		xd.add(quant); 																	// 2. quant
		xd.add(Integer.toString(1)); 													// 3.count
		xd.add(quant); 																	// 4. for max										
		info.add(xd); 																	// adding the arraylist into the main array list
	}

	public void update(String product, String month, String quant) {					// updates the arraylist "info" if the product, month is already present 
		int temp;
		int max;
		for (ArrayList<String> e : info) {
			if (e.get(0).equals(product) && e.get(1).equals(month)) {
				if (Integer.parseInt(e.get(4)) < Integer.parseInt(quant)) {
					max = Integer.parseInt(quant);										//also the info arraylist holds the maximum value in 4th position
				} else
					max = Integer.parseInt(e.get(4));
				temp = Integer.parseInt(e.get(2)) + Integer.parseInt(quant);			//by adding the quant to previous value 
				e.set(2, Integer.toString(temp));
				temp = Integer.parseInt(e.get(3)) + 1;
				e.set(3, Integer.toString(temp));										//increments the quant value by one everytime same product month appears
				e.set(4, Integer.toString(max));										//4. max, initially was set to the first quant value at line 34, later it updates
			}
		}
	}

	public void calculateAvg() {
		Float temp;																	//function calculates the average of each product, month, using the sum of 
		for (ArrayList<String> e : info) {											//quant values that is stored in 2nd position dividing it by the total no. of 
			temp = Float.parseFloat(e.get(2)) / (Float.parseFloat(e.get(3)));		//count variables, or same product, month appearances which is in 3rd position
			e.add(Float.toString(temp)); 											// 5. avg
			e.add("0"); 															// 6. before_tot initialization - initially the count is zero for both
			e.add("0"); 															// 7. after_tot initialization
		}
	}

	public void before_check(String product, int month, int quant) {
		for (ArrayList<String> e : info) {											// if the conditions are matched, the count values are incremented by 1 for before_tot
			if (e.get(1).equals("1")) {	
				e.set(6, "<NULL>");													//if the current month is january, the previous month is null		
			} else {
				if (e.get(1).equals(Integer.toString((month + 1))) && e.get(0).equals(product)) {			//if the product name matches and the month is previous month &
					if (quant > Float.parseFloat(e.get(5)) && quant < Integer.parseInt(e.get(4))) {			//quant for previous month in between current month avg and max
						e.set(6, Integer.toString(Integer.parseInt(e.get(6)) + 1));							//the value for count of before_tot is incremented by one
					}
				}
			}
			
		}
	}
	
	public void after_check(String product, int month, int quant) {
		for (ArrayList<String> e : info) {
			if (e.get(1).equals("12")) {															//if current month is december, the after_tot value is set to null
				e.set(7, "<NULL>");																			//same goes for after_check function as was in before_check
			} else {
				if (e.get(1).equals(Integer.toString((month - 1))) && e.get(0).equals(product)) {
					if (quant > Float.parseFloat(e.get(5)) && quant < Integer.parseInt(e.get(4))) {
						e.set(7, Integer.toString(Integer.parseInt(e.get(7)) + 1));
					}
				}
			}
		}
	}
	

	public void formatDisplay(String a, String b, String c, String d) {										//formating the display
		System.out.printf("%-7s %7s %15s %15s %n", a, b, c, d);
	}

	public void displayResult() {
		int count=0;
		System.out.printf("%-7s %7s %17s %16s %n", "PRODUCT", "MONTH", "BEFORE_TOT", "AFTER_TOT", "OTHER_CUST_AVG");
		System.out.printf("%-7s %7s %17s %16s %n", "=======", "=====", "==========", "=========", "==============");
		for (ArrayList<String> e : info) {
			if(e.get(6).equals("0")) {
				e.set(6, "<NULL>");
			}
			if(e.get(7).equals("0")) {																	//if the value doesn't exists or is unknown, we will represent it with <NULL>
				e.set(7, "<NULL>");
			}
			formatDisplay(e.get(0), e.get(1), e.get(6), e.get(7));														//displays the result on screen
			count++;																							
		}
		System.out.println(count);		
	}

	public static void main(String[] args) {	
		Assign2_3 cvar = new Assign2_3();												//class object that will contain the main arraylist, execute all functions
		String url = "jdbc:postgresql://localhost:5432/postgres"; 						// url
		String user = "postgres"; 														// user name
		String pass = "rocknroll"; 														// password
		String query = "SELECT * FROM Sales";											 // SQL Query

		/*															 /*
		 * Loading Driver-------------------------Creating Connection *
		/*															  */

		try {
			Class.forName("org.postgresql.Driver");
			System.out.println("Successfully loaded the driver!");
			Connection con = DriverManager.getConnection(url, user, pass);
			System.out.println("Connection Created!!");

			/*
			 * Statement created and a result set also created through which we
			 * will scan the table in the database
			 */

			Statement s = con.createStatement();									//for this report, we will have to scan the table 2 times, but using the same query
			
			ResultSet rs1 = s.executeQuery(query);														// 1st resultset
			while (rs1.next()) 																			// rs1.next() - each row at a time						
			{
				if (cvar.check(rs1.getString("prod"), rs1.getString("month"))) {											//step 1- check if value exist in arraylist info
					cvar.update(rs1.getString("prod"), rs1.getString("month"), rs1.getString("quant"));						//if yes, then update
				} else
					cvar.add(rs1.getString("prod"), rs1.getString("month"), rs1.getString("quant"));						//else, add
			}
			
			cvar.calculateAvg();																							//calls the average calculation function
			ResultSet rs2 = s.executeQuery(query);														//2nd resultset
			while (rs2.next()) {																		//scanning the table one more time
				cvar.before_check(rs2.getString("prod"), rs2.getInt("month"), rs2.getInt("quant"));		// for comparing the quants or sales for the previous month solution
				cvar.after_check(rs2.getString("prod"), rs2.getInt("month"), rs2.getInt("quant"));		// for comparing the quants or sales for the following month solution
			}

			cvar.displayResult();																		// display the result

		} catch (Exception e) {
			e.printStackTrace();																		//catch the exception
		}
	}
}
