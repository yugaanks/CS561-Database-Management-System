/*
 * REPORT 2
 * for detailed information about the program, read the comment on top of the program of report 1;
 * 					
 * 										NAME- YUGAANK ARUN SHARMA
 * 										CWID- 10419077
 * 
 */

package edu.stevens.cs561;

import java.sql.*;
import java.util.ArrayList;

public class Assign2_2 {

	public ArrayList<ArrayList<String>> info;										// only one data structure which holds the result

	Assign2_2() {																	//main multi-dimensional array list, each arraylist member of "info" contains
		info = new ArrayList<ArrayList<String>>();									//the data about customer, product, month, Grouping them and calculating the 
	}																				//average for each group, number of items that were required for calculating the average  
																					//average of the month before and average of the month after the current month
	public boolean check(String a, String b, String c) {
		int exists = 0;
		for (int i = 0; i < info.size(); i++) {
			if (info.get(i).get(0).equals(a) && info.get(i).get(1).equals(b) && info.get(i).get(2).equals(c)) {
				exists = 1;
				break;
			}																		//function checks if the customer, product and month are already present in main
		}																			//array list or not	
		if (exists == 1) {
			return false;
		} else
			return true;
	}

	public void add(String a, String b, String c, String d) {						// Positions are static, for example: 0- customer, 1- product, etc.
		ArrayList<String> xd = new ArrayList<String>();								// temporary arraylist
		xd.add(a); 																	// 0. cust
		xd.add(b); 																	// 1. prod
		xd.add(c); 																	// 2. month
		xd.add(d); 																	// 3. quant
		xd.add(Integer.toString(1)); 												// 4. count=1, initialized to one, if data occurs agains, it is incremented in update function
		info.add(xd);																//	adding the arraylist into the main arraylist
	}

	public void update(String a, String b, String c, String d) {					//function updates the quant value if the customer, product and month matches
		int temp;
		for (ArrayList<String> e : info) {
			if (e.get(0).equals(a) && e.get(1).equals(b) && e.get(2).equals(c)) {
				temp = Integer.parseInt(e.get(3)) + Integer.parseInt(d);
				e.set(3, Integer.toString(temp)); 									//  updated quant sum -sum
				temp = Integer.parseInt(e.get(4)) + 1;
				e.set(4, Integer.toString(temp)); 									//  total no.- ++count
			}
		}
	}

	public void calculateAvg() {													//calculates the avg of each arraylist inside the main arraylist
		Float temp;
		for (ArrayList<String> e : info) {
			temp = Float.parseFloat(e.get(3)) / (Float.parseFloat(e.get(4)));
			e.add(Float.toString(temp)); 											// 5. calculated avg- sum/count
		}
	}

	public String ret_month_avg(String name, String prod, String month) {			//returns the average from the main arraylist 
		String temp = new String();													//if the product, customer and month matches any of the arraylist present inside
		for (ArrayList<String> e : info) {
			if (e.get(0).equals(name) && e.get(1).equals(prod) && e.get(2).equals(month)) {
				temp = e.get(5);
				break;
			} else
				temp = "<NULL>";
		}																			//returns null if not present else returns the average value
		return temp;
	}

	public void before() {															//function that adds the average value of the month before the current month 
		for (ArrayList<String> e : info) {											//to the arraylist
			String name = e.get(0);													
			String prod = e.get(1);
			String month = e.get(2);
			String before_month = Integer.toString(Integer.parseInt(month) - 1);
			if (Integer.parseInt(before_month) == 0) {
				e.add("<NULL>");
			} else {																//6. month before current month average, null if the value or month doesn't exist
				e.add(ret_month_avg(name, prod, before_month));
			}
		}
	}

	public void after() {															//function that adds the average value of the month after the current month 
		for (ArrayList<String> e : info) {											//to the arraylist
			String name = e.get(0);
			String prod = e.get(1);
			String month = e.get(2);
			String after_month = Integer.toString(Integer.parseInt(month) + 1);
			if (Integer.parseInt(after_month) == 13) {
				e.add("<NULL>");
			} else {																//7. month after current month average, null if the value or month doesn't exist
				e.add(ret_month_avg(name, prod, after_month));
			}
		}
	}
	
	public void reformat() {
		ArrayList<ArrayList<String>> infoTemp=new ArrayList<ArrayList<String>>();								//changed a bit to add 
		infoTemp.addAll(info);																				//all those records for months who
		for (ArrayList<String> e : infoTemp) {														//dont have a sale but their previous
			for(int i=1;i<=12;i++)																	//month and following month had sales
			{																					//even if they don't, it will just show NULL
				if(check(e.get(0),e.get(1),Integer.toString(i))) {
					int monthx=i-1;
					int monthy=i+1;
					new_add(e.get(0),e.get(1),Integer.toString(i),"<NULL>","0","<NULL>",monthx,monthy);
				}
			}
		}
	}
	
	public void new_add(String a, String b, String c, String d, String e, String f, int g, int h) {
		ArrayList<String> mo=new ArrayList<String>();
		mo.add(a);					//a=customer										//function to add records for reformat function
		mo.add(b);					//b=product
		mo.add(c);					//c=month
		mo.add(d);					//d=quant
		mo.add(e);					//e=count
		mo.add(f);					//f=average for that month, in this case it is null, since the sale for this month doesn't exists
		mo.add(ret_month_avg(mo.get(0), mo.get(1), Integer.toString(g)));			//g=previous month
		mo.add(ret_month_avg(mo.get(0), mo.get(1), Integer.toString(h)));			//h=following month
		info.add(mo);
	}

	public void formatDisplay(String a, String b, String c, String d, String e) {						
		System.out.printf("%-7s %-7s %15s %15s %15s %n", a, b, c, d, e);
	}
																					//this part of the program displays the result in orderly fashion
	public void displayResult() {
		System.out.printf("%-7s %-7s %13s %18s %15s %n", "CUSTOMER", "PRODUCT", "MONTH", "BEFORE_AVG", "AFTER_AVG");
		System.out.printf("%-7s %-7s %13s %18s %15s %n", "========", "=======", "=====", "==========", "=========");
		for (ArrayList<String> e : info) {
			formatDisplay(e.get(0), e.get(1), e.get(2), e.get(6), e.get(7));
		}
	}

	public static void main(String[] args) {
		Assign2_2 cvar = new Assign2_2();											// object of the class created- cvar= class variable
		String url = "jdbc:postgresql://localhost:5432/postgres"; 					// url
		String user = "postgres"; 													// user name
		String pass = "rocknroll"; 													// password
		String query = "SELECT * FROM Sales"; 										// SQL Query
		
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

			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) 														// rs.next() - read one row at a time
			{
				if (cvar.check(rs.getString("cust"), rs.getString("prod"), rs.getString("month"))) {						// check function
					cvar.add(rs.getString("cust"), rs.getString("prod"), rs.getString("month"), rs.getString("quant"));		// if not exists, added to main arraylist
				} else
					cvar.update(rs.getString("cust"), rs.getString("prod"), rs.getString("month"),							//if exists, updated the value
							rs.getString("quant"));
			}
			cvar.calculateAvg();																							//calculates the average- calls the function
			cvar.before();																									//adds the before month average to arraylist
			cvar.after();																									//adds the after month average to arraylist
			cvar.reformat();
			cvar.displayResult();																							//displays the result

		} catch (Exception e) {
			e.printStackTrace();																							//catches the exception
		}
	}
}
